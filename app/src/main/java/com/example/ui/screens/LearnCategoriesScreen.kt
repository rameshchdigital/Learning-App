package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.ui.components.LittleBuddyBottomBar
import com.example.ui.components.StarsBar
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.launch

@Composable
fun LearnCategoriesScreen(
  totalStars: Int,
  learningStreak: Int = 0,
  onCategoryClick: (GameCategory) -> Unit,
  onNavigate: (Screen) -> Unit,
  onParentLockClick: () -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedFilter by remember { mutableStateOf("All") }

  val filterOptions = listOf("All", "👨‍👩‍👧 Everyday Life", "🧠 Learning & Dev", "🌎 World Around Us")

  val filteredCategories = GameCategory.entries.filter { cat ->
    val matchesSearch = cat.title.contains(searchQuery, ignoreCase = true) ||
        cat.subTitle.contains(searchQuery, ignoreCase = true)
    val matchesFilter = when (selectedFilter) {
      "👨‍👩‍👧 Everyday Life" -> cat.group == com.example.data.models.CategoryGroup.EVERYDAY_LIFE
      "🧠 Learning & Dev" -> cat.group == com.example.data.models.CategoryGroup.LEARNING_DEV
      "🌎 World Around Us" -> cat.group == com.example.data.models.CategoryGroup.WORLD_AROUND
      else -> true
    }
    matchesSearch && matchesFilter
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        streak = learningStreak,
        title = "📚 All Categories",
        onParentLockClick = onParentLockClick
      )
    },
    bottomBar = {
      LittleBuddyBottomBar(
        currentScreen = Screen.LEARN_CATEGORIES,
        onNavigate = onNavigate
      )
    },
    containerColor = Color(0xFFE8F5E9)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE8F5E9),
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
          .padding(bottom = 16.dp)
      ) {
        // Search Input
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search topics (e.g., Animals, Colors)...", fontSize = 14.sp) },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF4CAF50)) },
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("search_category_input"),
          shape = RoundedCornerShape(24.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF66BB6A),
            unfocusedBorderColor = Color(0xFFA5D6A7),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
          ),
          singleLine = true
        )

        // Filter Chips Row
        LazyRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
        ) {
          items(filterOptions) { filter ->
            val isSelected = filter == selectedFilter
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (isSelected) Color(0xFF4CAF50) else Color.White,
              shadowElevation = if (isSelected) 3.dp else 1.dp,
              modifier = Modifier
                .clickable { selectedFilter = filter }
                .testTag("filter_chip_$filter")
            ) {
              Text(
                text = filter,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (isSelected) Color.White else Color(0xFF2E7D32),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Select a Topic to Start Learning 🌟",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF1B5E20),
          modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        // Category Cards Grid
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          filteredCategories.chunked(2).forEach { rowList ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              rowList.forEach { cat ->
                Box(modifier = Modifier.weight(1f)) {
                  CategoryGridTile(
                    category = cat,
                    onClick = { onCategoryClick(cat) }
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
fun CategoryGridTile(
  category: GameCategory,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color(category.bgHex),
    shadowElevation = 3.dp,
    modifier = Modifier
      .scale(scaleAnim.value)
      .fillMaxWidth()
      .aspectRatio(1.1f)
      .testTag("learn_category_${category.id}")
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
        .padding(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(52.dp)
          .clip(CircleShape)
          .background(Color.White.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
      ) {
        Text(category.iconEmoji, fontSize = 28.sp)
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = category.title,
        fontSize = 15.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(category.colorHex),
        textAlign = TextAlign.Center,
        maxLines = 1
      )

      Text(
        text = category.subTitle,
        fontSize = 10.sp,
        color = Color(0xFF546E7A),
        textAlign = TextAlign.Center,
        maxLines = 1
      )
    }
  }
}
