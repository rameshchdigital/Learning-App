package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class MascotState {
  IDLE_WAVING,
  CELEBRATING,
  ENCOURAGING,
  SPEAKING
}

@Composable
fun BoboMascot(
  modifier: Modifier = Modifier,
  size: Dp = 120.dp,
  state: MascotState = MascotState.IDLE_WAVING
) {
  val bounceY = remember { Animatable(0f) }
  val earWiggle = remember { Animatable(0f) }

  LaunchedEffect(state) {
    when (state) {
      MascotState.IDLE_WAVING -> {
        bounceY.animateTo(
          targetValue = -10f,
          animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
          )
        )
      }
      MascotState.CELEBRATING -> {
        bounceY.animateTo(
          targetValue = -26f,
          animationSpec = infiniteRepeatable(
            animation = tween(280, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
          )
        )
        earWiggle.animateTo(
          targetValue = 12f,
          animationSpec = infiniteRepeatable(
            animation = tween(200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
          )
        )
      }
      MascotState.ENCOURAGING, MascotState.SPEAKING -> {
        bounceY.animateTo(
          targetValue = -14f,
          animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
          )
        )
      }
    }
  }

  Box(
    modifier = modifier.size(size),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(size)) {
      val w = size.toPx()
      val h = size.toPx()
      val offsetY = bounceY.value
      val earOff = earWiggle.value

      // Soft cute elephant palette
      val elephantBlue = Color(0xFF90CAF9) // Soft powder blue
      val elephantDarkBlue = Color(0xFF64B5F6)
      val innerEarPink = Color(0xFFF8BBD0) // Soft pastel pink
      val cheekPink = Color(0xFFFF8A80)
      val eyeColor = Color(0xFF1A237E)
      val backpackRed = Color(0xFFFF5252)
      val backpackStrap = Color(0xFFFFD54F)

      // Tiny Backpack (behind body)
      drawRoundRect(
        color = backpackRed,
        topLeft = Offset(w * 0.28f, h * 0.60f + offsetY),
        size = Size(w * 0.44f, h * 0.28f),
        cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
      )
      drawRoundRect(
        color = backpackStrap,
        topLeft = Offset(w * 0.35f, h * 0.64f + offsetY),
        size = Size(w * 0.30f, h * 0.06f),
        cornerRadius = CornerRadius(w * 0.03f, w * 0.03f)
      )

      // Big Floppy Elephant Ears (Left)
      drawOval(
        color = elephantBlue,
        topLeft = Offset(w * 0.02f - earOff, h * 0.25f + offsetY),
        size = Size(w * 0.38f, h * 0.46f)
      )
      drawOval(
        color = innerEarPink,
        topLeft = Offset(w * 0.08f - earOff, h * 0.32f + offsetY),
        size = Size(w * 0.24f, h * 0.32f)
      )

      // Big Floppy Elephant Ears (Right)
      drawOval(
        color = elephantBlue,
        topLeft = Offset(w * 0.60f + earOff, h * 0.25f + offsetY),
        size = Size(w * 0.38f, h * 0.46f)
      )
      drawOval(
        color = innerEarPink,
        topLeft = Offset(w * 0.68f + earOff, h * 0.32f + offsetY),
        size = Size(w * 0.24f, h * 0.32f)
      )

      // Elephant Body & Head
      drawCircle(
        color = elephantBlue,
        radius = w * 0.36f,
        center = Offset(w * 0.5f, h * 0.48f + offsetY)
      )

      // Cheeks
      drawCircle(
        color = cheekPink.copy(alpha = 0.5f),
        radius = w * 0.08f,
        center = Offset(w * 0.28f, h * 0.54f + offsetY)
      )
      drawCircle(
        color = cheekPink.copy(alpha = 0.5f),
        radius = w * 0.08f,
        center = Offset(w * 0.72f, h * 0.54f + offsetY)
      )

      // Large Expressive Eyes
      if (state == MascotState.CELEBRATING) {
        val eyeLeft = Path().apply {
          moveTo(w * 0.32f, h * 0.42f + offsetY)
          quadraticTo(w * 0.38f, h * 0.34f + offsetY, w * 0.44f, h * 0.42f + offsetY)
        }
        val eyeRight = Path().apply {
          moveTo(w * 0.56f, h * 0.42f + offsetY)
          quadraticTo(w * 0.62f, h * 0.34f + offsetY, w * 0.68f, h * 0.42f + offsetY)
        }
        drawPath(eyeLeft, color = eyeColor, style = Stroke(width = w * 0.045f))
        drawPath(eyeRight, color = eyeColor, style = Stroke(width = w * 0.045f))
      } else {
        // Left eye
        drawCircle(color = eyeColor, radius = w * 0.055f, center = Offset(w * 0.37f, h * 0.40f + offsetY))
        drawCircle(color = Color.White, radius = w * 0.022f, center = Offset(w * 0.355f, h * 0.385f + offsetY))

        // Right eye
        drawCircle(color = eyeColor, radius = w * 0.055f, center = Offset(w * 0.63f, h * 0.40f + offsetY))
        drawCircle(color = Color.White, radius = w * 0.022f, center = Offset(w * 0.615f, h * 0.385f + offsetY))
      }

      // Trunk
      val trunkPath = Path().apply {
        moveTo(w * 0.50f, h * 0.48f + offsetY)
        cubicTo(
          w * 0.50f, h * 0.64f + offsetY,
          w * 0.65f, h * 0.66f + offsetY,
          w * 0.58f, h * 0.56f + offsetY
        )
      }
      drawPath(
        path = trunkPath,
        color = elephantDarkBlue,
        style = Stroke(width = w * 0.09f)
      )

      // Cheerful Hat / Star Badge on Head
      drawCircle(
        color = Color(0xFFFFD54F),
        radius = w * 0.08f,
        center = Offset(w * 0.50f, h * 0.16f + offsetY)
      )
    }
  }
}
