package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class DifficultyLevel(
  val id: String,
  val title: String,
  val ageRange: String,
  val optionCount: Int,
  val iconEmoji: String,
  val description: String
) {
  EASY("easy", "Beginner", "3-4 Yrs", 2, "🌱", "2 big choices • Simple & gentle"),
  MEDIUM("medium", "Explorer", "4-6 Yrs", 3, "🌟", "3 choices • Standard toddler challenge"),
  HARD("hard", "Scholar", "6-8 Yrs", 4, "🚀", "4 choices • Richer & higher focus");

  companion object {
    fun fromId(id: String): DifficultyLevel {
      return entries.firstOrNull { it.id.equals(id, ignoreCase = true) || it.name.equals(id, ignoreCase = true) } ?: MEDIUM
    }
  }
}

enum class CategoryGroup(
  val title: String,
  val iconEmoji: String,
  val colorHex: Long,
  val bgHex: Long
) {
  EVERYDAY_LIFE("Everyday Life", "👨‍👩‍👧", 0xFFE65100, 0xFFFFF3E0),
  LEARNING_DEV("Learning & Development", "🧠", 0xFF6A1B9A, 0xFFEDE7F6),
  WORLD_AROUND("World Around Us", "🌎", 0xFF00695C, 0xFFE0F2F1)
}

enum class GameCategory(
  val id: String,
  val title: String,
  val iconEmoji: String,
  val colorHex: Long,
  val bgHex: Long,
  val subTitle: String = "Explore and learn!",
  val group: CategoryGroup = CategoryGroup.LEARNING_DEV
) {
  // 👨‍👩‍👧 Everyday Life
  COMMUNITY_HELPERS("community_helpers", "Community Helpers", "👮", 0xFFFF9800, 0xFFFFF3E0, "Police, Doctor & Firefighter", CategoryGroup.EVERYDAY_LIFE),
  TOYS("toys", "Toys", "🧸", 0xFFAB47BC, 0xFFF3E5F5, "Teddy, blocks, cars & robots", CategoryGroup.EVERYDAY_LIFE),
  PLACES("places", "Places", "🏫", 0xFF1E88E5, 0xFFE3F2FD, "Home, school, park & zoo", CategoryGroup.EVERYDAY_LIFE),
  HOUSEHOLD_OBJECTS("household_objects", "Household Objects", "🏠", 0xFF5D4037, 0xFFEFEBE9, "Chair, clock, fan & bed", CategoryGroup.EVERYDAY_LIFE),

  // 🧠 Learning & Development
  DAYS("days", "Days", "📅", 0xFF29B6F6, 0xFFE0F7FA, "Monday to Sunday", CategoryGroup.LEARNING_DEV),
  MONTHS("months", "Months", "🗓️", 0xFF7E57C2, 0xFFEDE7F6, "January to December", CategoryGroup.LEARNING_DEV),
  ACTION_WORDS("action_words", "Action Words", "🏃", 0xFFFF7043, 0xFFFBE9E7, "Run, jump, eat & sleep", CategoryGroup.LEARNING_DEV),
  HEALTH("health", "Health & Hygiene", "🏥", 0xFF26A69A, 0xFFE0F2F1, "Doctor, soap & brushing teeth", CategoryGroup.LEARNING_DEV),
  ALPHABETS("alphabets", "Alphabets", "🔤", 0xFF42A5F5, 0xFFE3F2FD, "Learn A to Z with sounds", CategoryGroup.LEARNING_DEV),
  PHONICS("phonics", "Phonics", "🔠", 0xFF26A69A, 0xFFE0F2F1, "Letter sounds & word blending", CategoryGroup.LEARNING_DEV),
  NUMBERS("numbers", "Numbers", "🔢", 0xFFFFB300, 0xFFFFF8E1, "Count 1 to 20 with objects", CategoryGroup.LEARNING_DEV),
  SPELLING("spelling", "Spelling Fun", "✏️", 0xFF7E57C2, 0xFFEDE7F6, "Arrange scrambled letters", CategoryGroup.LEARNING_DEV),
  SENTENCES("sentences", "Sentences", "🗣️", 0xFF26A69A, 0xFFE0F2F1, "Listen & read aloud", CategoryGroup.LEARNING_DEV),

  // 🌎 World Around Us
  NATURE("nature", "Nature", "🌳", 0xFF66BB6A, 0xFFE8F5E9, "Sun, moon, tree & river", CategoryGroup.WORLD_AROUND),
  ANIMALS("animals", "Animals", "🐶", 0xFF42A5F5, 0xFFE3F2FD, "Pets, farm & wild animals", CategoryGroup.WORLD_AROUND),
  FRUITS("fruits", "Fruits", "🍎", 0xFFEF5350, 0xFFFFEBEE, "Yummy apples, bananas & grapes", CategoryGroup.WORLD_AROUND),
  VEGETABLES("vegetables", "Vegetables", "🥦", 0xFF4CAF50, 0xFFE8F5E9, "Carrots, tomatoes & broccoli", CategoryGroup.WORLD_AROUND),
  VEHICLES("vehicles", "Vehicles", "🚗", 0xFF5C6BC0, 0xFFE8EAF6, "Cars, trains & airplanes", CategoryGroup.WORLD_AROUND),
  COLORS("colors", "Colors", "🎨", 0xFFEF5350, 0xFFFFEBEE, "Identify bright colors", CategoryGroup.WORLD_AROUND),
  SHAPES("shapes", "Shapes", "🔺", 0xFFAB47BC, 0xFFF3E5F5, "Learn geometry & forms", CategoryGroup.WORLD_AROUND),
  BODY_PARTS("body_parts", "Body Parts", "👀", 0xFFEC407A, 0xFFFCE4EC, "Eyes, ears, nose & hands", CategoryGroup.WORLD_AROUND),

  // Legacy Aliases for Seamless Backward Compatibility
  FRUITS_VEG("fruits_veg", "Fruits & Veggies", "🍎", 0xFFEF5350, 0xFFFFEBEE, "Yummy fruits & healthy veggies", CategoryGroup.WORLD_AROUND),
  FOOD("food", "Food & Meals", "🍕", 0xFFFFB300, 0xFFFFF8E1, "Tasty treats & meals", CategoryGroup.EVERYDAY_LIFE),
  CLOTHES("clothes", "Clothes", "👕", 0xFF5C6BC0, 0xFFE8EAF6, "Hats, shirts & shoes", CategoryGroup.EVERYDAY_LIFE),
  FAMILY("family", "Family", "👨‍👩‍👧", 0xFF26A69A, 0xFFE0F2F1, "Mom, Dad, Sister & Brother", CategoryGroup.EVERYDAY_LIFE),
  SCHOOL("school", "School", "🏫", 0xFF42A5F5, 0xFFE3F2FD, "Pencils, books & classroom", CategoryGroup.EVERYDAY_LIFE),
  TRANSPORTATION("transportation", "Vehicles", "🚗", 0xFF5C6BC0, 0xFFE8EAF6, "Cars, trains & planes", CategoryGroup.WORLD_AROUND),
  OCCUPATIONS("occupations", "Community Helpers", "👷", 0xFFFF9800, 0xFFFFF3E0, "Doctors, firefighters & teachers", CategoryGroup.EVERYDAY_LIFE),
  FEELINGS("feelings", "Feelings & Actions", "😊", 0xFFEC407A, 0xFFFCE4EC, "Happy, sad, run & jump", CategoryGroup.LEARNING_DEV),
  BIRDS("birds", "Birds", "🐦", 0xFF29B6F6, 0xFFE0F7FA, "Feathered friends & chirps", CategoryGroup.WORLD_AROUND),
  MUSIC("music", "Music", "🎵", 0xFF26A69A, 0xFFE0F2F1, "Drums, pianos & flutes", CategoryGroup.LEARNING_DEV),
  PUZZLES("puzzles", "Puzzles", "🧩", 0xFFEC407A, 0xFFFCE4EC, "Match pieces & shapes", CategoryGroup.EVERYDAY_LIFE),
  GOOD_HABITS("good_habits", "Good Habits", "⭐", 0xFF66BB6A, 0xFFE8F5E9, "Brush teeth & hygiene", CategoryGroup.LEARNING_DEV),
  SEASONS_WEATHER("seasons", "Seasons & Weather", "☀️", 0xFFFFB300, 0xFFFFF8E1, "Sun, rain, snow & wind", CategoryGroup.WORLD_AROUND)
}

data class AlphabetCard(
  val letterUpper: String,
  val letterLower: String,
  val word: String,
  val emoji: String,
  val phoneticSentence: String,
  val exampleSentence: String
)

data class PhonicsWord(
  val word: String,
  val letters: List<String>,
  val sounds: List<String>,
  val emoji: String,
  val categoryLevel: String
)

data class NumberCard(
  val number: Int,
  val word: String,
  val emoji: String,
  val countText: String
)

data class ColorCard(
  val name: String,
  val emoji: String,
  val colorHex: Long,
  val exampleObject: String
)

data class ShapeCard(
  val name: String,
  val emoji: String,
  val sidesDescription: String,
  val exampleObject: String
)

data class GameOption(
  val id: String,
  val label: String,
  val emoji: String,
  val soundResName: String? = null,
  val colorHex: Long? = null
)

data class AnimalCard(
  val id: String,
  val name: String,
  val emoji: String,
  val soundOnomatopoeia: String,
  val group: String,
  val cardColorHex: Long,
  val bgLightHex: Long
)

data class GameQuestion(
  val id: String,
  val category: GameCategory,
  val questionText: String,
  val voicePrompt: String,
  val options: List<GameOption>,
  val correctAnswerId: String
)

data class DailyQuizUiState(
  val questions: List<GameQuestion> = emptyList(),
  val currentIndex: Int = 0,
  val selectedOptionId: String? = null,
  val isAnswered: Boolean = false,
  val isCorrect: Boolean = false,
  val correctCount: Int = 0,
  val scoreStars: Int = 0,
  val bonusStars: Int = 0,
  val isQuizCompleted: Boolean = false,
  val practicedCategoriesText: String = "Animals, Colors & Shapes"
)

@Entity(tableName = "user_progress")
data class UserProgressEntity(
  @PrimaryKey val id: Int = 1,
  val totalStars: Int = 0,
  val activitiesCompleted: Int = 0,
  val soundEnabled: Boolean = true,
  val voiceEnabled: Boolean = true,
  val animationsEnabled: Boolean = true,
  val nightMode: Boolean = false,
  val firstLaunchCompleted: Boolean = false,
  val difficultyLevel: String = "MEDIUM",
  val learningStreak: Int = 3,
  val lastPracticeEpochDay: Long = 0L,
  val dailyReminderEnabled: Boolean = true,
  val reminderTimeHour: Int = 17,
  val reminderTimeMinute: Int = 0
)

@Entity(tableName = "category_stats")
data class CategoryStatEntity(
  @PrimaryKey val categoryId: String,
  val starsEarned: Int = 0,
  val completedCount: Int = 0,
  val percentComplete: Int = 0
)

@Entity(tableName = "unlocked_badges")
data class BadgeEntity(
  @PrimaryKey val badgeId: String,
  val title: String,
  val description: String,
  val iconEmoji: String,
  val unlockedDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "learned_words")
data class LearnedWordEntity(
  @PrimaryKey val word: String,
  val categoryId: String,
  val timestamp: Long = System.currentTimeMillis()
)

data class CategoryItem(
  val id: String,
  val name: String,
  val emoji: String,
  val description: String = "",
  val audioPrompt: String = name
)

data class StructuredCategoryContent(
  val category: GameCategory,
  val group: CategoryGroup,
  val items: List<CategoryItem>
)

