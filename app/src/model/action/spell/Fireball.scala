package model.action.spell

import model.action.Action
import ujson.Obj

class Fireball extends Action {

  val id: Int = 3

  def toJson: Obj = Obj(
    "id" -> id,
    "action" -> "Spell→Fireball"
  )
}
