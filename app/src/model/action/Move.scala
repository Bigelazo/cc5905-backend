package model.action
import ujson.Obj

class Move extends Action {

  val id: Int = 2

  def toJson: Obj = Obj(
    "id" -> id,
    "action" -> "Move"
  )

}
