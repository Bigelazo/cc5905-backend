package model.student

import model.{Mappable, Movable}

class Character(override val id: Int,
                val name: String,
                var hp: Int,
                val attack: Int) extends Movable {

  var mappable: Option[Mappable] = None

  override def moveTo(mappable: Mappable): Unit = {
    this.mappable = Some(mappable) // Implementación cuma
  }

  def toJson: ujson.Obj = {
    ujson.Obj(
      "id" -> id,
      "name" -> name,
      "hp" -> hp,
      "attack" -> attack,
      "mappableId" -> mappable.map(_.id)
    )
  }

  def attack(c: Character): Unit = {
    c.receiveDamage(this)
  }

  private def receiveDamage(c: Character): Unit = {
    hp -= c.attack
  }


}
