package model.student

import model.Visualizable
import ujson.Obj

class Party(val id: Int, val name: String) extends Visualizable {

  var characters: List[Character] = List[Character]()

  def isDefeated: Boolean =
    characters.foldLeft(0)((lifeTotal, character) => lifeTotal + Math.max(character.hp,0)) <= 0

  override def toJson: Obj = Obj(
    "id" -> id,
    "name" -> name,
    "isDefeated" -> isDefeated,
    "characters" -> (for (character <- characters) yield character.toJson),
  )
}
