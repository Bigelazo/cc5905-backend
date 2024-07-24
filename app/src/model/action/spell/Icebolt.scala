package model.action.spell

import model.action.Action
import ujson.Obj

class Icebolt extends Action {

  val id: Int = 4

  def toJson: Obj = Obj(
    "id" -> id,
    "action" -> "Spell→Icebolt"
  )
}
