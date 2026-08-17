package com.example.data.content

import com.example.data.models.AlphabetCard
import com.example.data.models.AnimalCard
import com.example.data.models.ColorCard
import com.example.data.models.ColorLessonItem
import com.example.data.models.ColorMinimalPair
import com.example.data.models.ColorMixRecipe
import com.example.data.models.ColorSeeExample
import com.example.data.models.ColorSubcategory
import com.example.data.models.DifficultyLevel
import com.example.data.models.GameCategory
import com.example.data.models.GameOption
import com.example.data.models.GameQuestion
import com.example.data.models.NumberCard
import com.example.data.models.NumberLessonItem
import com.example.data.models.NumberMinimalPair
import com.example.data.models.NumberSeeExample
import com.example.data.models.NumberSubcategory
import com.example.data.models.PhonicsLessonItem
import com.example.data.models.PhonicsMinimalPair
import com.example.data.models.PhonicsSeeExample
import com.example.data.models.PhonicsSoundSortItem
import com.example.data.models.PhonicsSubcategory
import com.example.data.models.PhonicsWord
import com.example.data.models.ShapeCard
import com.example.data.models.SoundBucket
import com.example.data.models.SoundSortGameItem
import com.example.data.models.SoundSortOption
import com.example.data.models.SoundSortRound

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

  fun getPhonicsLessonItems(): List<PhonicsLessonItem> {
    return listOf(
      // 1. Letter Sounds
      PhonicsLessonItem(
        id = "sound_b",
        subcategory = PhonicsSubcategory.LETTER_SOUNDS,
        soundOrTopic = "Sound /b/",
        targetWord = "BALL",
        letters = listOf("B", "A", "L", "L"),
        sounds = listOf("/b/", "/ɔː/", "/l/"),
        emoji = "⚽",
        hearPrompt = "B says /b/! Let's bounce the ball.",
        seeExamples = listOf(
          PhonicsSeeExample("Bee", "🐝", "/b/"),
          PhonicsSeeExample("Ball", "⚽", "/b/"),
          PhonicsSeeExample("Bus", "🚌", "/b/"),
          PhonicsSeeExample("Bear", "🐻", "/b/")
        ),
        chooseQuestion = "Which picture starts with the /b/ sound?",
        chooseOptions = listOf(
          GameOption("ball", "Ball", "⚽"),
          GameOption("apple", "Apple", "🍎"),
          GameOption("cat", "Cat", "🐱")
        ),
        correctOptionId = "ball",
        blendSequence = "B + A + L + L = BALL",
        speakPrompt = "Can you say /b/ for Ball?"
      ),
      PhonicsLessonItem(
        id = "sound_m",
        subcategory = PhonicsSubcategory.LETTER_SOUNDS,
        soundOrTopic = "Sound /m/",
        targetWord = "MOON",
        letters = listOf("M", "O", "O", "N"),
        sounds = listOf("/m/", "/uː/", "/n/"),
        emoji = "🌙",
        hearPrompt = "M says /m/! Mmmm, yummy mango.",
        seeExamples = listOf(
          PhonicsSeeExample("Monkey", "🐒", "/m/"),
          PhonicsSeeExample("Moon", "🌙", "/m/"),
          PhonicsSeeExample("Mouse", "🐭", "/m/"),
          PhonicsSeeExample("Mango", "🥭", "/m/")
        ),
        chooseQuestion = "Which picture starts with the /m/ sound?",
        chooseOptions = listOf(
          GameOption("moon", "Moon", "🌙"),
          GameOption("duck", "Duck", "🦆"),
          GameOption("sun", "Sun", "☀️")
        ),
        correctOptionId = "moon",
        blendSequence = "M + O + O + N = MOON",
        speakPrompt = "Can you say /m/ for Moon?"
      ),
      PhonicsLessonItem(
        id = "sound_s",
        subcategory = PhonicsSubcategory.LETTER_SOUNDS,
        soundOrTopic = "Sound /s/",
        targetWord = "SUN",
        letters = listOf("S", "U", "N"),
        sounds = listOf("/s/", "/ʌ/", "/n/"),
        emoji = "☀️",
        hearPrompt = "S says /s/! Ssss like a friendly snake.",
        seeExamples = listOf(
          PhonicsSeeExample("Sun", "☀️", "/s/"),
          PhonicsSeeExample("Star", "⭐", "/s/"),
          PhonicsSeeExample("Snake", "🐍", "/s/"),
          PhonicsSeeExample("Spoon", "🥄", "/s/")
        ),
        chooseQuestion = "Which picture starts with the /s/ sound?",
        chooseOptions = listOf(
          GameOption("sun", "Sun", "☀️"),
          GameOption("egg", "Egg", "🥚"),
          GameOption("pig", "Pig", "🐷")
        ),
        correctOptionId = "sun",
        blendSequence = "S + U + N = SUN",
        speakPrompt = "Can you say /s/ for Sun?"
      ),

      // 2. Short Vowels
      PhonicsLessonItem(
        id = "vowel_short_a",
        subcategory = PhonicsSubcategory.SHORT_VOWELS,
        soundOrTopic = "Short A (/æ/)",
        targetWord = "APPLE",
        letters = listOf("A", "P", "P", "L", "E"),
        sounds = listOf("/æ/", "/p/", "/l/"),
        emoji = "🍎",
        hearPrompt = "Short A says /æ/ as in Apple, Ant, and Cat!",
        seeExamples = listOf(
          PhonicsSeeExample("Apple", "🍎", "/æ/"),
          PhonicsSeeExample("Ant", "🐜", "/æ/"),
          PhonicsSeeExample("Axe", "🪓", "/æ/"),
          PhonicsSeeExample("Astronaut", "👨‍🚀", "/æ/")
        ),
        chooseQuestion = "Which picture has the short /æ/ vowel sound?",
        chooseOptions = listOf(
          GameOption("apple", "Apple", "🍎"),
          GameOption("umbrella", "Umbrella", "☂️"),
          GameOption("igloo", "Igloo", "🧊")
        ),
        correctOptionId = "apple",
        blendSequence = "A + P + P + L + E = APPLE",
        speakPrompt = "Say /æ/ ... Apple!"
      ),
      PhonicsLessonItem(
        id = "vowel_short_e",
        subcategory = PhonicsSubcategory.SHORT_VOWELS,
        soundOrTopic = "Short E (/e/)",
        targetWord = "EGG",
        letters = listOf("E", "G", "G"),
        sounds = listOf("/e/", "/g/"),
        emoji = "🥚",
        hearPrompt = "Short E says /e/ as in Egg, Elephant, and Bed!",
        seeExamples = listOf(
          PhonicsSeeExample("Egg", "🥚", "/e/"),
          PhonicsSeeExample("Elephant", "🐘", "/e/"),
          PhonicsSeeExample("Envelope", "✉️", "/e/"),
          PhonicsSeeExample("Elf", "🧝", "/e/")
        ),
        chooseQuestion = "Which picture starts with the short /e/ sound?",
        chooseOptions = listOf(
          GameOption("egg", "Egg", "🥚"),
          GameOption("octopus", "Octopus", "🐙"),
          GameOption("apple", "Apple", "🍎")
        ),
        correctOptionId = "egg",
        blendSequence = "E + G + G = EGG",
        speakPrompt = "Say /e/ ... Egg!"
      ),
      PhonicsLessonItem(
        id = "vowel_short_i",
        subcategory = PhonicsSubcategory.SHORT_VOWELS,
        soundOrTopic = "Short I (/ɪ/)",
        targetWord = "IGLOO",
        letters = listOf("I", "G", "L", "O", "O"),
        sounds = listOf("/ɪ/", "/g/", "/l/", "/uː/"),
        emoji = "🧊",
        hearPrompt = "Short I says /ɪ/ as in Igloo, Insect, and Pig!",
        seeExamples = listOf(
          PhonicsSeeExample("Igloo", "🧊", "/ɪ/"),
          PhonicsSeeExample("Insect", "🐛", "/ɪ/"),
          PhonicsSeeExample("Ink", "🖋️", "/ɪ/"),
          PhonicsSeeExample("Iguana", "🦎", "/ɪ/")
        ),
        chooseQuestion = "Which picture starts with the short /ɪ/ sound?",
        chooseOptions = listOf(
          GameOption("igloo", "Igloo", "🧊"),
          GameOption("orange", "Orange", "🍊"),
          GameOption("umbrella", "Umbrella", "☂️")
        ),
        correctOptionId = "igloo",
        blendSequence = "I + G + L + O + O = IGLOO",
        speakPrompt = "Say /ɪ/ ... Igloo!"
      ),
      PhonicsLessonItem(
        id = "vowel_short_o",
        subcategory = PhonicsSubcategory.SHORT_VOWELS,
        soundOrTopic = "Short O (/ɒ/)",
        targetWord = "OCTOPUS",
        letters = listOf("O", "C", "T", "O", "P", "U", "S"),
        sounds = listOf("/ɒ/", "/k/", "/t/", "/ə/", "/p/", "/ə/", "/s/"),
        emoji = "🐙",
        hearPrompt = "Short O says /ɒ/ as in Octopus, Otter, and Dog!",
        seeExamples = listOf(
          PhonicsSeeExample("Octopus", "🐙", "/ɒ/"),
          PhonicsSeeExample("Otter", "🦦", "/ɒ/"),
          PhonicsSeeExample("Ostrich", "🦤", "/ɒ/"),
          PhonicsSeeExample("Ox", "🐂", "/ɒ/")
        ),
        chooseQuestion = "Which picture starts with short /ɒ/?",
        chooseOptions = listOf(
          GameOption("octopus", "Octopus", "🐙"),
          GameOption("elephant", "Elephant", "🐘"),
          GameOption("apple", "Apple", "🍎")
        ),
        correctOptionId = "octopus",
        blendSequence = "O + C + T + O + P + U + S = OCTOPUS",
        speakPrompt = "Say /ɒ/ ... Octopus!"
      ),
      PhonicsLessonItem(
        id = "vowel_short_u",
        subcategory = PhonicsSubcategory.SHORT_VOWELS,
        soundOrTopic = "Short U (/ʌ/)",
        targetWord = "UMBRELLA",
        letters = listOf("U", "M", "B", "R", "E", "L", "L", "A"),
        sounds = listOf("/ʌ/", "/m/", "/b/", "/r/", "/e/", "/l/", "/ə/"),
        emoji = "☂️",
        hearPrompt = "Short U says /ʌ/ as in Umbrella, Up, and Sun!",
        seeExamples = listOf(
          PhonicsSeeExample("Umbrella", "☂️", "/ʌ/"),
          PhonicsSeeExample("Under", "👇", "/ʌ/"),
          PhonicsSeeExample("Up", "⬆️", "/ʌ/"),
          PhonicsSeeExample("Uncle", "👨", "/ʌ/")
        ),
        chooseQuestion = "Which picture starts with the short /ʌ/ sound?",
        chooseOptions = listOf(
          GameOption("umbrella", "Umbrella", "☂️"),
          GameOption("igloo", "Igloo", "🧊"),
          GameOption("egg", "Egg", "🥚")
        ),
        correctOptionId = "umbrella",
        blendSequence = "U + M + B + R + E + L + L + A = UMBRELLA",
        speakPrompt = "Say /ʌ/ ... Umbrella!"
      ),

      // 3. Beginning Sounds
      PhonicsLessonItem(
        id = "beg_sound_d",
        subcategory = PhonicsSubcategory.BEGINNING_SOUNDS,
        soundOrTopic = "Beginning /d/",
        targetWord = "DOG",
        letters = listOf("D", "O", "G"),
        sounds = listOf("/d/", "/ɒ/", "/g/"),
        emoji = "🐶",
        hearPrompt = "Listen closely: Dog! What is the first sound? /d/ for Dog!",
        seeExamples = listOf(
          PhonicsSeeExample("Dog", "🐶", "/d/"),
          PhonicsSeeExample("Duck", "🦆", "/d/"),
          PhonicsSeeExample("Drum", "🥁", "/d/"),
          PhonicsSeeExample("Door", "🚪", "/d/")
        ),
        chooseQuestion = "🐶 Dog begins with which letter sound?",
        chooseOptions = listOf(
          GameOption("d", "Letter D (/d/)", "🐶"),
          GameOption("m", "Letter M (/m/)", "🐒"),
          GameOption("s", "Letter S (/s/)", "☀️")
        ),
        correctOptionId = "d",
        blendSequence = "D + O + G = DOG",
        speakPrompt = "Say /d/ ... Dog!"
      ),
      PhonicsLessonItem(
        id = "beg_sound_c",
        subcategory = PhonicsSubcategory.BEGINNING_SOUNDS,
        soundOrTopic = "Beginning /k/",
        targetWord = "CAT",
        letters = listOf("C", "A", "T"),
        sounds = listOf("/k/", "/æ/", "/t/"),
        emoji = "🐱",
        hearPrompt = "Listen closely: Cat! The first sound is /k/! Letter C.",
        seeExamples = listOf(
          PhonicsSeeExample("Cat", "🐱", "/k/"),
          PhonicsSeeExample("Car", "🚗", "/k/"),
          PhonicsSeeExample("Cup", "☕", "/k/"),
          PhonicsSeeExample("Cake", "🎂", "/k/")
        ),
        chooseQuestion = "🐱 Cat starts with which sound?",
        chooseOptions = listOf(
          GameOption("c", "Letter C (/k/)", "🐱"),
          GameOption("b", "Letter B (/b/)", "⚽"),
          GameOption("p", "Letter P (/p/)", "🐷")
        ),
        correctOptionId = "c",
        blendSequence = "C + A + T = CAT",
        speakPrompt = "Say /k/ ... Cat!"
      ),

      // 4. Ending Sounds
      PhonicsLessonItem(
        id = "end_sound_t",
        subcategory = PhonicsSubcategory.ENDING_SOUNDS,
        soundOrTopic = "Ending /t/",
        targetWord = "BAT",
        letters = listOf("B", "A", "T"),
        sounds = listOf("/b/", "/æ/", "/t/"),
        emoji = "🦇",
        hearPrompt = "Listen to the end: Ba-t! It ends with /t/!",
        seeExamples = listOf(
          PhonicsSeeExample("Bat", "🦇", "/t/"),
          PhonicsSeeExample("Cat", "🐱", "/t/"),
          PhonicsSeeExample("Hat", "🎩", "/t/"),
          PhonicsSeeExample("Nut", "🥜", "/t/")
        ),
        chooseQuestion = "Which sound is at the very END of 'Cat' 🐱?",
        chooseOptions = listOf(
          GameOption("t", "/t/ (Letter T)", "🎯"),
          GameOption("p", "/p/ (Letter P)", "🐷"),
          GameOption("m", "/m/ (Letter M)", "🐒")
        ),
        correctOptionId = "t",
        blendSequence = "B + A + T = BAT",
        speakPrompt = "Say the ending sound: /t/!"
      ),
      PhonicsLessonItem(
        id = "end_sound_g",
        subcategory = PhonicsSubcategory.ENDING_SOUNDS,
        soundOrTopic = "Ending /g/",
        targetWord = "PIG",
        letters = listOf("P", "I", "G"),
        sounds = listOf("/p/", "/ɪ/", "/g/"),
        emoji = "🐷",
        hearPrompt = "Listen to the end: Pi-g! It ends with /g/!",
        seeExamples = listOf(
          PhonicsSeeExample("Pig", "🐷", "/g/"),
          PhonicsSeeExample("Dog", "🐶", "/g/"),
          PhonicsSeeExample("Bug", "🐞", "/g/"),
          PhonicsSeeExample("Frog", "🐸", "/g/")
        ),
        chooseQuestion = "Which sound is at the END of 'Pig' 🐷?",
        chooseOptions = listOf(
          GameOption("g", "/g/ (Letter G)", "🐷"),
          GameOption("d", "/d/ (Letter D)", "🐶"),
          GameOption("s", "/s/ (Letter S)", "☀️")
        ),
        correctOptionId = "g",
        blendSequence = "P + I + G = PIG",
        speakPrompt = "Say the ending sound: /g/!"
      ),

      // 5. Middle Sounds
      PhonicsLessonItem(
        id = "mid_sound_a",
        subcategory = PhonicsSubcategory.MIDDLE_SOUNDS,
        soundOrTopic = "Middle /æ/",
        targetWord = "HAT",
        letters = listOf("H", "A", "T"),
        sounds = listOf("/h/", "/æ/", "/t/"),
        emoji = "🎩",
        hearPrompt = "Listen to the middle: H - /æ/ - T. The middle vowel is /æ/ (Letter A)!",
        seeExamples = listOf(
          PhonicsSeeExample("Hat", "🎩", "/æ/"),
          PhonicsSeeExample("Cat", "🐱", "/æ/"),
          PhonicsSeeExample("Map", "🗺️", "/æ/"),
          PhonicsSeeExample("Bag", "🎒", "/æ/")
        ),
        chooseQuestion = "What is the middle vowel sound in 'HAT' 🎩?",
        chooseOptions = listOf(
          GameOption("a", "Short A (/æ/)", "🎩"),
          GameOption("i", "Short I (/ɪ/)", "🧊"),
          GameOption("o", "Short O (/ɒ/)", "🐙")
        ),
        correctOptionId = "a",
        blendSequence = "H + A + T = HAT",
        speakPrompt = "Say the middle sound: /æ/!"
      ),
      PhonicsLessonItem(
        id = "mid_sound_u",
        subcategory = PhonicsSubcategory.MIDDLE_SOUNDS,
        soundOrTopic = "Middle /ʌ/",
        targetWord = "CUP",
        letters = listOf("C", "U", "P"),
        sounds = listOf("/k/", "/ʌ/", "/p/"),
        emoji = "☕",
        hearPrompt = "Listen to the middle: C - /ʌ/ - P. The middle vowel is /ʌ/ (Letter U)!",
        seeExamples = listOf(
          PhonicsSeeExample("Cup", "☕", "/ʌ/"),
          PhonicsSeeExample("Sun", "☀️", "/ʌ/"),
          PhonicsSeeExample("Bug", "🐞", "/ʌ/"),
          PhonicsSeeExample("Bus", "🚌", "/ʌ/")
        ),
        chooseQuestion = "What is the middle vowel sound in 'CUP' ☕?",
        chooseOptions = listOf(
          GameOption("u", "Short U (/ʌ/)", "☕"),
          GameOption("e", "Short E (/e/)", "🥚"),
          GameOption("a", "Short A (/æ/)", "🍎")
        ),
        correctOptionId = "u",
        blendSequence = "C + U + P = CUP",
        speakPrompt = "Say the middle sound: /ʌ/!"
      ),

      // 6. CVC Words
      PhonicsLessonItem(
        id = "cvc_cat",
        subcategory = PhonicsSubcategory.CVC_WORDS,
        soundOrTopic = "CVC: C-A-T",
        targetWord = "CAT",
        letters = listOf("C", "A", "T"),
        sounds = listOf("/k/", "/æ/", "/t/"),
        emoji = "🐱",
        hearPrompt = "Consonant C + Vowel A + Consonant T makes CAT! /k/ - /æ/ - /t/.",
        seeExamples = listOf(
          PhonicsSeeExample("Cat", "🐱", "C-A-T"),
          PhonicsSeeExample("Bat", "🦇", "B-A-T"),
          PhonicsSeeExample("Hat", "🎩", "H-A-T"),
          PhonicsSeeExample("Rat", "🐀", "R-A-T")
        ),
        chooseQuestion = "Which 3-letter word spells 🐱?",
        chooseOptions = listOf(
          GameOption("cat", "C-A-T (Cat)", "🐱"),
          GameOption("dog", "D-O-G (Dog)", "🐶"),
          GameOption("sun", "S-U-N (Sun)", "☀️")
        ),
        correctOptionId = "cat",
        blendSequence = "C + A + T = CAT",
        speakPrompt = "Read aloud: C-A-T ... CAT!"
      ),
      PhonicsLessonItem(
        id = "cvc_dog",
        subcategory = PhonicsSubcategory.CVC_WORDS,
        soundOrTopic = "CVC: D-O-G",
        targetWord = "DOG",
        letters = listOf("D", "O", "G"),
        sounds = listOf("/d/", "/ɒ/", "/g/"),
        emoji = "🐶",
        hearPrompt = "D + O + G blends into DOG! /d/ - /ɒ/ - /g/.",
        seeExamples = listOf(
          PhonicsSeeExample("Dog", "🐶", "D-O-G"),
          PhonicsSeeExample("Log", "🪵", "L-O-G"),
          PhonicsSeeExample("Frog", "🐸", "F-R-O-G"),
          PhonicsSeeExample("Fog", "🌫️", "F-O-G")
        ),
        chooseQuestion = "Which word matches this cute puppy 🐶?",
        chooseOptions = listOf(
          GameOption("dog", "D-O-G", "🐶"),
          GameOption("pig", "P-I-G", "🐷"),
          GameOption("bed", "B-E-D", "🛏️")
        ),
        correctOptionId = "dog",
        blendSequence = "D + O + G = DOG",
        speakPrompt = "Read aloud: D-O-G ... DOG!"
      ),
      PhonicsLessonItem(
        id = "cvc_sun",
        subcategory = PhonicsSubcategory.CVC_WORDS,
        soundOrTopic = "CVC: S-U-N",
        targetWord = "SUN",
        letters = listOf("S", "U", "N"),
        sounds = listOf("/s/", "/ʌ/", "/n/"),
        emoji = "☀️",
        hearPrompt = "S + U + N blends into SUN! /s/ - /ʌ/ - /n/.",
        seeExamples = listOf(
          PhonicsSeeExample("Sun", "☀️", "S-U-N"),
          PhonicsSeeExample("Run", "🏃", "R-U-N"),
          PhonicsSeeExample("Fun", "🎉", "F-U-N"),
          PhonicsSeeExample("Bun", "🍞", "B-U-N")
        ),
        chooseQuestion = "Which word matches the bright sun ☀️?",
        chooseOptions = listOf(
          GameOption("sun", "S-U-N", "☀️"),
          GameOption("pan", "P-A-N", "🍳"),
          GameOption("box", "B-O-X", "📦")
        ),
        correctOptionId = "sun",
        blendSequence = "S + U + N = SUN",
        speakPrompt = "Read aloud: S-U-N ... SUN!"
      ),

      // 7. Word Families
      PhonicsLessonItem(
        id = "fam_at",
        subcategory = PhonicsSubcategory.WORD_FAMILIES,
        soundOrTopic = "-AT Family",
        targetWord = "CAT",
        letters = listOf("C", "A", "T"),
        sounds = listOf("/k/", "/æ/", "/t/"),
        emoji = "🏡",
        hearPrompt = "All words in the -AT family end with /æt/! Cat, Bat, Hat, Mat.",
        seeExamples = listOf(
          PhonicsSeeExample("Cat", "🐱", "-AT"),
          PhonicsSeeExample("Bat", "🦇", "-AT"),
          PhonicsSeeExample("Hat", "🎩", "-AT"),
          PhonicsSeeExample("Mat", "🧘", "-AT")
        ),
        chooseQuestion = "Which word rhymes with Cat in the -AT family?",
        chooseOptions = listOf(
          GameOption("hat", "Hat 🎩", "🎩"),
          GameOption("dog", "Dog 🐶", "🐶"),
          GameOption("sun", "Sun ☀️", "☀️")
        ),
        correctOptionId = "hat",
        blendSequence = "H + AT = HAT",
        speakPrompt = "Say: Cat, Bat, Hat, Mat!",
        wordFamilyList = listOf("cat" to "🐱", "bat" to "🦇", "hat" to "🎩", "mat" to "🧘", "rat" to "🐀")
      ),
      PhonicsLessonItem(
        id = "fam_an",
        subcategory = PhonicsSubcategory.WORD_FAMILIES,
        soundOrTopic = "-AN Family",
        targetWord = "FAN",
        letters = listOf("F", "A", "N"),
        sounds = listOf("/f/", "/æ/", "/n/"),
        emoji = "🪭",
        hearPrompt = "The -AN family sounds like /æn/! Fan, Pan, Can, Van, Man.",
        seeExamples = listOf(
          PhonicsSeeExample("Fan", "🪭", "-AN"),
          PhonicsSeeExample("Pan", "🍳", "-AN"),
          PhonicsSeeExample("Can", "🥫", "-AN"),
          PhonicsSeeExample("Van", "🚐", "-AN")
        ),
        chooseQuestion = "Which word belongs to the -AN family?",
        chooseOptions = listOf(
          GameOption("pan", "Pan 🍳", "🍳"),
          GameOption("pig", "Pig 🐷", "🐷"),
          GameOption("bed", "Bed 🛏️", "🛏️")
        ),
        correctOptionId = "pan",
        blendSequence = "P + AN = PAN",
        speakPrompt = "Say: Fan, Pan, Can, Van!",
        wordFamilyList = listOf("fan" to "🪭", "pan" to "🍳", "can" to "🥫", "van" to "🚐", "man" to "👨")
      ),

      // 8. Blending
      PhonicsLessonItem(
        id = "blend_bat",
        subcategory = PhonicsSubcategory.BLENDING,
        soundOrTopic = "Sound Blending",
        targetWord = "BAT",
        letters = listOf("B", "A", "T"),
        sounds = listOf("/b/", "/æ/", "/t/"),
        emoji = "🦇",
        hearPrompt = "Let's join the sounds smoothly: /b/ ... /æ/ ... /t/ → BAT!",
        seeExamples = listOf(
          PhonicsSeeExample("B", "🔵", "/b/"),
          PhonicsSeeExample("A", "🔵", "/æ/"),
          PhonicsSeeExample("T", "🔵", "/t/"),
          PhonicsSeeExample("BAT", "🦇", "BAT")
        ),
        chooseQuestion = "Join /b/ + /æ/ + /t/. What word does it make?",
        chooseOptions = listOf(
          GameOption("bat", "BAT 🦇", "🦇"),
          GameOption("bag", "BAG 🎒", "🎒"),
          GameOption("bed", "BED 🛏️", "🛏️")
        ),
        correctOptionId = "bat",
        blendSequence = "B + A + T = BAT",
        speakPrompt = "Swipe and blend: B + A + T → BAT!"
      ),
      PhonicsLessonItem(
        id = "blend_pig",
        subcategory = PhonicsSubcategory.BLENDING,
        soundOrTopic = "Sound Blending",
        targetWord = "PIG",
        letters = listOf("P", "I", "G"),
        sounds = listOf("/p/", "/ɪ/", "/g/"),
        emoji = "🐷",
        hearPrompt = "Let's blend: /p/ ... /ɪ/ ... /g/ → PIG!",
        seeExamples = listOf(
          PhonicsSeeExample("P", "🔵", "/p/"),
          PhonicsSeeExample("I", "🔵", "/ɪ/"),
          PhonicsSeeExample("G", "🔵", "/g/"),
          PhonicsSeeExample("PIG", "🐷", "PIG")
        ),
        chooseQuestion = "Join /p/ + /ɪ/ + /g/. What word is it?",
        chooseOptions = listOf(
          GameOption("pig", "PIG 🐷", "🐷"),
          GameOption("pen", "PEN 🖊️", "🖊️"),
          GameOption("pin", "PIN 📌", "📌")
        ),
        correctOptionId = "pig",
        blendSequence = "P + I + G = PIG",
        speakPrompt = "Blend the sounds: P + I + G → PIG!"
      ),

      // 9. Segmenting
      PhonicsLessonItem(
        id = "seg_dog",
        subcategory = PhonicsSubcategory.SEGMENTING,
        soundOrTopic = "Word Segmenting",
        targetWord = "DOG",
        letters = listOf("D", "O", "G"),
        sounds = listOf("/d/", "/ɒ/", "/g/"),
        emoji = "🐶",
        hearPrompt = "Let's break the word DOG into 3 pieces: D ... O ... G!",
        seeExamples = listOf(
          PhonicsSeeExample("DOG", "🐶", "Word"),
          PhonicsSeeExample("D", "✂️", "/d/"),
          PhonicsSeeExample("O", "✂️", "/ɒ/"),
          PhonicsSeeExample("G", "✂️", "/g/")
        ),
        chooseQuestion = "Break 'DOG' 🐶 into individual sounds. Which is correct?",
        chooseOptions = listOf(
          GameOption("dog_seg", "/d/ + /ɒ/ + /g/", "🐶"),
          GameOption("cat_seg", "/k/ + /æ/ + /t/", "🐱"),
          GameOption("sun_seg", "/s/ + /ʌ/ + /n/", "☀️")
        ),
        correctOptionId = "dog_seg",
        blendSequence = "DOG = D + O + G",
        speakPrompt = "Tap each sound: /d/ ... /ɒ/ ... /g/!"
      ),

      // 10. Digraphs
      PhonicsLessonItem(
        id = "digraph_sh",
        subcategory = PhonicsSubcategory.DIGRAPHS,
        soundOrTopic = "Digraph SH (/ʃ/)",
        targetWord = "SHIP",
        letters = listOf("S", "H", "I", "P"),
        sounds = listOf("/ʃ/", "/ɪ/", "/p/"),
        emoji = "🚢",
        hearPrompt = "S and H come together to make the quiet sound: /ʃ/! Shhh like a Ship.",
        seeExamples = listOf(
          PhonicsSeeExample("Ship", "🚢", "SH"),
          PhonicsSeeExample("Shell", "🐚", "SH"),
          PhonicsSeeExample("Sheep", "🐑", "SH"),
          PhonicsSeeExample("Shoe", "👟", "SH")
        ),
        chooseQuestion = "Which picture starts with the SH /ʃ/ sound?",
        chooseOptions = listOf(
          GameOption("ship", "Ship 🚢", "🚢"),
          GameOption("chair", "Chair 🪑", "🪑"),
          GameOption("thumb", "Thumb 👍", "👍")
        ),
        correctOptionId = "ship",
        blendSequence = "SH + I + P = SHIP",
        speakPrompt = "Say: Shhh ... SHIP!"
      ),
      PhonicsLessonItem(
        id = "digraph_ch",
        subcategory = PhonicsSubcategory.DIGRAPHS,
        soundOrTopic = "Digraph CH (/tʃ/)",
        targetWord = "CHAIR",
        letters = listOf("C", "H", "A", "I", "R"),
        sounds = listOf("/tʃ/", "/eə/"),
        emoji = "🪑",
        hearPrompt = "C and H make the choo-choo sound: /tʃ/! Chair and Cheese.",
        seeExamples = listOf(
          PhonicsSeeExample("Chair", "🪑", "CH"),
          PhonicsSeeExample("Cheese", "🧀", "CH"),
          PhonicsSeeExample("Chick", "🐥", "CH"),
          PhonicsSeeExample("Chips", "🍟", "CH")
        ),
        chooseQuestion = "Which picture starts with the CH /tʃ/ sound?",
        chooseOptions = listOf(
          GameOption("chair", "Chair 🪑", "🪑"),
          GameOption("ship", "Ship 🚢", "🚢"),
          GameOption("whale", "Whale 🐋", "🐋")
        ),
        correctOptionId = "chair",
        blendSequence = "CH + A + I + R = CHAIR",
        speakPrompt = "Say: Ch-ch-chair!"
      ),

      // 11. Consonant Blends
      PhonicsLessonItem(
        id = "blend_st",
        subcategory = PhonicsSubcategory.CONSONANT_BLENDS,
        soundOrTopic = "Blend ST",
        targetWord = "STAR",
        letters = listOf("S", "T", "A", "R"),
        sounds = listOf("/st/", "/ɑː/"),
        emoji = "⭐",
        hearPrompt = "S and T blend fast together: /st/! Star, Stop, Step.",
        seeExamples = listOf(
          PhonicsSeeExample("Star", "⭐", "ST"),
          PhonicsSeeExample("Stop", "🛑", "ST"),
          PhonicsSeeExample("Step", "🪜", "ST"),
          PhonicsSeeExample("Stick", "🥢", "ST")
        ),
        chooseQuestion = "Which picture starts with the ST blend?",
        chooseOptions = listOf(
          GameOption("star", "Star ⭐", "⭐"),
          GameOption("frog", "Frog 🐸", "🐸"),
          GameOption("cloud", "Cloud ☁️", "☁️")
        ),
        correctOptionId = "star",
        blendSequence = "ST + A + R = STAR",
        speakPrompt = "Say: St-st-star!"
      ),
      PhonicsLessonItem(
        id = "blend_bl",
        subcategory = PhonicsSubcategory.CONSONANT_BLENDS,
        soundOrTopic = "Blend BL",
        targetWord = "BLUE",
        letters = listOf("B", "L", "U", "E"),
        sounds = listOf("/bl/", "/uː/"),
        emoji = "🔵",
        hearPrompt = "B and L blend together: /bl/! Blue, Block, Blow.",
        seeExamples = listOf(
          PhonicsSeeExample("Blue", "🔵", "BL"),
          PhonicsSeeExample("Block", "🧱", "BL"),
          PhonicsSeeExample("Blow", "💨", "BL"),
          PhonicsSeeExample("Blanket", "🛏️", "BL")
        ),
        chooseQuestion = "Which picture starts with the BL blend?",
        chooseOptions = listOf(
          GameOption("blue", "Blue 🔵", "🔵"),
          GameOption("red", "Red 🔴", "🔴"),
          GameOption("green", "Green 🟢", "🟢")
        ),
        correctOptionId = "blue",
        blendSequence = "BL + U + E = BLUE",
        speakPrompt = "Say: Bl-bl-blue!"
      ),

      // 12. Long Vowels
      PhonicsLessonItem(
        id = "long_vowel_a",
        subcategory = PhonicsSubcategory.LONG_VOWELS,
        soundOrTopic = "Long A (/eɪ/)",
        targetWord = "CAKE",
        letters = listOf("C", "A", "K", "E"),
        sounds = listOf("/k/", "/eɪ/", "/k/"),
        emoji = "🎂",
        hearPrompt = "Long A says its own name: /eɪ/! Cake, Rain, Train.",
        seeExamples = listOf(
          PhonicsSeeExample("Cake", "🎂", "Long A"),
          PhonicsSeeExample("Rain", "🌧️", "Long A"),
          PhonicsSeeExample("Train", "🚂", "Long A"),
          PhonicsSeeExample("Lake", "🏞️", "Long A")
        ),
        chooseQuestion = "Which delicious treat has the LONG A sound?",
        chooseOptions = listOf(
          GameOption("cake", "Cake 🎂", "🎂"),
          GameOption("cat", "Cat 🐱", "🐱"),
          GameOption("cup", "Cup ☕", "☕")
        ),
        correctOptionId = "cake",
        blendSequence = "C + A + K + E = CAKE",
        speakPrompt = "Say: Long A ... Cake!"
      ),
      PhonicsLessonItem(
        id = "long_vowel_i",
        subcategory = PhonicsSubcategory.LONG_VOWELS,
        soundOrTopic = "Long I (/aɪ/)",
        targetWord = "BIKE",
        letters = listOf("B", "I", "K", "E"),
        sounds = listOf("/b/", "/aɪ/", "/k/"),
        emoji = "🚲",
        hearPrompt = "Long I says its own name: /aɪ/! Bike, Kite, Pie.",
        seeExamples = listOf(
          PhonicsSeeExample("Bike", "🚲", "Long I"),
          PhonicsSeeExample("Kite", "🪁", "Long I"),
          PhonicsSeeExample("Pie", "🥧", "Long I"),
          PhonicsSeeExample("Ice", "🧊", "Long I")
        ),
        chooseQuestion = "Which ride has the LONG I sound?",
        chooseOptions = listOf(
          GameOption("bike", "Bike 🚲", "🚲"),
          GameOption("bus", "Bus 🚌", "🚌"),
          GameOption("boat", "Boat ⛵", "⛵")
        ),
        correctOptionId = "bike",
        blendSequence = "B + I + K + E = BIKE",
        speakPrompt = "Say: Long I ... Bike!"
      ),

      // 13. Silent E (Magic E)
      PhonicsLessonItem(
        id = "magic_e_cap",
        subcategory = PhonicsSubcategory.SILENT_E,
        soundOrTopic = "Magic E (Cap → Cape)",
        targetWord = "CAPE",
        letters = listOf("C", "A", "P", "E"),
        sounds = listOf("/k/", "/eɪ/", "/p/"),
        emoji = "🦸‍♂️",
        hearPrompt = "Watch Magic E work! Cap 🧢 + Magic E becomes superhero CAPE 🦸‍♂️!",
        seeExamples = listOf(
          PhonicsSeeExample("Cap", "🧢", "Short A"),
          PhonicsSeeExample("Cape", "🦸‍♂️", "Magic E"),
          PhonicsSeeExample("Kit", "🧰", "Short I"),
          PhonicsSeeExample("Kite", "🪁", "Magic E")
        ),
        chooseQuestion = "When Magic-E joins 'CAP' 🧢, what does it turn into?",
        chooseOptions = listOf(
          GameOption("cape", "CAPE 🦸‍♂️ (Hero Cape!)", "🦸‍♂️"),
          GameOption("cup", "CUP ☕", "☕"),
          GameOption("cat", "CAT 🐱", "🐱")
        ),
        correctOptionId = "cape",
        blendSequence = "CAP + E = CAPE",
        speakPrompt = "Wave your magic wand: Cap turns into CAPE!",
        magicEBefore = "CAP" to "🧢",
        magicEAfter = "CAPE" to "🦸‍♂️"
      ),
      PhonicsLessonItem(
        id = "magic_e_kit",
        subcategory = PhonicsSubcategory.SILENT_E,
        soundOrTopic = "Magic E (Kit → Kite)",
        targetWord = "KITE",
        letters = listOf("K", "I", "T", "E"),
        sounds = listOf("/k/", "/aɪ/", "/t/"),
        emoji = "🪁",
        hearPrompt = "Magic E transforms KIT 🧰 into flying KITE 🪁 in the sky!",
        seeExamples = listOf(
          PhonicsSeeExample("Kit", "🧰", "Short I"),
          PhonicsSeeExample("Kite", "🪁", "Magic E"),
          PhonicsSeeExample("Hop", "🦘", "Short O"),
          PhonicsSeeExample("Hope", "🙏", "Magic E")
        ),
        chooseQuestion = "When Magic-E joins 'KIT' 🧰, what flying toy is made?",
        chooseOptions = listOf(
          GameOption("kite", "KITE 🪁 (Flying Kite!)", "🪁"),
          GameOption("king", "KING 👑", "👑"),
          GameOption("cat", "CAT 🐱", "🐱")
        ),
        correctOptionId = "kite",
        blendSequence = "KIT + E = KITE",
        speakPrompt = "Say: Kit + Magic E makes KITE!",
        magicEBefore = "KIT" to "🧰",
        magicEAfter = "KITE" to "🪁"
      ),

      // 14. Sight Words
      PhonicsLessonItem(
        id = "sight_the",
        subcategory = PhonicsSubcategory.SIGHT_WORDS,
        soundOrTopic = "Sight Word: THE & SEE",
        targetWord = "THE",
        letters = listOf("T", "H", "E"),
        sounds = listOf("/ð/", "/ə/"),
        emoji = "👁️",
        hearPrompt = "High-frequency word: THE! 'I see THE sun.' Remember it by sight!",
        seeExamples = listOf(
          PhonicsSeeExample("the", "⭐", "Sight Word"),
          PhonicsSeeExample("see", "👀", "Sight Word"),
          PhonicsSeeExample("is", "✨", "Sight Word"),
          PhonicsSeeExample("and", "➕", "Sight Word")
        ),
        chooseQuestion = "Which is the sight word 'THE'?",
        chooseOptions = listOf(
          GameOption("the", "THE", "⭐"),
          GameOption("and", "AND", "➕"),
          GameOption("you", "YOU", "👉")
        ),
        correctOptionId = "the",
        blendSequence = "T + H + E = THE",
        speakPrompt = "Say: THE ... I see the sun!"
      ),
      PhonicsLessonItem(
        id = "sight_play",
        subcategory = PhonicsSubcategory.SIGHT_WORDS,
        soundOrTopic = "Sight Word: PLAY & LIKE",
        targetWord = "PLAY",
        letters = listOf("P", "L", "A", "Y"),
        sounds = listOf("/p/", "/l/", "/eɪ/"),
        emoji = "🎮",
        hearPrompt = "Sight word: PLAY! 'We can play together!'",
        seeExamples = listOf(
          PhonicsSeeExample("play", "🎮", "Sight Word"),
          PhonicsSeeExample("like", "❤️", "Sight Word"),
          PhonicsSeeExample("can", "👍", "Sight Word"),
          PhonicsSeeExample("we", "👫", "Sight Word")
        ),
        chooseQuestion = "Which word says 'PLAY'?",
        chooseOptions = listOf(
          GameOption("play", "PLAY 🎮", "🎮"),
          GameOption("pig", "PIG 🐷", "🐷"),
          GameOption("pan", "PAN 🍳", "🍳")
        ),
        correctOptionId = "play",
        blendSequence = "P + L + A + Y = PLAY",
        speakPrompt = "Say: We love to PLAY!"
      ),

      // 15. Early Reading
      PhonicsLessonItem(
        id = "read_cat_mat",
        subcategory = PhonicsSubcategory.EARLY_READING,
        soundOrTopic = "Decodable Story 1",
        targetWord = "CAT ON MAT",
        letters = listOf("T", "H", "E", " ", "C", "A", "T"),
        sounds = listOf("/ðə/", "/kæt/"),
        emoji = "🐱",
        hearPrompt = "Let's read our first sentence: The cat sat on the mat!",
        seeExamples = listOf(
          PhonicsSeeExample("The", "📖", "Word 1"),
          PhonicsSeeExample("cat", "🐱", "Word 2"),
          PhonicsSeeExample("sat", "🪑", "Word 3"),
          PhonicsSeeExample("on the mat", "🧘", "Ending")
        ),
        chooseQuestion = "Where did the cat sit?",
        chooseOptions = listOf(
          GameOption("mat", "On the mat 🧘", "🧘"),
          GameOption("tree", "In the tree 🌳", "🌳"),
          GameOption("car", "In the car 🚗", "🚗")
        ),
        correctOptionId = "mat",
        blendSequence = "The + cat + sat + on + the + mat.",
        speakPrompt = "Read with me: The cat sat on the mat!",
        decodableSentence = "The cat sat on the mat."
      ),
      PhonicsLessonItem(
        id = "read_big_dog",
        subcategory = PhonicsSubcategory.EARLY_READING,
        soundOrTopic = "Decodable Story 2",
        targetWord = "I SEE DOG",
        letters = listOf("I", " ", "S", "E", "E"),
        sounds = listOf("/aɪ/", "/siː/"),
        emoji = "🐶",
        hearPrompt = "Let's read together: I see a big red dog!",
        seeExamples = listOf(
          PhonicsSeeExample("I", "🙋", "Word 1"),
          PhonicsSeeExample("see", "👀", "Word 2"),
          PhonicsSeeExample("a big", "🐘", "Word 3"),
          PhonicsSeeExample("dog", "🐶", "Word 4")
        ),
        chooseQuestion = "What animal did you see?",
        chooseOptions = listOf(
          GameOption("dog", "A big dog 🐶", "🐶"),
          GameOption("duck", "A little duck 🦆", "🦆"),
          GameOption("fish", "A tiny fish 🐟", "🐟")
        ),
        correctOptionId = "dog",
        blendSequence = "I + see + a + big + dog.",
        speakPrompt = "Read with me: I see a big dog!",
        decodableSentence = "I see a big red dog."
      )
    )
  }

  fun getPhonicsMinimalPairs(): List<PhonicsMinimalPair> {
    return listOf(
      PhonicsMinimalPair(
        id = "mp_bat_cat",
        word1 = "BAT",
        emoji1 = "🦇",
        word2 = "CAT",
        emoji2 = "🐱",
        contrastDescription = "Initial /b/ vs /k/",
        spokenWord = "BAT",
        promptVoice = "Listen carefully: Bat. Which word did you hear?",
        category = "Consonants",
        funFact = "Bat starts with /b/, while Cat starts with /k/!"
      ),
      PhonicsMinimalPair(
        id = "mp_pin_pan",
        word1 = "PIN",
        emoji1 = "📌",
        word2 = "PAN",
        emoji2 = "🍳",
        contrastDescription = "Vowel /ɪ/ vs /æ/",
        spokenWord = "PIN",
        promptVoice = "Listen carefully: Pin. Which word did you hear?",
        category = "Vowels",
        funFact = "Pin has the short 'i' sound /ɪ/, Pan has short 'a' /æ/!"
      ),
      PhonicsMinimalPair(
        id = "mp_ship_chip",
        word1 = "SHIP",
        emoji1 = "🚢",
        word2 = "CHIP",
        emoji2 = "🍟",
        contrastDescription = "Digraph /ʃ/ vs /tʃ/",
        spokenWord = "SHIP",
        promptVoice = "Listen carefully: Ship. Which word did you hear?",
        category = "Digraphs",
        funFact = "Ship starts with shhh /ʃ/, Chip starts with ch-ch /tʃ/!"
      ),
      PhonicsMinimalPair(
        id = "mp_pen_pan",
        word1 = "PEN",
        emoji1 = "🖊️",
        word2 = "PAN",
        emoji2 = "🍳",
        contrastDescription = "Vowel /e/ vs /æ/",
        spokenWord = "PAN",
        promptVoice = "Listen carefully: Pan. Which word did you hear?",
        category = "Vowels",
        funFact = "Pen has short /e/, Pan has short /æ/!"
      ),
      PhonicsMinimalPair(
        id = "mp_hat_hot",
        word1 = "HAT",
        emoji1 = "🎩",
        word2 = "HOT",
        emoji2 = "🔥",
        contrastDescription = "Vowel /æ/ vs /ɒ/",
        spokenWord = "HAT",
        promptVoice = "Listen carefully: Hat. Which word did you hear?",
        category = "Vowels",
        funFact = "Hat has short /æ/, Hot has short /ɒ/!"
      ),
      PhonicsMinimalPair(
        id = "mp_dog_log",
        word1 = "DOG",
        emoji1 = "🐶",
        word2 = "LOG",
        emoji2 = "🪵",
        contrastDescription = "Initial /d/ vs /l/",
        spokenWord = "DOG",
        promptVoice = "Listen carefully: Dog. Which word did you hear?",
        category = "Consonants",
        funFact = "Dog starts with bouncy /d/, Log starts with smooth /l/!"
      ),
      PhonicsMinimalPair(
        id = "mp_bear_pear",
        word1 = "BEAR",
        emoji1 = "🐻",
        word2 = "PEAR",
        emoji2 = "🍐",
        contrastDescription = "Voiced /b/ vs Unvoiced /p/",
        spokenWord = "PEAR",
        promptVoice = "Listen carefully: Pear. Which word did you hear?",
        category = "Consonants",
        funFact = "Bear uses voice box /b/, Pear is a gentle puff /p/!"
      ),
      PhonicsMinimalPair(
        id = "mp_fan_van",
        word1 = "FAN",
        emoji1 = "🪭",
        word2 = "VAN",
        emoji2 = "🚐",
        contrastDescription = "Unvoiced /f/ vs Voiced /v/",
        spokenWord = "VAN",
        promptVoice = "Listen carefully: Van. Which word did you hear?",
        category = "Consonants",
        funFact = "Fan makes a soft whisper /f/, Van buzzes with voice /v/!"
      ),
      PhonicsMinimalPair(
        id = "mp_bed_red",
        word1 = "BED",
        emoji1 = "🛏️",
        word2 = "RED",
        emoji2 = "🔴",
        contrastDescription = "Initial /b/ vs /r/",
        spokenWord = "BED",
        promptVoice = "Listen carefully: Bed. Which word did you hear?",
        category = "Rhyming",
        funFact = "Both rhyme with -ed, but start with /b/ vs /r/!"
      ),
      PhonicsMinimalPair(
        id = "mp_fox_box",
        word1 = "FOX",
        emoji1 = "🦊",
        word2 = "BOX",
        emoji2 = "📦",
        contrastDescription = "Initial /f/ vs /b/",
        spokenWord = "BOX",
        promptVoice = "Listen carefully: Box. Which word did you hear?",
        category = "Rhyming",
        funFact = "Both end in -ox, but start with /f/ vs /b/!"
      ),
      PhonicsMinimalPair(
        id = "mp_snake_cake",
        word1 = "SNAKE",
        emoji1 = "🐍",
        word2 = "CAKE",
        emoji2 = "🎂",
        contrastDescription = "Blend /sn/ vs Initial /k/",
        spokenWord = "SNAKE",
        promptVoice = "Listen carefully: Snake. Which word did you hear?",
        category = "Blends",
        funFact = "Snake has a hiss blend /sn/, Cake has a crisp /k/!"
      ),
      PhonicsMinimalPair(
        id = "mp_tree_three",
        word1 = "TREE",
        emoji1 = "🌳",
        word2 = "THREE",
        emoji2 = "3️⃣",
        contrastDescription = "Blend /tr/ vs Digraph /θr/",
        spokenWord = "THREE",
        promptVoice = "Listen carefully: Three. Which word did you hear?",
        category = "Digraphs",
        funFact = "Tree starts with /tr/, Three starts with soft th- /θ/!"
      )
    )
  }

  fun getPhonicsSoundSortItems(): List<PhonicsSoundSortItem> {
    return listOf(
      PhonicsSoundSortItem(
        id = "sort_m",
        targetSound = "/m/",
        targetLetter = "M",
        prompt = "Which words start with /m/?",
        options = listOf(
          SoundSortOption("m_mouse", "Mouse", "🐭", true),
          SoundSortOption("m_moon", "Moon", "🌙", true),
          SoundSortOption("m_apple", "Apple", "🍎", false),
          SoundSortOption("m_cat", "Cat", "🐱", false)
        )
      ),
      PhonicsSoundSortItem(
        id = "sort_s",
        targetSound = "/s/",
        targetLetter = "S",
        prompt = "Which words start with /s/?",
        options = listOf(
          SoundSortOption("s_sun", "Sun", "☀️", true),
          SoundSortOption("s_star", "Star", "⭐", true),
          SoundSortOption("s_dog", "Dog", "🐶", false),
          SoundSortOption("s_banana", "Banana", "🍌", false)
        )
      ),
      PhonicsSoundSortItem(
        id = "sort_b",
        targetSound = "/b/",
        targetLetter = "B",
        prompt = "Which words start with /b/?",
        options = listOf(
          SoundSortOption("b_ball", "Ball", "⚽", true),
          SoundSortOption("b_bee", "Bee", "🐝", true),
          SoundSortOption("b_car", "Car", "🚗", false),
          SoundSortOption("b_frog", "Frog", "🐸", false)
        )
      )
    )
  }

  fun getSoundSortingRounds(): List<SoundSortRound> {
    return listOf(
      // Round 1: /m/ vs /s/
      SoundSortRound(
        id = "round_m_s",
        title = "Sound /m/ vs /s/",
        description = "Drag objects into the /m/ Monkey basket or /s/ Sun basket!",
        bucketA = SoundBucket(
          sound = "/m/",
          letter = "M",
          name = "Monkey M",
          emoji = "🐒",
          colorHex = 0xFFFF8F00,
          bgHex = 0xFFFFF3E0
        ),
        bucketB = SoundBucket(
          sound = "/s/",
          letter = "S",
          name = "Sun S",
          emoji = "☀️",
          colorHex = 0xFF00897B,
          bgHex = 0xFFE0F2F1
        ),
        items = listOf(
          SoundSortGameItem("m1", "Monkey", "🐒", "M", "/m/", "M-M-Monkey! Starts with /m/!"),
          SoundSortGameItem("s1", "Sun", "☀️", "S", "/s/", "S-S-Sun! Starts with /s/!"),
          SoundSortGameItem("m2", "Moon", "🌙", "M", "/m/", "M-M-Moon! Starts with /m/!"),
          SoundSortGameItem("s2", "Star", "⭐", "S", "/s/", "S-S-Star! Starts with /s/!"),
          SoundSortGameItem("m3", "Milk", "🥛", "M", "/m/", "M-M-Milk! Starts with /m/!"),
          SoundSortGameItem("s3", "Snake", "🐍", "S", "/s/", "S-S-Snake! Starts with /s/!"),
          SoundSortGameItem("m4", "Mouse", "🐭", "M", "/m/", "M-M-Mouse! Starts with /m/!"),
          SoundSortGameItem("s4", "Spoon", "🥄", "S", "/s/", "S-S-Spoon! Starts with /s/!")
        )
      ),

      // Round 2: /b/ vs /p/
      SoundSortRound(
        id = "round_b_p",
        title = "Sound /b/ vs /p/",
        description = "Drag objects into the /b/ Ball basket or /p/ Pig basket!",
        bucketA = SoundBucket(
          sound = "/b/",
          letter = "B",
          name = "Ball B",
          emoji = "⚽",
          colorHex = 0xFF1E88E5,
          bgHex = 0xFFE3F2FD
        ),
        bucketB = SoundBucket(
          sound = "/p/",
          letter = "P",
          name = "Pig P",
          emoji = "🐷",
          colorHex = 0xFFEC407A,
          bgHex = 0xFFFCE4EC
        ),
        items = listOf(
          SoundSortGameItem("b1", "Ball", "⚽", "B", "/b/", "B-B-Ball! Starts with /b/!"),
          SoundSortGameItem("p1", "Pig", "🐷", "P", "/p/", "P-P-Pig! Starts with /p/!"),
          SoundSortGameItem("b2", "Bear", "🐻", "B", "/b/", "B-B-Bear! Starts with /b/!"),
          SoundSortGameItem("p2", "Pizza", "🍕", "P", "/p/", "P-P-Pizza! Starts with /p/!"),
          SoundSortGameItem("b3", "Bee", "🐝", "B", "/b/", "B-B-Bee! Starts with /b/!"),
          SoundSortGameItem("p3", "Panda", "🐼", "P", "/p/", "P-P-Panda! Starts with /p/!"),
          SoundSortGameItem("b4", "Book", "📖", "B", "/b/", "B-B-Book! Starts with /b/!"),
          SoundSortGameItem("p4", "Pencil", "✏️", "P", "/p/", "P-P-Pencil! Starts with /p/!")
        )
      ),

      // Round 3: /d/ vs /t/
      SoundSortRound(
        id = "round_d_t",
        title = "Sound /d/ vs /t/",
        description = "Sort puppy /d/ objects vs tiger /t/ objects!",
        bucketA = SoundBucket(
          sound = "/d/",
          letter = "D",
          name = "Dog D",
          emoji = "🐶",
          colorHex = 0xFF43A047,
          bgHex = 0xFFE8F5E9
        ),
        bucketB = SoundBucket(
          sound = "/t/",
          letter = "T",
          name = "Tiger T",
          emoji = "🐯",
          colorHex = 0xFFFB8C00,
          bgHex = 0xFFFFF3E0
        ),
        items = listOf(
          SoundSortGameItem("d1", "Dog", "🐶", "D", "/d/", "D-D-Dog! Starts with /d/!"),
          SoundSortGameItem("t1", "Tiger", "🐯", "T", "/t/", "T-T-Tiger! Starts with /t/!"),
          SoundSortGameItem("d2", "Duck", "🦆", "D", "/d/", "D-D-Duck! Starts with /d/!"),
          SoundSortGameItem("t2", "Tree", "🌳", "T", "/t/", "T-T-Tree! Starts with /t/!"),
          SoundSortGameItem("d3", "Drum", "🥁", "D", "/d/", "D-D-Drum! Starts with /d/!"),
          SoundSortGameItem("t3", "Train", "🚂", "T", "/t/", "T-T-Train! Starts with /t/!"),
          SoundSortGameItem("d4", "Donut", "🍩", "D", "/d/", "D-D-Donut! Starts with /d/!"),
          SoundSortGameItem("t4", "Turtle", "🐢", "T", "/t/", "T-T-Turtle! Starts with /t/!")
        )
      ),

      // Round 4: /c/ vs /f/
      SoundSortRound(
        id = "round_c_f",
        title = "Sound /c/ vs /f/",
        description = "Sort kitty /k/ objects vs fish /f/ objects!",
        bucketA = SoundBucket(
          sound = "/k/",
          letter = "C",
          name = "Cat C",
          emoji = "🐱",
          colorHex = 0xFF8E24AA,
          bgHex = 0xFFF3E5F5
        ),
        bucketB = SoundBucket(
          sound = "/f/",
          letter = "F",
          name = "Fish F",
          emoji = "🐟",
          colorHex = 0xFF00ACC1,
          bgHex = 0xFFE0F7FA
        ),
        items = listOf(
          SoundSortGameItem("c1", "Cat", "🐱", "C", "/k/", "C-C-Cat! Starts with /k/!"),
          SoundSortGameItem("f1", "Fish", "🐟", "F", "/f/", "F-F-Fish! Starts with /f/!"),
          SoundSortGameItem("c2", "Car", "🚗", "C", "/k/", "C-C-Car! Starts with /k/!"),
          SoundSortGameItem("f2", "Frog", "🐸", "F", "/f/", "F-F-Frog! Starts with /f/!"),
          SoundSortGameItem("c3", "Cake", "🎂", "C", "/k/", "C-C-Cake! Starts with /k/!"),
          SoundSortGameItem("f3", "Flower", "🌸", "F", "/f/", "F-F-Flower! Starts with /f/!"),
          SoundSortGameItem("c4", "Cow", "🐮", "C", "/k/", "C-C-Cow! Starts with /k/!"),
          SoundSortGameItem("f4", "Fox", "🦊", "F", "/f/", "F-F-Fox! Starts with /f/!")
        )
      ),

      // Round 5: /h/ vs /r/
      SoundSortRound(
        id = "round_h_r",
        title = "Sound /h/ vs /r/",
        description = "Sort cozy /h/ objects vs speedy /r/ objects!",
        bucketA = SoundBucket(
          sound = "/h/",
          letter = "H",
          name = "Hat H",
          emoji = "🎩",
          colorHex = 0xFF6D4C41,
          bgHex = 0xFFEFEBE9
        ),
        bucketB = SoundBucket(
          sound = "/r/",
          letter = "R",
          name = "Rabbit R",
          emoji = "🐰",
          colorHex = 0xFFE53935,
          bgHex = 0xFFFFEBEE
        ),
        items = listOf(
          SoundSortGameItem("h1", "Hat", "🎩", "H", "/h/", "H-H-Hat! Starts with /h/!"),
          SoundSortGameItem("r1", "Rabbit", "🐰", "R", "/r/", "R-R-Rabbit! Starts with /r/!"),
          SoundSortGameItem("h2", "House", "🏠", "H", "/h/", "H-H-House! Starts with /h/!"),
          SoundSortGameItem("r2", "Rocket", "🚀", "R", "/r/", "R-R-Rocket! Starts with /r/!"),
          SoundSortGameItem("h3", "Heart", "💖", "H", "/h/", "H-H-Heart! Starts with /h/!"),
          SoundSortGameItem("r3", "Rainbow", "🌈", "R", "/r/", "R-R-Rainbow! Starts with /r/!"),
          SoundSortGameItem("h4", "Horse", "🐴", "H", "/h/", "H-H-Horse! Starts with /h/!"),
          SoundSortGameItem("r4", "Robot", "🤖", "R", "/r/", "R-R-Robot! Starts with /r/!")
        )
      )
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
      NumberCard(10, "TEN", "🐥🐥🐥🐥🐥🐥🐥🐥🐥🐥", "10 Little Ducks"),
      NumberCard(11, "ELEVEN", "🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀", "11 Rockets"),
      NumberCard(12, "TWELVE", "🍪🍪🍪🍪🍪🍪🍪🍪🍪🍪🍪🍪", "12 Cookies"),
      NumberCard(15, "FIFTEEN", "💎💎💎💎💎💎💎💎💎💎💎💎💎💎💎", "15 Gems"),
      NumberCard(20, "TWENTY", "🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉", "20 Confetti Poppers")
    )
  }

  fun getNumberLessonItems(): List<NumberLessonItem> {
    return listOf(
      // Level 1: Numbers 1-5
      NumberLessonItem(
        id = "num_item_1",
        subcategory = NumberSubcategory.COUNTING_1_5,
        number = 1,
        word = "ONE",
        emoji = "🍎",
        countText = "1 Juicy Apple",
        hearPrompt = "Number 1! Only one shiny red apple.",
        seeExamples = listOf(
          NumberSeeExample("1 Sun", "☀️", 1, "One Sun in the sky!"),
          NumberSeeExample("1 Moon", "🌙", 1, "One Moon at night!"),
          NumberSeeExample("1 Puppy", "🐶", 1, "One playful puppy!")
        ),
        chooseQuestion = "Where is Number 1?",
        chooseOptions = listOf(
          GameOption("1", "One", "1️⃣"),
          GameOption("2", "Two", "2️⃣"),
          GameOption("3", "Three", "3️⃣")
        ),
        correctOptionId = "1",
        speakPrompt = "Can you say ONE?"
      ),
      NumberLessonItem(
        id = "num_item_2",
        subcategory = NumberSubcategory.COUNTING_1_5,
        number = 2,
        word = "TWO",
        emoji = "⚽",
        countText = "2 Bouncing Balls",
        hearPrompt = "Number 2! 1, 2 bouncing soccer balls.",
        seeExamples = listOf(
          NumberSeeExample("2 Shoes", "👟👟", 2, "A pair of shoes!"),
          NumberSeeExample("2 Eyes", "👀", 2, "Two seeing eyes!"),
          NumberSeeExample("2 Kittens", "🐱🐱", 2, "Two cute kittens!")
        ),
        chooseQuestion = "Which card shows 2 soccer balls?",
        chooseOptions = listOf(
          GameOption("1", "1 Ball", "⚽"),
          GameOption("2", "2 Balls", "⚽⚽"),
          GameOption("4", "4 Balls", "⚽⚽⚽⚽")
        ),
        correctOptionId = "2",
        speakPrompt = "Can you say TWO?"
      ),
      NumberLessonItem(
        id = "num_item_3",
        subcategory = NumberSubcategory.COUNTING_1_5,
        number = 3,
        word = "THREE",
        emoji = "🍌",
        countText = "3 Sweet Bananas",
        hearPrompt = "Number 3! 1, 2, 3 delicious bananas.",
        seeExamples = listOf(
          NumberSeeExample("3 Triangles", "🔺🔺🔺", 3, "Three triangle sides!"),
          NumberSeeExample("3 Balloons", "🎈🎈🎈", 3, "Three flying balloons!"),
          NumberSeeExample("3 Stars", "⭐🌟⭐", 3, "Three shining stars!")
        ),
        chooseQuestion = "Where is Number 3?",
        chooseOptions = listOf(
          GameOption("5", "Five", "5️⃣"),
          GameOption("3", "Three", "3️⃣"),
          GameOption("1", "One", "1️⃣")
        ),
        correctOptionId = "3",
        speakPrompt = "Can you say THREE?"
      ),
      NumberLessonItem(
        id = "num_item_4",
        subcategory = NumberSubcategory.COUNTING_1_5,
        number = 4,
        word = "FOUR",
        emoji = "🚗",
        countText = "4 Speedy Cars",
        hearPrompt = "Number 4! 1, 2, 3, 4 zooming cars.",
        seeExamples = listOf(
          NumberSeeExample("4 Wheels", "🛞🛞🛞🛞", 4, "Four car wheels!"),
          NumberSeeExample("4 Square Sides", "⬜", 4, "Four equal sides!"),
          NumberSeeExample("4 Birds", "🐦🐦🐦🐦", 4, "Four singing birds!")
        ),
        chooseQuestion = "Find the group with 4 cars!",
        chooseOptions = listOf(
          GameOption("4", "4 Cars", "🚗🚗🚗🚗"),
          GameOption("2", "2 Cars", "🚗🚗"),
          GameOption("5", "5 Cars", "🚗🚗🚗🚗🚗")
        ),
        correctOptionId = "4",
        speakPrompt = "Can you say FOUR?"
      ),
      NumberLessonItem(
        id = "num_item_5",
        subcategory = NumberSubcategory.COUNTING_1_5,
        number = 5,
        word = "FIVE",
        emoji = "⭐",
        countText = "5 Golden Stars",
        hearPrompt = "Number 5! High five! 1, 2, 3, 4, 5 stars.",
        seeExamples = listOf(
          NumberSeeExample("5 Fingers", "🖐️", 5, "Five fingers on your hand!"),
          NumberSeeExample("5 Star Points", "⭐", 5, "Five points on a star!"),
          NumberSeeExample("5 Flowers", "🌸🌸🌸🌸🌸", 5, "Five pretty flowers!")
        ),
        chooseQuestion = "Where is Number 5?",
        chooseOptions = listOf(
          GameOption("2", "Two", "2️⃣"),
          GameOption("4", "Four", "4️⃣"),
          GameOption("5", "Five", "5️⃣")
        ),
        correctOptionId = "5",
        speakPrompt = "Can you give a high FIVE?"
      ),

      // Level 2: Numbers 6-10
      NumberLessonItem(
        id = "num_item_6",
        subcategory = NumberSubcategory.COUNTING_6_10,
        number = 6,
        word = "SIX",
        emoji = "🐱",
        countText = "6 Playful Kittens",
        hearPrompt = "Number 6! 5 plus 1 makes 6!",
        seeExamples = listOf(
          NumberSeeExample("6 Dice Dots", "🎲", 6, "Six dots on a rolling die!"),
          NumberSeeExample("6 Crayons", "🖍️🖍️🖍️🖍️🖍️🖍️", 6, "Six colored crayons!"),
          NumberSeeExample("6 Fish", "🐟🐟🐟🐟🐟🐟", 6, "Six swimming fish!")
        ),
        chooseQuestion = "Which number comes after 5?",
        chooseOptions = listOf(
          GameOption("6", "Six", "6️⃣"),
          GameOption("7", "Seven", "7️⃣"),
          GameOption("4", "Four", "4️⃣")
        ),
        correctOptionId = "6",
        speakPrompt = "Can you say SIX?"
      ),
      NumberLessonItem(
        id = "num_item_7",
        subcategory = NumberSubcategory.COUNTING_6_10,
        number = 7,
        word = "SEVEN",
        emoji = "🎈",
        countText = "7 Colorful Balloons",
        hearPrompt = "Number 7! Like the 7 rainbow colors!",
        seeExamples = listOf(
          NumberSeeExample("7 Rainbow Colors", "🌈", 7, "Seven colors in a rainbow!"),
          NumberSeeExample("7 Days", "📅", 7, "Seven days in a week!"),
          NumberSeeExample("7 Butterflies", "🦋🦋🦋🦋🦋🦋🦋", 7, "Seven fluttering butterflies!")
        ),
        chooseQuestion = "Where is Number 7?",
        chooseOptions = listOf(
          GameOption("7", "Seven", "7️⃣"),
          GameOption("1", "One", "1️⃣"),
          GameOption("8", "Eight", "8️⃣")
        ),
        correctOptionId = "7",
        speakPrompt = "Can you say SEVEN?"
      ),
      NumberLessonItem(
        id = "num_item_8",
        subcategory = NumberSubcategory.COUNTING_6_10,
        number = 8,
        word = "EIGHT",
        emoji = "🍓",
        countText = "8 Ripe Strawberries",
        hearPrompt = "Number 8! Like the 8 legs of an octopus!",
        seeExamples = listOf(
          NumberSeeExample("8 Octopus Legs", "🐙", 8, "Eight wiggly octopus legs!"),
          NumberSeeExample("8 Spider Legs", "🕷️", 8, "Eight little spider legs!"),
          NumberSeeExample("8 Cookies", "🍪🍪🍪🍪🍪🍪🍪🍪", 8, "Eight sweet cookies!")
        ),
        chooseQuestion = "Find Number 8!",
        chooseOptions = listOf(
          GameOption("3", "Three", "3️⃣"),
          GameOption("8", "Eight", "8️⃣"),
          GameOption("9", "Nine", "9️⃣")
        ),
        correctOptionId = "8",
        speakPrompt = "Can you say EIGHT?"
      ),
      NumberLessonItem(
        id = "num_item_9",
        subcategory = NumberSubcategory.COUNTING_6_10,
        number = 9,
        word = "NINE",
        emoji = "🍦",
        countText = "9 Cold Ice Creams",
        hearPrompt = "Number 9! Almost full ten!",
        seeExamples = listOf(
          NumberSeeExample("9 Apples", "🍎🍎🍎🍎🍎🍎🍎🍎🍎", 9, "Nine crunchy apples!"),
          NumberSeeExample("9 Hearts", "💖💖💖💖💖💖💖💖💖", 9, "Nine glowing hearts!"),
          NumberSeeExample("9 Bees", "🐝🐝🐝🐝🐝🐝🐝🐝🐝", 9, "Nine buzzing bees!")
        ),
        chooseQuestion = "Which number is Number 9?",
        chooseOptions = listOf(
          GameOption("6", "Six", "6️⃣"),
          GameOption("9", "Nine", "9️⃣"),
          GameOption("10", "Ten", "🔟")
        ),
        correctOptionId = "9",
        speakPrompt = "Can you say NINE?"
      ),
      NumberLessonItem(
        id = "num_item_10",
        subcategory = NumberSubcategory.COUNTING_6_10,
        number = 10,
        word = "TEN",
        emoji = "🐥",
        countText = "10 Happy Little Ducks",
        hearPrompt = "Number 10! All ten fingers on two hands!",
        seeExamples = listOf(
          NumberSeeExample("10 Fingers", "🙌", 10, "Ten fingers together!"),
          NumberSeeExample("10 Toes", "🦶🦶", 10, "Ten little toes!"),
          NumberSeeExample("10 Coins", "🪙🪙🪙🪙🪙🪙🪙🪙🪙🪙", 10, "Ten shiny coins!")
        ),
        chooseQuestion = "Where is the big Number 10?",
        chooseOptions = listOf(
          GameOption("10", "Ten", "🔟"),
          GameOption("1", "One", "1️⃣"),
          GameOption("0", "Zero", "0️⃣")
        ),
        correctOptionId = "10",
        speakPrompt = "Can you say TEN!"
      ),

      // Level 3: Teen Numbers 11-20
      NumberLessonItem(
        id = "num_item_11",
        subcategory = NumberSubcategory.COUNTING_11_20,
        number = 11,
        word = "ELEVEN",
        emoji = "🚀",
        countText = "11 Blasting Rockets",
        hearPrompt = "Number 11! Ten plus 1 is eleven!",
        seeExamples = listOf(
          NumberSeeExample("10 + 1", "🔟➕1️⃣", 11, "Ten and one more!"),
          NumberSeeExample("11 Stars", "⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐", 11, "Eleven shining stars!")
        ),
        chooseQuestion = "Which number is Eleven?",
        chooseOptions = listOf(
          GameOption("11", "Eleven", "1️⃣1️⃣"),
          GameOption("12", "Twelve", "1️⃣2️⃣"),
          GameOption("10", "Ten", "🔟")
        ),
        correctOptionId = "11"
      ),
      NumberLessonItem(
        id = "num_item_12",
        subcategory = NumberSubcategory.COUNTING_11_20,
        number = 12,
        word = "TWELVE",
        emoji = "🍪",
        countText = "12 Sweet Cookies",
        hearPrompt = "Number 12! A full dozen cookies!",
        seeExamples = listOf(
          NumberSeeExample("A Dozen", "🥚🥚🥚🥚🥚🥚🥚🥚🥚🥚🥚🥚", 12, "Twelve eggs in a carton!"),
          NumberSeeExample("12 Clock Hours", "⏰", 12, "Twelve hours on the clock face!")
        ),
        chooseQuestion = "Where is Number 12?",
        chooseOptions = listOf(
          GameOption("12", "Twelve", "1️⃣2️⃣"),
          GameOption("20", "Twenty", "2️⃣0️⃣"),
          GameOption("13", "Thirteen", "1️⃣3️⃣")
        ),
        correctOptionId = "12"
      ),
      NumberLessonItem(
        id = "num_item_20",
        subcategory = NumberSubcategory.COUNTING_11_20,
        number = 20,
        word = "TWENTY",
        emoji = "🎉",
        countText = "20 Party Poppers",
        hearPrompt = "Number 20! Two whole tens make twenty!",
        seeExamples = listOf(
          NumberSeeExample("2 Tens", "🔟🔟", 20, "Two groups of ten!"),
          NumberSeeExample("20 Fingers & Toes", "🙌🦶🦶", 20, "All fingers and toes together!")
        ),
        chooseQuestion = "Find Number 20!",
        chooseOptions = listOf(
          GameOption("20", "Twenty", "2️⃣0️⃣"),
          GameOption("12", "Twelve", "1️⃣2️⃣"),
          GameOption("2", "Two", "2️⃣")
        ),
        correctOptionId = "20"
      ),

      // Level 4: Skip Counting Patterns
      NumberLessonItem(
        id = "num_pattern_2s",
        subcategory = NumberSubcategory.NUMBER_PATTERNS,
        number = 2,
        word = "COUNT BY 2s",
        emoji = "👟",
        countText = "2, 4, 6, 8, 10!",
        hearPrompt = "Let's count by 2s! 2, 4, 6, 8, 10! Two pairs at a time!",
        seeExamples = listOf(
          NumberSeeExample("Pair of Shoes", "👟👟", 2, "2 shoes"),
          NumberSeeExample("Two Pairs", "👟👟 👟👟", 4, "4 shoes"),
          NumberSeeExample("Three Pairs", "👟👟 👟👟 👟👟", 6, "6 shoes")
        ),
        chooseQuestion = "What comes next in: 2, 4, 6, ___?",
        chooseOptions = listOf(
          GameOption("8", "Eight", "8️⃣"),
          GameOption("7", "Seven", "7️⃣"),
          GameOption("5", "Five", "5️⃣")
        ),
        correctOptionId = "8",
        mathFormula = "2, 4, 6, 8, 10 🔢"
      ),
      NumberLessonItem(
        id = "num_pattern_5s",
        subcategory = NumberSubcategory.NUMBER_PATTERNS,
        number = 5,
        word = "COUNT BY 5s",
        emoji = "🖐️",
        countText = "5, 10, 15, 20!",
        hearPrompt = "Let's count by 5s! High five! 5, 10, 15, 20!",
        seeExamples = listOf(
          NumberSeeExample("One Hand", "🖐️", 5, "5 fingers"),
          NumberSeeExample("Two Hands", "🖐️🖐️", 10, "10 fingers"),
          NumberSeeExample("Three Hands", "🖐️🖐️🖐️", 15, "15 fingers")
        ),
        chooseQuestion = "What comes next in: 5, 10, ___?",
        chooseOptions = listOf(
          GameOption("15", "Fifteen", "1️⃣5️⃣"),
          GameOption("11", "Eleven", "1️⃣1️⃣"),
          GameOption("12", "Twelve", "1️⃣2️⃣")
        ),
        correctOptionId = "15",
        mathFormula = "5, 10, 15, 20 🖐️"
      ),

      // Level 5: Number Bonds & Early Math
      NumberLessonItem(
        id = "num_bond_5",
        subcategory = NumberSubcategory.NUMBER_BONDS,
        number = 5,
        word = "BONDS OF 5",
        emoji = "➕",
        countText = "2 + 3 = 5!",
        hearPrompt = "Number Bond! 2 apples plus 3 apples make 5 apples!",
        seeExamples = listOf(
          NumberSeeExample("1 + 4 = 5", "🍎 + 🍎🍎🍎🍎", 5, "One plus four makes five"),
          NumberSeeExample("2 + 3 = 5", "🍎🍎 + 🍎🍎🍎", 5, "Two plus three makes five"),
          NumberSeeExample("0 + 5 = 5", "⭐ + ⭐⭐⭐⭐⭐", 5, "Zero plus five makes five")
        ),
        chooseQuestion = "2 plus 3 equals what number?",
        chooseOptions = listOf(
          GameOption("5", "Five", "5️⃣"),
          GameOption("4", "Four", "4️⃣"),
          GameOption("6", "Six", "6️⃣")
        ),
        correctOptionId = "5",
        mathFormula = "2 + 3 = 5"
      ),
      NumberLessonItem(
        id = "num_bond_10",
        subcategory = NumberSubcategory.NUMBER_BONDS,
        number = 10,
        word = "BONDS OF 10",
        emoji = "🔟",
        countText = "5 + 5 = 10!",
        hearPrompt = "Ten Friends! 5 fingers on left hand and 5 on right hand make 10!",
        seeExamples = listOf(
          NumberSeeExample("5 + 5 = 10", "🖐️ + 🖐️", 10, "Five and five make ten"),
          NumberSeeExample("6 + 4 = 10", "🔵🔵🔵🔵🔵🔵 + 🔴🔴🔴🔴", 10, "Six and four make ten"),
          NumberSeeExample("9 + 1 = 10", "⭐⭐⭐⭐⭐⭐⭐⭐⭐ + ⭐", 10, "Nine and one make ten")
        ),
        chooseQuestion = "5 plus 5 equals what number?",
        chooseOptions = listOf(
          GameOption("10", "Ten", "🔟"),
          GameOption("9", "Nine", "9️⃣"),
          GameOption("8", "Eight", "8️⃣")
        ),
        correctOptionId = "10",
        mathFormula = "5 + 5 = 10"
      )
    )
  }

  fun getNumberMinimalPairs(): List<NumberMinimalPair> {
    return listOf(
      NumberMinimalPair("np_1", 2, "Two", "⚽⚽", 3, "Three", "🍌🍌🍌", 2, "Two", "Count the objects: 2 balls vs 3 bananas!"),
      NumberMinimalPair("np_2", 4, "Four", "🚗🚗🚗🚗", 5, "Five", "⭐⭐⭐⭐⭐", 5, "Five", "Listen carefully: Four or Five?"),
      NumberMinimalPair("np_3", 6, "Six", "🐱🐱🐱🐱🐱🐱", 9, "Nine", "🍦🍦🍦🍦🍦🍦🍦🍦🍦", 6, "Six", "Look at the shape: Six vs Nine!"),
      NumberMinimalPair("np_4", 7, "Seven", "🎈🎈🎈🎈🎈🎈🎈", 8, "Eight", "🍓🍓🍓🍓🍓🍓🍓🍓", 8, "Eight", "Listen carefully: Seven or Eight?"),
      NumberMinimalPair("np_5", 13, "Thirteen", "💎", 30, "Thirty", "💰", 13, "Thirteen", "Listen for the -teen sound: Thirteen!"),
      NumberMinimalPair("np_6", 14, "Fourteen", "🚀", 40, "Forty", "🏎️", 14, "Fourteen", "Listen for Fourteen!")
    )
  }

  fun getColorLessonItems(): List<ColorLessonItem> {
    return listOf(
      // Level 1: Primary Colors
      ColorLessonItem(
        id = "col_red",
        subcategory = ColorSubcategory.PRIMARY_COLORS,
        colorName = "Red",
        colorHex = 0xFFEF5350,
        emoji = "🔴",
        hearPrompt = "Red! Red is warm and vibrant like an apple, a rose, or a shiny fire engine!",
        seeExamples = listOf(
          ColorSeeExample("Red Apple", "🍎", "Red", "Crisp red apple"),
          ColorSeeExample("Red Fire Truck", "🚒", "Red", "Heroic red fire engine"),
          ColorSeeExample("Red Rose", "🌹", "Red", "Sweet red blossom"),
          ColorSeeExample("Red Heart", "❤️", "Red", "Loving red heart")
        ),
        chooseQuestion = "Which object is RED?",
        chooseOptions = listOf(
          GameOption("apple", "Red Apple", "🍎"),
          GameOption("leaf", "Green Leaf", "🍃"),
          GameOption("ocean", "Blue Ocean", "🌊")
        ),
        correctOptionId = "apple",
        speakPrompt = "Can you say RED?"
      ),
      ColorLessonItem(
        id = "col_blue",
        subcategory = ColorSubcategory.PRIMARY_COLORS,
        colorName = "Blue",
        colorHex = 0xFF1E88E5,
        emoji = "🔵",
        hearPrompt = "Blue! Blue is calm and cool like the clear sky and the deep ocean waves!",
        seeExamples = listOf(
          ColorSeeExample("Blue Sky", "🌤️", "Blue", "Bright blue sunny sky"),
          ColorSeeExample("Blue Whale", "🐋", "Blue", "Giant blue sea whale"),
          ColorSeeExample("Blue Berries", "🫐", "Blue", "Yummy blueberries"),
          ColorSeeExample("Blue Jeans", "👖", "Blue", "Cozy blue denim")
        ),
        chooseQuestion = "Where is the BLUE circle?",
        chooseOptions = listOf(
          GameOption("yellow", "Yellow Circle", "🟡"),
          GameOption("blue", "Blue Circle", "🔵"),
          GameOption("red", "Red Circle", "🔴")
        ),
        correctOptionId = "blue",
        speakPrompt = "Can you say BLUE?"
      ),
      ColorLessonItem(
        id = "col_yellow",
        subcategory = ColorSubcategory.PRIMARY_COLORS,
        colorName = "Yellow",
        colorHex = 0xFFFFB300,
        emoji = "🟡",
        hearPrompt = "Yellow! Yellow is cheerful and bright like the morning sun and ripe bananas!",
        seeExamples = listOf(
          ColorSeeExample("Yellow Sun", "☀️", "Yellow", "Warm morning sunshine"),
          ColorSeeExample("Yellow Banana", "🍌", "Yellow", "Sweet yellow banana"),
          ColorSeeExample("Yellow Duckling", "🐥", "Yellow", "Cute yellow baby duck"),
          ColorSeeExample("Yellow Sunflower", "🌻", "Yellow", "Tall yellow sunflower")
        ),
        chooseQuestion = "Which fruit is naturally YELLOW?",
        chooseOptions = listOf(
          GameOption("banana", "Yellow Banana", "🍌"),
          GameOption("strawberry", "Red Strawberry", "🍓"),
          GameOption("grape", "Purple Grape", "🍇")
        ),
        correctOptionId = "banana",
        speakPrompt = "Can you say YELLOW?"
      ),

      // Level 2: Secondary Colors
      ColorLessonItem(
        id = "col_green",
        subcategory = ColorSubcategory.SECONDARY_COLORS,
        colorName = "Green",
        colorHex = 0xFF43A047,
        emoji = "🟢",
        hearPrompt = "Green! Green is the color of nature, trees, frogs, and crunchy broccoli!",
        seeExamples = listOf(
          ColorSeeExample("Green Tree", "🌳", "Green", "Big green tree"),
          ColorSeeExample("Green Frog", "🐸", "Green", "Hopping green frog"),
          ColorSeeExample("Green Broccoli", "🥦", "Green", "Healthy green veggie"),
          ColorSeeExample("Green Turtle", "🐢", "Green", "Swimming green sea turtle")
        ),
        chooseQuestion = "Which animal is GREEN?",
        chooseOptions = listOf(
          GameOption("frog", "Green Frog", "🐸"),
          GameOption("flamingo", "Pink Flamingo", "🦩"),
          GameOption("lion", "Golden Lion", "🦁")
        ),
        correctOptionId = "frog",
        mixRecipe = ColorMixRecipe("Blue", "🔵", "Yellow", "🟡", "Green", "🟢", 0xFF43A047, "Blue and Yellow mix to make Green!"),
        speakPrompt = "Can you say GREEN?"
      ),
      ColorLessonItem(
        id = "col_orange",
        subcategory = ColorSubcategory.SECONDARY_COLORS,
        colorName = "Orange",
        colorHex = 0xFFFF9800,
        emoji = "🟠",
        hearPrompt = "Orange! Orange is warm and playful like carrots, tigers, and juicy oranges!",
        seeExamples = listOf(
          ColorSeeExample("Orange Fruit", "🍊", "Orange", "Juicy fresh orange"),
          ColorSeeExample("Orange Carrot", "🥕", "Orange", "Crunchy garden carrot"),
          ColorSeeExample("Orange Tiger", "🐯", "Orange", "Striped orange tiger"),
          ColorSeeExample("Orange Pumpkin", "🎃", "Orange", "Big orange pumpkin")
        ),
        chooseQuestion = "Find the ORANGE vegetable!",
        chooseOptions = listOf(
          GameOption("carrot", "Orange Carrot", "🥕"),
          GameOption("cucumber", "Green Cucumber", "🥒"),
          GameOption("eggplant", "Purple Eggplant", "🍆")
        ),
        correctOptionId = "carrot",
        mixRecipe = ColorMixRecipe("Red", "🔴", "Yellow", "🟡", "Orange", "🟠", 0xFFFF9800, "Red and Yellow mix to make Orange!"),
        speakPrompt = "Can you say ORANGE?"
      ),
      ColorLessonItem(
        id = "col_purple",
        subcategory = ColorSubcategory.SECONDARY_COLORS,
        colorName = "Purple",
        colorHex = 0xFF8E24AA,
        emoji = "🟣",
        hearPrompt = "Purple! Purple is royal and magical like sweet grapes and fragrant lavender!",
        seeExamples = listOf(
          ColorSeeExample("Purple Grapes", "🍇", "Purple", "Sweet bunch of grapes"),
          ColorSeeExample("Purple Eggplant", "🍆", "Purple", "Glossy purple eggplant"),
          ColorSeeExample("Purple Crown", "👑", "Purple", "Royal velvet crown"),
          ColorSeeExample("Purple Butterfly", "🦋", "Purple", "Gentle purple butterfly")
        ),
        chooseQuestion = "Which fruit is PURPLE?",
        chooseOptions = listOf(
          GameOption("grapes", "Purple Grapes", "🍇"),
          GameOption("orange", "Orange Fruit", "🍊"),
          GameOption("banana", "Yellow Banana", "🍌")
        ),
        correctOptionId = "grapes",
        mixRecipe = ColorMixRecipe("Red", "🔴", "Blue", "🔵", "Purple", "🟣", 0xFF8E24AA, "Red and Blue mix to make Purple!"),
        speakPrompt = "Can you say PURPLE?"
      ),

      // Level 3: Shades & Neutrals
      ColorLessonItem(
        id = "col_pink",
        subcategory = ColorSubcategory.SHADES_AND_NEUTRALS,
        colorName = "Pink",
        colorHex = 0xFFEC407A,
        emoji = "🌸",
        hearPrompt = "Pink! Soft and lovely like cherry blossoms, flamingos, and sweet cotton candy!",
        seeExamples = listOf(
          ColorSeeExample("Pink Flamingo", "🦩", "Pink", "Tall pink wading bird"),
          ColorSeeExample("Pink Flower", "🌸", "Pink", "Blossoming pink petal"),
          ColorSeeExample("Pink Piglet", "🐷", "Pink", "Cute little pink pig")
        ),
        chooseQuestion = "Where is the PINK flower?",
        chooseOptions = listOf(
          GameOption("pink", "Pink Flower", "🌸"),
          GameOption("sun", "Yellow Sun", "☀️"),
          GameOption("leaf", "Green Leaf", "🍃")
        ),
        correctOptionId = "pink",
        mixRecipe = ColorMixRecipe("Red", "🔴", "White", "⚪", "Pink", "🌸", 0xFFEC407A, "Red and White mix to make Pink!"),
        speakPrompt = "Can you say PINK?"
      ),
      ColorLessonItem(
        id = "col_brown",
        subcategory = ColorSubcategory.SHADES_AND_NEUTRALS,
        colorName = "Brown",
        colorHex = 0xFF795548,
        emoji = "🟤",
        hearPrompt = "Brown! Earthy and cozy like teddy bears, chocolate, and tree bark!",
        seeExamples = listOf(
          ColorSeeExample("Brown Bear", "🐻", "Brown", "Furry brown grizzly"),
          ColorSeeExample("Brown Chocolate", "🍫", "Brown", "Sweet chocolate bar"),
          ColorSeeExample("Brown Monkey", "🐒", "Brown", "Playful brown monkey")
        ),
        chooseQuestion = "Which cute friend is BROWN?",
        chooseOptions = listOf(
          GameOption("bear", "Brown Bear", "🐻"),
          GameOption("duck", "Yellow Duck", "🐥"),
          GameOption("frog", "Green Frog", "🐸")
        ),
        correctOptionId = "bear"
      ),
      ColorLessonItem(
        id = "col_white_black",
        subcategory = ColorSubcategory.SHADES_AND_NEUTRALS,
        colorName = "Black & White",
        colorHex = 0xFF37474F,
        emoji = "🐼",
        hearPrompt = "Black and White! Classic contrasts like penguins, pandas, and zebras!",
        seeExamples = listOf(
          ColorSeeExample("Panda", "🐼", "Black & White", "Friendly giant panda"),
          ColorSeeExample("Penguin", "🐧", "Black & White", "Cute waddling penguin"),
          ColorSeeExample("Zebra", "🦓", "Black & White", "Striped savannah zebra")
        ),
        chooseQuestion = "Which animal has BLACK and WHITE stripes?",
        chooseOptions = listOf(
          GameOption("zebra", "Zebra", "🦓"),
          GameOption("lion", "Lion", "🦁"),
          GameOption("elephant", "Elephant", "🐘")
        ),
        correctOptionId = "zebra"
      ),

      // Level 4: Color Mixing Lab
      ColorLessonItem(
        id = "col_mix_orange",
        subcategory = ColorSubcategory.COLOR_MIXING,
        colorName = "Making Orange",
        colorHex = 0xFFFF9800,
        emoji = "🧪",
        hearPrompt = "Color Magic! Mix Red paint with Yellow paint to make bright Orange!",
        seeExamples = listOf(
          ColorSeeExample("Red Paint", "🔴", "Red", "Start with Red"),
          ColorSeeExample("Yellow Paint", "🟡", "Yellow", "Add bright Yellow"),
          ColorSeeExample("Orange Result", "🟠", "Orange", "You get Orange!")
        ),
        chooseQuestion = "Red + Yellow = Which magic color?",
        chooseOptions = listOf(
          GameOption("orange", "Orange", "🟠", colorHex = 0xFFFF9800),
          GameOption("green", "Green", "🟢", colorHex = 0xFF43A047),
          GameOption("blue", "Blue", "🔵", colorHex = 0xFF1E88E5)
        ),
        correctOptionId = "orange",
        mixRecipe = ColorMixRecipe("Red", "🔴", "Yellow", "🟡", "Orange", "🟠", 0xFFFF9800, "Red plus Yellow makes Orange!")
      ),
      ColorLessonItem(
        id = "col_mix_green",
        subcategory = ColorSubcategory.COLOR_MIXING,
        colorName = "Making Green",
        colorHex = 0xFF43A047,
        emoji = "🧪",
        hearPrompt = "Color Magic! Mix Blue and Yellow together to make fresh Green!",
        seeExamples = listOf(
          ColorSeeExample("Blue Paint", "🔵", "Blue", "Start with Blue"),
          ColorSeeExample("Yellow Paint", "🟡", "Yellow", "Add sunny Yellow"),
          ColorSeeExample("Green Result", "🟢", "Green", "You get vibrant Green!")
        ),
        chooseQuestion = "Blue + Yellow = Which color?",
        chooseOptions = listOf(
          GameOption("green", "Green", "🟢", colorHex = 0xFF43A047),
          GameOption("purple", "Purple", "🟣", colorHex = 0xFF8E24AA),
          GameOption("pink", "Pink", "🌸", colorHex = 0xFFEC407A)
        ),
        correctOptionId = "green",
        mixRecipe = ColorMixRecipe("Blue", "🔵", "Yellow", "🟡", "Green", "🟢", 0xFF43A047, "Blue plus Yellow makes Green!")
      ),
      ColorLessonItem(
        id = "col_mix_purple",
        subcategory = ColorSubcategory.COLOR_MIXING,
        colorName = "Making Purple",
        colorHex = 0xFF8E24AA,
        emoji = "🧪",
        hearPrompt = "Color Magic! Mix Red and Blue together to make royal Purple!",
        seeExamples = listOf(
          ColorSeeExample("Red Paint", "🔴", "Red", "Warm Red"),
          ColorSeeExample("Blue Paint", "🔵", "Blue", "Cool Blue"),
          ColorSeeExample("Purple Result", "🟣", "Purple", "You get Royal Purple!")
        ),
        chooseQuestion = "Red + Blue = Which color?",
        chooseOptions = listOf(
          GameOption("purple", "Purple", "🟣", colorHex = 0xFF8E24AA),
          GameOption("orange", "Orange", "🟠", colorHex = 0xFFFF9800),
          GameOption("brown", "Brown", "🟤", colorHex = 0xFF795548)
        ),
        correctOptionId = "purple",
        mixRecipe = ColorMixRecipe("Red", "🔴", "Blue", "🔵", "Purple", "🟣", 0xFF8E24AA, "Red plus Blue makes Purple!")
      ),

      // Level 5: Rainbow Explorer
      ColorLessonItem(
        id = "col_rainbow_all",
        subcategory = ColorSubcategory.RAINBOW_DISCOVERY,
        colorName = "The Rainbow",
        colorHex = 0xFFFF5722,
        emoji = "🌈",
        hearPrompt = "Rainbow Magic! Red, Orange, Yellow, Green, Blue, Indigo, and Violet shine across the sky after the rain!",
        seeExamples = listOf(
          ColorSeeExample("Red & Orange", "🔴🟠", "Warm", "Warm top arches"),
          ColorSeeExample("Yellow & Green", "🟡🟢", "Middle", "Sunny middle arches"),
          ColorSeeExample("Blue & Violet", "🔵🟣", "Cool", "Cool bottom arches")
        ),
        chooseQuestion = "What creates a beautiful rainbow in the sky?",
        chooseOptions = listOf(
          GameOption("rain_sun", "Sun & Rain", "🌈"),
          GameOption("snow", "Snow Only", "❄️"),
          GameOption("night", "Dark Night", "🌙")
        ),
        correctOptionId = "rain_sun",
        speakPrompt = "Can you say RAINBOW!"
      )
    )
  }

  fun getColorMinimalPairs(): List<ColorMinimalPair> {
    return listOf(
      ColorMinimalPair("cp_1", "Red", "🔴", 0xFFEF5350, "Pink", "🌸", 0xFFEC407A, "Red", "Look closely: Red is deep, Pink is soft!"),
      ColorMinimalPair("cp_2", "Blue", "🔵", 0xFF1E88E5, "Purple", "🟣", 0xFF8E24AA, "Blue", "Listen for the color name: Blue!"),
      ColorMinimalPair("cp_3", "Yellow", "🟡", 0xFFFFB300, "Green", "🟢", 0xFF43A047, "Yellow", "Look for the sunny color: Yellow!"),
      ColorMinimalPair("cp_4", "Orange", "🟠", 0xFFFF9800, "Red", "🔴", 0xFFEF5350, "Orange", "Look for the citrus color: Orange!"),
      ColorMinimalPair("cp_5", "Brown", "🟤", 0xFF795548, "Black", "⚫", 0xFF37474F, "Brown", "Cozy teddy bear color: Brown!")
    )
  }

  fun getColorMixRecipes(): List<ColorMixRecipe> {
    return listOf(
      ColorMixRecipe("Red", "🔴", "Yellow", "🟡", "Orange", "🟠", 0xFFFF9800, "Red and Yellow make Orange!"),
      ColorMixRecipe("Blue", "🔵", "Yellow", "🟡", "Green", "🟢", 0xFF43A047, "Blue and Yellow make Green!"),
      ColorMixRecipe("Red", "🔴", "Blue", "🔵", "Purple", "🟣", 0xFF8E24AA, "Red and Blue make Purple!"),
      ColorMixRecipe("Red", "🔴", "White", "⚪", "Pink", "🌸", 0xFFEC407A, "Red and White make Pink!")
    )
  }

  fun getColors(): List<ColorCard> {
    return listOf(
      ColorCard("Red", "🍎", 0xFFEF5350, "Red Apple"),
      ColorCard("Blue", "🌊", 0xFF1E88E5, "Blue Ocean"),
      ColorCard("Yellow", "☀️", 0xFFFFB300, "Yellow Sun"),
      ColorCard("Green", "🌲", 0xFF43A047, "Green Tree"),
      ColorCard("Orange", "🍊", 0xFFFF9800, "Orange Fruit"),
      ColorCard("Purple", "🍇", 0xFF8E24AA, "Purple Grape"),
      ColorCard("Pink", "🌸", 0xFFEC407A, "Pink Flower"),
      ColorCard("Brown", "🧸", 0xFF795548, "Brown Bear"),
      ColorCard("Black", "🐈‍⬛", 0xFF37474F, "Black Cat"),
      ColorCard("White", "🕊️", 0xFFECEFF1, "White Dove")
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
      listOf(GameCategory.ANIMALS, GameCategory.COLORS, GameCategory.SHAPES, GameCategory.ALPHABETS, GameCategory.NUMBERS, GameCategory.FRUITS, GameCategory.VEGETABLES)
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

      GameCategory.CLOTHES -> listOf(
        GameQuestion("clo_1", category, "Where is the T-Shirt? 👕", "Where is the T-Shirt?", listOf(GameOption("sh", "T-Shirt", "👕"), GameOption("pt", "Pants", "👖"), GameOption("ht", "Hat", "🧢")), "sh"),
        GameQuestion("clo_2", category, "Find the Blue Pants! 👖", "Find the Blue Pants!", listOf(GameOption("pt", "Pants", "👖"), GameOption("dr", "Dress", "👗"), GameOption("sc", "Socks", "🧦")), "pt"),
        GameQuestion("clo_3", category, "Where are the Shoes? 👟", "Where are the Shoes?", listOf(GameOption("sh", "Shoes", "👟"), GameOption("jk", "Jacket", "🧥"), GameOption("gl", "Gloves", "🧤")), "sh"),
        GameQuestion("clo_4", category, "Find the Sun Hat! 🧢", "Find the Sun Hat!", listOf(GameOption("ht", "Sun Hat", "🧢"), GameOption("bt", "Boots", "👢"), GameOption("sf", "Scarf", "🧣")), "ht"),
        GameQuestion("clo_5", category, "Where is the Pretty Dress? 👗", "Where is the Pretty Dress?", listOf(GameOption("dr", "Dress", "👗"), GameOption("wt", "Watch", "⌚"), GameOption("gl", "Glasses", "👓")), "dr")
      )

      GameCategory.FAMILY -> listOf(
        GameQuestion("fam_1", category, "Who is Mommy? 👩", "Who is Mommy?", listOf(GameOption("mo", "Mommy", "👩"), GameOption("fa", "Daddy", "👨"), GameOption("bb", "Baby", "👶")), "mo"),
        GameQuestion("fam_2", category, "Who is Daddy? 👨", "Who is Daddy?", listOf(GameOption("fa", "Daddy", "👨"), GameOption("gr", "Grandpa", "👴"), GameOption("br", "Brother", "👦")), "fa"),
        GameQuestion("fam_3", category, "Where is the Baby? 👶", "Where is the Baby?", listOf(GameOption("bb", "Baby", "👶"), GameOption("si", "Sister", "👧"), GameOption("gm", "Grandma", "👵")), "bb"),
        GameQuestion("fam_4", category, "Where is Grandpa? 👴", "Where is Grandpa?", listOf(GameOption("gr", "Grandpa", "👴"), GameOption("mo", "Mommy", "👩"), GameOption("un", "Uncle", "👨‍🦰")), "gr"),
        GameQuestion("fam_5", category, "Where is Grandma? 👵", "Where is Grandma?", listOf(GameOption("gm", "Grandma", "👵"), GameOption("au", "Aunt", "👩‍🦰"), GameOption("fa", "Daddy", "👨")), "gm")
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
      GameCategory.TOYS -> listOf(
        GameQuestion("toy_1", category, "Where is the Teddy Bear? 🧸", "Where is the Teddy Bear?", listOf(GameOption("td", "Teddy Bear", "🧸"), GameOption("dl", "Doll", "🪆"), GameOption("kt", "Kite", "🪁")), "td"),
        GameQuestion("toy_2", category, "Find the Toy Robot! 🤖", "Find the Toy Robot!", listOf(GameOption("rb", "Toy Robot", "🤖"), GameOption("bl", "Ball", "⚽"), GameOption("tr", "Toy Train", "🚂")), "rb"),
        GameQuestion("toy_3", category, "Where is the Toy Car? 🚗", "Where is the Toy Car?", listOf(GameOption("car", "Toy Car", "🚗"), GameOption("bk", "Blocks", "🧊"), GameOption("dr", "Toy Drum", "🥁")), "car"),
        GameQuestion("toy_4", category, "Find the Colorful Yo-Yo! 🪀", "Find the Colorful Yo-Yo!", listOf(GameOption("yo", "Yo-Yo", "🪀"), GameOption("pz", "Puzzle", "🧩"), GameOption("dn", "Toy Dinosaur", "🦖")), "yo"),
        GameQuestion("toy_5", category, "Where is the Toy Plane? ✈️", "Where is the Toy Plane?", listOf(GameOption("pl", "Toy Plane", "✈️"), GameOption("bt", "Toy Boat", "⛵"), GameOption("gt", "Toy Guitar", "🎸")), "pl")
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
