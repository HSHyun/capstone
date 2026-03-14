import json
import random
from datetime import datetime, timezone
from hashlib import sha256
from pathlib import Path
from uuid import uuid4

from fastapi import APIRouter, Depends, File, HTTPException, UploadFile, status
from sqlalchemy import select
from sqlalchemy.orm import Session

from app.db import get_db
from app.models import AnalysisResult, AnalysisRun, Document, OcrResult, User
from app.schemas import DocumentOcrMockResponse, DocumentParseMockResponse, DocumentUploadResponse

router = APIRouter(prefix="/documents", tags=["documents"])

BASE_DIR = Path(__file__).resolve().parents[2]
UPLOAD_DIR = BASE_DIR / "uploads"
ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"}
ALLOWED_MIME_TYPES = {"image/jpeg", "image/png", "image/webp"}
MAX_FILE_SIZE = 10 * 1024 * 1024
TEST_JSON_PATH = BASE_DIR / "test.json"
_cached_mock_questions: list[dict] | None = None


def _load_mock_questions() -> list[dict]:
    global _cached_mock_questions
    if _cached_mock_questions is not None:
        return _cached_mock_questions
    try:
        raw = TEST_JSON_PATH.read_text(encoding="utf-8")
        parsed = json.loads(raw)
    except (OSError, json.JSONDecodeError) as exc:
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="failed to load mock data") from exc
    if not isinstance(parsed, list) or not parsed:
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="mock data is empty")
    _cached_mock_questions = parsed
    return parsed


@router.post("/upload", response_model=DocumentUploadResponse, status_code=status.HTTP_201_CREATED)
async def upload_document(
    image: UploadFile = File(...),
    db: Session = Depends(get_db),
) -> DocumentUploadResponse:
    suffix = Path(image.filename or "").suffix.lower()
    if suffix not in ALLOWED_EXTENSIONS:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="unsupported file extension")

    mime_type = image.content_type or ""
    if mime_type not in ALLOWED_MIME_TYPES:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="unsupported content type")

    data = await image.read()
    if not data:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="empty file")
    if len(data) > MAX_FILE_SIZE:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="file too large")

    UPLOAD_DIR.mkdir(parents=True, exist_ok=True)
    file_name = f"{uuid4().hex}{suffix}"
    save_path = UPLOAD_DIR / file_name
    save_path.write_bytes(data)

    # 프로토타입용 기본 사용자 보정
    user = db.scalar(select(User).where(User.id == 1))
    if user is None:
        user = User(
            id=1,
            username="tester",
            email="test@local.dev",
            password_hash="prototype",
        )
        db.add(user)
        db.flush()

    storage_key = f"/uploads/{file_name}"
    document = Document(
        user_id=1,
        title=image.filename or file_name,
        storage_key=storage_key,
        mime_type=mime_type,
        file_size=len(data),  # type: ignore[arg-type]
        checksum=sha256(data).hexdigest(),
        status="uploaded",
    )
    db.add(document)
    db.commit()
    db.refresh(document)

    return DocumentUploadResponse(
        document_id=document.id,
        image_url=storage_key,
    )


@router.post("/{document_id}/ocr", response_model=DocumentOcrMockResponse)
def run_ocr_mock(
    document_id: int,
    db: Session = Depends(get_db),
) -> DocumentOcrMockResponse:
    document = db.scalar(select(Document).where(Document.id == document_id))
    if document is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="document not found")

    mock_questions = _load_mock_questions()
    mock_item = random.choice(mock_questions)
    full_content = str(mock_item.get("content", {}).get("passage", ""))

    current_latest = db.scalars(
        select(OcrResult).where(
            OcrResult.document_id == document_id,
            OcrResult.is_latest.is_(True),
        )
    ).all()
    for row in current_latest:
        row.is_latest = False

    previous_attempt = db.scalar(
        select(OcrResult.attempt_no)
        .where(OcrResult.document_id == document_id)
        .order_by(OcrResult.attempt_no.desc())
        .limit(1)
    )
    attempt_no = (previous_attempt or 0) + 1
    now = datetime.now(timezone.utc)
    ocr_result = OcrResult(
        document_id=document_id,
        ocr_data=mock_item,
        full_content=full_content,
        engine="mock-json",
        status="succeeded",
        attempt_no=attempt_no,
        is_latest=True,
        finished_at=now,
    )
    db.add(ocr_result)
    document.status = "ocr_succeeded"
    db.commit()

    return DocumentOcrMockResponse(
        document_id=document_id,
        status="completed",
        mock_item=mock_item,
    )


@router.post("/{document_id}/parse", response_model=DocumentParseMockResponse)
def run_parse_mock(
    document_id: int,
    db: Session = Depends(get_db),
) -> DocumentParseMockResponse:
    document = db.scalar(select(Document).where(Document.id == document_id))
    if document is None:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="document not found")

    ocr_result = db.scalar(
        select(OcrResult)
        .where(OcrResult.document_id == document_id, OcrResult.is_latest.is_(True))
        .order_by(OcrResult.id.desc())
    )
    if ocr_result is None:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="ocr result not found")

    full_text = (ocr_result.full_content or "").strip()
    lines = [line.strip() for line in full_text.split("\n") if line.strip()]
    summary = lines[0] if lines else "요약 없음"

    now = datetime.now(timezone.utc)
    run = AnalysisRun(
        document_id=document_id,
        ocr_result_id=ocr_result.id,
        status="succeeded",
        model_name="mock-parser",
        model_version="0.1",
        prompt_version="mock-v1",
        started_at=now,
        finished_at=now,
    )
    db.add(run)
    db.flush()

    result_json = {
        "summary": summary,
        "sentence_count": len(lines),
        "sentences": [
            {
                "index": idx + 1,
                "text": text,
                "parse": f"(여기에 구문분석 결과 {idx + 1})",
            }
            for idx, text in enumerate(lines[:8])
        ],
    }
    analysis_result = AnalysisResult(
        analysis_run_id=run.id,
        result_json=result_json,
        summary_text=summary,
    )
    db.add(analysis_result)
    db.commit()

    return DocumentParseMockResponse(
        document_id=document_id,
        analysis_run_id=run.id,
        status="completed",
        result_json=result_json,
    )
