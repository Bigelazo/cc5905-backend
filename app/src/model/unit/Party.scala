package model.unit

import model.Visualizable
import model.section.Panel
import ujson.Obj

import java.lang

class Party(val id: Int, val name: String) extends Visualizable {

  var characters: List[Character] = List[Character]()

  var panels: List[Panel] = List[Panel]()

  def isDefeated: Boolean =
    characters.foldLeft(0)((lifeTotal, character) => lifeTotal + Math.max(character.hp,0)) <= 0

  override def toJson: Obj = Obj(
    "id" -> id,
    "name" -> name,
    "isDefeated" -> isDefeated,
    "characters" -> (for (character <- characters) yield character.toJson),
    "panels" -> (for (panel <- panels) yield panel.toJson)
  )
}
