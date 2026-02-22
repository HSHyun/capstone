from fastapi import FastAPI

from app.api import auth, health, posts
from app.db import Base, engine

app = FastAPI(title="Capstone API", version="0.1.0")


@app.on_event("startup")
def on_startup() -> None:
    Base.metadata.create_all(bind=engine)


# 라우터 등록
app.include_router(health.router)
app.include_router(auth.router)
app.include_router(posts.router)
