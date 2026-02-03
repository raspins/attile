package characteristics

abstract class Role {
  val attackMultiplier: Int
}

trait Warrior extends Role {

}

trait Mage extends Role {

}

trait Bard extends Role {

}

trait Archer extends Role {

}
