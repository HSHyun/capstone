package com.example.cpstone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cpstone.ui.theme.CpstoneTheme

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

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { TopBar() },
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F7FA) // 연한 회색 배경
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { HeroSection() }
            item { StatusCards() }
            item { StartSection() }
            item { CommunitySection() }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "AI 모의고사 플랫폼",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TextButton(onClick = { /* 로그인 */ }) { Text("로그인") }
            Button(
                onClick = { /* 회원가입 */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D63ED)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("회원가입", color = Color.White)
            }
        }
    }
}

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "지문 정밀 분석부터 변형 문제까지,\n한 번에 끝내는 AI 리딩 솔루션",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 36.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { /* 지문 업로드 */ }) { Text("지문 업로드 해보기") }
            OutlinedButton(onClick = { /* 지문 생성 */ }) { Text("지문 생성 해보기") }
        }
    }
}

@Composable
fun StatusCards() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DashboardCard(modifier = Modifier.weight(1f), title = "오늘의 문제", content = "11월 18일 화요일")
        DashboardCard(modifier = Modifier.weight(1f), title = "진행률", content = "2 / 8 완료")
    }
}

@Composable
fun DashboardCard(modifier: Modifier, title: String, content: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StartSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("시작하기", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("STEP 1: 지문 업로드 & 문제 분석", fontWeight = FontWeight.Bold)
                Text("사진·PDF로 올린 지문의 핵심 어휘·구문을 분석합니다.", fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun CommunitySection() {
    Text("커뮤니티 & 소식", fontSize = 20.sp, fontWeight = FontWeight.Bold)
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1280) // 태블릿 크기로 미리보기
@Composable
fun DefaultPreview() {
    CpstoneTheme {
        MainScreen()
    }
}