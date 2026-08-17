package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.data.models.ColorCard
import com.example.data.models.ColorLessonItem
import com.example.data.models.ColorMinimalPair
import com.example.data.models.ColorMixRecipe
import com.example.data.models.ColorSubcategory
import com.example.ui.components.BoboMascot
import com.example.ui.components.ColorMixSoundButton
import com.example.ui.components.ColorSoundButton
import com.example.ui.components.LessonNavigationControls
import com.example.ui.components.MascotState
import com.example.ui.components.SoundButton
import com.example.ui.components.SoundButtonSize
import com.example.ui.components.SoundButtonVariant
import com.example.ui.components.StarsBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ColorScreenTab(val title: String, val icon: String) {
  LESSON("Lessons & Colors", "🎨"),
  EAR_TRAINING("Ear Training", "🎧"),
  MIXING_LAB("Mixing Lab", "🧪"),
  PALETTE("Palette Wall", "🧱")
}

@Composable
fun ColorsLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val allLessonItems = remember { GameContentRepository.getColorLessonItems() }
  val minimalPairs = remember { GameContentRepository.getColorMinimalPairs() }
  val mixRecipes = remember { GameContentRepository.getColorMixRecipes() }
  val allColors: List<ColorCard> = remember { GameContentRepository.getColors() }

  var currentTab by remember { mutableStateOf(ColorScreenTab.LESSON) }
  var selectedSubcategory by remember { mutableStateOf(ColorSubcategory.PRIMARY_COLORS) }

  val filteredLessons = remember(selectedSubcategory, allLessonItems) {
    val items = allLessonItems.filter { it.subcategory == selectedSubcategory }
    if (items.isEmpty()) allLessonItems else items
  }

  var lessonIndex by remember { mutableIntStateOf(0) }
  var mascotState by remember { mutableStateOf(MascotState.IDLE_WAVING) }
  val scope = rememberCoroutineScope()

  LaunchedEffect(selectedSubcategory) {
    lessonIndex = 0
  }

  val activeLesson = filteredLessons.getOrElse(lessonIndex) { allLessonItems.first() }

  Scaffold(
    topBar = {
      StarsBar(
        stars = totalStars,
        title = "🎨 Colors & Art Explorer",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFFFEBEE)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFEBEE),
              Color(0xFFFFFDE7),
              Color(0xFFE3F2FD)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 14.dp, vertical = 8.dp)
      ) {
        // Back Header & Tab Selector
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
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
              tint = Color(0xFFE53935),
              modifier = Modifier.padding(10.dp)
            )
          }

          Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            ColorScreenTab.entries.forEach { tab ->
              val isSelected = currentTab == tab
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) Color(0xFFE53935) else Color.White,
                shadowElevation = if (isSelected) 3.dp else 1.dp,
                modifier = Modifier
                  .clickable {
                    currentTab = tab
                    audioController.playClickSound()
                  }
                  .testTag("tab_${tab.name.lowercase()}")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(text = tab.icon, fontSize = 13.sp)
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = tab.title,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                    color = if (isSelected) Color.White else Color(0xFF5D4037)
                  )
                }
              }
            }
          }
        }

        when (currentTab) {
          ColorScreenTab.LESSON -> {
            // Subcategory Ladder
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ColorSubcategory.entries.forEach { subcat ->
                val isSelected = selectedSubcategory == subcat
                val cardBg = if (isSelected) Color(subcat.colorHex) else Color.White
                val textColor = if (isSelected) Color.White else Color(0xFF37474F)

                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = cardBg,
                  shadowElevation = if (isSelected) 4.dp else 1.dp,
                  border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isSelected) Color(subcat.colorHex) else Color(0xFFE0E0E0)
                  ),
                  modifier = Modifier
                    .clickable {
                      selectedSubcategory = subcat
                      audioController.speak("Level ${subcat.levelNumber}: ${subcat.title}")
                    }
                    .testTag("subcat_${subcat.name.lowercase()}")
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(text = subcat.emoji, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                      Text(
                        text = "Lvl ${subcat.levelNumber} • ${subcat.title}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                      )
                      Text(
                        text = subcat.subtitle,
                        fontSize = 10.sp,
                        color = if (isSelected) Color.White.copy(alpha = 0.9f) else Color.Gray
                      )
                    }
                  }
                }
              }
            }

            // Pedagogical Goal banner
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color(selectedSubcategory.bgHex),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "🎨", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = selectedSubcategory.pedagogicalGoal,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(selectedSubcategory.colorHex)
                )
              }
            }

            // Active Lesson Content
            Box(modifier = Modifier.weight(1f)) {
              ColorLessonView(
                lesson = activeLesson,
                audioController = audioController,
                onAwardStars = onAwardStars,
                onMascotCheer = {
                  mascotState = MascotState.CELEBRATING
                  scope.launch {
                    delay(1500)
                    mascotState = MascotState.IDLE_WAVING
                  }
                }
              )
            }

            // Navigation Controls
            LessonNavigationControls(
              onPrevious = {
                if (lessonIndex > 0) {
                  lessonIndex--
                  audioController.playClickSound()
                }
              },
              onPlay = {
                audioController.speak(activeLesson.hearPrompt)
              },
              onNext = {
                if (lessonIndex < filteredLessons.size - 1) {
                  lessonIndex++
                  audioController.playClickSound()
                }
              },
              canPrevious = lessonIndex > 0,
              canNext = lessonIndex < filteredLessons.size - 1,
              currentIndex = lessonIndex + 1,
              totalCount = filteredLessons.size,
              accentColor = Color(selectedSubcategory.colorHex)
            )
          }

          ColorScreenTab.EAR_TRAINING -> {
            ColorEarTrainingTab(
              minimalPairs = minimalPairs,
              audioController = audioController,
              onAwardStars = onAwardStars
            )
          }

          ColorScreenTab.MIXING_LAB -> {
            ColorMixingLabTab(
              mixRecipes = mixRecipes,
              audioController = audioController,
              onAwardStars = onAwardStars
            )
          }

          ColorScreenTab.PALETTE -> {
            ColorPaletteGridTab(
              colors = allColors,
              audioController = audioController
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ColorLessonView(
  lesson: ColorLessonItem,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onMascotCheer: () -> Unit
) {
  var selectedQuizOption by remember(lesson.id) { mutableStateOf<String?>(null) }
  var isQuizCorrect by remember(lesson.id) { mutableStateOf(false) }
  var hasAnswered by remember(lesson.id) { mutableStateOf(false) }

  LaunchedEffect(lesson.id) {
    audioController.speak(lesson.hearPrompt)
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(vertical = 6.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // 1. Interactive Hero Color SoundButton
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 4.dp,
      border = androidx.compose.foundation.BorderStroke(
        2.dp,
        Color(lesson.colorHex).copy(alpha = 0.35f)
      ),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(lesson.colorHex)
          ) {
            Text(
              text = "1. HEAR COLOR 🔊",
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Text(
            text = "Tap cards to hear audio",
            fontSize = 11.sp,
            color = Color.Gray
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Hero Color SoundButton Component
        ColorSoundButton(
          colorName = lesson.colorName,
          colorHex = lesson.colorHex,
          emoji = lesson.emoji,
          audioController = audioController,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }

    // 2. Real-World Examples with SoundButtons
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 3.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Visibility,
            contentDescription = null,
            tint = Color(0xFF00897B),
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "2. SEE REAL EXAMPLES",
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF00897B)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          lesson.seeExamples.forEach { ex ->
            SoundButton(
              audioController = audioController,
              label = ex.label,
              emoji = ex.emoji,
              subtext = ex.colorName,
              variant = SoundButtonVariant.HERO_CARD,
              size = SoundButtonSize.MEDIUM,
              accentColor = Color(lesson.colorHex),
              onClick = {
                audioController.speak(ex.soundPrompt)
              }
            )
          }
        }
      }
    }

    // 3. Color Mixing Recipe if available
    if (lesson.mixRecipe != null) {
      val recipe = lesson.mixRecipe
      Surface(
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Science,
              contentDescription = null,
              tint = Color(recipe.resultHex),
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "3. COLOR MIXING RECIPE",
              fontSize = 13.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(recipe.resultHex)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          ColorMixSoundButton(
            colorA = recipe.colorA,
            emojiA = recipe.emojiA,
            colorB = recipe.colorB,
            emojiB = recipe.emojiB,
            resultColor = recipe.resultColor,
            resultEmoji = recipe.resultEmoji,
            resultHex = recipe.resultHex,
            prompt = recipe.prompt,
            audioController = audioController,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
              onAwardStars(1)
              onMascotCheer()
            }
          )
        }
      }
    }

    // 4. Interactive Quiz / Choose Question with SoundButtons
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 3.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = Color(0xFFE53935),
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "4. CHOOSE & FIND",
              fontSize = 13.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFE53935)
            )
          }

          SoundButton(
            audioController = audioController,
            label = "HEAR QUESTION",
            emoji = "🔊",
            variant = SoundButtonVariant.PHONEME_CHIP,
            size = SoundButtonSize.SMALL,
            accentColor = Color(0xFFE53935),
            onClick = {
              audioController.speak(lesson.chooseQuestion)
            }
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = lesson.chooseQuestion,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF263238)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          lesson.chooseOptions.forEach { opt ->
            val isSelected = selectedQuizOption == opt.id
            val isCorrectOption = opt.id == lesson.correctOptionId
            val borderColor = when {
              isSelected && isCorrectOption -> Color(0xFF43A047)
              isSelected && !isCorrectOption -> Color(0xFFE53935)
              else -> Color(0xFFE0E0E0)
            }

            Surface(
              shape = RoundedCornerShape(18.dp),
              color = if (isSelected && isCorrectOption) Color(0xFFE8F5E9) else Color.White,
              shadowElevation = if (isSelected) 4.dp else 1.dp,
              border = androidx.compose.foundation.BorderStroke(2.dp, borderColor),
              modifier = Modifier
                .weight(1f)
                .clickable {
                  selectedQuizOption = opt.id
                  hasAnswered = true
                  if (isCorrectOption) {
                    isQuizCorrect = true
                    audioController.playRewardSound()
                    audioController.speak("Great job! ${opt.label} is correct!")
                    onMascotCheer()
                    onAwardStars(1)
                  } else {
                    isQuizCorrect = false
                    audioController.playWrongSound()
                    audioController.speak("Try again! Find ${lesson.colorName}")
                  }
                }
                .testTag("color_quiz_option_${opt.id}")
            ) {
              Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(text = opt.emoji, fontSize = 28.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = opt.label,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF37474F),
                  textAlign = TextAlign.Center
                )
              }
            }
          }
        }

        AnimatedVisibility(visible = hasAnswered && isQuizCorrect) {
          Column(modifier = Modifier.padding(top = 10.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF43A047),
                modifier = Modifier.size(20.dp)
              )
              Text(
                text = "Artist Superstar! You found the color! ⭐ +1",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
              )
            }
          }
        }
      }
    }

    // 5. Speak & Practice Prompt
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color(0xFFFFEBEE),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = null,
            tint = Color(0xFFC62828),
            modifier = Modifier.size(26.dp)
          )
          Column {
            Text(
              text = "5. SPEAK OUT LOUD",
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFC62828)
            )
            Text(
              text = lesson.speakPrompt,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF263238)
            )
          }
        }

        SoundButton(
          audioController = audioController,
          label = "LISTEN",
          emoji = "🔊",
          variant = SoundButtonVariant.PHONEME_CHIP,
          size = SoundButtonSize.SMALL,
          accentColor = Color(0xFFC62828),
          onClick = {
            audioController.speak("${lesson.speakPrompt} ${lesson.colorName}!")
          }
        )
      }
    }
  }
}

@Composable
private fun ColorEarTrainingTab(
  minimalPairs: List<ColorMinimalPair>,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit
) {
  var pairIndex by remember { mutableIntStateOf(0) }
  val activePair = minimalPairs.getOrElse(pairIndex) { minimalPairs.first() }
  var selectedColorName by remember(pairIndex) { mutableStateOf<String?>(null) }
  var hasAnswered by remember(pairIndex) { mutableStateOf(false) }

  LaunchedEffect(pairIndex) {
    audioController.speak("Listen carefully! Which color is this: ${activePair.spokenColor}?")
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(vertical = 10.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 4.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "🎧 COLOR EAR TRAINING",
          fontSize = 13.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFFE91E63)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Listen carefully to the spoken voice & tap the matching color swatch!",
          fontSize = 12.sp,
          color = Color.Gray,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Big Audio Prompt Button
        SoundButton(
          audioController = audioController,
          label = "PLAY SPOKEN COLOR",
          subtext = "Tap to listen again",
          emoji = "🔊",
          variant = SoundButtonVariant.STANDARD,
          size = SoundButtonSize.HERO,
          accentColor = Color(0xFFE91E63),
          onClick = {
            audioController.speak(activePair.spokenColor)
          }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Contrast Cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Option 1
          val isOpt1Selected = selectedColorName == activePair.color1
          val isOpt1Correct = activePair.color1 == activePair.spokenColor
          val border1 = when {
            isOpt1Selected && isOpt1Correct -> Color(0xFF43A047)
            isOpt1Selected && !isOpt1Correct -> Color(0xFFE53935)
            else -> Color(0xFFE0E0E0)
          }

          Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color.White,
            shadowElevation = 3.dp,
            border = androidx.compose.foundation.BorderStroke(2.5.dp, border1),
            modifier = Modifier
              .weight(1f)
              .clickable {
                selectedColorName = activePair.color1
                hasAnswered = true
                if (isOpt1Correct) {
                  audioController.playRewardSound()
                  audioController.speak("Yes! That is ${activePair.color1}!")
                  onAwardStars(1)
                } else {
                  audioController.playWrongSound()
                  audioController.speak("Oops! That is ${activePair.color1}. Try again!")
                }
              }
          ) {
            Column(
              modifier = Modifier.padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(
                modifier = Modifier
                  .size(52.dp)
                  .clip(CircleShape)
                  .background(Color(activePair.hex1)),
                contentAlignment = Alignment.Center
              ) {
                Text(text = activePair.emoji1, fontSize = 24.sp)
              }
              Spacer(modifier = Modifier.height(8.dp))
              Text(text = activePair.color1, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF37474F))
            }
          }

          // Option 2
          val isOpt2Selected = selectedColorName == activePair.color2
          val isOpt2Correct = activePair.color2 == activePair.spokenColor
          val border2 = when {
            isOpt2Selected && isOpt2Correct -> Color(0xFF43A047)
            isOpt2Selected && !isOpt2Correct -> Color(0xFFE53935)
            else -> Color(0xFFE0E0E0)
          }

          Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color.White,
            shadowElevation = 3.dp,
            border = androidx.compose.foundation.BorderStroke(2.5.dp, border2),
            modifier = Modifier
              .weight(1f)
              .clickable {
                selectedColorName = activePair.color2
                hasAnswered = true
                if (isOpt2Correct) {
                  audioController.playRewardSound()
                  audioController.speak("Yes! That is ${activePair.color2}!")
                  onAwardStars(1)
                } else {
                  audioController.playWrongSound()
                  audioController.speak("Oops! That is ${activePair.color2}. Try again!")
                }
              }
          ) {
            Column(
              modifier = Modifier.padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(
                modifier = Modifier
                  .size(52.dp)
                  .clip(CircleShape)
                  .background(Color(activePair.hex2)),
                contentAlignment = Alignment.Center
              ) {
                Text(text = activePair.emoji2, fontSize = 24.sp)
              }
              Spacer(modifier = Modifier.height(8.dp))
              Text(text = activePair.color2, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF37474F))
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = activePair.hint, fontSize = 11.sp, color = Color(0xFF5D4037))
      }
    }

    // Pair Navigation
    LessonNavigationControls(
      onPrevious = {
        if (pairIndex > 0) {
          pairIndex--
          audioController.playClickSound()
        }
      },
      onPlay = {
        audioController.speak(activePair.spokenColor)
      },
      onNext = {
        if (pairIndex < minimalPairs.size - 1) {
          pairIndex++
          audioController.playClickSound()
        }
      },
      canPrevious = pairIndex > 0,
      canNext = pairIndex < minimalPairs.size - 1,
      currentIndex = pairIndex + 1,
      totalCount = minimalPairs.size,
      accentColor = Color(0xFFE91E63)
    )
  }
}

@Composable
private fun ColorMixingLabTab(
  mixRecipes: List<ColorMixRecipe>,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color(0xFFE0F7FA),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(text = "🧪", fontSize = 28.sp)
        Column {
          Text(
            text = "MAGIC COLOR MIXING LAB",
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF00838F)
          )
          Text(
            text = "Tap each color mixing formula to hear how 2 primary colors combine into a new vibrant color!",
            fontSize = 11.sp,
            color = Color(0xFF006064)
          )
        }
      }
    }

    mixRecipes.forEach { recipe ->
      ColorMixSoundButton(
        colorA = recipe.colorA,
        emojiA = recipe.emojiA,
        colorB = recipe.colorB,
        emojiB = recipe.emojiB,
        resultColor = recipe.resultColor,
        resultEmoji = recipe.resultEmoji,
        resultHex = recipe.resultHex,
        prompt = recipe.prompt,
        audioController = audioController,
        modifier = Modifier.fillMaxWidth(),
        onClick = {
          onAwardStars(1)
        }
      )
    }
  }
}

@Composable
private fun ColorPaletteGridTab(
  colors: List<ColorCard>,
  audioController: AudioController
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 6.dp)
  ) {
    Text(
      text = "🧱 INTERACTIVE COLOR PALETTE",
      fontSize = 13.sp,
      fontWeight = FontWeight.ExtraBold,
      color = Color(0xFFC2185B),
      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    )
    Text(
      text = "Tap any color swatch to hear its name and real-world example:",
      fontSize = 11.sp,
      color = Color.Gray,
      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    )

    Spacer(modifier = Modifier.height(6.dp))

    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(colors) { colCard ->
        ColorSoundButton(
          colorName = colCard.name,
          colorHex = colCard.colorHex,
          emoji = colCard.emoji,
          exampleObject = colCard.exampleObject,
          audioController = audioController,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}
