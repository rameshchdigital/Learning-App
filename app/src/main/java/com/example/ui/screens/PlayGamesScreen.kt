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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.data.models.GameCategory
import com.example.ui.components.BoboMascot
import com.example.ui.components.LittleBuddyBottomBar
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.launch

data class MiniGameItem(
  val title: String,
  val emoji: String,
  val description: String,
  val targetScreen: Screen,
  val category: GameCategory? = null,
  val colorHex: Long,
  val bgHex: Long
)

@Composable
fun PlayGamesScreen(
  totalStars: Int,
  learningStreak: Int = 0,
  onStartGame: (Screen, GameCategory?) -> Unit,
  onNavigate: (Screen) -> Unit,
  onParentLockClick: () -> Unit
) {
  val miniGames = listOf(
    MiniGameItem("Daily Quiz 🎯", "📝", "5 questions from practiced topics", Screen.DAILY_QUIZ, null, 0xFFFF8F00, 0xFFFFF3E0),
    MiniGameItem("Find Letter & Word", "🔤", "Listen & choose correct letter", Screen.GAME, GameCategory.ALPHABETS, 0xFF1E88E5, 0xFFE3F2FD),
    MiniGameItem("Find Color", "🎨", "Identify bright color objects", Screen.COLORS_LESSON, GameCategory.COLORS, 0xFFEF5350, 0xFFFFEBEE),
    MiniGameItem("Animal Sounds", "🐶", "Which animal says Woof?", Screen.ANIMALS_EXPLORER, GameCategory.ANIMALS, 0xFF42A5F5, 0xFFE3F2FD),
    MiniGameItem("Count Objects", "🔢", "Count objects and tap number", Screen.NUMBERS_LESSON, GameCategory.NUMBERS, 0xFFFFB300, 0xFFFFF8E1),
    MiniGameItem("Let's Speak! 🎤", "🗣️", "Say words aloud with Ellie", Screen.SPEAKING_PRACTICE, null, 0xFF00897B, 0xFFE0F2F1),
    MiniGameItem("Spelling Fun ✏️", "✏️", "Arrange scrambled letters", Screen.SPELLING_LESSON, GameCategory.SPELLING, 0xFF7E57C2, 0xFFEDE7F6),
    MiniGameItem("Music Playground 🎵", "🎵", "Play piano & drum notes", Screen.MUSIC_PLAYGROUND, GameCategory.MUSIC, 0xFF00897B, 0xFFE0F2F1),
    MiniGameItem("Puzzle Match 🧩", "🧩", "Match pairs & shapes", Screen.PUZZLE_PLAYGROUND, GameCategory.PUZZLES, 0xFFEC407A, 0xFFFCE4EC)
  )

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        streak = learningStreak,
        title = "🎮 Play & Learn Games",
        onParentLockClick = onParentLockClick
      )
    },
    bottomBar = {
      LittleBuddyBottomBar(
        currentScreen = Screen.PLAY_GAMES,
        onNavigate = onNavigate
      )
    },
    containerColor = Color(0xFFFFF3E0)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFF3E0),
              Color(0xFFFFFDE7),
              Color(0xFFE3F2FD)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(16.dp)
      ) {
        // Banner Card
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = Color.White,
          shadowElevation = 3.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            BoboMascot(size = 64.dp, state = MascotState.CELEBRATING)

            Column(modifier = Modifier.padding(start = 12.dp)) {
              Text(
                text = "Play Learning Games!",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFE65100)
              )
              Text(
                text = "Earn stars with every correct answer!",
                fontSize = 12.sp,
                color = Color(0xFF78909C)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mini Game Grid
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          miniGames.chunked(2).forEach { rowList ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              rowList.forEach { game ->
                Box(modifier = Modifier.weight(1f)) {
                  MiniGameCardTile(
                    game = game,
                    onClick = { onStartGame(game.targetScreen, game.category) }
                  )
                }
              }
              if (rowList.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun MiniGameCardTile(
  game: MiniGameItem,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    shape = RoundedCornerShape(26.dp),
    color = Color(game.bgHex),
    shadowElevation = 4.dp,
    modifier = Modifier
      .scale(scaleAnim.value)
      .fillMaxWidth()
      .aspectRatio(1.1f)
      .testTag("game_card_${game.title.lowercase().replace(" ", "_")}")
      .clickable {
        scope.launch {
          scaleAnim.animateTo(0.92f, spring())
          scaleAnim.animateTo(1.0f, spring())
          onClick()
        }
      }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(54.dp)
          .clip(CircleShape)
          .background(Color.White.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
      ) {
        Text(game.emoji, fontSize = 30.sp)
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = game.title,
        fontSize = 15.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(game.colorHex),
        textAlign = TextAlign.Center,
        maxLines = 1
      )

      Text(
        text = game.description,
        fontSize = 10.sp,
        color = Color(0xFF546E7A),
        textAlign = TextAlign.Center,
        maxLines = 1
      )
    }
  }
}
