package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.models.PhonicsLessonItem
import com.example.data.models.PhonicsMinimalPair
import com.example.data.models.PhonicsSoundSortItem
import com.example.data.models.PhonicsSubcategory
import com.example.ui.components.BlendSlider
import com.example.ui.components.BoboMascot
import com.example.ui.components.CvcSoundButton
import com.example.ui.components.LessonNavigationControls
import com.example.ui.components.LetterSoundButton
import com.example.ui.components.MascotState
import com.example.ui.components.SoundButton
import com.example.ui.components.SoundButtonSize
import com.example.ui.components.SoundButtonVariant
import com.example.ui.components.StarsBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PhonicsScreenTab(val title: String, val icon: String) {
  LESSON("Lessons & Blending", "📖"),
  MINIMAL_PAIRS("Ear Training", "🎧"),
  SOUND_SORT("Sound Sorter", "🎯")
}

@Composable
fun PhonicsLessonScreen(
  totalStars: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onOpenSoundSortingGame: (() -> Unit)? = null,
  onOpenListeningChallenge: (() -> Unit)? = null,
  onHomeClick: () -> Unit,
  onParentLockClick: () -> Unit
) {
  val allLessonItems = remember { GameContentRepository.getPhonicsLessonItems() }
  val minimalPairs = remember { GameContentRepository.getPhonicsMinimalPairs() }
  val soundSortItems = remember { GameContentRepository.getPhonicsSoundSortItems() }

  var currentTab by remember { mutableStateOf(PhonicsScreenTab.LESSON) }
  var selectedSubcategory by remember { mutableStateOf(PhonicsSubcategory.LETTER_SOUNDS) }

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
        title = "🔤 Phonics & Early Reading",
        onParentLockClick = onParentLockClick
      )
    },
    containerColor = Color(0xFFE0F2F1)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFFE0F2F1),
              Color(0xFFFFFDE7),
              Color(0xFFE8F5E9)
            )
          )
        )
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Navigation bar with Back button & Subcategory Badge
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 3.dp,
            modifier = Modifier
              .clickable { onHomeClick() }
              .testTag("phonics_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color(0xFF00897B),
              modifier = Modifier.padding(10.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(selectedSubcategory.colorHex).copy(alpha = 0.15f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(selectedSubcategory.colorHex))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${selectedSubcategory.emoji} Level ${selectedSubcategory.levelNumber}/15",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(selectedSubcategory.colorHex)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 15-Subcategory Progression Horizontal Scroll Bar
        PhonicsProgressionBar(
          selectedSubcategory = selectedSubcategory,
          onSelectSubcategory = { subcat ->
            selectedSubcategory = subcat
            audioController.playTapSound()
            audioController.speak("Level ${subcat.levelNumber}: ${subcat.title}. ${subcat.pedagogicalGoal}")
          }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mode Tabs (Lesson & Blending / Ear Training / Sound Sorter)
        Surface(
          shape = RoundedCornerShape(18.dp),
          color = Color.White,
          shadowElevation = 4.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            PhonicsScreenTab.entries.forEach { tab ->
              val isSelected = currentTab == tab
              val tabBg by animateColorAsState(
                targetValue = if (isSelected) Color(0xFF00897B) else Color.Transparent,
                label = "tabBg"
              )
              val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color(0xFF546E7A),
                label = "tabText"
              )

              Surface(
                shape = RoundedCornerShape(14.dp),
                color = tabBg,
                modifier = Modifier
                  .weight(1f)
                  .clickable {
                    currentTab = tab
                    audioController.playTapSound()
                    audioController.speak(tab.title)
                  }
                  .testTag("phonics_tab_${tab.name.lowercase()}")
              ) {
                Row(
                  modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(text = tab.icon, fontSize = 16.sp)
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = tab.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    maxLines = 1
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content Area based on Tab
        when (currentTab) {
          PhonicsScreenTab.LESSON -> {
            PhonicsLessonView(
              lessonItem = activeLesson,
              currentIndex = lessonIndex,
              totalInLevel = filteredLessons.size,
              audioController = audioController,
              onAwardStars = { stars ->
                onAwardStars(stars)
                mascotState = MascotState.CELEBRATING
                scope.launch {
                  delay(2500)
                  mascotState = MascotState.IDLE_WAVING
                }
              },
              onPrevious = {
                if (lessonIndex > 0) {
                  lessonIndex--
                  audioController.playTapSound()
                }
              },
              onNext = {
                if (lessonIndex < filteredLessons.size - 1) {
                  lessonIndex++
                  audioController.playTapSound()
                } else {
                  val nextLevel = selectedSubcategory.levelNumber + 1
                  if (nextLevel <= 15) {
                    selectedSubcategory = PhonicsSubcategory.fromLevel(nextLevel)
                    audioController.playRewardSound()
                    onAwardStars(5)
                  }
                }
              }
            )
          }

          PhonicsScreenTab.MINIMAL_PAIRS -> {
            PhonicsMinimalPairsView(
              pairs = minimalPairs,
              audioController = audioController,
              onOpenListeningChallenge = onOpenListeningChallenge,
              onAwardStars = { stars ->
                onAwardStars(stars)
                mascotState = MascotState.CELEBRATING
                scope.launch {
                  delay(2500)
                  mascotState = MascotState.IDLE_WAVING
                }
              }
            )
          }

          PhonicsScreenTab.SOUND_SORT -> {
            PhonicsSoundSortView(
              sortItems = soundSortItems,
              audioController = audioController,
              onOpenSoundSortingGame = onOpenSoundSortingGame,
              onAwardStars = { stars ->
                onAwardStars(stars)
                mascotState = MascotState.CELEBRATING
                scope.launch {
                  delay(2500)
                  mascotState = MascotState.IDLE_WAVING
                }
              }
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bobo Mascot Companion
        BoboMascot(
          modifier = Modifier
            .clickable {
              audioController.speak("You are doing great in phonics! Keep practicing sounds!")
              mascotState = MascotState.CELEBRATING
              scope.launch {
                delay(2000)
                mascotState = MascotState.IDLE_WAVING
              }
            }
            .testTag("phonics_mascot"),
          size = 85.dp,
          state = mascotState
        )

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}

@Composable
fun PhonicsProgressionBar(
  selectedSubcategory: PhonicsSubcategory,
  onSelectSubcategory: (PhonicsSubcategory) -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "15-Step Phonics Ladder 🪜",
        fontSize = 13.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(0xFF004D40)
      )
      Text(
        text = selectedSubcategory.subtitle,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF00796B)
      )
    }

    Spacer(modifier = Modifier.height(6.dp))

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      PhonicsSubcategory.entries.forEach { subcat ->
        val isSelected = subcat == selectedSubcategory
        val cardColor by animateColorAsState(
          targetValue = if (isSelected) Color(subcat.colorHex) else Color.White,
          label = "subcatColor"
        )
        val contentColor by animateColorAsState(
          targetValue = if (isSelected) Color.White else Color(0xFF37474F),
          label = "subcatContent"
        )

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = cardColor,
          shadowElevation = if (isSelected) 6.dp else 2.dp,
          modifier = Modifier
            .clickable { onSelectSubcategory(subcat) }
            .testTag("phonics_level_${subcat.levelNumber}")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isSelected) Color.White.copy(alpha = 0.25f) else Color(subcat.bgHex)),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${subcat.levelNumber}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) Color.White else Color(subcat.colorHex)
              )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "${subcat.emoji} ${subcat.title}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = contentColor
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhonicsLessonView(
  lessonItem: PhonicsLessonItem,
  currentIndex: Int,
  totalInLevel: Int,
  audioController: AudioController,
  onAwardStars: (Int) -> Unit,
  onPrevious: () -> Unit,
  onNext: () -> Unit
) {
  var selectedQuizOption by remember { mutableStateOf<String?>(null) }
  var isMagicETransformed by remember { mutableStateOf(false) }

  LaunchedEffect(lessonItem.id) {
    selectedQuizOption = null
    isMagicETransformed = false
  }

  Surface(
    shape = RoundedCornerShape(28.dp),
    color = Color.White,
    shadowElevation = 8.dp,
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Header Tag: Sound / Topic
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(lessonItem.subcategory.bgHex)
        ) {
          Text(
            text = "🎯 ${lessonItem.soundOrTopic}",
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(lessonItem.subcategory.colorHex),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }

        Text(
          text = "${currentIndex + 1} of $totalInLevel",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF78909C)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 👂 1. HEAR SECTION: Interactive SoundButton Hero / Specialized Mode
      when (lessonItem.subcategory) {
        PhonicsSubcategory.LETTER_SOUNDS -> {
          val targetLetter = lessonItem.letters.firstOrNull() ?: lessonItem.targetWord.take(1)
          val firstSound = lessonItem.sounds.firstOrNull() ?: lessonItem.soundOrTopic
          Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "🔤 Tap Letter Tile to Hear Sound:",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF00695C),
              modifier = Modifier.padding(bottom = 8.dp)
            )

            LetterSoundButton(
              letter = targetLetter,
              phoneticSound = firstSound,
              emoji = lessonItem.emoji,
              exampleWord = lessonItem.targetWord,
              accentColor = Color(0xFF00897B),
              size = SoundButtonSize.LARGE,
              audioController = audioController,
              onClick = { onAwardStars(1) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            SoundButton(
              audioController = audioController,
              soundPrompt = lessonItem.hearPrompt,
              label = "LISTEN LESSON PROMPT 🔊",
              subtext = lessonItem.hearPrompt,
              variant = SoundButtonVariant.STANDARD,
              size = SoundButtonSize.SMALL,
              accentColor = Color(0xFF00897B),
              onClick = { onAwardStars(1) }
            )
          }
        }

        PhonicsSubcategory.CVC_WORDS -> {
          Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "🐱 CVC Word Sound Builder:",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF006064),
              modifier = Modifier.padding(bottom = 8.dp)
            )

            CvcSoundButton(
              word = lessonItem.targetWord,
              letters = lessonItem.letters,
              sounds = lessonItem.sounds,
              emoji = lessonItem.emoji,
              accentColor = Color(0xFF00ACC1),
              audioController = audioController,
              onClick = { onAwardStars(1) }
            )
          }
        }

        else -> {
          // Standard Hero Sound Card
          SoundButton(
            audioController = audioController,
            word = lessonItem.targetWord,
            phoneticSound = lessonItem.sounds.joinToString(" "),
            soundPrompt = lessonItem.hearPrompt,
            emoji = lessonItem.emoji,
            subtext = lessonItem.hearPrompt,
            variant = SoundButtonVariant.HERO_CARD,
            accentColor = Color(lessonItem.subcategory.colorHex),
            onClick = { onAwardStars(1) }
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 👀 2. SEE SECTION: Interactive Word & Sound Tiles
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(horizontal = 4.dp)
        ) {
          Icon(Icons.Default.Visibility, contentDescription = null, tint = Color(0xFF00897B), modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "👀 See & Tap Examples:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004D40)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          lessonItem.seeExamples.take(4).forEach { ex ->
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = Color(0xFFE0F2F1),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFB2DFDB)),
              modifier = Modifier
                .weight(1f)
                .clickable {
                  audioController.speak("${ex.word}! ${ex.soundHighlight}")
                  audioController.playTapSound()
                  onAwardStars(1)
                }
                .testTag("sound_example_${ex.word.lowercase()}")
            ) {
              Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(text = ex.emoji, fontSize = 28.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = ex.word,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Black,
                  color = Color(0xFF00695C)
                )
                Text(
                  text = ex.soundHighlight,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF00897B)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // 🧩 3. TACTILE BLEND SLIDER COMPONENT
      BlendSlider(
        letters = lessonItem.letters,
        sounds = lessonItem.sounds,
        targetWord = lessonItem.targetWord,
        targetEmoji = lessonItem.emoji,
        audioController = audioController,
        onMergedSuccess = {
          onAwardStars(2)
        }
      )

      // 🪄 4. SPECIALIZED: Magic-E Transformation Card (if applicable)
      if (lessonItem.magicEBefore != null && lessonItem.magicEAfter != null) {
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFEDE7F6),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFB39DDB)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "🪄 Magic-E Transformation Wand!",
              fontSize = 13.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF4A148C)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = lessonItem.magicEBefore.second, fontSize = 36.sp)
                Text(
                  text = lessonItem.magicEBefore.first,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Black,
                  color = Color(0xFF5E35B1)
                )
                Text(text = "Short Vowel", fontSize = 10.sp, color = Color(0xFF7E57C2))
              }

              Button(
                onClick = {
                  isMagicETransformed = !isMagicETransformed
                  audioController.playRewardSound()
                  val activeText = if (isMagicETransformed) {
                    "${lessonItem.magicEBefore.first} plus magic E becomes ${lessonItem.magicEAfter.first}!"
                  } else {
                    lessonItem.magicEBefore.first
                  }
                  audioController.speak(activeText)
                  onAwardStars(2)
                },
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFF673AB7)
                ),
                shape = RoundedCornerShape(16.dp)
              ) {
                Text(if (isMagicETransformed) "✨ Revert" else "🪄 Add 'E'", fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }

              AnimatedVisibility(
                visible = isMagicETransformed,
                enter = scaleIn() + fadeIn()
              ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(text = lessonItem.magicEAfter.second, fontSize = 36.sp)
                  Text(
                    text = lessonItem.magicEAfter.first,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF2E7D32)
                  )
                  Text(text = "Long Vowel!", fontSize = 10.sp, color = Color(0xFF388E3C), fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }

      // 🏡 5. SPECIALIZED: Word Family Rhyme House (if applicable)
      if (lessonItem.wordFamilyList.isNotEmpty()) {
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFE8F5E9),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFA5D6A7)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "🏡 Word Family Rhyme House: ${lessonItem.soundOrTopic}",
              fontSize = 13.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF1B5E20)
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center,
              maxItemsInEachRow = 5
            ) {
              lessonItem.wordFamilyList.forEach { (w, em) ->
                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = Color.White,
                  shadowElevation = 2.dp,
                  modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                      audioController.speak("$w! $w rhymes in the ${lessonItem.soundOrTopic}")
                      audioController.playTapSound()
                    }
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(text = em, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = w.uppercase(),
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Black,
                      color = Color(0xFF2E7D32)
                    )
                  }
                }
              }
            }
          }
        }
      }

      // 📖 6. SPECIALIZED: Decodable Story Sentence (if applicable)
      if (lessonItem.decodableSentence != null) {
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFE3F2FD),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF90CAF9)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "📖 Early Reading Decodable Story",
              fontSize = 13.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF0D47A1)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "“${lessonItem.decodableSentence}”",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFF1565C0),
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
              onClick = {
                audioController.speak(lessonItem.decodableSentence)
                audioController.playRewardSound()
                onAwardStars(2)
              },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
              shape = RoundedCornerShape(20.dp)
            ) {
              Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
              Spacer(modifier = Modifier.width(6.dp))
              Text("Read Sentence With Me 🗣️", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // 👆 7. CHOOSE / QUIZ SECTION
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF3E5F5),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFCE93D8)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "👆 Quick Sound Quiz:",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6A1B9A)
          )
          Text(
            text = lessonItem.chooseQuestion,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF4A148C),
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            lessonItem.chooseOptions.forEach { opt ->
              val isSelected = selectedQuizOption == opt.id
              val isCorrect = opt.id == lessonItem.correctOptionId

              val btnColor = when {
                isSelected && isCorrect -> Color(0xFF4CAF50)
                isSelected && !isCorrect -> Color(0xFFE53935)
                else -> Color.White
              }

              val textColor = if (isSelected) Color.White else Color(0xFF4A148C)

              Surface(
                shape = RoundedCornerShape(14.dp),
                color = btnColor,
                shadowElevation = 3.dp,
                modifier = Modifier
                  .weight(1f)
                  .clickable {
                    selectedQuizOption = opt.id
                    if (isCorrect) {
                      audioController.playMatchSound()
                      audioController.speak("Awesome! ${opt.label} is correct!")
                      onAwardStars(3)
                    } else {
                      audioController.playTapSound()
                      audioController.speak("Good try! Let's listen again.")
                    }
                  }
                  .testTag("quiz_opt_${opt.id}")
              ) {
                Column(
                  modifier = Modifier.padding(10.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Text(text = opt.emoji, fontSize = 26.sp)
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = opt.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                  )
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 🎤 8. SPEAK PRACTICE BUTTON
      Button(
        onClick = {
          audioController.speak(lessonItem.speakPrompt)
          audioController.playRewardSound()
          onAwardStars(2)
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("phonics_speak_practice"),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = Color(0xFF00897B),
          contentColor = Color.White
        )
      ) {
        Icon(Icons.Default.Mic, contentDescription = "Speak Practice")
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "SPEAK: “${lessonItem.speakPrompt}” 🎤",
          fontSize = 13.sp,
          fontWeight = FontWeight.ExtraBold
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Standardized Navigation: Previous, Play/Hear Target Sound, Next Lesson
      LessonNavigationControls(
        onPrevious = onPrevious,
        onPlay = {
          audioController.speak(lessonItem.hearPrompt)
        },
        onNext = onNext,
        canPrevious = currentIndex > 0,
        canNext = true,
        playLabel = "HEAR SOUND 🔊",
        currentIndex = currentIndex,
        totalCount = totalInLevel,
        accentColor = Color(0xFF00897B)
      )
    }
  }
}

@Composable
fun PhonicsMinimalPairsView(
  pairs: List<PhonicsMinimalPair>,
  audioController: AudioController,
  onOpenListeningChallenge: (() -> Unit)? = null,
  onAwardStars: (Int) -> Unit
) {
  var currentPairIndex by remember { mutableIntStateOf(0) }
  var selectedChoice by remember { mutableStateOf<String?>(null) }

  val activePair = pairs.getOrElse(currentPairIndex) { pairs.first() }

  LaunchedEffect(currentPairIndex) {
    selectedChoice = null
    audioController.speak(activePair.promptVoice)
  }

  Surface(
    shape = RoundedCornerShape(28.dp),
    color = Color.White,
    shadowElevation = 8.dp,
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (onOpenListeningChallenge != null) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFEDE7F6),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF7E57C2)),
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              audioController.playTapSound()
              onOpenListeningChallenge()
            }
            .testTag("launch_full_listening_challenge_banner")
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Text("🎧", fontSize = 24.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Full Listening Challenge Game",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF5E35B1)
                )
                Text(
                  text = "12+ pairs with slow replay & soundwaves!",
                  fontSize = 11.sp,
                  color = Color(0xFF78909C)
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color(0xFF5E35B1)
            ) {
              Text(
                text = "Play 🚀",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFEDE7F6)
        ) {
          Text(
            text = "🎧 Ear Training • Minimal Pairs",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF5E35B1),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Text(
          text = "${currentPairIndex + 1} of ${pairs.size}",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF78909C)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "👂 Listen to the secret word!",
        fontSize = 18.sp,
        fontWeight = FontWeight.Black,
        color = Color(0xFF311B92)
      )

      Text(
        text = "Contrast: ${activePair.contrastDescription}",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF5E35B1)
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Big Sound Button to Replay
      Button(
        onClick = {
          audioController.speak(activePair.promptVoice)
          audioController.playTapSound()
        },
        modifier = Modifier
          .size(90.dp)
          .testTag("minimal_pair_audio_btn"),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2))
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.VolumeUp,
          contentDescription = "Replay Sound",
          tint = Color.White,
          modifier = Modifier.size(44.dp)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Tap speaker to hear again 🔊",
        fontSize = 11.sp,
        color = Color(0xFF78909C)
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Two Big Choice Cards (Word 1 vs Word 2)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        val isChoice1Correct = activePair.spokenWord.equals(activePair.word1, ignoreCase = true)
        val isChoice1Selected = selectedChoice == activePair.word1

        val card1Color = when {
          isChoice1Selected && isChoice1Correct -> Color(0xFFE8F5E9)
          isChoice1Selected && !isChoice1Correct -> Color(0xFFFFEBEE)
          else -> Color(0xFFEDE7F6)
        }

        val border1Color = when {
          isChoice1Selected && isChoice1Correct -> Color(0xFF4CAF50)
          isChoice1Selected && !isChoice1Correct -> Color(0xFFE53935)
          else -> Color(0xFFB39DDB)
        }

        Surface(
          shape = RoundedCornerShape(20.dp),
          color = card1Color,
          border = androidx.compose.foundation.BorderStroke(2.dp, border1Color),
          shadowElevation = 4.dp,
          modifier = Modifier
            .weight(1f)
            .height(130.dp)
            .clickable {
              selectedChoice = activePair.word1
              if (isChoice1Correct) {
                audioController.playMatchSound()
                audioController.speak("Correct! You heard ${activePair.word1}!")
                onAwardStars(3)
              } else {
                audioController.playTapSound()
                audioController.speak("Not quite! The word was ${activePair.spokenWord}.")
              }
            }
            .testTag("mp_choice_${activePair.word1}")
        ) {
          Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(text = activePair.emoji1, fontSize = 42.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = activePair.word1,
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFF4A148C)
            )
          }
        }

        val isChoice2Correct = activePair.spokenWord.equals(activePair.word2, ignoreCase = true)
        val isChoice2Selected = selectedChoice == activePair.word2

        val card2Color = when {
          isChoice2Selected && isChoice2Correct -> Color(0xFFE8F5E9)
          isChoice2Selected && !isChoice2Correct -> Color(0xFFFFEBEE)
          else -> Color(0xFFEDE7F6)
        }

        val border2Color = when {
          isChoice2Selected && isChoice2Correct -> Color(0xFF4CAF50)
          isChoice2Selected && !isChoice2Correct -> Color(0xFFE53935)
          else -> Color(0xFFB39DDB)
        }

        Surface(
          shape = RoundedCornerShape(20.dp),
          color = card2Color,
          border = androidx.compose.foundation.BorderStroke(2.dp, border2Color),
          shadowElevation = 4.dp,
          modifier = Modifier
            .weight(1f)
            .height(130.dp)
            .clickable {
              selectedChoice = activePair.word2
              if (isChoice2Correct) {
                audioController.playMatchSound()
                audioController.speak("Awesome! You heard ${activePair.word2}!")
                onAwardStars(3)
              } else {
                audioController.playTapSound()
                audioController.speak("Almost! The word was ${activePair.spokenWord}.")
              }
            }
            .testTag("mp_choice_${activePair.word2}")
        ) {
          Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Text(text = activePair.emoji2, fontSize = 42.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = activePair.word2,
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFF4A148C)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Next Pair Button
      Button(
        onClick = {
          currentPairIndex = (currentPairIndex + 1) % pairs.size
          audioController.playTapSound()
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E35B1))
      ) {
        Text("Next Listening Pair 🚀", fontWeight = FontWeight.Bold, fontSize = 14.sp)
      }
    }
  }
}

@Composable
fun PhonicsSoundSortView(
  sortItems: List<PhonicsSoundSortItem>,
  audioController: AudioController,
  onOpenSoundSortingGame: (() -> Unit)? = null,
  onAwardStars: (Int) -> Unit
) {
  var currentSortIndex by remember { mutableIntStateOf(0) }
  val activeSort = sortItems.getOrElse(currentSortIndex) { sortItems.first() }

  val selectedOptionIds = remember { mutableStateListOf<String>() }
  var isChecked by remember { mutableStateOf(false) }

  LaunchedEffect(currentSortIndex) {
    selectedOptionIds.clear()
    isChecked = false
    audioController.speak("Which words start with the sound ${activeSort.targetSound}?")
  }

  Surface(
    shape = RoundedCornerShape(28.dp),
    color = Color.White,
    shadowElevation = 8.dp,
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      if (onOpenSoundSortingGame != null) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFFFF3E0),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFF8F00)),
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              audioController.playTapSound()
              onOpenSoundSortingGame()
            }
            .testTag("launch_drag_drop_sound_sort")
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Text("🎯", fontSize = 24.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Tactile Drag & Drop Sorter",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFFE65100)
                )
                Text(
                  text = "Drag items into /m/ vs /s/ baskets with haptics!",
                  fontSize = 11.sp,
                  color = Color(0xFF78909C)
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color(0xFFE65100)
            ) {
              Text(
                text = "Play 🚀",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFFFF3E0)
        ) {
          Text(
            text = "🎯 Sound Sorter Challenge",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFE65100),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Text(
          text = "${currentSortIndex + 1} of ${sortItems.size}",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF78909C)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = activeSort.prompt,
        fontSize = 18.sp,
        fontWeight = FontWeight.Black,
        color = Color(0xFFE65100),
        textAlign = TextAlign.Center
      )

      Text(
        text = "Target Sound: Letter ${activeSort.targetLetter} (${activeSort.targetSound})",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB8C00)
      )

      Spacer(modifier = Modifier.height(16.dp))

      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        val pairs = activeSort.options.chunked(2)
        pairs.forEach { rowOptions ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            rowOptions.forEach { opt ->
              val isSelected = selectedOptionIds.contains(opt.id)

              val bgColor = when {
                isChecked && isSelected && opt.isCorrect -> Color(0xFFC8E6C9)
                isChecked && isSelected && !opt.isCorrect -> Color(0xFFFFCDD2)
                isSelected -> Color(0xFFFFE082)
                else -> Color(0xFFFFF8E1)
              }

              Surface(
                shape = RoundedCornerShape(18.dp),
                color = bgColor,
                border = androidx.compose.foundation.BorderStroke(
                  2.dp,
                  if (isSelected) Color(0xFFFF8F00) else Color(0xFFFFE082)
                ),
                shadowElevation = 3.dp,
                modifier = Modifier
                  .weight(1f)
                  .clickable {
                    if (selectedOptionIds.contains(opt.id)) {
                      selectedOptionIds.remove(opt.id)
                    } else {
                      selectedOptionIds.add(opt.id)
                    }
                    audioController.playTapSound()
                    audioController.speak("${opt.word}!")
                  }
                  .testTag("sort_card_${opt.id}")
              ) {
                Column(
                  modifier = Modifier.padding(14.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Text(text = opt.emoji, fontSize = 36.sp)
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = opt.word,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF5D4037)
                  )
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Check Answers Button
      Button(
        onClick = {
          isChecked = true
          val correctCount = activeSort.options.count { it.isCorrect && selectedOptionIds.contains(it.id) }
          val mistakeCount = activeSort.options.count { !it.isCorrect && selectedOptionIds.contains(it.id) }

          if (correctCount >= 2 && mistakeCount == 0) {
            audioController.playRewardSound()
            audioController.speak("Super sorting! You found all words starting with ${activeSort.targetSound}!")
            onAwardStars(5)
          } else {
            audioController.playTapSound()
            audioController.speak("Good job trying! Keep listening to the initial sounds.")
            onAwardStars(2)
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("check_sort_answers"),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
      ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null)
        Spacer(modifier = Modifier.width(6.dp))
        Text("Check My Answers 🌟", fontSize = 14.sp, fontWeight = FontWeight.Bold)
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Next Challenge Button
      Button(
        onClick = {
          currentSortIndex = (currentSortIndex + 1) % sortItems.size
          audioController.playTapSound()
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp),
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFB8C00))
      ) {
        Text("Next Sound Sorter 🚀", fontSize = 13.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}
