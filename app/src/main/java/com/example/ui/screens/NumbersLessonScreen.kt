package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioController
import com.example.data.content.GameContentRepository
import com.example.data.models.NumberCard
import com.example.data.models.NumberLessonItem
import com.example.data.models.NumberMinimalPair
import com.example.data.models.NumberSubcategory
import com.example.ui.components.BoboMascot
import com.example.ui.components.CountItemSoundButton
import com.example.ui.components.LessonNavigationControls
import com.example.ui.components.MascotState
import com.example.ui.components.NumberSoundButton
import com.example.ui.components.SoundButton
import com.example.ui.components.SoundButtonSize
import com.example.ui.components.SoundButtonVariant
import com.example.ui.components.StarsBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class NumberScreenTab(val title: String, val icon: String) {
  LESSON("Lessons & Counting", "🔢"),
  EAR_TRAINING("Ear Training", "🎧"),
  GRID_EXPLORER("Number Wall", "🧱")
}

@Composable
fun NumbersLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val allLessonItems = remember { GameContentRepository.getNumberLessonItems() }
  val minimalPairs = remember { GameContentRepository.getNumberMinimalPairs() }
  val allNumbers = remember { GameContentRepository.getNumbers() }

  var currentTab by remember { mutableStateOf(NumberScreenTab.LESSON) }
  var selectedSubcategory by remember { mutableStateOf(NumberSubcategory.COUNTING_1_5) }

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
        title = "🔢 Numbers & Early Math",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFFFF8E1)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFFFF8E1),
              Color(0xFFFFFDE7),
              Color(0xFFFFECB3)
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
              tint = Color(0xFFFF8F00),
              modifier = Modifier.padding(10.dp)
            )
          }

          Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            NumberScreenTab.entries.forEach { tab ->
              val isSelected = currentTab == tab
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) Color(0xFFFF8F00) else Color.White,
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
          NumberScreenTab.LESSON -> {
            // Subcategory Ladder
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              NumberSubcategory.entries.forEach { subcat ->
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
                Text(text = "🎯", fontSize = 12.sp)
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
              NumberLessonView(
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

          NumberScreenTab.EAR_TRAINING -> {
            NumberEarTrainingTab(
              minimalPairs = minimalPairs,
              audioController = audioController,
              onAwardStars = onAwardStars
            )
          }

          NumberScreenTab.GRID_EXPLORER -> {
            NumberGridExplorerTab(
              numbers = allNumbers,
              audioController = audioController
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun NumberLessonView(
  lesson: NumberLessonItem,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onMascotCheer: () -> Unit
) {
  var selectedQuizOption by remember(lesson.id) { mutableStateOf<String?>(null) }
  var isQuizCorrect by remember(lesson.id) { mutableStateOf(false) }
  var hasAnswered by remember(lesson.id) { mutableStateOf(false) }
  val tappedCounts = remember(lesson.id) { mutableStateListOf<Int>() }

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
    // 1. Interactive Hero Number SoundButton
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      shadowElevation = 4.dp,
      border = androidx.compose.foundation.BorderStroke(
        2.dp,
        Color(lesson.subcategory.colorHex).copy(alpha = 0.35f)
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
            color = Color(lesson.subcategory.colorHex)
          ) {
            Text(
              text = "1. HEAR & COUNT 🔊",
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

        // Hero Number SoundButton Component
        NumberSoundButton(
          number = lesson.number,
          word = lesson.word,
          emoji = lesson.emoji,
          countText = lesson.countText,
          audioController = audioController,
          accentColor = Color(lesson.subcategory.colorHex),
          modifier = Modifier.fillMaxWidth()
        )

        // Tap-to-count interactive tiles
        if (lesson.number in 1..10) {
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Tap each ${lesson.emoji} to count out loud:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF455A64)
          )
          Spacer(modifier = Modifier.height(6.dp))

          FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            for (i in 1..lesson.number) {
              CountItemSoundButton(
                index = i,
                emoji = lesson.emoji,
                label = lesson.word,
                audioController = audioController,
                accentColor = Color(lesson.subcategory.colorHex),
                modifier = Modifier.padding(4.dp),
                onClick = {
                  if (!tappedCounts.contains(i)) {
                    tappedCounts.add(i)
                    if (tappedCounts.size == lesson.number) {
                      audioController.playRewardSound()
                      onMascotCheer()
                      onAwardStars(1)
                    }
                  }
                }
              )
            }
          }
        }
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
              subtext = "${ex.count} items",
              variant = SoundButtonVariant.HERO_CARD,
              size = SoundButtonSize.MEDIUM,
              accentColor = Color(lesson.subcategory.colorHex),
              onClick = {
                audioController.speak(ex.soundPrompt)
              }
            )
          }
        }
      }
    }

    // 3. Math Formula or Sequence Banner
    if (lesson.mathFormula != null) {
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(lesson.subcategory.bgHex),
        border = androidx.compose.foundation.BorderStroke(
          1.5.dp,
          Color(lesson.subcategory.colorHex).copy(alpha = 0.5f)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "3. NUMBER PATTERN",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(lesson.subcategory.colorHex)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = lesson.mathFormula,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF263238)
          )
          Spacer(modifier = Modifier.height(6.dp))
          SoundButton(
            audioController = audioController,
            label = "PLAY PATTERN",
            subtext = "Tap to listen",
            emoji = "🔊",
            variant = SoundButtonVariant.PHONEME_CHIP,
            size = SoundButtonSize.SMALL,
            accentColor = Color(lesson.subcategory.colorHex),
            onClick = {
              audioController.speak(lesson.mathFormula)
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
              tint = Color(0xFFFF8F00),
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "4. CHOOSE & FIND",
              fontSize = 13.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFFF8F00)
            )
          }

          SoundButton(
            audioController = audioController,
            label = "HEAR QUESTION",
            emoji = "🔊",
            variant = SoundButtonVariant.PHONEME_CHIP,
            size = SoundButtonSize.SMALL,
            accentColor = Color(0xFFFF8F00),
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
                    audioController.speak("Try again! Find ${lesson.word}")
                  }
                }
                .testTag("quiz_option_${opt.id}")
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
                text = "Superstar! You found the right answer! ⭐ +1",
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
      color = Color(0xFFFFF3E0),
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
            tint = Color(0xFFE65100),
            modifier = Modifier.size(26.dp)
          )
          Column {
            Text(
              text = "5. SPEAK OUT LOUD",
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFE65100)
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
          accentColor = Color(0xFFE65100),
          onClick = {
            audioController.speak("${lesson.speakPrompt} ${lesson.word}!")
          }
        )
      }
    }
  }
}

@Composable
private fun NumberEarTrainingTab(
  minimalPairs: List<NumberMinimalPair>,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit
) {
  var pairIndex by remember { mutableIntStateOf(0) }
  val activePair = minimalPairs.getOrElse(pairIndex) { minimalPairs.first() }
  var selectedNumber by remember(pairIndex) { mutableIntStateOf(-1) }
  var hasAnswered by remember(pairIndex) { mutableStateOf(false) }

  LaunchedEffect(pairIndex) {
    audioController.speak("Listen carefully! Which number is this: ${activePair.spokenWord}?")
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
          text = "🎧 NUMBER EAR TRAINING",
          fontSize = 13.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFF7E57C2)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Listen carefully to the spoken voice & tap the matching number!",
          fontSize = 12.sp,
          color = Color.Gray,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Big Audio Prompt Button
        SoundButton(
          audioController = audioController,
          label = "PLAY SPOKEN NUMBER",
          subtext = "Tap to listen again",
          emoji = "🔊",
          variant = SoundButtonVariant.STANDARD,
          size = SoundButtonSize.HERO,
          accentColor = Color(0xFF7E57C2),
          onClick = {
            audioController.speak(activePair.spokenWord)
          }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Contrast Cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Option 1
          val isOpt1Selected = selectedNumber == activePair.number1
          val isOpt1Correct = activePair.number1 == activePair.spokenNumber
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
                selectedNumber = activePair.number1
                hasAnswered = true
                if (isOpt1Correct) {
                  audioController.playRewardSound()
                  audioController.speak("Yes! That is ${activePair.word1}!")
                  onAwardStars(1)
                } else {
                  audioController.playWrongSound()
                  audioController.speak("Oops! That is ${activePair.word1}. Try again!")
                }
              }
          ) {
            Column(
              modifier = Modifier.padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(text = "${activePair.number1}", fontSize = 42.sp, fontWeight = FontWeight.Black, color = Color(0xFF7E57C2))
              Text(text = activePair.word1, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF37474F))
              Spacer(modifier = Modifier.height(4.dp))
              Text(text = activePair.emoji1, fontSize = 24.sp)
            }
          }

          // Option 2
          val isOpt2Selected = selectedNumber == activePair.number2
          val isOpt2Correct = activePair.number2 == activePair.spokenNumber
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
                selectedNumber = activePair.number2
                hasAnswered = true
                if (isOpt2Correct) {
                  audioController.playRewardSound()
                  audioController.speak("Yes! That is ${activePair.word2}!")
                  onAwardStars(1)
                } else {
                  audioController.playWrongSound()
                  audioController.speak("Oops! That is ${activePair.word2}. Try again!")
                }
              }
          ) {
            Column(
              modifier = Modifier.padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(text = "${activePair.number2}", fontSize = 42.sp, fontWeight = FontWeight.Black, color = Color(0xFF7E57C2))
              Text(text = activePair.word2, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF37474F))
              Spacer(modifier = Modifier.height(4.dp))
              Text(text = activePair.emoji2, fontSize = 24.sp)
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
        audioController.speak(activePair.spokenWord)
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
      accentColor = Color(0xFF7E57C2)
    )
  }
}

@Composable
private fun NumberGridExplorerTab(
  numbers: List<NumberCard>,
  audioController: AudioController
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 6.dp)
  ) {
    Text(
      text = "🧱 INTERACTIVE NUMBER WALL",
      fontSize = 13.sp,
      fontWeight = FontWeight.ExtraBold,
      color = Color(0xFF00897B),
      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    )
    Text(
      text = "Tap any number card to hear its pronunciation and counting rhythm:",
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
      items(numbers) { numCard ->
        NumberSoundButton(
          number = numCard.number,
          word = numCard.word,
          emoji = numCard.emoji,
          countText = numCard.countText,
          audioController = audioController,
          accentColor = Color(0xFFFF8F00),
          size = SoundButtonSize.MEDIUM,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}
