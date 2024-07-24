package model.T2.action

import ujson.Obj

trait Spell extends Action with ActionOnCharacter {
  val name: String
  val element: String
  val cost: Int

  def toJson: Obj = Obj(
    "id" -> id,
    "action" -> s"Spell→${name} (${cost} mana)",
    "cost" -> cost
  )
}
