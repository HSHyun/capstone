package com.example.cpstone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
                            onClick = { selectedNav = item },
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
        if (selectedNav == "홈") {
            HomeContent(modifier = Modifier.padding(innerPadding))
        } else {
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

    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val contentWidth = minOf(maxWidth - 24.dp, 1080.dp)
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
private fun TodayTasksSection(tasks: List<TaskItem>) {
    val progress by animateFloatAsState(targetValue = 0.25f, animationSpec = tween(700), label = "progress")
    val sectionRadius = RoundedCornerShape(18.dp)
    val cardRadius = RoundedCornerShape(12.dp)

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

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                if (maxWidth >= 680.dp) {
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
@Preview(showBackground = true, widthDp = 1280, heightDp = 800, name = "Tablet Preview")
@Composable
private fun MainPreviewTablet() {
    CpstoneTheme {
        MainScreen()
    }
}

@Preview(showBackground = true, widthDp = 430, heightDp = 920, name = "Phone Preview")
@Composable
private fun MainPreviewPhone() {
    CpstoneTheme {
        MainScreen()
    }
}
