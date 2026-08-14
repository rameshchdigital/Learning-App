package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.StarGold
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class ConfettiParticle(
  val x: Float,
  val initialY: Float,
  val speedY: Float,
  val size: Float,
  val color: Color
)

data class TapBurstParticle(
  val angleRad: Double,
  val speed: Float,
  val size: Float,
  val color: Color
)

@Composable
fun TapStarBurstOverlay(
  triggerKey: Int,
  modifier: Modifier = Modifier
) {
  if (triggerKey <= 0) return

  val progress = remember(triggerKey) { Animatable(0f) }

  val particles = remember(triggerKey) {
    val colors = listOf(
      Color(0xFFFFD54F), // Gold Star
      Color(0xFFFF7043), // Coral
      Color(0xFF26C6DA), // Turquoise
      Color(0xFFEC407A), // Bright Pink
      Color(0xFFAB47BC), // Purple
      Color(0xFF9CCC65)  // Lime Green
    )
    List(28) {
      TapBurstParticle(
        angleRad = Random.nextDouble(0.0, 2.0 * Math.PI),
        speed = 220f + Random.nextFloat() * 480f,
        size = 14f + Random.nextFloat() * 22f,
        color = colors[Random.nextInt(colors.size)]
      )
    }
  }

  LaunchedEffect(triggerKey) {
    progress.snapTo(0f)
    progress.animateTo(
      targetValue = 1f,
      animationSpec = tween(750, easing = LinearOutSlowInEasing)
    )
  }

  if (progress.value < 1f) {
    Canvas(modifier = modifier.fillMaxSize()) {
      val centerX = size.width / 2f
      val centerY = size.height / 2f
      val t = progress.value
      val alpha = (1f - t).coerceIn(0f, 1f)

      particles.forEach { p ->
        val dist = p.speed * t
        val px = centerX + (dist * cos(p.angleRad)).toFloat()
        val py = centerY + (dist * sin(p.angleRad)).toFloat() - (t * 90f)
        val currentSize = p.size * (1.2f - t * 0.4f)

        drawCircle(
          color = p.color.copy(alpha = alpha),
          radius = currentSize,
          center = Offset(px, py)
        )
      }
    }
  }
}

@Composable
fun CelebrationOverlay(
  visible: Boolean,
  message: String = "Great Job!",
  starsAwarded: Int = 1,
  onDismiss: () -> Unit = {}
) {
  val animProgress = remember { Animatable(0f) }

  val particles = remember {
    val colors = listOf(
      Color(0xFFEF5350), Color(0xFF42A5F5), Color(0xFFFFCA28),
      Color(0xFF66BB6A), Color(0xFFAB47BC), Color(0xFFEC407A)
    )
    List(40) {
      ConfettiParticle(
        x = Random.nextFloat(),
        initialY = Random.nextFloat() * -0.5f,
        speedY = 0.8f + Random.nextFloat() * 1.2f,
        size = 12f + Random.nextFloat() * 18f,
        color = colors[Random.nextInt(colors.size)]
      )
    }
  }

  LaunchedEffect(visible) {
    if (visible) {
      animProgress.snapTo(0f)
      animProgress.animateTo(
        targetValue = 1f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing)
      )
      onDismiss()
    }
  }

  AnimatedVisibility(
    visible = visible,
    enter = fadeIn() + scaleIn(),
    exit = fadeOut() + scaleOut()
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.35f)),
      contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val t = animProgress.value

        particles.forEach { p ->
          val currentY = (p.initialY + p.speedY * t) * h
          val currentX = p.x * w + Math.sin((currentY / 50).toDouble()).toFloat() * 20f

          drawCircle(
            color = p.color,
            radius = p.size,
            center = Offset(currentX, currentY)
          )
        }
      }

      Surface(
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 12.dp,
        modifier = Modifier.padding(24.dp)
      ) {
        Column(
          modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          BoboMascot(
            size = 110.dp,
            state = MascotState.CELEBRATING
          )

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "🎉 $message",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F)
          )

          Spacer(modifier = Modifier.height(8.dp))

          Box(
            modifier = Modifier
              .background(Color(0xFFFFF8E1), RoundedCornerShape(20.dp))
              .padding(horizontal = 16.dp, vertical = 8.dp)
          ) {
            Text(
              text = "+$starsAwarded ⭐ STAR!",
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              color = StarGold
            )
          }
        }
      }
    }
  }
}
