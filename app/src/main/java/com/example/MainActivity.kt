package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.data.models.CategoryStatEntity
import com.example.ui.components.BottomNavBar
import com.example.ui.components.ParentalControlOverlay
import com.example.ui.screens.AlphabetLessonScreen
import com.example.ui.screens.AnimalsExplorerScreen
import com.example.ui.screens.BadgeCollectionScreen
import com.example.ui.screens.CategoryLessonScreen
import com.example.ui.screens.ColorsLessonScreen
import com.example.ui.screens.DailyQuizScreen
import com.example.ui.screens.GameScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnCategoriesScreen
import com.example.ui.screens.MusicScreen
import com.example.ui.screens.NumbersLessonScreen
import com.example.ui.screens.ParentDashboardScreen
import com.example.ui.screens.PhonicsLessonScreen
import com.example.ui.screens.PlayGamesScreen
import com.example.ui.screens.ProgressRewardsScreen
import com.example.ui.screens.PuzzleScreen
import com.example.ui.screens.SentenceLearningScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ShapesLessonScreen
import com.example.ui.screens.SpeakingPracticeScreen
import com.example.ui.screens.SpellingLessonScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.VocabularyCardsScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.BabyWorldTheme
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // Request notification permission for Android 13+
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
      if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
        requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
      }
    }

    // Initialize notification channel and schedule reminder
    com.example.notification.DailyReminderManager.createNotificationChannel(this)
    com.example.notification.DailyReminderManager.scheduleDailyReminder(this, 17, 0)

    setContent {
      BabyWorldTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = Color(0xFFFFFDE7)
        ) {
          BabyWorldApp(viewModel)
        }
      }
    }
  }
}

@Composable
fun BabyWorldApp(viewModel: MainViewModel) {
  val context = androidx.compose.ui.platform.LocalContext.current
  val currentScreen by viewModel.currentScreen.collectAsState()
  val gameUiState by viewModel.gameUiState.collectAsState()
  val userProgress by viewModel.userProgress.collectAsState()
  val categoryStats by viewModel.categoryStats.collectAsState(initial = emptyList())

  var showParentGateModal by remember { mutableStateOf(false) }

  if (showParentGateModal) {
    ParentalControlOverlay(
      soundEnabled = userProgress.soundEnabled,
      onToggleSound = { enabled -> viewModel.toggleSound(enabled) },
      onClearScore = { viewModel.resetAllProgress() },
      onOpenDashboard = {
        showParentGateModal = false
        viewModel.navigateTo(Screen.PARENT_DASHBOARD)
      },
      onDismiss = { showParentGateModal = false }
    )
  }

  val showBottomBar = currentScreen in listOf(
    Screen.HOME,
    Screen.LEARN_CATEGORIES,
    Screen.PLAY_GAMES,
    Screen.SPEAKING_PRACTICE,
    Screen.PROGRESS_REWARDS,
    Screen.BADGE_COLLECTION,
    Screen.VOCABULARY_CARDS
  )

  Scaffold(
    bottomBar = {
      if (showBottomBar) {
        BottomNavBar(
          currentScreen = currentScreen,
          onNavigate = { screen -> viewModel.navigateTo(screen) }
        )
      }
    },
    containerColor = Color(0xFFFFFDE7)
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
        when (screen) {
          Screen.SPLASH -> SplashScreen()

          Screen.WELCOME -> WelcomeScreen(
            onPlayClick = { viewModel.completeFirstLaunch() }
          )

          Screen.HOME -> HomeScreen(
            totalStars = userProgress.totalStars,
            learningStreak = userProgress.learningStreak,
            currentDifficulty = com.example.data.models.DifficultyLevel.fromId(userProgress.difficultyLevel),
            onCategoryClick = { category ->
              viewModel.startCategoryGame(category)
            },
            onDifficultyClick = { difficulty ->
              viewModel.updateDifficultyLevel(difficulty)
            },
            onStartDailyQuiz = { viewModel.startDailyQuiz() },
            onNavigate = { screen -> viewModel.navigateTo(screen) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.LEARN_CATEGORIES -> LearnCategoriesScreen(
            totalStars = userProgress.totalStars,
            learningStreak = userProgress.learningStreak,
            onCategoryClick = { category -> viewModel.startCategoryGame(category) },
            onNavigate = { screen -> viewModel.navigateTo(screen) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.PLAY_GAMES -> PlayGamesScreen(
            totalStars = userProgress.totalStars,
            learningStreak = userProgress.learningStreak,
            onStartGame = { targetScreen, category ->
              if (targetScreen == Screen.DAILY_QUIZ) {
                viewModel.startDailyQuiz()
                viewModel.navigateTo(Screen.DAILY_QUIZ)
              } else if (category != null) {
                viewModel.startCategoryGame(category)
              } else {
                viewModel.navigateTo(targetScreen)
              }
            },
            onNavigate = { screen -> viewModel.navigateTo(screen) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.GAME -> GameScreen(
            state = gameUiState,
            totalStars = userProgress.totalStars,
            onOptionSelected = { option -> viewModel.onOptionSelected(option) },
            onCategorySelected = { category -> viewModel.startCategoryGame(category) },
            onDifficultySelected = { difficulty -> viewModel.updateDifficultyLevel(difficulty) },
            onSpeakOption = { option -> viewModel.speakOption(option) },
            onReplayVoiceClick = { viewModel.speakCurrentQuestion() },
            onRestartClick = { viewModel.restartCategoryGame() },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.ALPHABET_LESSON -> AlphabetLessonScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "alphabet") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.PHONICS_LESSON -> PhonicsLessonScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "phonics") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.NUMBERS_LESSON -> NumbersLessonScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "numbers") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.COLORS_LESSON -> ColorsLessonScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "colors") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.SHAPES_LESSON -> ShapesLessonScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "shapes") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.CATEGORY_LESSON, Screen.FRUITS_VEG_LESSON, Screen.BODY_PARTS_LESSON -> CategoryLessonScreen(
            category = gameUiState.currentCategory,
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, gameUiState.currentCategory.id) },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.ANIMALS_EXPLORER -> AnimalsExplorerScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "animals") },
            onStartQuizMode = {
              viewModel.navigateTo(Screen.GAME)
              viewModel.speakCurrentQuestion()
            },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.SPEAKING_PRACTICE -> SpeakingPracticeScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "speaking") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.SENTENCE_LEARNING -> SentenceLearningScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "sentences") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.SPELLING_LESSON -> SpellingLessonScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "spelling") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.VOCABULARY_CARDS -> VocabularyCardsScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "vocab") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onNavigate = { screen -> viewModel.navigateTo(screen) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.PROGRESS_REWARDS -> ProgressRewardsScreen(
            viewModel = viewModel,
            audioController = viewModel.audioController,
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onNavigate = { screen -> viewModel.navigateTo(screen) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.BADGE_COLLECTION -> BadgeCollectionScreen(
            viewModel = viewModel,
            audioController = viewModel.audioController,
            onBackClick = { viewModel.navigateTo(Screen.PROGRESS_REWARDS) },
            onNavigate = { screen -> viewModel.navigateTo(screen) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.DAILY_QUIZ -> DailyQuizScreen(
            viewModel = viewModel,
            audioController = viewModel.audioController,
            onBackClick = { viewModel.navigateTo(Screen.HOME) },
            onNavigate = { screen -> viewModel.navigateTo(screen) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.MUSIC_PLAYGROUND -> MusicScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.PUZZLE_PLAYGROUND -> PuzzleScreen(
            totalStars = userProgress.totalStars,
            audioController = viewModel.audioController,
            onAwardStars = { stars -> viewModel.awardStarsDirectly(stars, "puzzle") },
            onHomeClick = { viewModel.navigateTo(Screen.HOME) },
            onParentLockClick = { showParentGateModal = true }
          )

          Screen.PARENT_DASHBOARD -> ParentDashboardScreen(
            userProgress = userProgress,
            categoryStats = categoryStats,
            onToggleSound = { enabled -> viewModel.toggleSound(enabled) },
            onToggleVoice = { enabled -> viewModel.toggleVoice(enabled) },
            onToggleAnimations = { enabled -> viewModel.toggleAnimations(enabled) },
            onToggleNightMode = { enabled -> viewModel.toggleNightMode(enabled) },
            onToggleDailyReminder = { enabled -> viewModel.toggleDailyReminder(enabled, context = context) },
            onTestNotification = { viewModel.triggerTestNotification(context) },
            onResetProgress = { viewModel.resetAllProgress() },
            onOpenSettings = { viewModel.navigateTo(Screen.SETTINGS) },
            onBackToGame = { viewModel.navigateTo(Screen.HOME) }
          )

          Screen.SETTINGS -> SettingsScreen(
            userProgress = userProgress,
            onToggleSound = { enabled -> viewModel.toggleSound(enabled) },
            onToggleVoice = { enabled -> viewModel.toggleVoice(enabled) },
            onToggleAnimations = { enabled -> viewModel.toggleAnimations(enabled) },
            onToggleDailyReminder = { enabled -> viewModel.toggleDailyReminder(enabled, context = context) },
            onSelectDifficulty = { difficulty -> viewModel.updateDifficultyLevel(difficulty) },
            onBackClick = { viewModel.navigateTo(Screen.PARENT_DASHBOARD) }
          )
        }
      }
    }
  }
}

