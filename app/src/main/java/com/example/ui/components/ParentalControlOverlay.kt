package com.example.ui.components

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@Composable
fun ParentalControlOverlay(
  soundEnabled: Boolean,
  onToggleSound: (Boolean) -> Unit,
  onClearScore: () -> Unit,
  onOpenDashboard: () -> Unit,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  var isUnlocked by remember { mutableStateOf(false) }
  val holdProgress = remember { Animatable(0f) }
  var isHolding by remember { mutableStateOf(false) }
  var scoreClearedMsg by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(28.dp),
      color = Color.White,
      shadowElevation = 12.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .testTag("parental_control_overlay")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3E5F5)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Parental Control Lock",
                tint = Color(0xFF8E24AA),
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Parental Controls",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF37474F)
              )
              Text(
                text = if (isUnlocked) "🔒 Secure Mode Active" else "🖐️ Gestural Gate Locked",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isUnlocked) Color(0xFF2E7D32) else Color(0xFFAB47BC)
              )
            }
          }

          IconButton(onClick = onDismiss) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close Overlay",
              tint = Color(0xFF78909C)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isUnlocked) {
          // Gestural Verification Gate Step
          Text(
            text = "Press and HOLD the button below for 2.5 seconds to unlock parent controls.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color(0xFF546E7A),
            modifier = Modifier.padding(horizontal = 12.dp)
          )

          Spacer(modifier = Modifier.height(20.dp))

          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(110.dp)
              .testTag("gestural_lock_hold_button")
              .pointerInput(Unit) {
                detectTapGestures(
                  onPress = {
                    isHolding = true
                    val job = coroutineScope.launch {
                      holdProgress.animateTo(1f, animationSpec = tween(2200))
                      if (holdProgress.value >= 1f) {
                        isUnlocked = true
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
              modifier = Modifier.size(110.dp),
              color = Color(0xFF8E24AA),
              strokeWidth = 8.dp,
              trackColor = Color(0xFFF3E5F5)
            )

            Surface(
              shape = CircleShape,
              color = if (isHolding) Color(0xFFE1BEE7) else Color(0xFFF3E5F5),
              modifier = Modifier.size(90.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  text = if (isHolding) "HOLDING..." else "HOLD 2.5s",
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF6A1B9A),
                  fontSize = 14.sp,
                  textAlign = TextAlign.Center
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))
        } else {
          // Unlocked Parental Control Overlay Actions
          Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // 1. Mute / Unmute Sound Toggle
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (soundEnabled) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("parent_toggle_sound_button")
                .clickable { onToggleSound(!soundEnabled) }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                    contentDescription = "Sound Status",
                    tint = if (soundEnabled) Color(0xFF2E7D32) else Color(0xFFC62828),
                    modifier = Modifier.size(26.dp)
                  )
                  Spacer(modifier = Modifier.width(12.dp))
                  Column {
                    Text(
                      text = if (soundEnabled) "Sound Active (ON)" else "Sound Muted (OFF)",
                      fontWeight = FontWeight.Bold,
                      fontSize = 15.sp,
                      color = if (soundEnabled) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                    )
                    Text(
                      text = "Tap to toggle game sound effects",
                      fontSize = 11.sp,
                      color = Color(0xFF616161)
                    )
                  }
                }
                Switch(
                  checked = soundEnabled,
                  onCheckedChange = { onToggleSound(it) }
                )
              }
            }

            // 2. Clear Score & Progress
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = Color(0xFFFFF3E0),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("parent_clear_score_button")
                .clickable {
                  onClearScore()
                  scoreClearedMsg = true
                }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Refresh,
                  contentDescription = "Clear Score",
                  tint = Color(0xFFE65100),
                  modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = "Clear Score & Stars",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFFE65100)
                  )
                  Text(
                    text = if (scoreClearedMsg) "✓ Score & stars cleared!" else "Reset total stars collected",
                    fontSize = 11.sp,
                    color = if (scoreClearedMsg) Color(0xFF2E7D32) else Color(0xFF616161)
                  )
                }
              }
            }

            // 3. Exit App
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = Color(0xFFECEFF1),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("parent_exit_app_button")
                .clickable {
                  (context as? Activity)?.finish()
                }
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.ExitToApp,
                  contentDescription = "Exit App",
                  tint = Color(0xFF37474F),
                  modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = "Exit Application",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF263238)
                  )
                  Text(
                    text = "Close Baby World",
                    fontSize = 11.sp,
                    color = Color(0xFF616161)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 4. Full Parent Dashboard
            OutlinedButton(
              onClick = {
                onDismiss()
                onOpenDashboard()
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("parent_open_dashboard_button"),
              shape = RoundedCornerShape(20.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Open Settings",
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Parent Dashboard & Detailed Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            }
          }
        }
      }
    }
  }
}
