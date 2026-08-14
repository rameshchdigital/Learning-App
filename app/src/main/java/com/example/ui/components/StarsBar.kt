package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import com.example.ui.theme.StarGold

@Composable
fun StarsBar(
  stars: Int,
  streak: Int = 0,
  title: String = "Baby World",
  showHome: Boolean = false,
  showBack: Boolean = false,
  onHomeClick: () -> Unit = {},
  onBackClick: () -> Unit = {},
  onParentLockClick: () -> Unit = {}
) {
  val scaleAnim = remember { Animatable(1f) }

  LaunchedEffect(stars) {
    if (stars > 0) {
      scaleAnim.animateTo(1.3f, animationSpec = spring(dampingRatio = 0.4f))
      scaleAnim.animateTo(1.0f, animationSpec = spring(dampingRatio = 0.6f))
    }
  }

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 8.dp),
    shape = RoundedCornerShape(24.dp),
    color = Color.White.copy(alpha = 0.9f),
    shadowElevation = 4.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f, fill = false)
      ) {
        if (showBack) {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(Color(0xFFFFEBEE))
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFFEF5350)
            )
          }
        } else if (showHome) {
          IconButton(
            onClick = onHomeClick,
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(Color(0xFFE3F2FD))
          ) {
            Icon(
              imageVector = Icons.Default.Home,
              contentDescription = "Home",
              tint = Color(0xFF42A5F5)
            )
          }
        }

        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium,
          color = Color(0xFF37474F),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.padding(start = 8.dp)
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // Daily Streak Counter Pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFFF3E0))
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("daily_streak_counter")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "🔥",
              fontSize = 15.sp
            )
            Text(
              text = "$streak",
              fontSize = 15.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFE65100),
              modifier = Modifier.padding(start = 3.dp)
            )
          }
        }

        // Stars Pill
        Box(
          modifier = Modifier
            .scale(scaleAnim.value)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFFF8E1))
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("stars_counter")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Stars",
              tint = StarGold,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "$stars",
              fontSize = 15.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFF57F17),
              modifier = Modifier.padding(start = 3.dp)
            )
          }
        }

        IconButton(
          onClick = onParentLockClick,
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Color(0xFFF3E5F5))
            .testTag("parent_lock_button")
        ) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Parent Control Lock",
            tint = Color(0xFFAB47BC),
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
