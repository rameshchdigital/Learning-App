package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.AudioController
import com.example.ui.components.BoboMascot
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.LittleBuddyBottomBar
import com.example.ui.components.MascotState
import com.example.ui.components.StarsBar
import com.example.ui.components.TapStarBurstOverlay
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.launch

data class VocabCardItem(
  val id: String,
  val word: String,
  val category: String,
  val iconEmoji: String,
  val phonetic: String,
  val sentence: String,
  val cardColor: Color,
  val bgGradient: List<Color>
)

object VocabCardData {
  val cards = listOf(
    // Animals
    VocabCardItem(
      id = "lion",
      word = "Lion",
      category = "Animals",
      iconEmoji = "🦁",
      phonetic = "/ˈlaɪ.ən/",
      sentence = "Roar! The lion is king of the jungle.",
      cardColor = Color(0xFFFF8F00),
      bgGradient = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2))
    ),
    VocabCardItem(
      id = "elephant",
      word = "Elephant",
      category = "Animals",
      iconEmoji = "🐘",
      phonetic = "/ˈel.ɪ.fənt/",
      sentence = "The big elephant has floppy ears and a long trunk!",
      cardColor = Color(0xFF1E88E5),
      bgGradient = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
    ),
    VocabCardItem(
      id = "monkey",
      word = "Monkey",
      category = "Animals",
      iconEmoji = "🐒",
      phonetic = "/ˈmʌŋ.ki/",
      sentence = "The happy monkey loves swinging and eating bananas!",
      cardColor = Color(0xFF6D4C41),
      bgGradient = listOf(Color(0xFFEFEBE9), Color(0xD1D7D2))
    ),
    VocabCardItem(
      id = "dog",
      word = "Dog",
      category = "Animals",
      iconEmoji = "🐶",
      phonetic = "/dɒɡ/",
      sentence = "Woof! The friendly dog wags its tail happily.",
      cardColor = Color(0xFFFB8C00),
      bgGradient = listOf(Color(0xFFFFF8E1), Color(0xFFFFECB3))
    ),
    VocabCardItem(
      id = "duck",
      word = "Duck",
      category = "Animals",
      iconEmoji = "🦆",
      phonetic = "/dʌk/",
      sentence = "Quack! The yellow duck swims gracefully in the pond.",
      cardColor = Color(0xFF009688),
      bgGradient = listOf(Color(0xFFE0F2F1), Color(0xFFB2DFDB))
    ),
    VocabCardItem(
      id = "frog",
      word = "Frog",
      category = "Animals",
      iconEmoji = "🐸",
      phonetic = "/frɒɡ/",
      sentence = "Ribbit! The green frog leaps high onto lily pads.",
      cardColor = Color(0xFF43A047),
      bgGradient = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))
    ),

    // Colors
    VocabCardItem(
      id = "red",
      word = "Red",
      category = "Colors",
      iconEmoji = "🔴",
      phonetic = "/red/",
      sentence = "Red apples are crunchy, sweet, and delicious!",
      cardColor = Color(0xFFE53935),
      bgGradient = listOf(Color(0xFFFFEBEE), Color(0xFFFFCDD2))
    ),
    VocabCardItem(
      id = "blue",
      word = "Blue",
      category = "Colors",
      iconEmoji = "🔵",
      phonetic = "/bluː/",
      sentence = "The wide blue sky has fluffy white clouds floating by.",
      cardColor = Color(0xFF1E88E5),
      bgGradient = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
    ),
    VocabCardItem(
      id = "yellow",
      word = "Yellow",
      category = "Colors",
      iconEmoji = "🟡",
      phonetic = "/ˈjel.əʊ/",
      sentence = "The warm yellow sun shines bright all day long.",
      cardColor = Color(0xFFFDD835),
      bgGradient = listOf(Color(0xFFFFFDE7), Color(0xFFFFF59D))
    ),
    VocabCardItem(
      id = "green",
      word = "Green",
      category = "Colors",
      iconEmoji = "🟢",
      phonetic = "/ɡriːn/",
      sentence = "Green grass and fresh leaves grow in the park.",
      cardColor = Color(0xFF43A047),
      bgGradient = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))
    ),
    VocabCardItem(
      id = "purple",
      word = "Purple",
      category = "Colors",
      iconEmoji = "🟣",
      phonetic = "/ˈpɜː.pəl/",
      sentence = "Sweet purple grapes grow in juicy bunches on vines.",
      cardColor = Color(0xFF8E24AA),
      bgGradient = listOf(Color(0xFFA19C1), Color(0xFFE1BEE7))
    ),
    VocabCardItem(
      id = "pink",
      word = "Pink",
      category = "Colors",
      iconEmoji = "🩷",
      phonetic = "/pɪŋk/",
      sentence = "Pretty pink flowers bloom in the spring garden.",
      cardColor = Color(0xFFD81B60),
      bgGradient = listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0))
    ),

    // Shapes
    VocabCardItem(
      id = "circle",
      word = "Circle",
      category = "Shapes",
      iconEmoji = "⭕",
      phonetic = "/ˈsɜː.kəl/",
      sentence = "A round circle has no corners, like a shiny coin!",
      cardColor = Color(0xFFD81B60),
      bgGradient = listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0))
    ),
    VocabCardItem(
      id = "square",
      word = "Square",
      category = "Shapes",
      iconEmoji = "🔲",
      phonetic = "/skweər/",
      sentence = "A square has four straight sides of equal length.",
      cardColor = Color(0xFF3949AB),
      bgGradient = listOf(Color(0xFFE8EAF6), Color(0xFFC5CAE9))
    ),
    VocabCardItem(
      id = "triangle",
      word = "Triangle",
      category = "Shapes",
      iconEmoji = "🔺",
      phonetic = "/ˈtraɪ.æŋ.ɡəl/",
      sentence = "A slice of yummy pizza is shaped like a triangle!",
      cardColor = Color(0xFFFB8C00),
      bgGradient = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2))
    ),
    VocabCardItem(
      id = "star",
      word = "Star",
      category = "Shapes",
      iconEmoji = "⭐",
      phonetic = "/stɑːr/",
      sentence = "A bright golden star twinkles in the night sky.",
      cardColor = Color(0xFFFFB300),
      bgGradient = listOf(Color(0xFFFFF8E1), Color(0xFFFFECB3))
    ),
    VocabCardItem(
      id = "heart",
      word = "Heart",
      category = "Shapes",
      iconEmoji = "💖",
      phonetic = "/hɑːt/",
      sentence = "A warm loving heart shows kindness and friendship.",
      cardColor = Color(0xFFE53935),
      bgGradient = listOf(Color(0xFFFFEBEE), Color(0xFFFFCDD2))
    ),

    // Fruits
    VocabCardItem(
      id = "apple",
      word = "Apple",
      category = "Fruits",
      iconEmoji = "🍎",
      phonetic = "/ˈæp.əl/",
      sentence = "An apple a day keeps us healthy, strong, and active!",
      cardColor = Color(0xFFE53935),
      bgGradient = listOf(Color(0xFFFFEBEE), Color(0xFFFFCDD2))
    ),
    VocabCardItem(
      id = "banana",
      word = "Banana",
      category = "Fruits",
      iconEmoji = "🍌",
      phonetic = "/bəˈnɑː.nə/",
      sentence = "Peel a sweet yellow banana for a tasty snack!",
      cardColor = Color(0xFFFDD835),
      bgGradient = listOf(Color(0xFFFFFDE7), Color(0xFFFFF59D))
    ),
    VocabCardItem(
      id = "strawberry",
      word = "Strawberry",
      category = "Fruits",
      iconEmoji = "🍓",
      phonetic = "/ˈstrɔː.bər.i/",
      sentence = "Red juicy strawberries are filled with sweet vitamins!",
      cardColor = Color(0xFFD81B60),
      bgGradient = listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0))
    ),
    VocabCardItem(
      id = "carrot",
      word = "Carrot",
      category = "Fruits",
      iconEmoji = "🥕",
      phonetic = "/ˈkær.ət/",
      sentence = "Crunchy orange carrots help us build bright vision!",
      cardColor = Color(0xFFFB8C00),
      bgGradient = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2))
    ),

    // Numbers
    VocabCardItem(
      id = "one",
      word = "One",
      category = "Numbers",
      iconEmoji = "1️⃣",
      phonetic = "/wʌn/",
      sentence = "One bright yellow sun warming the whole morning.",
      cardColor = Color(0xFF1E88E5),
      bgGradient = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
    ),
    VocabCardItem(
      id = "two",
      word = "Two",
      category = "Numbers",
      iconEmoji = "2️⃣",
      phonetic = "/tuː/",
      sentence = "Two fluffy bunnies hopping around together.",
      cardColor = Color(0xFF8E24AA),
      bgGradient = listOf(Color(0xFFF3E5F5), Color(0xFFE1BEE7))
    ),
    VocabCardItem(
      id = "five",
      word = "Five",
      category = "Numbers",
      iconEmoji = "5️⃣",
      phonetic = "/faɪv/",
      sentence = "Five cheerful fingers waving hello to everyone!",
      cardColor = Color(0xFF43A047),
      bgGradient = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))
    ),

    // Vehicles
    VocabCardItem(
      id = "car",
      word = "Car",
      category = "Vehicles",
      iconEmoji = "🚗",
      phonetic = "/kɑːr/",
      sentence = "Vroom vroom! The red car zooms down the road.",
      cardColor = Color(0xFFE53935),
      bgGradient = listOf(Color(0xFFFFEBEE), Color(0xFFFFCDD2))
    ),
    VocabCardItem(
      id = "bus",
      word = "Bus",
      category = "Vehicles",
      iconEmoji = "🚌",
      phonetic = "/bʌs/",
      sentence = "The big yellow bus safely carries kids to school.",
      cardColor = Color(0xFFFDD835),
      bgGradient = listOf(Color(0xFFFFFDE7), Color(0xFFFFF59D))
    ),
    VocabCardItem(
      id = "airplane",
      word = "Airplane",
      category = "Vehicles",
      iconEmoji = "✈️",
      phonetic = "/ˈeə.pleɪn/",
      sentence = "The silver airplane flies high above white clouds!",
      cardColor = Color(0xFF0288D1),
      bgGradient = listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2))
    )
  )
}

@Composable
fun VocabularyCardsScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onNavigate: (Screen) -> Unit,
  onParentLockClick: () -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf("All") }
  var activeCardModal by remember { mutableStateOf<VocabCardItem?>(null) }
  var burstTriggerKey by remember { mutableStateOf(0) }
  var showCelebrationOverlay by remember { mutableStateOf(false) }
  var celebrationMessage by remember { mutableStateOf("Vocabulary Lesson Milestone!") }
  var cardsLearnedInSession by remember { mutableStateOf(0) }

  val categories = listOf("All", "Animals", "Colors", "Shapes", "Fruits", "Numbers", "Vehicles")

  val filteredCards = remember(searchQuery, selectedCategory) {
    VocabCardData.cards.filter { item ->
      val matchesSearch = item.word.contains(searchQuery, ignoreCase = true) ||
          item.sentence.contains(searchQuery, ignoreCase = true)
      val matchesCat = selectedCategory == "All" || item.category.equals(selectedCategory, ignoreCase = true)
      matchesSearch && matchesCat
    }
  }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🎨 Vocab Explorer",
        onParentLockClick = onParentLockClick
      )
    },
    bottomBar = {
      LittleBuddyBottomBar(
        currentScreen = Screen.LEARN_CATEGORIES,
        onNavigate = onNavigate
      )
    },
    containerColor = Color(0xFFFFFDE7)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFFDE7),
              Color(0xFFF3E5F5),
              Color(0xFFE8F5E9)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        // Back Button & Subtitle Row
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 3.dp,
            modifier = Modifier.clickable { onHomeClick() }
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFF7E57C2),
              modifier = Modifier.padding(10.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = "Interactive Word Cards 🎴",
              fontSize = 18.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF4A148C)
            )
            Text(
              text = "Tap any card to hear pronunciation & sentence!",
              fontSize = 12.sp,
              color = Color(0xFF6A1B9A)
            )
          }
        }

        // Search Bar
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search words (e.g. Elephant, Red, Star)...", fontSize = 13.sp) },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF7E57C2)) },
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .testTag("vocab_search_input"),
          shape = RoundedCornerShape(24.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF7E57C2),
            unfocusedBorderColor = Color(0xFFD1C4E9),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
          ),
          singleLine = true
        )

        // Filter Categories Chips
        LazyRow(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(categories) { cat ->
            val isSelected = cat == selectedCategory
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (isSelected) Color(0xFF7E57C2) else Color.White,
              shadowElevation = if (isSelected) 4.dp else 1.dp,
              modifier = Modifier
                .clickable {
                  selectedCategory = cat
                  audioController.speak("Filter: $cat")
                }
                .testTag("vocab_category_chip_$cat")
            ) {
              Text(
                text = cat,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (isSelected) Color.White else Color(0xFF512DA8),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
              )
            }
          }
        }

        // Vocabulary Cards Grid
        if (filteredCards.isEmpty()) {
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text("🔍", fontSize = 48.sp)
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                "No vocabulary words found!",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 16.sp
              )
              Text(
                "Try searching for another word or select 'All'.",
                fontSize = 13.sp,
                color = Color.LightGray
              )
            }
          }
        } else {
          LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp),
            modifier = Modifier.weight(1f)
          ) {
            items(filteredCards, key = { it.id }) { item ->
              VocabGridCardTile(
                item = item,
                onClick = {
                  val animalSoundId = if (item.category.equals("Animals", ignoreCase = true)) item.id else null
                  audioController.speakCard(item.word, item.sentence, animalSoundId)
                  onAwardStars(1)
                  activeCardModal = item
                  burstTriggerKey++
                  cardsLearnedInSession++

                  // Trigger particle celebration confetti every 4 words learned
                  if (cardsLearnedInSession % 4 == 0) {
                    celebrationMessage = "Super Star! You practiced $cardsLearnedInSession word cards!"
                    showCelebrationOverlay = true
                  }
                }
              )
            }
          }
        }

        // Bottom Bobo Mascot Encouragement
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          BoboMascot(
            size = 60.dp,
            state = MascotState.SPEAKING,
            modifier = Modifier.clickable {
              audioController.speak("Great job exploring vocabulary words! You are so smart!")
            }
          )
        }
      }

      // Card Modal Dialog
      activeCardModal?.let { item ->
        VocabDetailDialog(
          item = item,
          audioController = audioController,
          onDismiss = { activeCardModal = null }
        )
      }

      // Particle Star Burst Overlay on card tap
      TapStarBurstOverlay(triggerKey = burstTriggerKey)

      // Particle Confetti Celebration Overlay on lesson milestone
      CelebrationOverlay(
        visible = showCelebrationOverlay,
        message = celebrationMessage,
        starsAwarded = 5,
        onDismiss = { showCelebrationOverlay = false }
      )
    }
  }
}

@Composable
fun VocabGridCardTile(
  item: VocabCardItem,
  onClick: () -> Unit
) {
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color.White,
    shadowElevation = 5.dp,
    modifier = Modifier
      .scale(scaleAnim.value)
      .fillMaxWidth()
      .aspectRatio(0.95f)
      .border(
        width = 2.dp,
        color = item.cardColor.copy(alpha = 0.3f),
        shape = RoundedCornerShape(24.dp)
      )
      .clickable {
        scope.launch {
          scaleAnim.animateTo(0.9f, spring())
          scaleAnim.animateTo(1.0f, spring())
          onClick()
        }
      }
      .testTag("vocab_card_${item.id}")
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          Brush.verticalGradient(colors = item.bgGradient)
        )
        .padding(12.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        // Category Pill & Sound Icon
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = item.cardColor.copy(alpha = 0.15f)
          ) {
            Text(
              text = item.category.uppercase(),
              fontSize = 9.sp,
              fontWeight = FontWeight.Black,
              color = item.cardColor,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }

          Icon(
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = "Listen",
            tint = item.cardColor,
            modifier = Modifier.size(18.dp)
          )
        }

        // Cheerful Icon Badge
        Box(
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.9f)),
          contentAlignment = Alignment.Center
        ) {
          Text(text = item.iconEmoji, fontSize = 34.sp)
        }

        // Word Title & Phonetic
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = item.word,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black,
            color = item.cardColor,
            textAlign = TextAlign.Center
          )

          Text(
            text = item.phonetic,
            fontSize = 11.sp,
            fontStyle = FontStyle.Italic,
            color = Color(0xFF616161),
            textAlign = TextAlign.Center
          )
        }
      }
    }
  }
}

@Composable
fun VocabDetailDialog(
  item: VocabCardItem,
  audioController: AudioController,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(32.dp),
      color = Color.White,
      shadowElevation = 12.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("vocab_detail_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(colors = item.bgGradient)
          )
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
          }
        }

        // Huge Emoji Display
        Box(
          modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.White),
          contentAlignment = Alignment.Center
        ) {
          Text(text = item.iconEmoji, fontSize = 60.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = item.word,
          fontSize = 28.sp,
          fontWeight = FontWeight.Black,
          color = item.cardColor
        )

        Text(
          text = item.phonetic,
          fontSize = 15.sp,
          fontStyle = FontStyle.Italic,
          color = Color(0xFF512DA8)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Example Sentence Box
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "💬 Example Sentence:",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "\"${item.sentence}\"",
              fontSize = 15.sp,
              fontWeight = FontWeight.Medium,
              color = Color(0xFF212121),
              textAlign = TextAlign.Center
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Replay Voice Button
        Button(
          onClick = {
            val animalSoundId = if (item.category.equals("Animals", ignoreCase = true)) item.id else null
            audioController.speakCard(item.word, item.sentence, animalSoundId)
          },
          shape = RoundedCornerShape(24.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = item.cardColor,
            contentColor = Color.White
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("vocab_listen_again_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null,
            modifier = Modifier.padding(end = 8.dp)
          )
          Text("LISTEN AGAIN 🔊", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
      }
    }
  }
}
