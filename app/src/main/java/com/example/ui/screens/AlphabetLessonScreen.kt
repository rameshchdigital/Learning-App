package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.data.models.AlphabetCard
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar

@Composable
fun AlphabetLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val alphabets = remember { GameContentRepository.getAlphabets() }
  var selectedIndex by remember { mutableIntStateOf(0) }
  val currentCard = alphabets[selectedIndex]

  LaunchedEffect(selectedIndex) {
    audioController.speak("${currentCard.letterUpper} is for ${currentCard.word}. ${currentCard.phoneticSentence}")
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🔤 Learn Alphabets",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFE3F2FD)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE3F2FD),
              Color(0xFFFFFDE7),
              Color(0xFFFFF3E0)
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
        // Back Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Start
        ) {
          Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.clickable { onHomeClick() }
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFF1E88E5),
              modifier = Modifier.padding(10.dp)
            )
          }
        }

        // Hero Letter Display Card
        AnimatedContent(
          targetState = selectedIndex,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "letterCard"
        ) { idx ->
          val card = alphabets[idx]
          Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 8.dp)
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${card.letterUpper} ${card.letterLower}",
                  fontSize = 54.sp,
                  fontWeight = FontWeight.Black,
                  color = Color(0xFF1E88E5)
                )

                BoboMascot(size = 72.dp, state = MascotState.SPEAKING)
              }

              Spacer(modifier = Modifier.height(12.dp))

              Box(
                modifier = Modifier
                  .size(110.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = card.emoji,
                  fontSize = 64.sp
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = card.word,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF263238)
              )

              Text(
                text = card.phoneticSentence,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE65100),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = card.exampleSentence,
                fontSize = 14.sp,
                color = Color(0xFF546E7A),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(16.dp))

              Button(
                onClick = {
                  audioController.speak("${card.letterUpper}! ${card.word}! ${card.phoneticSentence}")
                },
                modifier = Modifier
                  .height(48.dp)
                  .testTag("listen_letter_audio"),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFF1E88E5),
                  contentColor = Color.White
                )
              ) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Listen")
                Spacer(modifier = Modifier.width(8.dp))
                Text("LISTEN SOUND 🔊", fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        // Navigation Controls (Prev / Replay / Next)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = {
              if (selectedIndex > 0) selectedIndex--
            },
            enabled = selectedIndex > 0
          ) {
            Surface(
              shape = CircleShape,
              color = if (selectedIndex > 0) Color(0xFF1E88E5) else Color.LightGray
            ) {
              Icon(Icons.Default.ChevronLeft, contentDescription = "Prev", tint = Color.White, modifier = Modifier.size(36.dp))
            }
          }

          Text(
            text = "${selectedIndex + 1} / ${alphabets.size}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F)
          )

          IconButton(
            onClick = {
              if (selectedIndex < alphabets.size - 1) {
                selectedIndex++
                onAwardStars(1)
              }
            },
            enabled = selectedIndex < alphabets.size - 1
          ) {
            Surface(
              shape = CircleShape,
              color = if (selectedIndex < alphabets.size - 1) Color(0xFF1E88E5) else Color.LightGray
            ) {
              Icon(Icons.Default.ChevronRight, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(36.dp))
            }
          }
        }

        // Horizontal A to Z Selector Row
        LazyRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
          itemsIndexed(alphabets) { idx, card ->
            val isSelected = idx == selectedIndex
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isSelected) Color(0xFF1E88E5) else Color.White,
              shadowElevation = if (isSelected) 4.dp else 1.dp,
              modifier = Modifier
                .size(52.dp)
                .clickable { selectedIndex = idx }
                .testTag("select_letter_${card.letterUpper}")
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  text = card.letterUpper,
                  fontSize = 20.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = if (isSelected) Color.White else Color(0xFF1E88E5)
                )
              }
            }
          }
        }
      }
    }
  }
}
