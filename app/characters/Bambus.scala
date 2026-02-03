package characters

import characteristics.{Bard, Dwarf}

object Bambus extends Dwarf with Bard {
  override val backStory: String = "Drinking, music and acting like your 10 feet tall."

  override val attackMultiplier: Int = 5
}
