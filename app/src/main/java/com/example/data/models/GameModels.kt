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
  TOYS("toys", "Toys", "🧸", 0xFFAB47BC, 0xFFF3E5F5, "Teddy, blocks, cars & robots", CategoryGroup.EVERYDAY_LIFE),
  PLACES("places", "Places", "🏫", 0xFF1E88E5, 0xFFE3F2FD, "Home, school, park & zoo", CategoryGroup.EVERYDAY_LIFE),
  HOUSEHOLD_OBJECTS("household_objects", "Household Objects", "🏠", 0xFF5D4037, 0xFFEFEBE9, "Chair, clock, fan & bed", CategoryGroup.EVERYDAY_LIFE),
  CLOTHES("clothes", "Clothes & Shoes", "👕", 0xFF5C6BC0, 0xFFE8EAF6, "Hats, shirts, pants & shoes", CategoryGroup.EVERYDAY_LIFE),
  FAMILY("family", "Family Members", "👨‍👩‍👧", 0xFF26A69A, 0xFFE0F2F1, "Mom, Dad, Sister & Brother", CategoryGroup.EVERYDAY_LIFE),

  // 🧠 Learning & Development
  DAYS("days", "Days", "📅", 0xFF29B6F6, 0xFFE0F7FA, "Monday to Sunday", CategoryGroup.LEARNING_DEV),
  MONTHS("months", "Months", "🗓️", 0xFF7E57C2, 0xFFEDE7F6, "January to December", CategoryGroup.LEARNING_DEV),
  ACTION_WORDS("action_words", "Action Words", "🏃", 0xFFFF7043, 0xFFFBE9E7, "Run, jump, eat & sleep", CategoryGroup.LEARNING_DEV),
  ALPHABETS("alphabets", "Alphabets (A-Z)", "🔤", 0xFF42A5F5, 0xFFE3F2FD, "Learn A to Z with sounds", CategoryGroup.LEARNING_DEV),
  PHONICS("phonics", "Phonics & Sounds", "🔤", 0xFF26A69A, 0xFFE0F2F1, "Sounds, blending, word families & reading", CategoryGroup.LEARNING_DEV),
  NUMBERS("numbers", "Numbers (1-20)", "🔢", 0xFFFFB300, 0xFFFFF8E1, "Count 1 to 20 with objects", CategoryGroup.LEARNING_DEV),
  SPELLING("spelling", "Spelling Fun", "✏️", 0xFF7E57C2, 0xFFEDE7F6, "Arrange scrambled letters", CategoryGroup.LEARNING_DEV),
  SENTENCES("sentences", "Sentences", "🗣️", 0xFF26A69A, 0xFFE0F2F1, "Listen & read aloud", CategoryGroup.LEARNING_DEV),
  MUSIC("music", "Music & Sounds", "🎵", 0xFF00897B, 0xFFE0F2F1, "Drums, pianos & flutes", CategoryGroup.LEARNING_DEV),
  PUZZLES("puzzles", "Fun Puzzles", "🧩", 0xFFEC407A, 0xFFFCE4EC, "Match pieces & shapes", CategoryGroup.LEARNING_DEV),

  // 🌎 World Around Us
  ANIMALS("animals", "Animals & Pets", "🐶", 0xFF42A5F5, 0xFFE3F2FD, "Pets, farm & wild animals", CategoryGroup.WORLD_AROUND),
  FRUITS("fruits", "Fruits", "🍎", 0xFFEF5350, 0xFFFFEBEE, "Yummy apples, bananas & grapes", CategoryGroup.WORLD_AROUND),
  VEGETABLES("vegetables", "Vegetables", "🥦", 0xFF4CAF50, 0xFFE8F5E9, "Carrots, tomatoes & broccoli", CategoryGroup.WORLD_AROUND),
  VEHICLES("vehicles", "Vehicles & Transport", "🚗", 0xFF5C6BC0, 0xFFE8EAF6, "Cars, trains & airplanes", CategoryGroup.WORLD_AROUND),
  COLORS("colors", "Colors", "🎨", 0xFFEF5350, 0xFFFFEBEE, "Identify bright colors", CategoryGroup.WORLD_AROUND),
  SHAPES("shapes", "Shapes", "🔺", 0xFFAB47BC, 0xFFF3E5F5, "Learn geometry & forms", CategoryGroup.WORLD_AROUND),
  BODY_PARTS("body_parts", "Body Parts", "👀", 0xFFEC407A, 0xFFFCE4EC, "Eyes, ears, nose & hands", CategoryGroup.WORLD_AROUND)
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

enum class PhonicsSubcategory(
  val levelNumber: Int,
  val title: String,
  val emoji: String,
  val subtitle: String,
  val pedagogicalGoal: String,
  val colorHex: Long,
  val bgHex: Long
) {
  LETTER_SOUNDS(1, "Letter Sounds", "🔤", "A → /a/, B → /b/, C → /k/", "Learn the distinct sound each letter makes", 0xFF00897B, 0xFFE0F2F1),
  SHORT_VOWELS(2, "Short Vowels", "🍎", "a, e, i, o, u in words", "Master short vowel core sounds", 0xFFE53935, 0xFFFFEBEE),
  BEGINNING_SOUNDS(3, "Beginning Sounds", "🐶", "🐶 Dog starts with /d/", "Identify the first sound in spoken words", 0xFFFF8F00, 0xFFFFF3E0),
  ENDING_SOUNDS(4, "Ending Sounds", "🎯", "🐱 Cat ends with /t/", "Isolate the final consonant sound", 0xFF8E24AA, 0xFFF3E5F5),
  MIDDLE_SOUNDS(5, "Middle Sounds", "🔠", "🐱 Cat middle vowel is /æ/", "Identify the medial vowel in 3-letter words", 0xFF3949AB, 0xFFE8EAF6),
  CVC_WORDS(6, "CVC Words", "🐱", "cat, sun, dog, pig, bed", "Read consonant-vowel-consonant words", 0xFF00ACC1, 0xFFE0F7FA),
  WORD_FAMILIES(7, "Word Families", "🏡", "-at, -an, -og, -ig, -un", "Recognize rhyming ending sound families", 0xFF43A047, 0xFFE8F5E9),
  BLENDING(8, "Blending", "🧩", "c + a + t → cat", "Join individual sounds smoothly into whole words", 0xFFFB8C00, 0xFFFFF3E0),
  SEGMENTING(9, "Segmenting", "✂️", "dog → d + o + g", "Break whole words into individual sound parts", 0xFF5E35B1, 0xFFEDE7F6),
  DIGRAPHS(10, "Digraphs", "✨", "sh, ch, th, wh, ph", "Discover two letters making one special sound", 0xFFD81B60, 0xFFFCE4EC),
  CONSONANT_BLENDS(11, "Consonant Blends", "🚀", "bl, cl, fl, br, st, sp", "Blend two consonant sounds together", 0xFF1E88E5, 0xFFE3F2FD),
  LONG_VOWELS(12, "Long Vowels", "🍰", "cake, bike, home, cube", "Learn vowels that say their own name", 0xFFFFB300, 0xFFFFF8E1),
  SILENT_E(13, "Silent E (Magic E)", "🪄", "cap → cape, kit → kite", "Watch Magic-E change short vowels to long", 0xFF6D4C41, 0xFFEFEBE9),
  SIGHT_WORDS(14, "Sight Words", "👁️", "the, is, a, I, to, and, you", "Recognize high-frequency words instantly", 0xFF00897B, 0xFFE0F2F1),
  EARLY_READING(15, "Early Reading", "📖", "Simple decodable sentences", "Read complete illustrated sentences confidently", 0xFF1E88E5, 0xFFE3F2FD);

  companion object {
    fun fromLevel(level: Int): PhonicsSubcategory {
      return entries.firstOrNull { it.levelNumber == level } ?: LETTER_SOUNDS
    }
  }
}

data class PhonicsSeeExample(
  val word: String,
  val emoji: String,
  val soundHighlight: String
)

data class PhonicsLessonItem(
  val id: String,
  val subcategory: PhonicsSubcategory,
  val soundOrTopic: String,          // e.g. "/b/", "Short A", "-at Family", "Magic E"
  val targetWord: String,            // e.g. "BAT", "APPLE", "CAPE"
  val letters: List<String>,         // e.g. ["B", "A", "T"]
  val sounds: List<String>,          // e.g. ["/b/", "/æ/", "/t/"]
  val emoji: String,                 // e.g. "🦇"
  val hearPrompt: String,            // e.g. "B says /b/! /b/ for Ball and Bat."
  val seeExamples: List<PhonicsSeeExample>, // e.g. [("Bee", "🐝", "/b/"), ("Ball", "⚽", "/b/")]
  val chooseQuestion: String,        // e.g. "Which picture starts with /b/?"
  val chooseOptions: List<GameOption>,
  val correctOptionId: String,
  val blendSequence: String,         // e.g. "B + A + T = BAT"
  val speakPrompt: String,           // e.g. "Say 'B'!" or "Say 'BAT'!"
  val magicEBefore: Pair<String, String>? = null, // e.g. ("CAP", "🧢")
  val magicEAfter: Pair<String, String>? = null,  // e.g. ("CAPE", "🦸‍♂️")
  val wordFamilyList: List<Pair<String, String>> = emptyList(), // e.g. [("cat", "🐱"), ("bat", "🦇")]
  val decodableSentence: String? = null // e.g. "The cat sat on the mat."
)

data class PhonicsMinimalPair(
  val id: String,
  val word1: String,
  val emoji1: String,
  val word2: String,
  val emoji2: String,
  val contrastDescription: String,   // e.g. "/b/ vs /k/" or "/ɪ/ vs /æ/"
  val spokenWord: String,            // "bat" or "cat"
  val promptVoice: String = "Which word did you hear?",
  val category: String = "Consonant Sounds", // e.g. "Consonants", "Vowels", "Digraphs", "Rhyming"
  val funFact: String = ""
)

data class PhonicsSoundSortItem(
  val id: String,
  val targetSound: String,           // e.g. "/m/"
  val targetLetter: String,          // e.g. "M"
  val prompt: String,                // e.g. "Which words start with /m/?"
  val options: List<SoundSortOption>
)

data class SoundSortOption(
  val id: String,
  val word: String,
  val emoji: String,
  val isCorrect: Boolean
)

data class SoundBucket(
  val sound: String,          // e.g. "/m/"
  val letter: String,         // e.g. "M"
  val name: String,           // e.g. "Monkey M"
  val emoji: String,          // e.g. "🐒"
  val colorHex: Long,         // e.g. 0xFFFF8F00
  val bgHex: Long             // e.g. 0xFFFFF3E0
)

data class SoundSortGameItem(
  val id: String,
  val word: String,           // e.g. "Monkey"
  val emoji: String,          // e.g. "🐒"
  val targetLetter: String,   // e.g. "M"
  val targetSound: String,    // e.g. "/m/"
  val hint: String = ""       // e.g. "M-M-Monkey! Starts with /m/."
)

data class SoundSortRound(
  val id: String,
  val title: String,          // e.g. "Sound /m/ vs /s/"
  val description: String,    // e.g. "Sort items starting with M (Monkey) vs S (Sun)"
  val bucketA: SoundBucket,
  val bucketB: SoundBucket,
  val items: List<SoundSortGameItem>
)

data class NumberCard(
  val number: Int,
  val word: String,
  val emoji: String,
  val countText: String
)

enum class NumberSubcategory(
  val levelNumber: Int,
  val title: String,
  val emoji: String,
  val subtitle: String,
  val pedagogicalGoal: String,
  val colorHex: Long,
  val bgHex: Long
) {
  COUNTING_1_5(1, "Numbers 1-5", "🌱", "Early Counting", "Count small quantities 1 to 5 with touch and audio", 0xFFFF8F00, 0xFFFFF8E1),
  COUNTING_6_10(2, "Numbers 6-10", "🖐️", "Ten Frame Explorer", "Count quantities up to 10 and recognize patterns", 0xFF00897B, 0xFFE0F2F1),
  COUNTING_11_20(3, "Teen Numbers 11-20", "🚀", "Double-Digit Journey", "Learn teen numbers 11 through 20 with audio rhythm", 0xFF7E57C2, 0xFFEDE7F6),
  NUMBER_PATTERNS(4, "Skip Counting", "🔢", "2s, 5s & 10s", "Recognize sequence patterns and skip counting rhythms", 0xFF1E88E5, 0xFFE3F2FD),
  NUMBER_BONDS(5, "Number Bonds", "➕", "Early Addition", "Discover how numbers combine to make totals", 0xFFE91E63, 0xFFFCE4EC);

  companion object {
    fun fromLevel(level: Int): NumberSubcategory =
      entries.firstOrNull { it.levelNumber == level } ?: COUNTING_1_5
  }
}

data class NumberSeeExample(
  val label: String,
  val emoji: String,
  val count: Int,
  val soundPrompt: String = label
)

data class NumberLessonItem(
  val id: String,
  val subcategory: NumberSubcategory,
  val number: Int,
  val word: String,
  val emoji: String,
  val countText: String,
  val hearPrompt: String,
  val seeExamples: List<NumberSeeExample>,
  val chooseQuestion: String,
  val chooseOptions: List<GameOption>,
  val correctOptionId: String,
  val mathFormula: String? = null,
  val speakPrompt: String = "Say '$word'!"
)

data class NumberMinimalPair(
  val id: String,
  val number1: Int,
  val word1: String,
  val emoji1: String,
  val number2: Int,
  val word2: String,
  val emoji2: String,
  val spokenNumber: Int,
  val spokenWord: String,
  val hint: String = "Listen carefully to the spoken number!"
)

data class ColorCard(
  val name: String,
  val emoji: String,
  val colorHex: Long,
  val exampleObject: String
)

enum class ColorSubcategory(
  val levelNumber: Int,
  val title: String,
  val emoji: String,
  val subtitle: String,
  val pedagogicalGoal: String,
  val colorHex: Long,
  val bgHex: Long
) {
  PRIMARY_COLORS(1, "Primary Colors", "🔴", "Red, Blue & Yellow", "Identify fundamental primary colors", 0xFFE53935, 0xFFFFEBEE),
  SECONDARY_COLORS(2, "Secondary Colors", "🟢", "Green, Orange & Purple", "Discover secondary color combinations", 0xFF43A047, 0xFFE8F5E9),
  SHADES_AND_NEUTRALS(3, "Shades & Neutrals", "🌸", "Pink, Brown, Black, White", "Expand vocabulary with soft shades and neutral tones", 0xFF8E24AA, 0xFFF3E5F5),
  COLOR_MIXING(4, "Color Mixing Lab", "🧪", "Magic Transformations", "Blend two colors to create exciting new hues", 0xFF00ACC1, 0xFFE0F7FA),
  RAINBOW_DISCOVERY(5, "Rainbow Explorer", "🌈", "Colors in Nature", "Explore the spectrum of colors across the sky", 0xFFFF9800, 0xFFFFF3E0);

  companion object {
    fun fromLevel(level: Int): ColorSubcategory =
      entries.firstOrNull { it.levelNumber == level } ?: PRIMARY_COLORS
  }
}

data class ColorSeeExample(
  val label: String,
  val emoji: String,
  val colorName: String,
  val soundPrompt: String = label
)

data class ColorMixRecipe(
  val colorA: String,
  val emojiA: String,
  val colorB: String,
  val emojiB: String,
  val resultColor: String,
  val resultEmoji: String,
  val resultHex: Long,
  val prompt: String
)

data class ColorLessonItem(
  val id: String,
  val subcategory: ColorSubcategory,
  val colorName: String,
  val colorHex: Long,
  val emoji: String,
  val hearPrompt: String,
  val seeExamples: List<ColorSeeExample>,
  val chooseQuestion: String,
  val chooseOptions: List<GameOption>,
  val correctOptionId: String,
  val mixRecipe: ColorMixRecipe? = null,
  val speakPrompt: String = "Say '$colorName'!"
)

data class ColorMinimalPair(
  val id: String,
  val color1: String,
  val emoji1: String,
  val hex1: Long,
  val color2: String,
  val emoji2: String,
  val hex2: Long,
  val spokenColor: String,
  val hint: String = "Listen carefully to the color name!"
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

