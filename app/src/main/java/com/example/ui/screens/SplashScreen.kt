package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState

@Composable
fun SplashScreen() {
  val scaleAnim = remember { Animatable(0.5f) }

  LaunchedEffect(Unit) {
    scaleAnim.animateTo(
      targetValue = 1.05f,
      animationSpec = tween(220, easing = FastOutSlowInEasing)
    )
    scaleAnim.animateTo(
      targetValue = 1.0f,
      animationSpec = tween(80)
    )
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            Color(0xFFE0F7FA),
            Color(0xFFFFFDE7),
            Color(0xFFFFF3E0)
          )
        )
      ),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier
        .scale(scaleAnim.value)
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      BoboMascot(
        size = 160.dp,
        state = MascotState.IDLE_WAVING
      )

      Spacer(modifier = Modifier.height(24.dp))

      Surface(
        shape = RoundedCornerShape(32.dp),
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 8.dp
      ) {
        Column(
          modifier = Modifier.padding(horizontal = 32.dp, vertical = 20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "🐘 Little English Buddy",
            style = MaterialTheme.typography.displayLarge,
            color = Color(0xFF1E88E5)
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "“Learn English. Play. Speak. Grow!”",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800)
          )
        }
      }
    }
  }
}
