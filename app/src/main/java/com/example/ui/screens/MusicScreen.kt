package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import kotlinx.coroutines.launch

data class PianoKeyItem(
  val noteName: String,
  val freq: Double,
  val colorHex: Long
)

@Composable
fun MusicScreen(
  totalStars: Int,
  audioController: AudioController,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  var mascotState by remember { mutableStateOf(MascotState.IDLE_WAVING) }
  val scope = rememberCoroutineScope()

  val pianoKeys = remember {
    listOf(
      PianoKeyItem("DO", 261.63, 0xFFEF5350),  // C4
      PianoKeyItem("RE", 293.66, 0xFFFF9800),  // D4
      PianoKeyItem("MI", 329.63, 0xFFFFCA28),  // E4
      PianoKeyItem("FA", 349.23, 0xFF66BB6A),  // F4
      PianoKeyItem("SOL", 392.00, 0xFF29B6F6), // G4
      PianoKeyItem("LA", 440.00, 0xFFAB47BC),  // A4
      PianoKeyItem("SI", 493.88, 0xFFEC407A)   // B4
    )
  }

  fun triggerMascotDance() {
    mascotState = MascotState.CELEBRATING
    scope.launch {
      kotlinx.coroutines.delay(1200)
      mascotState = MascotState.IDLE_WAVING
    }
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🎵 Music Band",
        showHome = true,
        onHomeClick = onHomeClick,
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFE0F7FA)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE0F7FA),
              Color.White
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        Surface(
          shape = RoundedCornerShape(28.dp),
          color = Color.White,
          shadowElevation = 4.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            BoboMascot(
              size = 80.dp,
              state = mascotState
            )

            Column(modifier = Modifier.padding(start = 12.dp)) {
              Text(
                text = "Baby Band 🎶",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF37474F)
              )
              Text(
                text = "Tap keys or instruments to play sound!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF78909C)
              )
            }
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          InstrumentButton(
            emoji = "🥁",
            label = "Drum",
            colorHex = 0xFFEF5350,
            onClick = {
              audioController.playDrumSound()
              triggerMascotDance()
            }
          )

          InstrumentButton(
            emoji = "🔔",
            label = "Bell",
            colorHex = 0xFFFFCA28,
            onClick = {
              audioController.playBellSound()
              triggerMascotDance()
            }
          )

          InstrumentButton(
            emoji = "🎺",
            label = "Trumpet",
            colorHex = 0xFF42A5F5,
            onClick = {
              audioController.playTrumpetSound()
              triggerMascotDance()
            }
          )

          InstrumentButton(
            emoji = "🎸",
            label = "Guitar",
            colorHex = 0xFFAB47BC,
            onClick = {
              audioController.playGuitarSound()
              triggerMascotDance()
            }
          )
        }

        Surface(
          shape = RoundedCornerShape(32.dp),
          color = Color.White,
          shadowElevation = 8.dp,
          modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        ) {
          Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "🎹 Play Piano Notes",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF37474F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              pianoKeys.forEach { key ->
                PianoKeyButton(
                  key = key,
                  modifier = Modifier.weight(1f),
                  onClick = {
                    audioController.playNote(key.freq)
                    triggerMascotDance()
                  }
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
fun InstrumentButton(
  emoji: String,
  label: String,
  colorHex: Long,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color(colorHex).copy(alpha = 0.15f),
    shadowElevation = 4.dp,
    modifier = Modifier
      .scale(scaleAnim.value)
      .size(72.dp)
      .testTag("instrument_${label.lowercase()}")
      .clickable {
        scope.launch {
          scaleAnim.animateTo(0.88f, spring(dampingRatio = 0.4f))
          scaleAnim.animateTo(1.0f, spring(dampingRatio = 0.6f))
          onClick()
        }
      }
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = emoji, fontSize = 30.sp)
      Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(colorHex)
      )
    }
  }
}

@Composable
fun PianoKeyButton(
  key: PianoKeyItem,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    shape = RoundedCornerShape(18.dp),
    color = Color(key.colorHex),
    shadowElevation = 4.dp,
    modifier = modifier
      .scale(scaleAnim.value)
      .testTag("piano_key_${key.noteName}")
      .clickable {
        scope.launch {
          scaleAnim.animateTo(0.9f, spring(dampingRatio = 0.3f))
          scaleAnim.animateTo(1.0f, spring(dampingRatio = 0.5f))
          onClick()
        }
      }
  ) {
    Box(
      contentAlignment = Alignment.BottomCenter,
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 12.dp)
    ) {
      Text(
        text = key.noteName,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        textAlign = TextAlign.Center
      )
    }
  }
}
