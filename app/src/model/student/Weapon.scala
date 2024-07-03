package model.student

class Weapon(val id: Int, val name: String, val damage: Int) {
  def toJson: ujson.Obj = {
    ujson.Obj(
      "id" -> id,
      "name" -> name,
      "damage" -> damage
    )
  }
}
