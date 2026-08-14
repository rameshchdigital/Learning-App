package com.example.data.content

import com.example.data.models.AlphabetCard
import com.example.data.models.AnimalCard
import com.example.data.models.ColorCard
import com.example.data.models.DifficultyLevel
import com.example.data.models.GameCategory
import com.example.data.models.GameOption
import com.example.data.models.GameQuestion
import com.example.data.models.NumberCard
import com.example.data.models.PhonicsWord
import com.example.data.models.ShapeCard

object GameContentRepository {

  fun getAlphabets(): List<AlphabetCard> {
    return listOf(
      AlphabetCard("A", "a", "Apple", "🍎", "A says /æ/ as in Apple", "A is for Apple! An apple a day."),
      AlphabetCard("B", "b", "Ball", "⚽", "B says /b/ as in Ball", "B is for Ball! Let's bounce the ball."),
      AlphabetCard("C", "c", "Cat", "🐱", "C says /k/ as in Cat", "C is for Cat! The cat says Meow."),
      AlphabetCard("D", "d", "Dog", "🐶", "D says /d/ as in Dog", "D is for Dog! The friendly puppy."),
      AlphabetCard("E", "e", "Elephant", "🐘", "E says /e/ as in Elephant", "E is for Elephant! Big floppy ears."),
      AlphabetCard("F", "f", "Fish", "🐟", "F says /f/ as in Fish", "F is for Fish! Swimming in the water."),
      AlphabetCard("G", "g", "Giraffe", "🦒", "G says /g/ as in Giraffe", "G is for Giraffe! Tall neck in the trees."),
      AlphabetCard("H", "h", "Hat", "🎩", "H says /h/ as in Hat", "H is for Hat! Wear a cozy hat."),
      AlphabetCard("I", "i", "Ice Cream", "🍦", "I says /aɪ/ as in Ice Cream", "I is for Ice Cream! Sweet and cold."),
      AlphabetCard("J", "j", "Juice", "🧃", "J says /dʒ/ as in Juice", "J is for Juice! Yummy orange juice."),
      AlphabetCard("K", "k", "Kangaroo", "🦘", "K says /k/ as in Kangaroo", "K is for Kangaroo! Hop hop hop."),
      AlphabetCard("L", "l", "Lion", "🦁", "L says /l/ as in Lion", "L is for Lion! The brave jungle king."),
      AlphabetCard("M", "m", "Monkey", "🐒", "M says /m/ as in Monkey", "M is for Monkey! Eating a banana."),
      AlphabetCard("N", "n", "Nest", "🪹", "N says /n/ as in Nest", "N is for Nest! Birds sleep in a nest."),
      AlphabetCard("O", "o", "Owl", "🦉", "O says /ɒ/ as in Owl", "O is for Owl! Wise owl at night."),
      AlphabetCard("P", "p", "Pencil", "✏️", "P says /p/ as in Pencil", "P is for Pencil! Draw a picture."),
      AlphabetCard("Q", "q", "Queen", "👑", "Q says /kw/ as in Queen", "Q is for Queen! Wearing a crown."),
      AlphabetCard("R", "r", "Rabbit", "🐰", "R says /r/ as in Rabbit", "R is for Rabbit! Fluffy little bunny."),
      AlphabetCard("S", "s", "Sun", "☀️", "S says /s/ as in Sun", "S is for Sun! Shining bright."),
      AlphabetCard("T", "t", "Tiger", "🐯", "T says /t/ as in Tiger", "T is for Tiger! Fast and strong."),
      AlphabetCard("U", "u", "Umbrella", "☂️", "U says /ʌ/ as in Umbrella", "U is for Umbrella! Rain rain go away."),
      AlphabetCard("V", "v", "Violin", "🎻", "V says /v/ as in Violin", "V is for Violin! Play sweet music."),
      AlphabetCard("W", "w", "Watermelon", "🍉", "W says /w/ as in Watermelon", "W is for Watermelon! Juicy summer fruit."),
      AlphabetCard("X", "x", "Xylophone", "🎼", "X says /ks/ as in Xylophone", "X is for Xylophone! Ring the bells."),
      AlphabetCard("Y", "y", "Yo-Yo", "🪀", "Y says /j/ as in Yo-Yo", "Y is for Yo-Yo! Up and down."),
      AlphabetCard("Z", "z", "Zebra", "🦓", "Z says /z/ as in Zebra", "Z is for Zebra! Black and white stripes.")
    )
  }

  fun getPhonicsWords(): List<PhonicsWord> {
    return listOf(
      PhonicsWord("CAT", listOf("C", "A", "T"), listOf("/k/", "/æ/", "/t/"), "🐱", "Level 1 • Short A"),
      PhonicsWord("DOG", listOf("D", "O", "G"), listOf("/d/", "/ɒ/", "/g/"), "🐶", "Level 1 • Short O"),
      PhonicsWord("SUN", listOf("S", "U", "N"), listOf("/s/", "/ʌ/", "/n/"), "☀️", "Level 1 • Short U"),
      PhonicsWord("RED", listOf("R", "E", "D"), listOf("/r/", "/e/", "/d/"), "🔴", "Level 1 • Short E"),
      PhonicsWord("PIG", listOf("P", "I", "G"), listOf("/p/", "/ɪ/", "/g/"), "🐷", "Level 1 • Short I"),
      PhonicsWord("BUG", listOf("B", "U", "G"), listOf("/b/", "/ʌ/", "/g/"), "🐞", "Level 2 • Word Blending"),
      PhonicsWord("HAT", listOf("H", "A", "T"), listOf("/h/", "/æ/", "/t/"), "🎩", "Level 2 • Word Blending"),
      PhonicsWord("PEN", listOf("P", "E", "N"), listOf("/p/", "/e/", "/n/"), "🖊️", "Level 2 • Word Blending"),
      PhonicsWord("BOX", listOf("B", "O", "X"), listOf("/b/", "/ɒ/", "/ks/"), "📦", "Level 3 • CVC Words"),
      PhonicsWord("FAN", listOf("F", "A", "N"), listOf("/f/", "/æ/", "/n/"), "🪭", "Level 3 • CVC Words")
    )
  }

  fun getNumbers(): List<NumberCard> {
    return listOf(
      NumberCard(1, "ONE", "🍎", "1 Apple"),
      NumberCard(2, "TWO", "⚽⚽", "2 Balls"),
      NumberCard(3, "THREE", "🍌🍌🍌", "3 Bananas"),
      NumberCard(4, "FOUR", "🚗🚗🚗🚗", "4 Cars"),
      NumberCard(5, "FIVE", "⭐🌟⭐🌟⭐", "5 Stars"),
      NumberCard(6, "SIX", "🐱🐱🐱🐱🐱🐱", "6 Cats"),
      NumberCard(7, "SEVEN", "🎈🎈🎈🎈🎈🎈🎈", "7 Balloons"),
      NumberCard(8, "EIGHT", "🍓🍓🍓🍓🍓🍓🍓🍓", "8 Strawberries"),
      NumberCard(9, "NINE", "🍦🍦🍦🍦🍦🍦🍦🍦🍦", "9 Ice Creams"),
      NumberCard(10, "TEN", "🐥🐥🐥🐥🐥🐥🐥🐥🐥🐥", "10 Little Ducks")
    )
  }

  fun getColors(): List<ColorCard> {
    return listOf(
      ColorCard("Red", "🔴", 0xFFEF5350, "Red Apple 🍎"),
      ColorCard("Blue", "🔵", 0xFF42A5F5, "Blue Sea 🌊"),
      ColorCard("Yellow", "🟡", 0xFFFFCA28, "Yellow Sun ☀️"),
      ColorCard("Green", "🟢", 0xFF66BB6A, "Green Leaf 🍃"),
      ColorCard("Orange", "🟠", 0xFFFF9800, "Orange Carrot 🥕"),
      ColorCard("Purple", "🟣", 0xFFAB47BC, "Purple Grapes 🍇"),
      ColorCard("Pink", "🩷", 0xFFEC407A, "Pink Flower 🌸"),
      ColorCard("Brown", "🟤", 0xFF8D6E63, "Brown Bear 🐻"),
      ColorCard("Black", "⚫", 0xFF37474F, "Black Cat 🐈‍⬛"),
      ColorCard("White", "⚪", 0xFFCFD8DC, "White Snowman ☃️")
    )
  }

  fun getShapes(): List<ShapeCard> {
    return listOf(
      ShapeCard("Circle", "⭕", "Round with no straight sides", "Like a full moon 🌕"),
      ShapeCard("Square", "⬜", "Has 4 equal sides and 4 corners", "Like a toy block 🧊"),
      ShapeCard("Triangle", "🔺", "Has 3 straight sides and 3 corners", "Like a slice of pizza 🍕"),
      ShapeCard("Rectangle", "▭", "Has 4 sides (2 long, 2 short)", "Like a storybook 📘"),
      ShapeCard("Star", "⭐", "Has 5 pointed stars", "Like a shining star 🌟"),
      ShapeCard("Heart", "💖", "Curved and sweet form", "Symbol of love ❤️"),
      ShapeCard("Oval", "⬢", "Stretched circle shape", "Like an egg 🥚"),
      ShapeCard("Diamond", "🔶", "Kite shape with 4 slanted sides", "Like a flying kite 🪁")
    )
  }

  fun getBodyParts(): List<GameOption> {
    return listOf(
      GameOption("head", "Head", "🗣️"),
      GameOption("eyes", "Eyes", "👀"),
      GameOption("ears", "Ears", "👂"),
      GameOption("nose", "Nose", "👃"),
      GameOption("mouth", "Mouth", "👄"),
      GameOption("teeth", "Teeth", "🦷"),
      GameOption("hands", "Hands", "👐"),
      GameOption("legs", "Legs", "🦵"),
      GameOption("feet", "Feet", "🦶")
    )
  }

  fun getSentenceCards(): List<Pair<String, String>> {
    return listOf(
      "This is a cat." to "🐱",
      "I have a red ball." to "⚽",
      "I like sweet apples." to "🍎",
      "The sun is shining hot." to "☀️",
      "The dog is running fast." to "🐶",
      "This is my blue book." to "📘",
      "I can jump so high!" to "🏃"
    )
  }

  fun getSpellingWords(): List<Pair<String, String>> {
    return listOf(
      "CAT" to "🐱",
      "DOG" to "🐶",
      "SUN" to "☀️",
      "BALL" to "⚽",
      "STAR" to "⭐",
      "APPLE" to "🍎"
    )
  }

  fun getAnimalCards(): List<AnimalCard> {
    return listOf(
      AnimalCard("dog", "Dog", "🐶", "Woof! Woof!", "Pets", 0xFFFFB300, 0xFFFFF8E1),
      AnimalCard("cat", "Cat", "🐱", "Meow! Meow!", "Pets", 0xFFEC407A, 0xFFFCE4EC),
      AnimalCard("cow", "Cow", "🐮", "Moo! Moo!", "Farm", 0xFF66BB6A, 0xFFE8F5E9),
      AnimalCard("duck", "Duck", "🦆", "Quack! Quack!", "Farm", 0xFF26A69A, 0xFFE0F2F1),
      AnimalCard("lion", "Lion", "🦁", "Roar!", "Wild", 0xFFFFA726, 0xFFFFF3E0),
      AnimalCard("elephant", "Elephant", "🐘", "Trumpet!", "Wild", 0xFF42A5F5, 0xFFE3F2FD),
      AnimalCard("monkey", "Monkey", "🐒", "Ooh Ooh Aah Aah!", "Wild", 0xFF8D6E63, 0xFFEFEBE9),
      AnimalCard("pig", "Pig", "🐷", "Oink! Oink!", "Farm", 0xFFEC407A, 0xFFFCE4EC),
      AnimalCard("sheep", "Sheep", "🐑", "Baa! Baa!", "Farm", 0xFF78909C, 0xFFECEFF1),
      AnimalCard("rooster", "Rooster", "🐓", "Cock-a-doodle-doo!", "Farm", 0xFFEF5350, 0xFFFFEBEE)
    )
  }

  fun getDailyQuizQuestions(
    practicedCategoryIds: List<String>,
    difficulty: DifficultyLevel = DifficultyLevel.MEDIUM
  ): List<GameQuestion> {
    val categoriesToUse = if (practicedCategoryIds.isNotEmpty()) {
      GameCategory.entries.filter { cat ->
        practicedCategoryIds.any { id -> id.equals(cat.id, ignoreCase = true) || id.equals(cat.name, ignoreCase = true) }
      }.ifEmpty {
        listOf(GameCategory.ANIMALS, GameCategory.COLORS, GameCategory.SHAPES, GameCategory.ALPHABETS, GameCategory.NUMBERS)
      }
    } else {
      listOf(GameCategory.ANIMALS, GameCategory.COLORS, GameCategory.SHAPES, GameCategory.ALPHABETS, GameCategory.NUMBERS, GameCategory.FRUITS_VEG)
    }

    val allQuestions = categoriesToUse.flatMap { category ->
      getQuestionsForCategory(category, difficulty)
    }.shuffled()

    return allQuestions.distinctBy { it.id }.take(5)
  }

  fun getQuestionsForCategory(
    category: GameCategory,
    difficulty: DifficultyLevel = DifficultyLevel.MEDIUM
  ): List<GameQuestion> {
    val base = getBaseQuestionsForCategory(category)
    val pool = base.flatMap { it.options }.distinctBy { it.id }

    return base.map { q ->
      val adjustedOptions = when (difficulty) {
        DifficultyLevel.EASY -> {
          val correct = q.options.firstOrNull { it.id == q.correctAnswerId } ?: q.options.first()
          val distractor = q.options.firstOrNull { it.id != q.correctAnswerId } ?: pool.first { it.id != correct.id }
          listOf(correct, distractor)
        }
        DifficultyLevel.MEDIUM -> {
          q.options.take(3)
        }
        DifficultyLevel.HARD -> {
          val existing = q.options.toMutableList()
          val candidates = pool.filter { p -> existing.none { e -> e.id == p.id } }
          if (candidates.isNotEmpty()) {
            existing.add(candidates.random())
          }
          existing
        }
      }
      q.copy(options = adjustedOptions)
    }
  }

  private fun getBaseQuestionsForCategory(category: GameCategory): List<GameQuestion> {
    return when (category) {
      GameCategory.ANIMALS -> listOf(
        GameQuestion(
          id = "anim_1",
          category = category,
          questionText = "Where is the Dog? 🐶",
          voicePrompt = "Where is the Dog?",
          options = listOf(
            GameOption("dog", "Dog", "🐶"),
            GameOption("cat", "Cat", "🐱"),
            GameOption("cow", "Cow", "🐮")
          ),
          correctAnswerId = "dog"
        ),
        GameQuestion(
          id = "anim_2",
          category = category,
          questionText = "Where is the Cat? 🐱",
          voicePrompt = "Where is the Cat?",
          options = listOf(
            GameOption("lion", "Lion", "🦁"),
            GameOption("cat", "Cat", "🐱"),
            GameOption("duck", "Duck", "🦆")
          ),
          correctAnswerId = "cat"
        ),
        GameQuestion(
          id = "anim_3",
          category = category,
          questionText = "Find the Duck! 🦆",
          voicePrompt = "Find the Duck!",
          options = listOf(
            GameOption("duck", "Duck", "🦆"),
            GameOption("pig", "Pig", "🐷"),
            GameOption("frog", "Frog", "🐸")
          ),
          correctAnswerId = "duck"
        ),
        GameQuestion(
          id = "anim_4",
          category = category,
          questionText = "Where is the Lion? 🦁",
          voicePrompt = "Where is the Lion?",
          options = listOf(
            GameOption("elephant", "Elephant", "🐘"),
            GameOption("lion", "Lion", "🦁"),
            GameOption("sheep", "Sheep", "🐑")
          ),
          correctAnswerId = "lion"
        ),
        GameQuestion(
          id = "anim_5",
          category = category,
          questionText = "Where is the Frog? 🐸",
          voicePrompt = "Where is the Frog?",
          options = listOf(
            GameOption("frog", "Frog", "🐸"),
            GameOption("bird", "Bird", "🐦"),
            GameOption("pig", "Pig", "🐷")
          ),
          correctAnswerId = "frog"
        ),
        GameQuestion(
          id = "anim_6",
          category = category,
          questionText = "Find the Elephant! 🐘",
          voicePrompt = "Find the Elephant!",
          options = listOf(
            GameOption("sheep", "Sheep", "🐑"),
            GameOption("dog", "Dog", "🐶"),
            GameOption("elephant", "Elephant", "🐘")
          ),
          correctAnswerId = "elephant"
        )
      )

      GameCategory.COLORS -> listOf(
        GameQuestion(
          id = "col_1",
          category = category,
          questionText = "Which one is RED? 🔴",
          voicePrompt = "Which one is RED?",
          options = listOf(
            GameOption("red", "Red", "🔴", colorHex = 0xFFEF5350),
            GameOption("blue", "Blue", "🔵", colorHex = 0xFF42A5F5),
            GameOption("yellow", "Yellow", "🟡", colorHex = 0xFFFFCA28)
          ),
          correctAnswerId = "red"
        ),
        GameQuestion(
          id = "col_2",
          category = category,
          questionText = "Find the BLUE circle! 🔵",
          voicePrompt = "Find the BLUE circle!",
          options = listOf(
            GameOption("green", "Green", "🟢", colorHex = 0xFF66BB6A),
            GameOption("blue", "Blue", "🔵", colorHex = 0xFF42A5F5),
            GameOption("purple", "Purple", "🟣", colorHex = 0xFFAB47BC)
          ),
          correctAnswerId = "blue"
        ),
        GameQuestion(
          id = "col_3",
          category = category,
          questionText = "Where is YELLOW? 🟡",
          voicePrompt = "Where is YELLOW?",
          options = listOf(
            GameOption("yellow", "Yellow", "🟡", colorHex = 0xFFFFCA28),
            GameOption("red", "Red", "🔴", colorHex = 0xFFEF5350),
            GameOption("orange", "Orange", "🟠", colorHex = 0xFFFF9800)
          ),
          correctAnswerId = "yellow"
        ),
        GameQuestion(
          id = "col_4",
          category = category,
          questionText = "Find the GREEN leaf! 🟢",
          voicePrompt = "Find the GREEN leaf!",
          options = listOf(
            GameOption("pink", "Pink", "🩷", colorHex = 0xFFEC407A),
            GameOption("green", "Green", "🟢", colorHex = 0xFF66BB6A),
            GameOption("blue", "Blue", "🔵", colorHex = 0xFF42A5F5)
          ),
          correctAnswerId = "green"
        ),
        GameQuestion(
          id = "col_5",
          category = category,
          questionText = "Which one is PURPLE? 🟣",
          voicePrompt = "Which one is PURPLE?",
          options = listOf(
            GameOption("purple", "Purple", "🟣", colorHex = 0xFFAB47BC),
            GameOption("yellow", "Yellow", "🟡", colorHex = 0xFFFFCA28),
            GameOption("red", "Red", "🔴", colorHex = 0xFFEF5350)
          ),
          correctAnswerId = "purple"
        ),
        GameQuestion(
          id = "col_6",
          category = category,
          questionText = "Find ORANGE! 🟠",
          voicePrompt = "Find ORANGE!",
          options = listOf(
            GameOption("blue", "Blue", "🔵", colorHex = 0xFF42A5F5),
            GameOption("orange", "Orange", "🟠", colorHex = 0xFFFF9800),
            GameOption("white", "White", "⚪", colorHex = 0xFFB0BEC5)
          ),
          correctAnswerId = "orange"
        )
      )

      GameCategory.NUMBERS -> listOf(
        GameQuestion(
          id = "num_1",
          category = category,
          questionText = "Where is Number 1? 1️⃣",
          voicePrompt = "Where is Number 1?",
          options = listOf(
            GameOption("1", "One", "1️⃣"),
            GameOption("2", "Two", "2️⃣"),
            GameOption("3", "Three", "3️⃣")
          ),
          correctAnswerId = "1"
        ),
        GameQuestion(
          id = "num_2",
          category = category,
          questionText = "Find Number 2! 2️⃣",
          voicePrompt = "Find Number 2!",
          options = listOf(
            GameOption("4", "Four", "4️⃣"),
            GameOption("2", "Two", "2️⃣"),
            GameOption("5", "Five", "5️⃣")
          ),
          correctAnswerId = "2"
        ),
        GameQuestion(
          id = "num_3",
          category = category,
          questionText = "Which one is Number 3? 3️⃣",
          voicePrompt = "Which one is Number 3?",
          options = listOf(
            GameOption("3", "Three", "3️⃣"),
            GameOption("1", "One", "1️⃣"),
            GameOption("4", "Four", "4️⃣")
          ),
          correctAnswerId = "3"
        ),
        GameQuestion(
          id = "num_4",
          category = category,
          questionText = "Where is Number 4? 4️⃣",
          voicePrompt = "Where is Number 4?",
          options = listOf(
            GameOption("5", "Five", "5️⃣"),
            GameOption("6", "Six", "6️⃣"),
            GameOption("4", "Four", "4️⃣")
          ),
          correctAnswerId = "4"
        ),
        GameQuestion(
          id = "num_5",
          category = category,
          questionText = "Find Number 5! 5️⃣",
          voicePrompt = "Find Number 5!",
          options = listOf(
            GameOption("2", "Two", "2️⃣"),
            GameOption("5", "Five", "5️⃣"),
            GameOption("3", "Three", "3️⃣")
          ),
          correctAnswerId = "5"
        ),
        GameQuestion(
          id = "num_6",
          category = category,
          questionText = "Which one is Number 10? 🔟",
          voicePrompt = "Which one is Number 10?",
          options = listOf(
            GameOption("7", "Seven", "7️⃣"),
            GameOption("9", "Nine", "9️⃣"),
            GameOption("10", "Ten", "🔟")
          ),
          correctAnswerId = "10"
        )
      )

      GameCategory.SHAPES -> listOf(
        GameQuestion(
          id = "shp_1",
          category = category,
          questionText = "Where is the Star? ⭐",
          voicePrompt = "Where is the Star?",
          options = listOf(
            GameOption("star", "Star", "⭐"),
            GameOption("circle", "Circle", "⭕"),
            GameOption("square", "Square", "⬜")
          ),
          correctAnswerId = "star"
        ),
        GameQuestion(
          id = "shp_2",
          category = category,
          questionText = "Find the Circle! ⭕",
          voicePrompt = "Find the Circle!",
          options = listOf(
            GameOption("triangle", "Triangle", "🔺"),
            GameOption("circle", "Circle", "⭕"),
            GameOption("heart", "Heart", "💖")
          ),
          correctAnswerId = "circle"
        ),
        GameQuestion(
          id = "shp_3",
          category = category,
          questionText = "Where is the Square? ⬜",
          voicePrompt = "Where is the Square?",
          options = listOf(
            GameOption("square", "Square", "⬜"),
            GameOption("diamond", "Diamond", "🔶"),
            GameOption("star", "Star", "⭐")
          ),
          correctAnswerId = "square"
        ),
        GameQuestion(
          id = "shp_4",
          category = category,
          questionText = "Find the Triangle! 🔺",
          voicePrompt = "Find the Triangle!",
          options = listOf(
            GameOption("heart", "Heart", "💖"),
            GameOption("square", "Square", "⬜"),
            GameOption("triangle", "Triangle", "🔺")
          ),
          correctAnswerId = "triangle"
        ),
        GameQuestion(
          id = "shp_5",
          category = category,
          questionText = "Where is the Heart? 💖",
          voicePrompt = "Where is the Heart?",
          options = listOf(
            GameOption("heart", "Heart", "💖"),
            GameOption("oval", "Oval", "⬢"),
            GameOption("circle", "Circle", "⭕")
          ),
          correctAnswerId = "heart"
        ),
        GameQuestion(
          id = "shp_6",
          category = category,
          questionText = "Find the Diamond! 🔶",
          voicePrompt = "Find the Diamond!",
          options = listOf(
            GameOption("star", "Star", "⭐"),
            GameOption("diamond", "Diamond", "🔶"),
            GameOption("triangle", "Triangle", "🔺")
          ),
          correctAnswerId = "diamond"
        )
      )

      GameCategory.MUSIC -> listOf(
        GameQuestion(
          id = "mus_1",
          category = category,
          questionText = "Where is the Drum? 🥁",
          voicePrompt = "Where is the Drum?",
          options = listOf(
            GameOption("drum", "Drum", "🥁"),
            GameOption("guitar", "Guitar", "🎸"),
            GameOption("piano", "Piano", "🎹")
          ),
          correctAnswerId = "drum"
        ),
        GameQuestion(
          id = "mus_2",
          category = category,
          questionText = "Find the Guitar! 🎸",
          voicePrompt = "Find the Guitar!",
          options = listOf(
            GameOption("trumpet", "Trumpet", "🎺"),
            GameOption("guitar", "Guitar", "🎸"),
            GameOption("violin", "Violin", "🎻")
          ),
          correctAnswerId = "guitar"
        ),
        GameQuestion(
          id = "mus_3",
          category = category,
          questionText = "Where is the Piano? 🎹",
          voicePrompt = "Where is the Piano?",
          options = listOf(
            GameOption("piano", "Piano", "🎹"),
            GameOption("saxophone", "Saxophone", "🎷"),
            GameOption("drum", "Drum", "🥁")
          ),
          correctAnswerId = "piano"
        ),
        GameQuestion(
          id = "mus_4",
          category = category,
          questionText = "Find the Trumpet! 🎺",
          voicePrompt = "Find the Trumpet!",
          options = listOf(
            GameOption("bell", "Bell", "🔔"),
            GameOption("trumpet", "Trumpet", "🎺"),
            GameOption("guitar", "Guitar", "🎸")
          ),
          correctAnswerId = "trumpet"
        ),
        GameQuestion(
          id = "mus_5",
          category = category,
          questionText = "Where is the Music Note? 🎵",
          voicePrompt = "Where is the Music Note?",
          options = listOf(
            GameOption("note", "Music Note", "🎵"),
            GameOption("piano", "Piano", "🎹"),
            GameOption("mic", "Microphone", "🎤")
          ),
          correctAnswerId = "note"
        )
      )

      GameCategory.FRUITS_VEG -> listOf(
        GameQuestion(
          id = "frt_1",
          category = category,
          questionText = "Where is the Apple? 🍎",
          voicePrompt = "Where is the Apple?",
          options = listOf(
            GameOption("banana", "Banana", "🍌"),
            GameOption("apple", "Apple", "🍎"),
            GameOption("grapes", "Grapes", "🍇")
          ),
          correctAnswerId = "apple"
        ),
        GameQuestion(
          id = "frt_2",
          category = category,
          questionText = "Find the Banana! 🍌",
          voicePrompt = "Find the Banana!",
          options = listOf(
            GameOption("banana", "Banana", "🍌"),
            GameOption("strawberry", "Strawberry", "🍓"),
            GameOption("orange", "Orange", "🍊")
          ),
          correctAnswerId = "banana"
        ),
        GameQuestion(
          id = "frt_3",
          category = category,
          questionText = "Where are the Grapes? 🍇",
          voicePrompt = "Where are the Grapes?",
          options = listOf(
            GameOption("pineapple", "Pineapple", "🍍"),
            GameOption("watermelon", "Watermelon", "🍉"),
            GameOption("grapes", "Grapes", "🍇")
          ),
          correctAnswerId = "grapes"
        ),
        GameQuestion(
          id = "frt_4",
          category = category,
          questionText = "Find the Strawberry! 🍓",
          voicePrompt = "Find the Strawberry!",
          options = listOf(
            GameOption("strawberry", "Strawberry", "🍓"),
            GameOption("apple", "Apple", "🍎"),
            GameOption("peach", "Peach", "🍑")
          ),
          correctAnswerId = "strawberry"
        ),
        GameQuestion(
          id = "frt_5",
          category = category,
          questionText = "Where is the Watermelon? 🍉",
          voicePrompt = "Where is the Watermelon?",
          options = listOf(
            GameOption("cherry", "Cherry", "🍒"),
            GameOption("watermelon", "Watermelon", "🍉"),
            GameOption("banana", "Banana", "🍌")
          ),
          correctAnswerId = "watermelon"
        ),
        GameQuestion(
          id = "frt_6",
          category = category,
          questionText = "Find the Orange! 🍊",
          voicePrompt = "Find the Orange!",
          options = listOf(
            GameOption("lemon", "Lemon", "🍋"),
            GameOption("orange", "Orange", "🍊"),
            GameOption("pear", "Pear", "🍐")
          ),
          correctAnswerId = "orange"
        )
      )

      GameCategory.TRANSPORTATION -> listOf(
        GameQuestion(
          id = "veh_1",
          category = category,
          questionText = "Where is the Car? 🚗",
          voicePrompt = "Where is the Car?",
          options = listOf(
            GameOption("car", "Car", "🚗"),
            GameOption("bus", "Bus", "🚌"),
            GameOption("train", "Train", "🚂")
          ),
          correctAnswerId = "car"
        ),
        GameQuestion(
          id = "veh_2",
          category = category,
          questionText = "Find the Airplane! ✈️",
          voicePrompt = "Find the Airplane!",
          options = listOf(
            GameOption("boat", "Boat", "⛵"),
            GameOption("airplane", "Airplane", "✈️"),
            GameOption("rocket", "Rocket", "🚀")
          ),
          correctAnswerId = "airplane"
        ),
        GameQuestion(
          id = "veh_3",
          category = category,
          questionText = "Where is the Train? 🚂",
          voicePrompt = "Where is the Train?",
          options = listOf(
            GameOption("train", "Train", "🚂"),
            GameOption("bicycle", "Bicycle", "🚲"),
            GameOption("bus", "Bus", "🚌")
          ),
          correctAnswerId = "train"
        ),
        GameQuestion(
          id = "veh_4",
          category = category,
          questionText = "Find the Fire Truck! 🚒",
          voicePrompt = "Find the Fire Truck!",
          options = listOf(
            GameOption("police", "Police Car", "🚓"),
            GameOption("firetruck", "Fire Truck", "🚒"),
            GameOption("taxi", "Taxi", "🚕")
          ),
          correctAnswerId = "firetruck"
        ),
        GameQuestion(
          id = "veh_5",
          category = category,
          questionText = "Where is the Rocket? 🚀",
          voicePrompt = "Where is the Rocket?",
          options = listOf(
            GameOption("helicopter", "Helicopter", "🚁"),
            GameOption("rocket", "Rocket", "🚀"),
            GameOption("boat", "Boat", "⛵")
          ),
          correctAnswerId = "rocket"
        ),
        GameQuestion(
          id = "veh_6",
          category = category,
          questionText = "Find the Bus! 🚌",
          voicePrompt = "Find the Bus!",
          options = listOf(
            GameOption("bus", "Bus", "🚌"),
            GameOption("car", "Car", "🚗"),
            GameOption("train", "Train", "🚂")
          ),
          correctAnswerId = "bus"
        )
      )

      GameCategory.PUZZLES -> listOf(
        GameQuestion(
          id = "puz_1",
          category = category,
          questionText = "Find the Puzzle Piece! 🧩",
          voicePrompt = "Find the Puzzle Piece!",
          options = listOf(
            GameOption("puzzle", "Puzzle Piece", "🧩"),
            GameOption("block", "Toy Block", "🧊"),
            GameOption("gem", "Gem", "💎")
          ),
          correctAnswerId = "puzzle"
        ),
        GameQuestion(
          id = "puz_2",
          category = category,
          questionText = "Which one is the Red Block? 🟥",
          voicePrompt = "Which one is the Red Block?",
          options = listOf(
            GameOption("red_block", "Red Block", "🟥"),
            GameOption("blue_block", "Blue Block", "🟦"),
            GameOption("yellow_block", "Yellow Block", "🟨")
          ),
          correctAnswerId = "red_block"
        ),
        GameQuestion(
          id = "puz_3",
          category = category,
          questionText = "Find the Magic Wand! 🪄",
          voicePrompt = "Find the Magic Wand!",
          options = listOf(
            GameOption("key", "Key", "🔑"),
            GameOption("wand", "Magic Wand", "🪄"),
            GameOption("crown", "Crown", "👑")
          ),
          correctAnswerId = "wand"
        ),
        GameQuestion(
          id = "puz_4",
          category = category,
          questionText = "Where is the Golden Key? 🔑",
          voicePrompt = "Where is the Golden Key?",
          options = listOf(
            GameOption("key", "Golden Key", "🔑"),
            GameOption("lock", "Lock", "🔒"),
            GameOption("star", "Star", "⭐")
          ),
          correctAnswerId = "key"
        ),
        GameQuestion(
          id = "puz_5",
          category = category,
          questionText = "Find the Shining Gem! 💎",
          voicePrompt = "Find the Shining Gem!",
          options = listOf(
            GameOption("ring", "Ring", "💍"),
            GameOption("gem", "Shining Gem", "💎"),
            GameOption("puzzle", "Puzzle Piece", "🧩")
          ),
          correctAnswerId = "gem"
        )
      )
      GameCategory.COMMUNITY_HELPERS -> listOf(
        GameQuestion("ch_1", category, "Who puts out fires? 👨‍🚒", "Who puts out fires?", listOf(GameOption("ff", "Firefighter", "👨‍🚒"), GameOption("dr", "Doctor", "👨‍⚕️"), GameOption("tc", "Teacher", "👩‍🏫")), "ff"),
        GameQuestion("ch_2", category, "Who helps us when we are sick? 👨‍⚕️", "Who helps us when we are sick?", listOf(GameOption("po", "Police Officer", "👮‍♂️"), GameOption("dr", "Doctor", "👨‍⚕️"), GameOption("fm", "Farmer", "👨‍🌾")), "dr"),
        GameQuestion("ch_3", category, "Who teaches us at school? 👩‍🏫", "Who teaches us at school?", listOf(GameOption("tc", "Teacher", "👩‍🏫"), GameOption("ch", "Chef", "👨‍🍳"), GameOption("pl", "Pilot", "👨‍✈️")), "tc"),
        GameQuestion("ch_4", category, "Who keeps our neighborhood safe? 👮‍♂️", "Who keeps our neighborhood safe?", listOf(GameOption("po", "Police Officer", "👮‍♂️"), GameOption("bk", "Baker", "🧑‍🍳"), GameOption("vet", "Vet", "🐶")), "po"),
        GameQuestion("ch_5", category, "Who flies an airplane? 👨‍✈️", "Who flies an airplane?", listOf(GameOption("pl", "Pilot", "👨‍✈️"), GameOption("dr", "Doctor", "👨‍⚕️"), GameOption("gr", "Gardener", "🧑‍🌾")), "pl")
      )
      GameCategory.TOYS -> listOf(
        GameQuestion("toy_1", category, "Where is the Teddy Bear? 🧸", "Where is the Teddy Bear?", listOf(GameOption("td", "Teddy Bear", "🧸"), GameOption("dl", "Doll", "🪆"), GameOption("kt", "Kite", "🪁")), "td"),
        GameQuestion("toy_2", category, "Find the Toy Robot! 🤖", "Find the Toy Robot!", listOf(GameOption("rb", "Toy Robot", "🤖"), GameOption("bl", "Ball", "⚽"), GameOption("tr", "Toy Train", "🚂")), "rb"),
        GameQuestion("toy_3", category, "Where is the Toy Car? 🚗", "Where is the Toy Car?", listOf(GameOption("car", "Toy Car", "🚗"), GameOption("bk", "Blocks", "🧊"), GameOption("dr", "Toy Drum", "🥁")), "car"),
        GameQuestion("toy_4", category, "Find the Colorful Yo-Yo! 🪀", "Find the Colorful Yo-Yo!", listOf(GameOption("yo", "Yo-Yo", "🪀"), GameOption("pz", "Puzzle", "🧩"), GameOption("dn", "Toy Dinosaur", "🦖")), "yo"),
        GameQuestion("toy_5", category, "Where is the Toy Plane? ✈️", "Where is the Toy Plane?", listOf(GameOption("pl", "Toy Plane", "✈️"), GameOption("bt", "Toy Boat", "⛵"), GameOption("gt", "Toy Guitar", "🎸")), "pl")
      )
      GameCategory.HEALTH -> listOf(
        GameQuestion("hl_1", category, "What do we use to brush our teeth? 🪥", "What do we use to brush our teeth?", listOf(GameOption("tb", "Toothbrush", "🪥"), GameOption("sp", "Soap", "🧼"), GameOption("bd", "Bandage", "🩹")), "tb"),
        GameQuestion("hl_2", category, "What cleans our hands? 🧼", "What cleans our hands?", listOf(GameOption("sp", "Soap", "🧼"), GameOption("tm", "Thermometer", "🌡️"), GameOption("hs", "Hospital", "🏥")), "sp"),
        GameQuestion("hl_3", category, "Where do we go when sick? 🏥", "Where do we go when sick?", listOf(GameOption("hs", "Hospital", "🏥"), GameOption("pk", "Park", "🛝"), GameOption("bk", "Bakery", "🥖")), "hs"),
        GameQuestion("hl_4", category, "Find the Ambulance! 🚑", "Find the Ambulance!", listOf(GameOption("amb", "Ambulance", "🚑"), GameOption("wat", "Water", "💧"), GameOption("slp", "Sleep", "🛌")), "amb"),
        GameQuestion("hl_5", category, "What keeps us hydrated? 💧", "What keeps us hydrated?", listOf(GameOption("wat", "Water", "💧"), GameOption("med", "Medicine", "💊"), GameOption("st", "Stethoscope", "🩺")), "wat")
      )
      GameCategory.DAYS -> listOf(
        GameQuestion("dy_1", category, "What is the first day of the school week? 📅", "What is the first day of the school week?", listOf(GameOption("mon", "Monday", "📅"), GameOption("sat", "Saturday", "📅"), GameOption("sun", "Sunday", "📅")), "mon"),
        GameQuestion("dy_2", category, "Which day comes after Friday? 🥳", "Which day comes after Friday?", listOf(GameOption("sat", "Saturday", "🎉"), GameOption("tue", "Tuesday", "📅"), GameOption("thu", "Thursday", "📅")), "sat"),
        GameQuestion("dy_3", category, "Which day is the weekend family day? ☀️", "Which day is the weekend family day?", listOf(GameOption("sun", "Sunday", "☀️"), GameOption("wed", "Wednesday", "📅"), GameOption("mon", "Monday", "📅")), "sun"),
        GameQuestion("dy_4", category, "Find Wednesday! 🗓️", "Find Wednesday!", listOf(GameOption("wed", "Wednesday", "🗓️"), GameOption("fri", "Friday", "🗓️"), GameOption("tue", "Tuesday", "🗓️")), "wed"),
        GameQuestion("dy_5", category, "Find Friday! 🎈", "Find Friday!", listOf(GameOption("fri", "Friday", "🎈"), GameOption("mon", "Monday", "📅"), GameOption("thu", "Thursday", "📅")), "fri")
      )
      GameCategory.MONTHS -> listOf(
        GameQuestion("mn_1", category, "What is the first month of the year? 🎆", "What is the first month of the year?", listOf(GameOption("jan", "January", "🎆"), GameOption("jun", "June", "☀️"), GameOption("dec", "December", "❄️")), "jan"),
        GameQuestion("mn_2", category, "Which month comes after October? 🍁", "Which month comes after October?", listOf(GameOption("nov", "November", "🍁"), GameOption("feb", "February", "❄️"), GameOption("mar", "March", "🌱")), "nov"),
        GameQuestion("mn_3", category, "Which month is known for end-of-year holidays? 🎄", "Which month is known for end-of-year holidays?", listOf(GameOption("dec", "December", "🎄"), GameOption("aug", "August", "☀️"), GameOption("apr", "April", "🌸")), "dec"),
        GameQuestion("mn_4", category, "Find July! ☀️", "Find July!", listOf(GameOption("jul", "July", "☀️"), GameOption("may", "May", "🌸"), GameOption("sep", "September", "🍂")), "jul"),
        GameQuestion("mn_5", category, "Find March! 🌱", "Find March!", listOf(GameOption("mar", "March", "🌱"), GameOption("jan", "January", "🎆"), GameOption("aug", "August", "☀️")), "mar")
      )
      GameCategory.ACTION_WORDS -> listOf(
        GameQuestion("act_1", category, "Which action is Run? 🏃", "Which action is Run?", listOf(GameOption("run", "Run", "🏃"), GameOption("sit", "Sit", "🪑"), GameOption("slp", "Sleep", "😴")), "run"),
        GameQuestion("act_2", category, "Which action is Jump? 🦘", "Which action is Jump?", listOf(GameOption("jmp", "Jump", "🦘"), GameOption("wlk", "Walk", "🚶"), GameOption("std", "Stand", "🧍")), "jmp"),
        GameQuestion("act_3", category, "Where is Eat? 🍽️", "Where is Eat?", listOf(GameOption("eat", "Eat", "🍽️"), GameOption("drk", "Drink", "🥤"), GameOption("rd", "Read", "📖")), "eat"),
        GameQuestion("act_4", category, "Where is Sing? 🎤", "Where is Sing?", listOf(GameOption("sng", "Sing", "🎤"), GameOption("dnc", "Dance", "💃"), GameOption("clp", "Clap", "👏")), "sng"),
        GameQuestion("act_5", category, "Which action is Sleep? 😴", "Which action is Sleep?", listOf(GameOption("slp", "Sleep", "😴"), GameOption("wke", "Wake Up", "⏰"), GameOption("swm", "Swim", "🏊")), "slp")
      )
      GameCategory.PLACES -> listOf(
        GameQuestion("plc_1", category, "Where do we live with family? 🏠", "Where do we live with family?", listOf(GameOption("hm", "Home", "🏠"), GameOption("sch", "School", "🏫"), GameOption("air", "Airport", "✈️")), "hm"),
        GameQuestion("plc_2", category, "Where do children learn with teachers? 🏫", "Where do children learn with teachers?", listOf(GameOption("sch", "School", "🏫"), GameOption("prk", "Park", "🛝"), GameOption("zoo", "Zoo", "🦁")), "sch"),
        GameQuestion("plc_3", category, "Where do we see wild animals? 🦁", "Where do we see wild animals?", listOf(GameOption("zoo", "Zoo", "🦁"), GameOption("bnk", "Bank", "🏦"), GameOption("bch", "Beach", "🏖️")), "zoo"),
        GameQuestion("plc_4", category, "Where do we play on swings and slides? 🛝", "Where do we play on swings and slides?", listOf(GameOption("prk", "Playground", "🛝"), GameOption("bkr", "Bakery", "🥖"), GameOption("lib", "Library", "📚")), "prk"),
        GameQuestion("plc_5", category, "Where do airplanes land and take off? ✈️", "Where do airplanes land and take off?", listOf(GameOption("air", "Airport", "✈️"), GameOption("frm", "Farm", "🚜"), GameOption("rst", "Restaurant", "🍽️")), "air")
      )
      GameCategory.HOUSEHOLD_OBJECTS -> listOf(
        GameQuestion("ho_1", category, "What do we sit on at a desk? 🪑", "What do we sit on at a desk?", listOf(GameOption("chr", "Chair", "🪑"), GameOption("bed", "Bed", "🛏️"), GameOption("sfa", "Sofa", "🛋️")), "chr"),
        GameQuestion("ho_2", category, "What tells us the time? ⏰", "What tells us the time?", listOf(GameOption("clk", "Clock", "⏰"), GameOption("lmp", "Lamp", "💡"), GameOption("fan", "Fan", "🪭")), "clk"),
        GameQuestion("ho_3", category, "What keeps our food cold? 🧊", "What keeps our food cold?", listOf(GameOption("frg", "Refrigerator", "🧊"), GameOption("tv", "Television", "📺"), GameOption("wm", "Washing Machine", "🧺")), "frg"),
        GameQuestion("ho_4", category, "What do we sleep on at night? 🛏️", "What do we sleep on at night?", listOf(GameOption("bed", "Bed", "🛏️"), GameOption("tbl", "Table", "🪵"), GameOption("dr", "Door", "🚪")), "bed"),
        GameQuestion("ho_5", category, "What do we use to eat soup? 🥄", "What do we use to eat soup?", listOf(GameOption("spn", "Spoon", "🥄"), GameOption("plt", "Plate", "🍽️"), GameOption("gla", "Glass", "🥛")), "spn")
      )
      GameCategory.NATURE -> listOf(
        GameQuestion("nat_1", category, "What shines bright in the day sky? ☀️", "What shines bright in the day sky?", listOf(GameOption("sun", "Sun", "☀️"), GameOption("mon", "Moon", "🌙"), GameOption("cld", "Cloud", "☁️")), "sun"),
        GameQuestion("nat_2", category, "What has colorful arches after rain? 🌈", "What has colorful arches after rain?", listOf(GameOption("rnb", "Rainbow", "🌈"), GameOption("rn", "Rain", "🌧️"), GameOption("mnt", "Mountain", "🏔️")), "rnb"),
        GameQuestion("nat_3", category, "What has green leaves and trunks? 🌳", "What has green leaves and trunks?", listOf(GameOption("tre", "Tree", "🌳"), GameOption("flw", "Flower", "🌸"), GameOption("rck", "Rock", "🪨")), "tre"),
        GameQuestion("nat_4", category, "What flutters gently on flowers? 🦋", "What flutters gently on flowers?", listOf(GameOption("btf", "Butterfly", "🦋"), GameOption("bee", "Bee", "🐝"), GameOption("ant", "Ant", "🐜")), "btf"),
        GameQuestion("nat_5", category, "What shines at night in the sky? 🌙", "What shines at night in the sky?", listOf(GameOption("mon", "Moon", "🌙"), GameOption("str", "Stars", "⭐"), GameOption("rvr", "River", "🏞️")), "mon")
      )
      GameCategory.FRUITS -> listOf(
        GameQuestion("fr_1", category, "Where is the Apple? 🍎", "Where is the Apple?", listOf(GameOption("app", "Apple", "🍎"), GameOption("ban", "Banana", "🍌"), GameOption("org", "Orange", "🍊")), "app"),
        GameQuestion("fr_2", category, "Find the Yellow Banana! 🍌", "Find the Yellow Banana!", listOf(GameOption("ban", "Banana", "🍌"), GameOption("str", "Strawberry", "🍓"), GameOption("grp", "Grape", "🍇")), "ban"),
        GameQuestion("fr_3", category, "Where is the Sweet Watermelon? 🍉", "Where is the Sweet Watermelon?", listOf(GameOption("wtm", "Watermelon", "🍉"), GameOption("mng", "Mango", "🥭"), GameOption("pne", "Pineapple", "🍍")), "wtm"),
        GameQuestion("fr_4", category, "Find the Purple Grapes! 🍇", "Find the Purple Grapes!", listOf(GameOption("grp", "Grape", "🍇"), GameOption("ch", "Cherry", "🍒"), GameOption("pr", "Pear", "🍐")), "grp"),
        GameQuestion("fr_5", category, "Find the Juicy Mango! 🥭", "Find the Juicy Mango!", listOf(GameOption("mng", "Mango", "🥭"), GameOption("lmn", "Lemon", "🍋"), GameOption("kw", "Kiwi", "🥝")), "mng")
      )
      GameCategory.VEGETABLES -> listOf(
        GameQuestion("vg_1", category, "Where is the Orange Carrot? 🥕", "Where is the Orange Carrot?", listOf(GameOption("crt", "Carrot", "🥕"), GameOption("pto", "Potato", "🥔"), GameOption("tmo", "Tomato", "🍅")), "crt"),
        GameQuestion("vg_2", category, "Find the Green Broccoli! 🥦", "Find the Green Broccoli!", listOf(GameOption("brc", "Broccoli", "🥦"), GameOption("crn", "Corn", "🌽"), GameOption("ccm", "Cucumber", "🥒")), "brc"),
        GameQuestion("vg_3", category, "Where is the Yellow Corn? 🌽", "Where is the Yellow Corn?", listOf(GameOption("crn", "Corn", "🌽"), GameOption("pmp", "Pumpkin", "🎃"), GameOption("egp", "Eggplant", "🍆")), "crn"),
        GameQuestion("vg_4", category, "Find the Red Tomato! 🍅", "Find the Red Tomato!", listOf(GameOption("tmo", "Tomato", "🍅"), GameOption("oni", "Onion", "🧅"), GameOption("pes", "Peas", "🫛")), "tmo"),
        GameQuestion("vg_5", category, "Find the Purple Eggplant! 🍆", "Find the Purple Eggplant!", listOf(GameOption("egp", "Eggplant", "🍆"), GameOption("grl", "Garlic", "🧄"), GameOption("ppr", "Pepper", "🫑")), "egp")
      )
      GameCategory.VEHICLES -> listOf(
        GameQuestion("vh_1", category, "Where is the Red Car? 🚗", "Where is the Red Car?", listOf(GameOption("car", "Car", "🚗"), GameOption("bus", "Bus", "🚌"), GameOption("trn", "Train", "🚂")), "car"),
        GameQuestion("vh_2", category, "Find the School Bus! 🚌", "Find the School Bus!", listOf(GameOption("bus", "Bus", "🚌"), GameOption("trk", "Truck", "🚚"), GameOption("bik", "Bicycle", "🚲")), "bus"),
        GameQuestion("vh_3", category, "Where is the Airplane in the sky? ✈️", "Where is the Airplane in the sky?", listOf(GameOption("pln", "Airplane", "✈️"), GameOption("hlc", "Helicopter", "🚁"), GameOption("rck", "Rocket", "🚀")), "pln"),
        GameQuestion("vh_4", category, "Find the Chug-Chug Train! 🚂", "Find the Chug-Chug Train!", listOf(GameOption("trn", "Train", "🚂"), GameOption("bot", "Boat", "⛵"), GameOption("shp", "Ship", "🚢")), "trn"),
        GameQuestion("vh_5", category, "Find the Fire Engine! 🚒", "Find the Fire Engine!", listOf(GameOption("fre", "Fire Engine", "🚒"), GameOption("pol", "Police Car", "🚔"), GameOption("trc", "Tractor", "🚜")), "fre")
      )
      else -> listOf(
        GameQuestion(
          id = "gen_1",
          category = category,
          questionText = "Explore ${category.title}! 🌟",
          voicePrompt = "Tap the matching item!",
          options = listOf(
            GameOption("opt_1", category.title, category.iconEmoji),
            GameOption("opt_2", "Star", "⭐"),
            GameOption("opt_3", "Heart", "💖")
          ),
          correctAnswerId = "opt_1"
        )
      )
    }
  }
}
