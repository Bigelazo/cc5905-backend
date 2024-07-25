package model.action.spell

import model.action.{Action, ActionOnCharacter}
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
