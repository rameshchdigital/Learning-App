package com.example.data.repository

import com.example.data.models.CategoryGroup
import com.example.data.models.CategoryItem
import com.example.data.models.GameCategory
import com.example.data.models.StructuredCategoryContent

object CategoryRepository {

  val structuredCategories: List<StructuredCategoryContent> by lazy {
    listOf(
      // 👨‍👩‍👧 Everyday Life
      StructuredCategoryContent(
        category = GameCategory.COMMUNITY_HELPERS,
        group = CategoryGroup.EVERYDAY_LIFE,
        items = listOf(
          CategoryItem("ch_1", "Police Officer", "👮‍♂️", "Keeps our community safe"),
          CategoryItem("ch_2", "Firefighter", "👨‍🚒", "Puts out fires and saves lives"),
          CategoryItem("ch_3", "Doctor", "👨‍⚕️", "Helps us stay healthy"),
          CategoryItem("ch_4", "Nurse", "👩‍⚕️", "Cares for sick people"),
          CategoryItem("ch_5", "Teacher", "👩‍🏫", "Teaches us new things at school"),
          CategoryItem("ch_6", "Farmer", "👨‍🌾", "Grows fresh food and crops"),
          CategoryItem("ch_7", "Chef", "👨‍🍳", "Cooks delicious meals"),
          CategoryItem("ch_8", "Baker", "🧑‍🍳", "Bakes fresh bread and treats"),
          CategoryItem("ch_9", "Postman", "📬", "Delivers letters and packages"),
          CategoryItem("ch_10", "Pilot", "👨‍✈️", "Flies airplanes in the sky"),
          CategoryItem("ch_11", "Bus Driver", "🚌", "Drives children safely"),
          CategoryItem("ch_12", "Plumber", "🔧", "Fixes water pipes and sinks"),
          CategoryItem("ch_13", "Electrician", "⚡", "Fixes lights and electricity"),
          CategoryItem("ch_14", "Carpenter", "🔨", "Builds wooden furniture"),
          CategoryItem("ch_15", "Dentist", "🦷", "Takes care of our teeth"),
          CategoryItem("ch_16", "Vet", "🐶", "Takes care of sick animals"),
          CategoryItem("ch_17", "Construction Worker", "👷‍♂️", "Builds houses and roads"),
          CategoryItem("ch_18", "Gardener", "🧑‍🌾", "Plants flowers and trees"),
          CategoryItem("ch_19", "Mechanic", "🚘", "Fixes cars and engines"),
          CategoryItem("ch_20", "Soldier", "🎖️", "Protects our country")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.TOYS,
        group = CategoryGroup.EVERYDAY_LIFE,
        items = listOf(
          CategoryItem("toy_1", "Teddy Bear", "🧸", "Soft and cuddly bear"),
          CategoryItem("toy_2", "Doll", "🪆", "Cute toy doll"),
          CategoryItem("toy_3", "Toy Car", "🚗", "Zooms fast on wheels"),
          CategoryItem("toy_4", "Toy Train", "🚂", "Chug-chug along the tracks"),
          CategoryItem("toy_5", "Ball", "⚽", "Bounces high and rolls"),
          CategoryItem("toy_6", "Blocks", "🧊", "Stack and build towers"),
          CategoryItem("toy_7", "Puzzle", "🧩", "Fit matching pieces together"),
          CategoryItem("toy_8", "Yo-Yo", "🪀", "Spins up and down on a string"),
          CategoryItem("toy_9", "Kite", "🪁", "Flies high in the windy sky"),
          CategoryItem("toy_10", "Toy Robot", "🤖", "Beep boop walking robot"),
          CategoryItem("toy_11", "Toy Plane", "✈️", "Flies through the room"),
          CategoryItem("toy_12", "Toy Boat", "⛵", "Floats on water"),
          CategoryItem("toy_13", "Jump Rope", "🪢", "Skip and jump over"),
          CategoryItem("toy_14", "Spinning Top", "🪀", "Spins round and round"),
          CategoryItem("toy_15", "Building Bricks", "🧱", "Build colorful houses"),
          CategoryItem("toy_16", "Stuffed Bunny", "🐰", "Soft fluffy bunny"),
          CategoryItem("toy_17", "Toy Drum", "🥁", "Tap tap rhythm drum"),
          CategoryItem("toy_18", "Toy Guitar", "🎸", "Pluck strings to play music"),
          CategoryItem("toy_19", "Toy Kitchen Set", "🍳", "Pretend cooking playset"),
          CategoryItem("toy_20", "Toy Dinosaur", "🦖", "Roaring fun dinosaur")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.PLACES,
        group = CategoryGroup.EVERYDAY_LIFE,
        items = listOf(
          CategoryItem("plc_1", "Home", "🏠", "Where family lives together"),
          CategoryItem("plc_2", "School", "🏫", "Where we learn and play"),
          CategoryItem("plc_3", "Hospital", "🏥", "Where doctors help sick people"),
          CategoryItem("plc_4", "Police Station", "🏢", "Where police officers work"),
          CategoryItem("plc_5", "Fire Station", "🚒", "Where fire engines stay"),
          CategoryItem("plc_6", "Park", "🏞️", "Green park with trees and grass"),
          CategoryItem("plc_7", "Zoo", "🦁", "Where we see animals"),
          CategoryItem("plc_8", "Farm", "🚜", "Where cows and crops live"),
          CategoryItem("plc_9", "Library", "📚", "Where we read quiet books"),
          CategoryItem("plc_10", "Supermarket", "🛒", "Where we buy groceries"),
          CategoryItem("plc_11", "Bakery", "🥖", "Where fresh bread is baked"),
          CategoryItem("plc_12", "Restaurant", "🍽️", "Where we eat yummy food"),
          CategoryItem("plc_13", "Airport", "✈️", "Where airplanes land"),
          CategoryItem("plc_14", "Railway Station", "🚉", "Where trains stop for passengers"),
          CategoryItem("plc_15", "Beach", "🏖️", "Sandy shore by the ocean"),
          CategoryItem("plc_16", "Playground", "🛝", "Swings, slides and fun"),
          CategoryItem("plc_17", "Post Office", "📮", "Where mail and letters go"),
          CategoryItem("plc_18", "Bank", "🏦", "Where money is kept safe"),
          CategoryItem("plc_19", "Museum", "🏛️", "Where historical things stay"),
          CategoryItem("plc_20", "Bus Stop", "🏣", "Where we wait for the bus"),
          CategoryItem("plc_21", "Garden", "🪴", "Pretty garden with flowers"),
          CategoryItem("plc_22", "Temple", "🛕", "Peaceful place of worship")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.HOUSEHOLD_OBJECTS,
        group = CategoryGroup.EVERYDAY_LIFE,
        items = listOf(
          CategoryItem("ho_1", "Chair", "🪑", "Furniture to sit on"),
          CategoryItem("ho_2", "Table", "🪵", "Table to place things on"),
          CategoryItem("ho_3", "Bed", "🛏️", "Soft bed for sleeping"),
          CategoryItem("ho_4", "Sofa", "🛋️", "Comfortable couch for resting"),
          CategoryItem("ho_5", "Door", "🚪", "Opens and closes rooms"),
          CategoryItem("ho_6", "Window", "🪟", "Lets in fresh air and light"),
          CategoryItem("ho_7", "Lamp", "💡", "Shines warm light"),
          CategoryItem("ho_8", "Fan", "🪭", "Brings cool breeze"),
          CategoryItem("ho_9", "Clock", "⏰", "Tells us the time"),
          CategoryItem("ho_10", "Television", "📺", "Watch favorite cartoons"),
          CategoryItem("ho_11", "Refrigerator", "🧊", "Keeps food fresh and cold"),
          CategoryItem("ho_12", "Washing Machine", "🧺", "Cleans our clothes"),
          CategoryItem("ho_13", "Spoon", "🥄", "Used for eating soup"),
          CategoryItem("ho_14", "Plate", "🍽️", "Dish to serve food"),
          CategoryItem("ho_15", "Cup", "☕", "Used to drink warm milk"),
          CategoryItem("ho_16", "Bowl", "🥣", "Used for cereal and soup"),
          CategoryItem("ho_17", "Glass", "🥛", "Used to drink water"),
          CategoryItem("ho_18", "Fork", "🍴", "Used to pick up food"),
          CategoryItem("ho_19", "Knife", "🔪", "Butter knife for spreading"),
          CategoryItem("ho_20", "Bucket", "🪣", "Holds water for cleaning"),
          CategoryItem("ho_21", "Broom", "🧹", "Sweeps floors clean"),
          CategoryItem("ho_22", "Pillow", "🛌", "Soft pillow for head"),
          CategoryItem("ho_23", "Blanket", "🛋️", "Warm blanket for night"),
          CategoryItem("ho_24", "Towel", "🧴", "Dries us after bath"),
          CategoryItem("ho_25", "Mirror", "🪞", "Reflects our face"),
          CategoryItem("ho_26", "Brush", "🪥", "Combs hair and brushes"),
          CategoryItem("ho_27", "Comb", "🪮", "Keeps hair neat")
        )
      ),

      // 🧠 Learning & Development
      StructuredCategoryContent(
        category = GameCategory.DAYS,
        group = CategoryGroup.LEARNING_DEV,
        items = listOf(
          CategoryItem("dy_1", "Monday", "📅", "First day of school week"),
          CategoryItem("dy_2", "Tuesday", "📅", "Second day of the week"),
          CategoryItem("dy_3", "Wednesday", "🗓️", "Middle day of the week"),
          CategoryItem("dy_4", "Thursday", "📅", "Fourth day of the week"),
          CategoryItem("dy_5", "Friday", "🎈", "Fifth day before weekend"),
          CategoryItem("dy_6", "Saturday", "🎉", "Weekend fun day"),
          CategoryItem("dy_7", "Sunday", "☀️", "Family weekend day")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.MONTHS,
        group = CategoryGroup.LEARNING_DEV,
        items = listOf(
          CategoryItem("mn_1", "January", "🎆", "Month 1 - New Year"),
          CategoryItem("mn_2", "February", "❄️", "Month 2"),
          CategoryItem("mn_3", "March", "🌱", "Month 3 - Spring"),
          CategoryItem("mn_4", "April", "🌸", "Month 4"),
          CategoryItem("mn_5", "May", "🌺", "Month 5"),
          CategoryItem("mn_6", "June", "☀️", "Month 6 - Summer"),
          CategoryItem("mn_7", "July", "🏖️", "Month 7"),
          CategoryItem("mn_8", "August", "🌻", "Month 8"),
          CategoryItem("mn_9", "September", "🍂", "Month 9 - Autumn"),
          CategoryItem("mn_10", "October", "🎃", "Month 10"),
          CategoryItem("mn_11", "November", "🍁", "Month 11"),
          CategoryItem("mn_12", "December", "🎄", "Month 12 - Holidays")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.ACTION_WORDS,
        group = CategoryGroup.LEARNING_DEV,
        items = listOf(
          CategoryItem("act_1", "Run", "🏃", "Move fast on feet"),
          CategoryItem("act_2", "Walk", "🚶", "Step along gently"),
          CategoryItem("act_3", "Jump", "🦘", "Hop up off the ground"),
          CategoryItem("act_4", "Sit", "🪑", "Rest on a chair"),
          CategoryItem("act_5", "Stand", "🧍", "Upright on two feet"),
          CategoryItem("act_6", "Eat", "🍽️", "Chew delicious food"),
          CategoryItem("act_7", "Drink", "🥤", "Sip fresh water"),
          CategoryItem("act_8", "Sleep", "😴", "Rest eyes in bed"),
          CategoryItem("act_9", "Wake Up", "⏰", "Morning time to wake"),
          CategoryItem("act_10", "Read", "📖", "Look at book words"),
          CategoryItem("act_11", "Write", "✏️", "Pencil on paper"),
          CategoryItem("act_12", "Draw", "🎨", "Create pictures with colors"),
          CategoryItem("act_13", "Sing", "🎤", "Make musical tunes"),
          CategoryItem("act_14", "Dance", "💃", "Move to music rhythm"),
          CategoryItem("act_15", "Clap", "👏", "Pat hands together"),
          CategoryItem("act_16", "Laugh", "😄", "Happy cheerful sound"),
          CategoryItem("act_17", "Cry", "😢", "Tears when feeling sad"),
          CategoryItem("act_18", "Swim", "🏊", "Paddle in the pool"),
          CategoryItem("act_19", "Climb", "🧗", "Climb up safely"),
          CategoryItem("act_20", "Throw", "🏐", "Toss a ball forward"),
          CategoryItem("act_21", "Catch", "🤲", "Hold a flying ball"),
          CategoryItem("act_22", "Kick", "⚽", "Foot kick the ball"),
          CategoryItem("act_23", "Push", "🫸", "Push forward gently"),
          CategoryItem("act_24", "Pull", "🫷", "Pull towards yourself"),
          CategoryItem("act_25", "Open", "👐", "Open door or box"),
          CategoryItem("act_26", "Close", "✊", "Shut door safely"),
          CategoryItem("act_27", "Wash", "🧼", "Clean with soap"),
          CategoryItem("act_28", "Brush", "🪥", "Brush teeth neat"),
          CategoryItem("act_29", "Cook", "🍳", "Prepare warm food")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.HEALTH,
        group = CategoryGroup.LEARNING_DEV,
        items = listOf(
          CategoryItem("hl_1", "Doctor", "👨‍⚕️", "Helps when feeling sick"),
          CategoryItem("hl_2", "Hospital", "🏥", "Healthcare place"),
          CategoryItem("hl_3", "Medicine", "💊", "Helps body recover"),
          CategoryItem("hl_4", "Bandage", "🩹", "Covers small scratches"),
          CategoryItem("hl_5", "Thermometer", "🌡️", "Checks body temperature"),
          CategoryItem("hl_6", "Stethoscope", "🩺", "Listens to heartbeat"),
          CategoryItem("hl_7", "Toothbrush", "🪥", "Keeps teeth sparkling"),
          CategoryItem("hl_8", "Toothpaste", "🧴", "Minty paste for teeth"),
          CategoryItem("hl_9", "Soap", "🧼", "Washes away germs"),
          CategoryItem("hl_10", "Hand Sanitizer", "🧴", "Cleans hands on the go"),
          CategoryItem("hl_11", "First Aid Kit", "🧰", "Emergency medical box"),
          CategoryItem("hl_12", "Syringe", "💉", "Protective vaccine shot"),
          CategoryItem("hl_13", "Healthy Food", "🥗", "Fruits and veggies"),
          CategoryItem("hl_14", "Water", "💧", "Fresh clean drink"),
          CategoryItem("hl_15", "Exercise", "🤸", "Play and move body"),
          CategoryItem("hl_16", "Sleep", "🛌", "Rest for strong energy"),
          CategoryItem("hl_17", "Washing Hands", "🧼", "Wash before eating"),
          CategoryItem("hl_18", "Brushing Teeth", "🪥", "Brush morning and night"),
          CategoryItem("hl_19", "Healthy Body", "💪", "Strong and happy body"),
          CategoryItem("hl_20", "Ambulance", "🚑", "Emergency rescue car")
        )
      ),

      // 🌎 World Around Us
      StructuredCategoryContent(
        category = GameCategory.NATURE,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("nat_1", "Sun", "☀️", "Bright warm star in sky"),
          CategoryItem("nat_2", "Moon", "🌙", "Glowing moon at night"),
          CategoryItem("nat_3", "Stars", "⭐", "Twinkling night stars"),
          CategoryItem("nat_4", "Cloud", "☁️", "Fluffy cloud in sky"),
          CategoryItem("nat_5", "Rain", "🌧️", "Water drops from sky"),
          CategoryItem("nat_6", "Rainbow", "🌈", "Colorful arch after rain"),
          CategoryItem("nat_7", "Tree", "🌳", "Tall green tree with leaves"),
          CategoryItem("nat_8", "Flower", "🌸", "Pretty blooming petal"),
          CategoryItem("nat_9", "Grass", "🌱", "Soft green lawn grass"),
          CategoryItem("nat_10", "Leaf", "🍃", "Green leaf on branch"),
          CategoryItem("nat_11", "Mountain", "🏔️", "Tall rocky peak"),
          CategoryItem("nat_12", "River", "🏞️", "Flowing water stream"),
          CategoryItem("nat_13", "Lake", "🌊", "Calm water lake"),
          CategoryItem("nat_14", "Waterfall", "🌊", "Water cascading down"),
          CategoryItem("nat_15", "Rock", "🪨", "Hard stone rock"),
          CategoryItem("nat_16", "Soil", "🪴", "Earth soil for plants"),
          CategoryItem("nat_17", "Sand", "🏖️", "Soft beach sand"),
          CategoryItem("nat_18", "Forest", "🌲", "Many trees together"),
          CategoryItem("nat_19", "Bird", "🐦", "Feathered flying friend"),
          CategoryItem("nat_20", "Butterfly", "🦋", "Colorful fluttering wing"),
          CategoryItem("nat_21", "Bee", "🐝", "Buzzy honey bee"),
          CategoryItem("nat_22", "Fish", "🐟", "Swims in fresh water"),
          CategoryItem("nat_23", "Ant", "🐜", "Tiny hardworking ant"),
          CategoryItem("nat_24", "Ladybug", "🐞", "Red spotted ladybug"),
          CategoryItem("nat_25", "Earth", "🌍", "Our beautiful planet"),
          CategoryItem("nat_26", "Wind", "💨", "Blowing gentle breeze")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.ANIMALS,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("anim_1", "Dog", "🐶", "Loyal barking pet"),
          CategoryItem("anim_2", "Cat", "🐱", "Cute meowing cat"),
          CategoryItem("anim_3", "Lion", "🦁", "King of the jungle"),
          CategoryItem("anim_4", "Elephant", "🐘", "Big elephant with trunk"),
          CategoryItem("anim_5", "Monkey", "🐒", "Playful swinging monkey"),
          CategoryItem("anim_6", "Tiger", "🐯", "Striped wild cat"),
          CategoryItem("anim_7", "Bear", "🐻", "Furry forest bear"),
          CategoryItem("anim_8", "Giraffe", "🦒", "Tall neck giraffe"),
          CategoryItem("anim_9", "Zebra", "🦓", "Black and white stripes"),
          CategoryItem("anim_10", "Horse", "🐴", "Galloping strong horse"),
          CategoryItem("anim_11", "Cow", "🐮", "Gives fresh milk"),
          CategoryItem("anim_12", "Sheep", "🐑", "Soft woolly sheep"),
          CategoryItem("anim_13", "Pig", "🐷", "Pink oinking pig"),
          CategoryItem("anim_14", "Rabbit", "🐰", "Hopping fluffy bunny"),
          CategoryItem("anim_15", "Duck", "🦆", "Quacking swimming duck"),
          CategoryItem("anim_16", "Frog", "🐸", "Green hopping frog"),
          CategoryItem("anim_17", "Turtle", "🐢", "Slow shell turtle"),
          CategoryItem("anim_18", "Dolphin", "🐬", "Friendly ocean swimmer"),
          CategoryItem("anim_19", "Penguin", "🐧", "Waddling ice bird"),
          CategoryItem("anim_20", "Kangaroo", "🦘", "Pouch hopping animal")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.FRUITS,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("fr_1", "Apple", "🍎", "Sweet red apple"),
          CategoryItem("fr_2", "Banana", "🍌", "Yellow sweet banana"),
          CategoryItem("fr_3", "Orange", "🍊", "Juicy citrus orange"),
          CategoryItem("fr_4", "Strawberry", "🍓", "Red berry with seeds"),
          CategoryItem("fr_5", "Grape", "🍇", "Sweet purple grapes"),
          CategoryItem("fr_6", "Watermelon", "🍉", "Big refreshing slice"),
          CategoryItem("fr_7", "Mango", "🥭", "King of fruits"),
          CategoryItem("fr_8", "Pineapple", "🍍", "Sweet tropical fruit"),
          CategoryItem("fr_9", "Cherry", "🍒", "Pair of red cherries"),
          CategoryItem("fr_10", "Pear", "🍐", "Juicy sweet pear"),
          CategoryItem("fr_11", "Lemon", "🍋", "Sour yellow lemon"),
          CategoryItem("fr_12", "Peach", "🍑", "Soft fuzzy peach"),
          CategoryItem("fr_13", "Kiwi", "🥝", "Green fuzzy fruit"),
          CategoryItem("fr_14", "Plum", "🫐", "Sweet purple plum"),
          CategoryItem("fr_15", "Papaya", "🥭", "Orange tropical fruit"),
          CategoryItem("fr_16", "Guava", "🍏", "Fresh green guava"),
          CategoryItem("fr_17", "Coconut", "🥥", "Hard shell coconut"),
          CategoryItem("fr_18", "Pomegranate", "🍎", "Red crunchy seeds")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.VEGETABLES,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("vg_1", "Carrot", "🥕", "Crunchy orange root"),
          CategoryItem("vg_2", "Potato", "🥔", "Yummy potato"),
          CategoryItem("vg_3", "Tomato", "🍅", "Juicy red tomato"),
          CategoryItem("vg_4", "Broccoli", "🥦", "Little green tree veggie"),
          CategoryItem("vg_5", "Corn", "🌽", "Yellow sweet cob"),
          CategoryItem("vg_6", "Cucumber", "🥒", "Cool green slice"),
          CategoryItem("vg_7", "Pumpkin", "🎃", "Big orange pumpkin"),
          CategoryItem("vg_8", "Eggplant", "🍆", "Purple vegetable"),
          CategoryItem("vg_9", "Onion", "🧅", "Flavorful cooking onion"),
          CategoryItem("vg_10", "Garlic", "🧄", "Aromatic garlic clove"),
          CategoryItem("vg_11", "Bell Pepper", "🫑", "Crunchy sweet pepper"),
          CategoryItem("vg_12", "Peas", "🫛", "Sweet green peas in pod"),
          CategoryItem("vg_13", "Spinach", "🥬", "Leafy green spinach"),
          CategoryItem("vg_14", "Cauliflower", "🥦", "White florets veggie"),
          CategoryItem("vg_15", "Mushroom", "🍄", "Earthy mushroom"),
          CategoryItem("vg_16", "Radish", "🧅", "Pink crunchy radish")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.VEHICLES,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("vh_1", "Car", "🚗", "Four-wheel family car"),
          CategoryItem("vh_2", "Bus", "🚌", "School bus for kids"),
          CategoryItem("vh_3", "Train", "🚂", "Chug-chug railway train"),
          CategoryItem("vh_4", "Airplane", "✈️", "Flies high in clouds"),
          CategoryItem("vh_5", "Helicopter", "🚁", "Spinning rotor aircraft"),
          CategoryItem("vh_6", "Boat", "⛵", "Sailing on water"),
          CategoryItem("vh_7", "Ship", "🚢", "Big ocean cruise ship"),
          CategoryItem("vh_8", "Bicycle", "🚲", "Pedal on two wheels"),
          CategoryItem("vh_9", "Motorcycle", "🏍️", "Motorized two-wheeler"),
          CategoryItem("vh_10", "Truck", "🚚", "Big cargo delivery truck"),
          CategoryItem("vh_11", "Ambulance", "🚑", "Emergency health van"),
          CategoryItem("vh_12", "Fire Engine", "🚒", "Red firefighter engine"),
          CategoryItem("vh_13", "Police Car", "🚔", "Patrol police vehicle"),
          CategoryItem("vh_14", "Tractor", "🚜", "Farm tractor for fields"),
          CategoryItem("vh_15", "Rocket", "🚀", "Zooms into outer space")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.COLORS,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("col_1", "Red", "🔴", "Bright red color"),
          CategoryItem("col_2", "Blue", "🔵", "Sky blue color"),
          CategoryItem("col_3", "Yellow", "🟡", "Sunshine yellow color"),
          CategoryItem("col_4", "Green", "🟢", "Grass green color"),
          CategoryItem("col_5", "Orange", "🟠", "Citrus orange color"),
          CategoryItem("col_6", "Purple", "🟣", "Grape purple color"),
          CategoryItem("col_7", "Pink", "🩷", "Pretty pink color"),
          CategoryItem("col_8", "Brown", "🟤", "Wood brown color"),
          CategoryItem("col_9", "Black", "⬛", "Night black color"),
          CategoryItem("col_10", "White", "⬜", "Snow white color")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.SHAPES,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("shp_1", "Circle", "⭕", "Round like a wheel"),
          CategoryItem("shp_2", "Square", "🟧", "Four equal sides"),
          CategoryItem("shp_3", "Triangle", "🔺", "Three pointy corners"),
          CategoryItem("shp_4", "Rectangle", "▭", "Long sides shape"),
          CategoryItem("shp_5", "Star", "⭐", "Five-point star"),
          CategoryItem("shp_6", "Heart", "💖", "Lovely heart shape"),
          CategoryItem("shp_7", "Oval", "🥚", "Egg-shaped oval"),
          CategoryItem("shp_8", "Diamond", "🔷", "Kite diamond shape")
        )
      ),
      StructuredCategoryContent(
        category = GameCategory.BODY_PARTS,
        group = CategoryGroup.WORLD_AROUND,
        items = listOf(
          CategoryItem("bp_1", "Eyes", "👀", "Used to see the world"),
          CategoryItem("bp_2", "Ears", "👂", "Used to hear sound"),
          CategoryItem("bp_3", "Nose", "👃", "Used to smell flowers"),
          CategoryItem("bp_4", "Mouth", "👄", "Used to smile and talk"),
          CategoryItem("bp_5", "Hands", "🖐️", "Used to hold and clap"),
          CategoryItem("bp_6", "Feet", "🦶", "Used to walk and run"),
          CategoryItem("bp_7", "Head", "🗣️", "Holds our brain"),
          CategoryItem("bp_8", "Legs", "🦵", "Helps us jump high"),
          CategoryItem("bp_9", "Arms", "💪", "Used for big hugs"),
          CategoryItem("bp_10", "Fingers", "🤌", "Ten fingers on hands"),
          CategoryItem("bp_11", "Teeth", "🦷", "Clean white teeth"),
          CategoryItem("bp_12", "Tongue", "👅", "Tastes sweet food")
        )
      )
    )
  }

  fun getCategoryContent(category: GameCategory): StructuredCategoryContent? {
    return structuredCategories.find { it.category == category }
  }

  fun getCategoriesForGroup(group: CategoryGroup): List<StructuredCategoryContent> {
    return structuredCategories.filter { it.group == group }
  }
}
