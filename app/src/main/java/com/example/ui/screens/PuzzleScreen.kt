package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class PuzzlePiece(
  val id: String,
  val shapeEmoji: String,
  val name: String,
  val colorHex: Long
)

@Composable
fun PuzzleScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val pieces = remember {
    listOf(
      PuzzlePiece("circle", "⭕", "Circle", 0xFFEF5350),
      PuzzlePiece("square", "⬜", "Square", 0xFF42A5F5),
      PuzzlePiece("triangle", "🔺", "Triangle", 0xFFFFCA28),
      PuzzlePiece("star", "⭐", "Star", 0xFFAB47BC)
    )
  }

  val matchedPieces = remember { mutableStateMapOf<String, Boolean>() }
  var isCelebrationVisible by remember { mutableStateOf(false) }
  var mascotState by remember { mutableStateOf(MascotState.IDLE_WAVING) }
  val scope = rememberCoroutineScope()

  fun onPieceTapped(piece: PuzzlePiece) {
    if (matchedPieces[piece.id] == true) return

    matchedPieces[piece.id] = true
    audioController.playSuccessSound()
    audioController.speak("${piece.name} placed!")

    if (matchedPieces.size == pieces.size) {
      mascotState = MascotState.CELEBRATING
      isCelebrationVisible = true
      audioController.speak("Puzzle Complete! Great job!")
      onAwardStars(3)

      scope.launch {
        delay(2500)
        isCelebrationVisible = false
        matchedPieces.clear()
        mascotState = MascotState.IDLE_WAVING
      }
    }
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🧩 Shape Puzzle",
        showHome = true,
        onHomeClick = onHomeClick,
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFFCE4EC)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFCE4EC),
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
                text = "Shape Matching! 🧩",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF37474F)
              )
              Text(
                text = "Tap shapes to match them into slots!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF78909C)
              )
            }
          }
        }

        Surface(
          shape = RoundedCornerShape(32.dp),
          color = Color.White,
          shadowElevation = 8.dp,
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "Match the Shapes",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF37474F)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              pieces.forEach { piece ->
                val isMatched = matchedPieces[piece.id] == true
                PuzzleSlotItem(
                  piece = piece,
                  isMatched = isMatched,
                  onClick = { onPieceTapped(piece) }
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
      }

      CelebrationOverlay(
        visible = isCelebrationVisible,
        message = "Puzzle Complete!",
        starsAwarded = 3
      )
    }
  }
}

@Composable
fun PuzzleSlotItem(
  piece: PuzzlePiece,
  isMatched: Boolean,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Box(
    modifier = Modifier
      .scale(scaleAnim.value)
      .size(76.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(if (isMatched) Color(piece.colorHex).copy(alpha = 0.2f) else Color(0xFFF5F5F5))
      .border(
        width = 3.dp,
        color = if (isMatched) Color(piece.colorHex) else Color(0xFFB0BEC5),
        shape = RoundedCornerShape(20.dp)
      )
      .testTag("puzzle_piece_${piece.id}")
      .clickable {
        scope.launch {
          scaleAnim.animateTo(0.88f, spring())
          scaleAnim.animateTo(1.0f, spring())
          onClick()
        }
      },
    contentAlignment = Alignment.Center
  ) {
    if (isMatched) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = piece.shapeEmoji, fontSize = 32.sp)
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Matched",
          tint = Color(piece.colorHex),
          modifier = Modifier.size(16.dp)
        )
      }
    } else {
      Text(
        text = piece.shapeEmoji,
        fontSize = 32.sp,
        color = Color.Gray.copy(alpha = 0.4f)
      )
    }
  }
}
