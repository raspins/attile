package characters

import characteristics.{Human, Warrior}

object Frank extends Human with Warrior {
  override val backStory: String = "Who hit him? He doesn't always know, but it didn't hurt - much."

  override val attackMultiplier: Int = 5
}
