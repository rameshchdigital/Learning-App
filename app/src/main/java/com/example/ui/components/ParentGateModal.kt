package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@Composable
fun ParentGateModal(
  onDismiss: () -> Unit,
  onSuccess: () -> Unit
) {
  val coroutineScope = rememberCoroutineScope()
  val holdProgress = remember { Animatable(0f) }
  var isHolding by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(28.dp),
      color = Color.White,
      shadowElevation = 8.dp,
      modifier = Modifier.padding(16.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(modifier = Modifier.fillMaxWidth()) {
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.TopEnd)
          ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Icon(
          imageVector = Icons.Default.Lock,
          contentDescription = "Parent Gate",
          tint = Color(0xFFAB47BC),
          modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Parents Only 🔒",
          style = MaterialTheme.typography.titleLarge,
          color = Color(0xFF37474F)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Press and HOLD the button below for 3 seconds to access Parent Area.",
          textAlign = TextAlign.Center,
          fontSize = 14.sp,
          color = Color(0xFF78909C)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(120.dp)
            .pointerInput(Unit) {
              detectTapGestures(
                onPress = {
                  isHolding = true
                  val job = coroutineScope.launch {
                    holdProgress.animateTo(1f, animationSpec = tween(2800))
                    if (holdProgress.value >= 1f) {
                      onSuccess()
                    }
                  }
                  tryAwaitRelease()
                  isHolding = false
                  job.cancel()
                  coroutineScope.launch {
                    holdProgress.snapTo(0f)
                  }
                }
              )
            }
        ) {
          CircularProgressIndicator(
            progress = { holdProgress.value },
            modifier = Modifier.size(120.dp),
            color = Color(0xFFAB47BC),
            strokeWidth = 8.dp,
            trackColor = Color(0xFFF3E5F5)
          )

          Surface(
            shape = RoundedCornerShape(100.dp),
            color = if (isHolding) Color(0xFFE1BEE7) else Color(0xFFF3E5F5),
            modifier = Modifier.size(100.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = if (isHolding) "HOLD..." else "HOLD 3s",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6A1B9A),
                fontSize = 16.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
      }
    }
  }
}
