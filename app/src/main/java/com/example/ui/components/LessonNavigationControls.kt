package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Unified, child-friendly Previous / Play / Next navigation bar
 * ensuring standard accessible UI/UX across all learning and lesson screens.
 */
@Composable
fun LessonNavigationControls(
  onPrevious: () -> Unit,
  onPlay: () -> Unit,
  onNext: () -> Unit,
  modifier: Modifier = Modifier,
  canPrevious: Boolean = true,
  canNext: Boolean = true,
  playLabel: String = "PLAY SOUND 🔊",
  currentIndex: Int? = null,
  totalCount: Int? = null,
  accentColor: Color = Color(0xFF1E88E5)
) {
  val haptic = LocalHapticFeedback.current
  val scope = rememberCoroutineScope()
  var isPlayPressed by remember { mutableStateOf(false) }
  val playScale by animateFloatAsState(
    targetValue = if (isPlayPressed) 0.92f else 1.0f,
    animationSpec = spring(dampingRatio = 0.4f),
    label = "playScale"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp, vertical = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (currentIndex != null && totalCount != null && totalCount > 0) {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = accentColor.copy(alpha = 0.12f),
        modifier = Modifier.padding(bottom = 8.dp)
      ) {
        Text(
          text = "${currentIndex + 1} of $totalCount",
          fontSize = 13.sp,
          fontWeight = FontWeight.ExtraBold,
          color = accentColor,
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // PREVIOUS BUTTON
      OutlinedButton(
        onClick = {
          if (canPrevious) {
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
            onPrevious()
          }
        },
        enabled = canPrevious,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(
          2.dp,
          if (canPrevious) accentColor else Color.LightGray.copy(alpha = 0.6f)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
          containerColor = if (canPrevious) Color.White else Color(0xFFF5F5F5),
          contentColor = if (canPrevious) accentColor else Color.LightGray
        ),
        modifier = Modifier
          .height(48.dp)
          .testTag("nav_previous_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Previous",
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "PREV",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 13.sp
        )
      }

      // PLAY / HEAR BUTTON
      Button(
        onClick = {
          haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
          isPlayPressed = true
          scope.launch {
            delay(150)
            isPlayPressed = false
          }
          onPlay()
        },
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = accentColor,
          contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp, pressedElevation = 2.dp),
        modifier = Modifier
          .scale(playScale)
          .height(52.dp)
          .testTag("nav_play_sound_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.VolumeUp,
          contentDescription = "Play Sound",
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = playLabel,
          fontWeight = FontWeight.Black,
          fontSize = 14.sp
        )
      }

      // NEXT BUTTON
      Button(
        onClick = {
          if (canNext) {
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
            onNext()
          }
        },
        enabled = canNext,
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (canNext) accentColor else Color.LightGray.copy(alpha = 0.5f),
          contentColor = Color.White,
          disabledContainerColor = Color.LightGray.copy(alpha = 0.4f),
          disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        elevation = ButtonDefaults.buttonElevation(
          defaultElevation = if (canNext) 4.dp else 0.dp
        ),
        modifier = Modifier
          .height(48.dp)
          .testTag("nav_next_button")
      ) {
        Text(
          text = "NEXT",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 13.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = "Next",
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}
