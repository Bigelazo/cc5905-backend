package model.T2.action

import ujson.Obj

class Punch extends Action with ActionOnCharacter {

  val id: Int = 1

  def toJson: Obj = Obj(
    "id" -> id,
    "action" -> "Punch"
  )

}
