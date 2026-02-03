package characters

import characteristics.{Druid, Mage}

object Dahrion extends Druid with Mage {
  override val backStory: String = "A true friend to the animals and a devastating adversary... " +
    "if he's in the mood."

  override val attackMultiplier: Int = 5
}
