package model.action
import ujson.Obj

class Punch extends Action {

  val id: Int = 1

  def toJson: Obj = Obj(
    "id" -> id,
    "action" -> "Punch"
  )

}
