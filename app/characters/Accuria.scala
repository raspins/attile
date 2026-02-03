package characters

import characteristics.{Elf, Archer}

object Accuria extends Elf with Archer {
  override val backStory: String = "A life of hunting in the dark forest have created " +
    "a marksman with a fierce eye for accuracy and efficiency."

  override val attackMultiplier: Int = 5
}
