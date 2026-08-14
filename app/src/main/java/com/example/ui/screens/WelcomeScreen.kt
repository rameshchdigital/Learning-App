package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.components.BoboMascot
import com.example.ui.components.MascotState
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
  onPlayClick: () -> Unit
) {
  var currentPage by remember { mutableIntStateOf(0) }
  val buttonScale = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  val pages = listOf(
    Triple(
      "Hello Little Learner! 👋",
      "Welcome to Little English Buddy!",
      "Fun songs, interactive lessons, and speech practice crafted for beginner learners!"
    ),
    Triple(
      "What shall we learn? 📚",
      "Alphabets, Phonics, Numbers & Animals!",
      "Explore 20+ exciting categories with words, cheerful sounds, and games!"
    ),
    Triple(
      "Speak & Play! 🚀",
      "Earn Stars, Badges & Speak Aloud!",
      "Practice speaking with Ellie the elephant buddy and collect shiny stars!"
    )
  )

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            Color(0xFFE3F2FD),
            Color(0xFFFFFDE7),
            Color(0xFFFFF3E0)
          )
        )
      )
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth()
    ) {
      BoboMascot(
        size = 150.dp,
        state = if (currentPage == 2) MascotState.CELEBRATING else MascotState.IDLE_WAVING
      )

      Spacer(modifier = Modifier.height(20.dp))

      Surface(
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          AnimatedContent(
            targetState = currentPage,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "carousel"
          ) { page ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = pages[page].first,
                style = MaterialTheme.typography.displayMedium,
                color = Color(0xFF1E88E5),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = pages[page].second,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF37474F),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = pages[page].third,
                fontSize = 14.sp,
                color = Color(0xFF78909C),
                textAlign = TextAlign.Center
              )
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Page Indicators
          Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            pages.indices.forEach { index ->
              Box(
                modifier = Modifier
                  .padding(4.dp)
                  .size(if (index == currentPage) 12.dp else 8.dp)
                  .background(
                    if (index == currentPage) Color(0xFF42A5F5) else Color(0xFFCFD8DC),
                    CircleShape
                  )
              )
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = {
              scope.launch {
                buttonScale.animateTo(0.92f, spring())
                buttonScale.animateTo(1.0f, spring())
                if (currentPage < pages.size - 1) {
                  currentPage++
                } else {
                  onPlayClick()
                }
              }
            },
            modifier = Modifier
              .scale(buttonScale.value)
              .height(64.dp)
              .fillMaxWidth(0.9f)
              .testTag("onboarding_next_button"),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF66BB6A),
              contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = if (currentPage < pages.size - 1) "NEXT ➔" else "LET'S START! 🚀",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
              )
            }
          }
        }
      }
    }
  }
}

