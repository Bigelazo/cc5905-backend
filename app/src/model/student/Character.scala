package model.student

import model.{Section, Movable}

class Character(override val id: Int,
                val name: String,
                var hp: Int,
                val attack: Int) extends Movable {

  var currentSection: Option[Section] = None

  override def moveTo(section: Section): Unit = {
    this.currentSection = Some(section) // Implementación cuma
  }

  def toJson: ujson.Obj = {
    ujson.Obj(
      "id" -> id,
      "name" -> name,
      "hp" -> hp,
      "attack" -> attack,
      "mappableId" -> currentSection.map(_.id)
    )
  }

  def attack(c: Character): Unit = {
    c.receiveDamage(this)
  }

  private def receiveDamage(c: Character): Unit = {
    hp -= c.attack
  }


}
