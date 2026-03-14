package com.example.cpstone

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import java.io.ByteArrayOutputStream
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cpstone.ui.theme.AccentSky
import com.example.cpstone.ui.theme.AppBackground
import com.example.cpstone.ui.theme.BorderSoft
import com.example.cpstone.ui.theme.BrandBlue
import com.example.cpstone.ui.theme.BrandBlueDark
import com.example.cpstone.ui.theme.CpstoneTheme
import com.example.cpstone.ui.theme.SurfaceCard
import com.example.cpstone.ui.theme.SurfaceSoft
import com.example.cpstone.ui.theme.TextPrimary
import com.example.cpstone.ui.theme.TextSecondary
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Path
import retrofit2.http.Part
import retrofit2.http.POST
import retrofit2.http.Query

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CpstoneTheme {
                MainScreen()
            }
        }
    }
}

private data class TaskItem(
    val title: String,
    val remain: String,
    val difficulty: String,
    val action: String,
    val icon: ImageVector
)
private data class StudyItem(val title: String, val metric: String, val score: String)
private data class SignupRequestDto(val username: String, val email: String, val password: String)
private data class LoginRequestDto(val email: String, val password: String)
private data class SignupUserDto(val id: Long, val username: String, val email: String)
private data class SignupResponseDto(val message: String, val user: SignupUserDto)
private data class LoginResponseDto(val message: String, val user: SignupUserDto)
private data class CommunityPostListDto(
    val id: Long,
    val category: String,
    val title: String,
    val summary: String,
    @SerializedName("hours_ago") val hoursAgo: Int,
    @SerializedName("time_ago") val timeAgo: String,
    val views: Int,
    val likes: Int,
    val comments: Int,
    val author: String,
    @SerializedName("authored_at") val authoredAt: String
)
private data class CommunityPostDetailDto(
    val id: Long,
    val category: String,
    val title: String,
    val summary: String,
    val body: String,
    @SerializedName("hours_ago") val hoursAgo: Int,
    @SerializedName("time_ago") val timeAgo: String,
    val views: Int,
    val likes: Int,
    val comments: Int,
    val author: String,
    @SerializedName("authored_at") val authoredAt: String,
    @SerializedName("is_liked") val isLiked: Boolean
)
private data class LikeToggleRequestDto(@SerializedName("user_id") val userId: Long)
private data class LikeToggleResponseDto(
    @SerializedName("post_id") val postId: Long,
    val liked: Boolean,
    @SerializedName("like_count") val likeCount: Int
)
private data class CommentDto(
    val id: Long,
    @SerializedName("post_id") val postId: Long,
    @SerializedName("author_id") val authorId: Long,
    val author: String,
    val content: String,
    @SerializedName("hours_ago") val hoursAgo: Int,
    @SerializedName("time_ago") val timeAgo: String,
    @SerializedName("authored_at") val authoredAt: String
)
private data class CommentCreateRequestDto(
    @SerializedName("author_id") val authorId: Long,
    val content: String
)
private data class PostCreateRequestDto(
    @SerializedName("author_id") val authorId: Long,
    val category: String,
    val title: String,
    val content: String
)
private data class DocumentUploadResponseDto(
    @SerializedName("document_id") val documentId: Long,
    @SerializedName("image_url") val imageUrl: String
)
private data class OcrMockCategoryDto(
    val code: String,
    val name: String
)
private data class OcrMockContentDto(
    val instruction: String,
    val passage: String
)
private data class OcrMockItemDto(
    val code: String,
    val number: Int,
    val category: OcrMockCategoryDto,
    val content: OcrMockContentDto
)
private data class DocumentOcrMockResponseDto(
    @SerializedName("document_id") val documentId: Long,
    val status: String,
    @SerializedName("mock_item") val mockItem: OcrMockItemDto
)
private data class ParseSentenceDto(
    val index: Int,
    val text: String,
    val parse: String
)
private data class ParseResultJsonDto(
    val summary: String,
    @SerializedName("sentence_count") val sentenceCount: Int,
    val sentences: List<ParseSentenceDto>
)
private data class DocumentParseMockResponseDto(
    @SerializedName("document_id") val documentId: Long,
    @SerializedName("analysis_run_id") val analysisRunId: Long,
    val status: String,
    @SerializedName("result_json") val resultJson: ParseResultJsonDto
)
private data class UploadAnalysisData(
    val ocr: DocumentOcrMockResponseDto,
    val parse: DocumentParseMockResponseDto?
)

private interface AuthApi {
    @POST("auth/signup")
    suspend fun signup(@Body payload: SignupRequestDto): SignupResponseDto

    @POST("auth/login")
    suspend fun login(@Body payload: LoginRequestDto): LoginResponseDto
}

private interface CommunityApi {
    @GET("posts")
    suspend fun getPosts(@Query("category") category: String? = null): List<CommunityPostListDto>

    @POST("posts")
    suspend fun createPost(@Body payload: PostCreateRequestDto): CommunityPostDetailDto

    @GET("posts/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Long,
        @Query("user_id") userId: Long
    ): CommunityPostDetailDto

    @GET("posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId: Long): List<CommentDto>

    @POST("posts/{postId}/comments")
    suspend fun createComment(
        @Path("postId") postId: Long,
        @Body payload: CommentCreateRequestDto
    ): CommentDto

    @POST("posts/{postId}/like")
    suspend fun toggleLike(
        @Path("postId") postId: Long,
        @Body payload: LikeToggleRequestDto
    ): LikeToggleResponseDto
}

private interface UploadApi {
    @Multipart
    @POST("documents/upload")
    suspend fun uploadDocument(@Part image: MultipartBody.Part): Response<DocumentUploadResponseDto>

    @POST("documents/{documentId}/ocr")
    suspend fun runOcrMock(@Path("documentId") documentId: Long): Response<DocumentOcrMockResponseDto>

    @POST("documents/{documentId}/parse")
    suspend fun runParseMock(@Path("documentId") documentId: Long): Response<DocumentParseMockResponseDto>
}

private object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val communityApi: CommunityApi = retrofit.create(CommunityApi::class.java)
    val uploadApi: UploadApi = retrofit.create(UploadApi::class.java)
}

private data class CommunityPostListItem(
    val id: Long,
    val category: String,
    val title: String,
    val summary: String,
    val hoursAgo: Int,
    val timeAgo: String,
    val views: Int,
    val likes: Int,
    val comments: Int,
    val author: String = "리딩고수12",
    val authoredAt: String = "2026.2.20.12:16"
)
private data class CommunityPostDetail(
    val id: Long,
    val category: String,
    val title: String,
    val summary: String,
    val body: String,
    val hoursAgo: Int,
    val timeAgo: String,
    val views: Int,
    val likes: Int,
    val comments: Int,
    val author: String = "리딩고수12",
    val authoredAt: String = "2026.2.20.12:16",
    val isLiked: Boolean = false
)
private data class CommunityComment(
    val id: Long,
    val postId: Long,
    val authorId: Long,
    val author: String,
    val content: String,
    val hoursAgo: Int,
    val timeAgo: String,
    val authoredAt: String
)

private fun CommunityPostListDto.toUiModel(): CommunityPostListItem = CommunityPostListItem(
    id = id,
    category = category,
    title = title,
    summary = summary,
    hoursAgo = hoursAgo,
    timeAgo = timeAgo,
    views = views,
    likes = likes,
    comments = comments,
    author = author,
    authoredAt = authoredAt
)

private fun CommunityPostDetailDto.toUiModel(): CommunityPostDetail = CommunityPostDetail(
    id = id,
    category = category,
    title = title,
    summary = summary,
    body = body,
    hoursAgo = hoursAgo,
    timeAgo = timeAgo,
    views = views,
    likes = likes,
    comments = comments,
    author = author,
    authoredAt = authoredAt,
    isLiked = isLiked
)

private fun CommentDto.toUiModel(): CommunityComment = CommunityComment(
    id = id,
    postId = postId,
    authorId = authorId,
    author = author,
    content = content,
    hoursAgo = hoursAgo,
    timeAgo = timeAgo,
    authoredAt = authoredAt
)

private fun Modifier.softCardShadow(radius: RoundedCornerShape): Modifier =
    this.shadow(
        elevation = 10.dp,
        shape = radius,
        ambientColor = Color.Black.copy(alpha = 0.08f),
        spotColor = Color.Black.copy(alpha = 0.10f),
        clip = false
    )

private fun Modifier.softInnerShadow(radius: RoundedCornerShape): Modifier =
    this.shadow(
        elevation = 6.dp,
        shape = radius,
        ambientColor = Color.Black.copy(alpha = 0.05f),
        spotColor = Color.Black.copy(alpha = 0.06f),
        clip = false
    )

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen() {
    val navItems = listOf("홈", "문제", "업로드", "커뮤니티", "내정보")
    var selectedNav by remember { mutableStateOf("홈") }
    var selectedCommunityPostId by remember { mutableStateOf<Long?>(null) }
    var selectedCommunityCategory by remember { mutableStateOf("문제풀이") }
    var communityWriteMode by remember { mutableStateOf(false) }
    var communityRefreshKey by remember { mutableStateOf(0) }
    var uploadAnalysisResult by remember { mutableStateOf<UploadAnalysisData?>(null) }
    var currentUser by remember {
        mutableStateOf<SignupUserDto?>(
            if (BuildConfig.DEBUG) SignupUserDto(1L, "tester", "test@local") else null
        )
    }
    val resetNavToRoot: (String) -> Unit = { target ->
        when (target) {
            "커뮤니티" -> {
                selectedCommunityPostId = null
                communityWriteMode = false
                selectedCommunityCategory = "문제풀이"
            }
            "업로드" -> {
                selectedCommunityPostId = null
                communityWriteMode = false
                uploadAnalysisResult = null
            }
            else -> {
                selectedCommunityPostId = null
                communityWriteMode = false
            }
        }
    }

    if (currentUser == null) {
        AuthContent(
            modifier = Modifier.fillMaxSize(),
            onLoginSuccess = { user -> currentUser = user }
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppBackground,
        topBar = { HeaderBar() },
        floatingActionButton = {
            if (selectedNav == "홈") {
                FloatingActionButton(
                    onClick = { selectedNav = "업로드" },
                    containerColor = Color(0xFF2F2F31),
                    contentColor = Color.White,
                    modifier = Modifier
                        .padding(end = 34.dp, bottom = 8.dp)
                        .size(62.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PhotoCamera,
                        contentDescription = "카메라 업로드",
                        modifier = Modifier.size(26.dp)
                    )
                }
            } else if (selectedNav == "커뮤니티" && selectedCommunityPostId == null && !communityWriteMode) {
                FloatingActionButton(
                    onClick = { communityWriteMode = true },
                    containerColor = Color(0xFF2F2F31),
                    contentColor = Color.White,
                    modifier = Modifier
                        .padding(end = 34.dp, bottom = 8.dp)
                        .size(62.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "글쓰기",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                NavigationBar(
                    containerColor = SurfaceCard,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(64.dp)
                ) {
                    navItems.forEach { item ->
                        val icon = when (item) {
                            "홈" -> Icons.Rounded.Home
                            "문제" -> Icons.AutoMirrored.Rounded.List
                            "업로드" -> Icons.Rounded.Add
                            "커뮤니티" -> Icons.Rounded.Info
                            else -> Icons.Rounded.Face
                        }
                        NavigationBarItem(
                            selected = selectedNav == item,
                            onClick = {
                                resetNavToRoot(item)
                                selectedNav = item
                            },
                            icon = {
                                Icon(
                                    icon,
                                    contentDescription = item,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .offset(y = 2.dp)
                                )
                            },
                            label = {
                                Text(
                                    item,
                                    fontSize = 13.sp,
                                    modifier = Modifier.offset(y = (-3).dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF111111),
                                selectedTextColor = Color(0xFF111111),
                                unselectedIconColor = Color(0xFF9A9DA7),
                                unselectedTextColor = Color(0xFF9A9DA7),
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        when (selectedNav) {
            "홈" -> HomeContent(modifier = Modifier.padding(innerPadding))
            "업로드" -> {
                val result = uploadAnalysisResult
                if (result == null) {
                    UploadContent(
                        modifier = Modifier.padding(innerPadding),
                        onAnalysisReady = { uploadAnalysisResult = it }
                    )
                } else {
                    UploadAnalysisResultContent(
                        result = result,
                        modifier = Modifier.padding(innerPadding),
                        onBack = { uploadAnalysisResult = null }
                    )
                }
            }
            "커뮤니티" -> {
                if (communityWriteMode) {
                    CommunityWriteContent(
                        modifier = Modifier.padding(innerPadding),
                        currentUserId = currentUser!!.id,
                        onBack = { communityWriteMode = false },
                        onCreated = { category ->
                            selectedCommunityCategory = category
                            communityWriteMode = false
                            communityRefreshKey += 1
                        }
                    )
                } else if (selectedCommunityPostId == null) {
                    CommunityContent(
                        modifier = Modifier.padding(innerPadding),
                        selectedCategory = selectedCommunityCategory,
                        refreshKey = communityRefreshKey,
                        onCategoryChange = { selectedCommunityCategory = it },
                        onPostClick = { selectedCommunityPostId = it }
                    )
                } else {
                    CommunityDetailContent(
                        postId = selectedCommunityPostId!!,
                        currentUserId = currentUser!!.id,
                        modifier = Modifier.padding(innerPadding),
                        onBack = {
                            selectedCommunityPostId = null
                            communityRefreshKey += 1
                        }
                    )
                }
            }
            "내정보" -> MyInfoContent(
                user = currentUser!!,
                modifier = Modifier.padding(innerPadding),
                onLogout = {
                    currentUser = null
                    selectedNav = "홈"
                    communityWriteMode = false
                    selectedCommunityPostId = null
                }
            )
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${selectedNav} 화면 준비중", color = TextSecondary)
                }
            }
        }
    }
}

private fun decodeBitmapFromUri(context: Context, uri: Uri): Bitmap? = runCatching {
    context.contentResolver.openInputStream(uri)?.use(BitmapFactory::decodeStream)
}.getOrNull()

private fun bitmapToUploadPart(bitmap: Bitmap): MultipartBody.Part {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 92, stream)
    val bytes = stream.toByteArray()
    val body = RequestBody.create(MediaType.parse("image/jpeg"), bytes)
    return MultipartBody.Part.createFormData("image", "capture.jpg", body)
}

private fun uriToUploadPart(context: Context, uri: Uri): MultipartBody.Part? = runCatching {
    val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: return null
    val mimeType = context.contentResolver.getType(uri) ?: "image/*"
    val fileName = "upload_${System.currentTimeMillis()}.jpg"
    val body = RequestBody.create(MediaType.parse(mimeType), bytes)
    MultipartBody.Part.createFormData("image", fileName, body)
}.getOrNull()

@Composable
private fun UploadContent(
    modifier: Modifier = Modifier,
    onAnalysisReady: (UploadAnalysisData) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var uploadMessage by remember { mutableStateOf<String?>(null) }
    var uploading by remember { mutableStateOf(false) }
    val canAnalyze = (selectedBitmap != null || selectedUri != null) && !uploading
    val cardRadius = RoundedCornerShape(18.dp)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            selectedBitmap = bitmap
            selectedUri = null
            uploadMessage = null
        }
    }

    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            selectedUri = uri
            selectedBitmap = null
            uploadMessage = null
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .width(960.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .padding(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .softCardShadow(cardRadius),
                shape = cardRadius,
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("지문 업로드", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { cameraLauncher.launch(null) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
                        ) {
                            Text("사진 촬영", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = { fileLauncher.launch(arrayOf("image/*")) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
                        ) {
                            Text("파일 선택", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .softCardShadow(cardRadius),
                shape = cardRadius,
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        selectedBitmap != null -> {
                            Image(
                                bitmap = selectedBitmap!!.asImageBitmap(),
                                contentDescription = "촬영한 이미지",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        selectedUri != null -> {
                            val uriBitmap = remember(selectedUri) { decodeBitmapFromUri(context, selectedUri!!) }
                            if (uriBitmap != null) {
                                Image(
                                    bitmap = uriBitmap.asImageBitmap(),
                                    contentDescription = "선택한 이미지",
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Text("이미지를 불러오지 못했습니다.", color = TextSecondary, fontSize = 13.sp)
                            }
                        }
                        else -> {
                            Text("선택된 이미지가 없습니다.", color = TextSecondary, fontSize = 13.sp)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (uploading) return@Button
                    val part = when {
                        selectedBitmap != null -> bitmapToUploadPart(selectedBitmap!!)
                        selectedUri != null -> uriToUploadPart(context, selectedUri!!)
                        else -> null
                    }
                    if (part == null) {
                        uploadMessage = "이미지를 먼저 선택해 주세요."
                        return@Button
                    }
                    scope.launch {
                        uploading = true
                        uploadMessage = null
                        runCatching {
                            ApiClient.uploadApi.uploadDocument(part)
                        }.onSuccess { uploadResponse ->
                            if (!uploadResponse.isSuccessful) {
                                uploadMessage = "저장 실패 (${uploadResponse.code()})"
                            } else {
                                val documentId = uploadResponse.body()?.documentId
                                if (documentId == null) {
                                    uploadMessage = "저장 성공, OCR 요청 실패 (document_id 없음)"
                                } else {
                                    runCatching {
                                        ApiClient.uploadApi.runOcrMock(documentId)
                                    }.onSuccess { ocrResponse ->
                                        uploadMessage = if (ocrResponse.isSuccessful) {
                                            val ocrBody = ocrResponse.body()
                                            if (ocrBody != null) {
                                                val item = ocrBody.mockItem
                                                runCatching {
                                                    ApiClient.uploadApi.runParseMock(documentId)
                                                }.onSuccess { parseResponse ->
                                                    val parseBody = if (parseResponse.isSuccessful) parseResponse.body() else null
                                                    onAnalysisReady(
                                                        UploadAnalysisData(
                                                            ocr = ocrBody,
                                                            parse = parseBody
                                                        )
                                                    )
                                                    uploadMessage = if (parseBody != null) {
                                                        "저장+OCR+구문분석 완료: ${item.code} / ${item.category.name}"
                                                    } else {
                                                        "저장+OCR 완료, 구문분석 실패 (${parseResponse.code()})"
                                                    }
                                                }.onFailure {
                                                    onAnalysisReady(
                                                        UploadAnalysisData(
                                                            ocr = ocrBody,
                                                            parse = null
                                                        )
                                                    )
                                                    uploadMessage = "저장+OCR 완료, 구문분석 호출 실패"
                                                }
                                                "저장+OCR 완료: ${item.code} / ${item.category.name}"
                                            } else {
                                                "저장 완료, OCR 응답 파싱 실패"
                                            }
                                        } else {
                                            "저장 완료, OCR 실패 (${ocrResponse.code()})"
                                        }
                                    }.onFailure {
                                        uploadMessage = "저장 완료, OCR 호출 실패"
                                    }
                                }
                            }
                        }.onFailure {
                            uploadMessage = "저장 실패"
                        }
                        uploading = false
                    }
                },
                enabled = canAnalyze,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
            ) {
                Text("분석하기", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            uploadMessage?.let { msg ->
                Text(msg, color = TextSecondary, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

@Composable
private fun UploadAnalysisResultContent(
    result: UploadAnalysisData,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val isWide = LocalConfiguration.current.screenWidthDp >= 700
    val cardRadius = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .width(960.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .padding(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .softCardShadow(cardRadius),
                shape = cardRadius,
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "뒤로가기",
                            tint = TextPrimary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text("분석 결과", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (isWide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .softCardShadow(cardRadius),
                        shape = cardRadius,
                        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("OCR 결과", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(
                                "${result.ocr.mockItem.code} · ${result.ocr.mockItem.category.name}",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                            Text(result.ocr.mockItem.content.instruction, color = TextPrimary, fontSize = 13.sp)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(320.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    result.ocr.mockItem.content.passage,
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .softCardShadow(cardRadius),
                        shape = cardRadius,
                        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("구문분석", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            val parse = result.parse
                            if (parse == null) {
                                Text("구문분석 결과를 불러오지 못했습니다.", color = TextSecondary, fontSize = 14.sp)
                            } else {
                                Text(parse.resultJson.summary, color = TextPrimary, fontSize = 13.sp)
                                Text("문장 수 ${parse.resultJson.sentenceCount}", color = TextSecondary, fontSize = 12.sp)
                                HorizontalDivider(color = BorderSoft)
                                parse.resultJson.sentences.take(4).forEach { sentence ->
                                    Text(
                                        "${sentence.index}. ${sentence.text}",
                                        color = TextSecondary,
                                        fontSize = 12.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(sentence.parse, color = TextPrimary, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .softCardShadow(cardRadius),
                    shape = cardRadius,
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("OCR 결과", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            "${result.ocr.mockItem.code} · ${result.ocr.mockItem.category.name}",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                result.ocr.mockItem.content.passage,
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        HorizontalDivider(color = BorderSoft)
                        val parse = result.parse
                        if (parse == null) {
                            Text("구문분석 결과를 불러오지 못했습니다.", color = TextSecondary, fontSize = 14.sp)
                        } else {
                            Text(parse.resultJson.summary, color = TextPrimary, fontSize = 13.sp)
                            Text("문장 수 ${parse.resultJson.sentenceCount}", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AuthContent(
    modifier: Modifier = Modifier,
    onLoginSuccess: (SignupUserDto) -> Unit
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    var signupMode by remember { mutableStateOf(false) }
    var loginEmail by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }
    var showLoginPassword by remember { mutableStateOf(false) }
    var signupUsername by remember { mutableStateOf("") }
    var signupEmail by remember { mutableStateOf("") }
    var signupPassword by remember { mutableStateOf("") }
    var signupPasswordConfirm by remember { mutableStateOf("") }
    var showSignupPassword by remember { mutableStateOf(false) }
    var showSignupPasswordConfirm by remember { mutableStateOf(false) }
    var signupLoading by remember { mutableStateOf(false) }
    var signupMessage by remember { mutableStateOf<String?>(null) }
    var loginLoading by remember { mutableStateOf(false) }
    var loginMessage by remember { mutableStateOf<String?>(null) }
    var loginError by remember { mutableStateOf(false) }
    val attemptLogin: () -> Unit = {
        when {
            loginEmail.isBlank() || loginPassword.isBlank() -> {
                loginMessage = "이메일/비밀번호를 입력해 주세요."
                loginError = true
            }
            else -> {
                scope.launch {
                    loginLoading = true
                    loginMessage = null
                    loginError = false
                    runCatching {
                        ApiClient.authApi.login(
                            LoginRequestDto(
                                email = loginEmail.trim(),
                                password = loginPassword
                            )
                        )
                    }.onSuccess {
                        loginMessage = "로그인 완료: ${it.user.username}"
                        loginError = false
                        onLoginSuccess(it.user)
                    }.onFailure {
                        loginMessage = "아이디 혹은 비밀번호가 틀렸습니다."
                        loginError = true
                    }
                    loginLoading = false
                }
            }
        }
    }

    val cardRadius = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(560.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .softCardShadow(cardRadius),
                    shape = cardRadius,
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (signupMode) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "로그인으로 돌아가기",
                                tint = TextSecondary,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable {
                                        signupMode = false
                                        signupMessage = null
                                        loginMessage = null
                                    }
                            )
                            Text(
                                "회원가입",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        }
                    } else {
                        Text(
                            "로그인",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                    if (signupMode) {
                        OutlinedTextField(
                            value = signupUsername,
                            onValueChange = { signupUsername = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("아이디") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = signupEmail,
                            onValueChange = { signupEmail = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("이메일") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = signupPassword,
                            onValueChange = { signupPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("비밀번호") },
                            singleLine = true,
                            visualTransformation = if (showSignupPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showSignupPassword = !showSignupPassword }) {
                                    Icon(
                                        imageVector = if (showSignupPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                        contentDescription = if (showSignupPassword) "비밀번호 숨기기" else "비밀번호 보기",
                                        tint = TextSecondary
                                    )
                                }
                            }
                        )
                        OutlinedTextField(
                            value = signupPasswordConfirm,
                            onValueChange = { signupPasswordConfirm = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("비밀번호 확인") },
                            singleLine = true,
                            visualTransformation = if (showSignupPasswordConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showSignupPasswordConfirm = !showSignupPasswordConfirm }) {
                                    Icon(
                                        imageVector = if (showSignupPasswordConfirm) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                        contentDescription = if (showSignupPasswordConfirm) "비밀번호 숨기기" else "비밀번호 보기",
                                        tint = TextSecondary
                                    )
                                }
                            }
                        )
                        Button(
                            onClick = {
                                when {
                                    signupUsername.isBlank() || signupEmail.isBlank() || signupPassword.isBlank() || signupPasswordConfirm.isBlank() -> {
                                        signupMessage = "모든 항목을 입력해 주세요."
                                    }
                                    signupPassword != signupPasswordConfirm -> {
                                        signupMessage = "비밀번호가 일치하지 않습니다."
                                    }
                                    else -> {
                                        scope.launch {
                                            signupLoading = true
                                            signupMessage = null
                                            runCatching {
                                                ApiClient.authApi.signup(
                                                    SignupRequestDto(
                                                        username = signupUsername.trim(),
                                                        email = signupEmail.trim(),
                                                        password = signupPassword
                                                    )
                                                )
                                            }.onSuccess {
                                                signupMessage = "회원가입 완료: ${it.user.username}"
                                                signupMode = false
                                                signupUsername = ""
                                                signupEmail = ""
                                                signupPassword = ""
                                                signupPasswordConfirm = ""
                                            }.onFailure {
                                                signupMessage = "회원가입 실패"
                                            }
                                            signupLoading = false
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
                        ) {
                            Text("회원가입", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        signupMessage?.let { msg ->
                            Text(msg, color = TextSecondary, fontSize = 12.sp)
                        }
                    } else {
                        OutlinedTextField(
                            value = loginEmail,
                            onValueChange = { loginEmail = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("이메일") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = loginPassword,
                            onValueChange = { loginPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("비밀번호") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    attemptLogin()
                                }
                            ),
                            visualTransformation = if (showLoginPassword) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            trailingIcon = {
                                IconButton(onClick = { showLoginPassword = !showLoginPassword }) {
                                    Icon(
                                        imageVector = if (showLoginPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                        contentDescription = if (showLoginPassword) "비밀번호 숨기기" else "비밀번호 보기",
                                        tint = TextSecondary
                                    )
                                }
                            }
                        )
                        Button(
                            onClick = attemptLogin,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
                        ) {
                            Text(
                                "로그인",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        loginMessage?.let { msg ->
                            Text(
                                msg,
                                color = if (loginError) Color(0xFFD64545) else TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        CompositionLocalProvider(LocalRippleConfiguration provides null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "회원가입",
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .clickable {
                                            signupMode = true
                                            signupMessage = null
                                            loginMessage = null
                                        }
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                                Text("|", color = TextSecondary.copy(alpha = 0.7f), fontSize = 12.sp)
                                Text(
                                    "아이디 찾기",
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .clickable { }
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                                Text("|", color = TextSecondary.copy(alpha = 0.7f), fontSize = 12.sp)
                                Text(
                                    "비밀번호 찾기",
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .clickable { }
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MyInfoContent(
    user: SignupUserDto,
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {
    val cardRadius = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .width(560.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .softCardShadow(cardRadius),
                shape = cardRadius,
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(shape = RoundedCornerShape(12.dp), color = SurfaceSoft) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("아이디: ${user.username}", color = TextPrimary, fontSize = 14.sp)
                            Text("이메일: ${user.email}", color = TextSecondary, fontSize = 13.sp)
                        }
                    }
                    Surface(shape = RoundedCornerShape(12.dp), color = SurfaceSoft) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("학습 요약", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = SurfaceCard,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("오늘 푼 문제", color = TextSecondary, fontSize = 11.sp)
                                        Text("8문제", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    }
                                }
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = SurfaceCard,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("누적 문제", color = TextSecondary, fontSize = 11.sp)
                                        Text("124문제", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    }
                                }
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = SurfaceCard,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("평균 정답률", color = TextSecondary, fontSize = 11.sp)
                                        Text("78%", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
                    ) {
                        Text("로그아웃", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderBar() {
    Surface(color = AppBackground, shadowElevation = 0.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(shape = CircleShape, color = BrandBlue) {
                    Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                        Text("R", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }

            Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "알림",
                    tint = BrandBlueDark
                )
            }
        }
    }
}

@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    val tasks = listOf(
        TaskItem("빈칸 추론", "1문제 남음", "난이도 상", "이어하기", Icons.AutoMirrored.Rounded.List),
        TaskItem("순서 배열", "2문제 남음", "난이도 중", "시작하기", Icons.Rounded.Info),
        TaskItem("어휘 리뷰", "3문제 남음", "난이도 중", "시작하기", Icons.Rounded.Face)
    )
    val studies = listOf(
        StudyItem("빈칸 추론 세트 #12", "10문제 · 오늘 14:20", "정답률 80%"),
        StudyItem("순서 배열 연습", "8문제 · 어제 21:10", "정답률 75%"),
        StudyItem("어휘 리뷰", "25단어 · 어제 18:42", "")
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val contentWidth = minOf(screenWidth - 24.dp, 1080.dp)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .width(contentWidth)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item { TodayTasksSection(tasks) }
            item { RecentStudiesCard(studies) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CommunityContent(
    modifier: Modifier = Modifier,
    selectedCategory: String,
    refreshKey: Int,
    onCategoryChange: (String) -> Unit,
    onPostClick: (Long) -> Unit
) {
    val categories = listOf("문제풀이", "공부", "자유")
    var posts by remember { mutableStateOf<List<CommunityPostListItem>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var pullRefreshKey by remember { mutableStateOf(0) }
    val pullToRefreshState = rememberPullToRefreshState()
    val density = LocalDensity.current
    val refreshSpin by rememberInfiniteTransition(label = "refreshSpin").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "refreshSpinAngle"
    )

    LaunchedEffect(selectedCategory, refreshKey, pullRefreshKey) {
        val pullTriggered = refreshing
        loading = true
        errorMessage = null
        try {
            val response = ApiClient.communityApi.getPosts(category = selectedCategory)
            posts = response.map { it.toUiModel() }
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            errorMessage = "게시글을 불러오지 못했습니다."
        } finally {
            loading = false
            if (pullTriggered) {
                refreshing = false
            }
        }
    }

    val cardRadius = RoundedCornerShape(18.dp)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .width(960.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .softCardShadow(cardRadius),
                shape = cardRadius,
                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("커뮤니티", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(categories) { title ->
                                val selected = selectedCategory == title
                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = if (selected) BrandBlue.copy(alpha = 0.14f) else SurfaceCard,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(999.dp))
                                        .clickable { onCategoryChange(title) }
                                ) {
                                    Text(
                                        text = title,
                                        color = if (selected) BrandBlueDark else TextSecondary,
                                        fontSize = 13.sp,
                                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                    PullToRefreshBox(
                        isRefreshing = refreshing,
                        onRefresh = {
                            if (!refreshing) {
                                refreshing = true
                                pullRefreshKey += 1
                            }
                        },
                        state = pullToRefreshState,
                        indicator = {},
                        modifier = Modifier.weight(1f)
                    ) {
                        val progress = pullToRefreshState.distanceFraction.coerceAtLeast(0f)
                        var keepSpinning by remember { mutableStateOf(false) }
                        val contentOffsetPx = with(density) {
                            (progress * 56f).roundToInt().dp.roundToPx()
                        }
                        LaunchedEffect(progress, refreshing) {
                            if (progress >= 1f || refreshing) {
                                keepSpinning = true
                            }
                            if (!refreshing && progress <= 0f) {
                                keepSpinning = false
                            }
                        }
                        val indicatorRotation = if (keepSpinning) 360f + refreshSpin else progress * 360f

                        Box(modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Rounded.Cached,
                                contentDescription = "새로고침",
                                tint = TextSecondary.copy(alpha = 0.8f),
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 8.dp)
                                    .size(24.dp)
                                    .rotate(indicatorRotation)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset { IntOffset(0, contentOffsetPx) }
                            ) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    if (loading) {
                                        item {
                                            Text(
                                                text = "불러오는 중...",
                                                color = TextSecondary,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                    if (!errorMessage.isNullOrBlank()) {
                                        item {
                                            Text(
                                                text = errorMessage!!,
                                                color = TextSecondary,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                    items(posts) { post ->
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onPostClick(post.id) },
                                            shape = RoundedCornerShape(14.dp),
                                            colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                                            border = BorderStroke(1.dp, BorderSoft.copy(alpha = 0.9f))
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(14.dp),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Text(post.title, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                                                Text(post.summary, color = TextSecondary, fontSize = 13.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Row(
                                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Rounded.Visibility,
                                                                contentDescription = "조회수",
                                                                tint = TextSecondary.copy(alpha = 0.85f),
                                                                modifier = Modifier.size(14.dp)
                                                            )
                                                            Text("${post.views}", color = TextSecondary.copy(alpha = 0.85f), fontSize = 12.sp)
                                                        }
                                                        Row(
                                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Rounded.FavoriteBorder,
                                                                contentDescription = "좋아요",
                                                                tint = TextSecondary.copy(alpha = 0.85f),
                                                                modifier = Modifier.size(14.dp)
                                                            )
                                                            Text("${post.likes}", color = TextSecondary.copy(alpha = 0.85f), fontSize = 12.sp)
                                                        }
                                                        Row(
                                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.AutoMirrored.Rounded.Chat,
                                                                contentDescription = "댓글",
                                                                tint = TextSecondary.copy(alpha = 0.85f),
                                                                modifier = Modifier.size(14.dp)
                                                            )
                                                            Text("${post.comments}", color = TextSecondary.copy(alpha = 0.85f), fontSize = 12.sp)
                                                        }
                                                    }
                                                    Text(
                                                        post.timeAgo,
                                                        color = TextSecondary.copy(alpha = 0.85f),
                                                        fontSize = 12.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    item { Spacer(modifier = Modifier.height(16.dp)) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CommunityWriteContent(
    modifier: Modifier = Modifier,
    currentUserId: Long,
    onBack: () -> Unit,
    onCreated: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val categories = listOf("문제풀이", "공부", "자유")
    var category by remember { mutableStateOf(categories.first()) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var submitting by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    val cardRadius = RoundedCornerShape(18.dp)

    fun submitPost() {
        val trimmedTitle = title.trim()
        val trimmedContent = content.trim()
        if (trimmedTitle.isBlank() || trimmedContent.isBlank()) {
            message = "제목과 내용을 입력해 주세요."
            return
        }
        scope.launch {
            submitting = true
            message = null
            runCatching {
                ApiClient.communityApi.createPost(
                    PostCreateRequestDto(
                        authorId = currentUserId,
                        category = category,
                        title = trimmedTitle,
                        content = trimmedContent
                    )
                )
            }.onSuccess {
                onCreated(category)
            }.onFailure {
                message = "게시글 등록에 실패했습니다."
            }
            submitting = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .width(960.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .softCardShadow(cardRadius),
                    shape = cardRadius,
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            TextButton(
                                onClick = onBack,
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.align(Alignment.CenterStart)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "닫기",
                                    tint = TextPrimary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Text(
                                "글쓰기",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            TextButton(
                                onClick = { if (!submitting) submitPost() },
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Text(
                                    "등록",
                                    color = if (submitting) TextSecondary else BrandBlueDark,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(categories) { item ->
                                val selected = item == category
                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = if (selected) BrandBlue.copy(alpha = 0.14f) else SurfaceSoft,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(999.dp))
                                        .clickable { category = item }
                                ) {
                                    Text(
                                        text = item,
                                        color = if (selected) BrandBlueDark else TextSecondary,
                                        fontSize = 13.sp,
                                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                        HorizontalDivider(
                            color = BorderSoft,
                            thickness = 1.dp
                        )
                        TextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("제목을 작성해주세요.", color = TextSecondary) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                        HorizontalDivider(
                            color = BorderSoft,
                            thickness = 1.dp
                        )
                        TextField(
                            value = content,
                            onValueChange = { content = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 180.dp),
                            placeholder = { Text("내용을 작성해보세요", color = TextSecondary) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        if (!message.isNullOrBlank()) {
                            Text(message!!, color = TextSecondary, fontSize = 13.sp)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun CommunityDetailContent(
    postId: Long,
    currentUserId: Long,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var commentDraft by remember { mutableStateOf("") }
    var post by remember { mutableStateOf<CommunityPostDetail?>(null) }
    var comments by remember { mutableStateOf<List<CommunityComment>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var commentSubmitting by remember { mutableStateOf(false) }
    var likeSubmitting by remember { mutableStateOf(false) }
    var liked by remember(postId) { mutableStateOf(false) }
    var likeCount by remember(postId) { mutableStateOf(0) }
    val cardRadius = RoundedCornerShape(18.dp)
    val commentBarHeight = 64.dp
    fun submitComment() {
        val draft = commentDraft.trim()
        if (draft.isBlank() || commentSubmitting) {
            return
        }
        scope.launch {
            commentSubmitting = true
            runCatching {
                ApiClient.communityApi.createComment(
                    postId = postId,
                    payload = CommentCreateRequestDto(
                        authorId = currentUserId,
                        content = draft
                    )
                )
            }.onSuccess { response ->
                val created = response.toUiModel()
                comments = comments + created
                post?.let { currentDetail ->
                    post = currentDetail.copy(comments = currentDetail.comments + 1)
                }
                commentDraft = ""
            }
            commentSubmitting = false
        }
    }

    LaunchedEffect(postId) {
        loading = true
        errorMessage = null
        runCatching {
            ApiClient.communityApi.getPostDetail(postId = postId, userId = currentUserId)
        }.onSuccess { response ->
            post = response.toUiModel()
            likeCount = response.likes
            liked = response.isLiked
        }.onFailure {
            post = null
            errorMessage = "게시글 상세를 불러오지 못했습니다."
        }
        runCatching {
            ApiClient.communityApi.getComments(postId)
        }.onSuccess { response ->
            comments = response.map { it.toUiModel() }
        }.onFailure {
            comments = emptyList()
        }
        loading = false
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val detail = post
        LazyColumn(
            modifier = Modifier
                .width(960.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = commentBarHeight),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .softCardShadow(cardRadius),
                    shape = cardRadius,
                    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            TextButton(
                                onClick = onBack,
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.align(Alignment.CenterStart)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                    contentDescription = "뒤로가기",
                                    tint = BrandBlueDark,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Text(
                                text = detail?.category ?: "",
                                color = TextPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            Spacer(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(30.dp)
                            )
                        }
                        if (loading) {
                            Text("불러오는 중...", color = TextSecondary, fontSize = 13.sp)
                            return@Column
                        }
                        if (!errorMessage.isNullOrBlank()) {
                            Text(errorMessage!!, color = TextSecondary, fontSize = 13.sp)
                            return@Column
                        }
                        if (detail == null) {
                            Text("게시글을 찾을 수 없습니다.", color = TextSecondary, fontSize = 13.sp)
                            return@Column
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(shape = CircleShape, color = AccentSky) {
                                Box(
                                    modifier = Modifier.size(38.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = detail.author.take(1),
                                        color = BrandBlueDark,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(detail.author, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                Text(detail.authoredAt, color = TextSecondary, fontSize = 12.sp)
                            }
                        }
                        Text(detail.title, color = TextPrimary, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                        Text(detail.body, color = TextPrimary, fontSize = 14.sp, lineHeight = 22.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Visibility,
                                        contentDescription = "조회수",
                                        tint = TextSecondary.copy(alpha = 0.85f),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text("${detail.views}", color = TextSecondary, fontSize = 12.sp)
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        if (likeSubmitting) {
                                            return@clickable
                                        }
                                        scope.launch {
                                            likeSubmitting = true
                                            runCatching {
                                                ApiClient.communityApi.toggleLike(
                                                    postId = postId,
                                                    payload = LikeToggleRequestDto(userId = currentUserId)
                                                )
                                            }.onSuccess { response ->
                                                liked = response.liked
                                                likeCount = response.likeCount
                                                post?.let { currentDetail ->
                                                    post = currentDetail.copy(likes = response.likeCount, isLiked = response.liked)
                                                }
                                            }
                                            likeSubmitting = false
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (liked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                                        contentDescription = "좋아요",
                                        tint = if (liked) Color(0xFFE54949) else TextSecondary.copy(alpha = 0.85f),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text("${likeCount}", color = TextSecondary, fontSize = 12.sp)
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.Chat,
                                        contentDescription = "댓글",
                                        tint = TextSecondary.copy(alpha = 0.85f),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text("${detail.comments}", color = TextSecondary, fontSize = 12.sp)
                                }
                            }
                            Text(detail.timeAgo, color = TextSecondary, fontSize = 12.sp)
                        }
                        if (comments.isNotEmpty()) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                comments.forEach { comment ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = SurfaceSoft),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 12.dp, vertical = 10.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    comment.author,
                                                    color = TextPrimary,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                                Text(
                                                    comment.timeAgo,
                                                    color = TextSecondary,
                                                    fontSize = 11.sp
                                                )
                                            }
                                            Text(comment.content, color = TextPrimary, fontSize = 13.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!loading && errorMessage.isNullOrBlank() && detail != null) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(960.dp)
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 8.dp),
                shape = RoundedCornerShape(14.dp),
                color = SurfaceCard,
                shadowElevation = 8.dp
            ) {
                OutlinedTextField(
                    value = commentDraft,
                    onValueChange = { commentDraft = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(commentBarHeight)
                        .padding(horizontal = 2.dp),
                    placeholder = { Text("댓글을 입력해보세요", color = TextSecondary) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent
                    ),
                    trailingIcon = {
                        if (commentDraft.trim().isNotEmpty()) {
                            IconButton(
                                onClick = { submitComment() },
                                enabled = !commentSubmitting
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowUpward,
                                    contentDescription = "댓글 등록",
                                    tint = if (commentSubmitting) TextSecondary else BrandBlueDark
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TodayTasksSection(tasks: List<TaskItem>) {
    val progress by animateFloatAsState(targetValue = 0.25f, animationSpec = tween(700), label = "progress")
    val sectionRadius = RoundedCornerShape(18.dp)
    val cardRadius = RoundedCornerShape(12.dp)
    val isWide = LocalConfiguration.current.screenWidthDp >= 680

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .softCardShadow(sectionRadius),
        shape = sectionRadius,
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("오늘 할 일", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .softInnerShadow(cardRadius),
                shape = cardRadius,
                colors = CardDefaults.cardColors(containerColor = SurfaceSoft),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("오늘 8문제 챌린지", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(shape = RoundedCornerShape(14.dp), color = AccentSky) {
                                Text(
                                    "5일 연속",
                                    color = BrandBlueDark,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                    Text("2월 10일 화요일", color = TextSecondary.copy(alpha = 0.8f), fontSize = 11.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val completedCount = 2
                        repeat(8) { index ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(10.dp)
                                    .background(
                                        color = if (index < completedCount) BrandBlue else BorderSoft,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            )
                        }
                    }
                }
            }

            if (isWide) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    tasks.forEach { task ->
                        TodayTaskItemCard(task = task, modifier = Modifier.weight(1f), cardRadius = cardRadius)
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    tasks.forEach { task ->
                        TodayTaskItemCard(task = task, modifier = Modifier.fillMaxWidth(), cardRadius = cardRadius)
                    }
                }
            }
        }
    }
}

@Composable
private fun TodayTaskItemCard(task: TaskItem, modifier: Modifier, cardRadius: RoundedCornerShape) {
    Card(
        modifier = modifier.softInnerShadow(cardRadius),
        shape = cardRadius,
        colors = CardDefaults.cardColors(containerColor = SurfaceSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 170.dp)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp)) {
                Surface(shape = CircleShape, color = AccentSky) {
                    Box(
                        modifier = Modifier.size(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = task.icon,
                            contentDescription = null,
                            tint = BrandBlueDark,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(task.title, color = TextPrimary, fontWeight = FontWeight.SemiBold)
            }
            Text(task.remain, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Surface(shape = RoundedCornerShape(12.dp), color = AccentSky) {
                Text(
                    task.difficulty,
                    color = BrandBlueDark,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Text(task.action, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun RecentStudiesCard(studies: List<StudyItem>) {
    val cardRadius = RoundedCornerShape(18.dp)
    val itemRadius = RoundedCornerShape(12.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .softCardShadow(cardRadius),
        shape = cardRadius,
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("최근 학습", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("더보기", color = BrandBlue, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(studies) { study ->
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .heightIn(min = 116.dp)
                            .softInnerShadow(itemRadius),
                        shape = itemRadius,
                        colors = CardDefaults.cardColors(containerColor = SurfaceSoft),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(study.title, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            if (study.score.isNotBlank()) {
                                Text(study.score, color = BrandBlueDark, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                            }
                            Text(
                                study.metric,
                                color = TextSecondary,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (study.score.isBlank()) {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
private val previewPost = CommunityPostDetail(
    id = 1L,
    category = "문제풀이",
    title = "빈칸 추론에서 1등급 가는 팁 정리",
    summary = "실전에서 자주 막히는 연결사/지시어 포인트를 짧게 정리했습니다.",
    body = "빈칸 추론은 접속사와 지시어를 먼저 고정한 뒤, 문장 논리 흐름에 맞춰 답을 좁히는 방식이 가장 안정적이었습니다. 실제로 오답 노트에서 틀린 포인트를 접속사/지시어/어휘로 나눠 정리하면 다음 문제에서 재발을 많이 줄일 수 있습니다.",
    hoursAgo = 2,
    timeAgo = "2시간 전",
    views = 128,
    likes = 24,
    comments = 14
)

@Preview(showBackground = true, widthDp = 1280, heightDp = 800, name = "Post Detail Tablet Preview")
@Composable
private fun PostDetailPreviewTablet() {
    CpstoneTheme {
        CommunityDetailContent(postId = previewPost.id, currentUserId = 1L, onBack = {})
    }
}

@Preview(showBackground = true, widthDp = 1280, heightDp = 800, name = "Write Post Tablet Preview")
@Composable
private fun WritePostPreviewTablet() {
    CpstoneTheme {
        CommunityWriteContent(currentUserId = 1L, onBack = {}, onCreated = {})
    }
}

@Preview(showBackground = true, widthDp = 430, heightDp = 920, name = "Post Detail Phone Preview")
@Composable
private fun PostDetailPreviewPhone() {
    CpstoneTheme {
        CommunityDetailContent(postId = previewPost.id, currentUserId = 1L, onBack = {})
    }
}
