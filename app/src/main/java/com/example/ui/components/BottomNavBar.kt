package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.Screen

data class NavItem(
  val screen: Screen,
  val label: String,
  val icon: ImageVector,
  val activeColor: Color
)

@Composable
fun LittleBuddyBottomBar(
  currentScreen: Screen,
  onNavigate: (Screen) -> Unit,
  modifier: Modifier = Modifier
) {
  BottomNavBar(currentScreen, onNavigate, modifier)
}

@Composable
fun BottomNavBar(
  currentScreen: Screen,
  onNavigate: (Screen) -> Unit,
  modifier: Modifier = Modifier
) {
  val navItems = listOf(
    NavItem(Screen.HOME, "Home", Icons.Default.Home, Color(0xFF42A5F5)),
    NavItem(Screen.LEARN_CATEGORIES, "Learn", Icons.Default.MenuBook, Color(0xFF66BB6A)),
    NavItem(Screen.PLAY_GAMES, "Play", Icons.Default.SportsEsports, Color(0xFFFF9800)),
    NavItem(Screen.PROGRESS_REWARDS, "Progress", Icons.Default.Star, Color(0xFFAB47BC))
  )

  Surface(
    modifier = modifier.fillMaxWidth(),
    color = Color.White,
    shadowElevation = 12.dp,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .height(68.dp)
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      navItems.forEach { item ->
        val isSelected = when (item.screen) {
          Screen.HOME -> currentScreen == Screen.HOME
          Screen.LEARN_CATEGORIES -> currentScreen == Screen.LEARN_CATEGORIES ||
              currentScreen == Screen.ALPHABET_LESSON ||
              currentScreen == Screen.PHONICS_LESSON ||
              currentScreen == Screen.NUMBERS_LESSON ||
              currentScreen == Screen.COLORS_LESSON ||
              currentScreen == Screen.SHAPES_LESSON ||
              currentScreen == Screen.ANIMALS_EXPLORER ||
              currentScreen == Screen.FRUITS_VEG_LESSON ||
              currentScreen == Screen.BODY_PARTS_LESSON ||
              currentScreen == Screen.CATEGORY_LESSON ||
              currentScreen == Screen.SENTENCE_LEARNING ||
              currentScreen == Screen.SPELLING_LESSON ||
              currentScreen == Screen.SPEAKING_PRACTICE
          Screen.PLAY_GAMES -> currentScreen == Screen.PLAY_GAMES || currentScreen == Screen.GAME
          Screen.PROGRESS_REWARDS -> currentScreen == Screen.PROGRESS_REWARDS
          else -> false
        }

        val bgAnim by animateColorAsState(
          targetValue = if (isSelected) item.activeColor.copy(alpha = 0.18f) else Color.Transparent,
          animationSpec = tween(300),
          label = "navBg"
        )

        val iconAnim by animateColorAsState(
          targetValue = if (isSelected) item.activeColor else Color(0xFF78909C),
          animationSpec = tween(300),
          label = "navIcon"
        )

        Box(
          modifier = Modifier
            .clip(CircleShape)
            .background(bgAnim)
            .clickable { onNavigate(item.screen) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("nav_${item.label.lowercase()}"),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = item.icon,
              contentDescription = item.label,
              tint = iconAnim,
              modifier = Modifier.size(24.dp)
            )
            Text(
              text = item.label,
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = iconAnim
            )
          }
        }
      }
    }
  }
}
