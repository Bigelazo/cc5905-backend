package model.action.move

import model.action.{Action, ActionOnPanel}
import ujson.Obj

class Move extends Action with ActionOnPanel {

  val id: Int = 2

  def toJson: Obj = Obj(
    "id" -> id,
    "action" -> "Move"
  )

}
