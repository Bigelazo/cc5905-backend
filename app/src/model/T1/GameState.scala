package model.T1

import ujson.Obj

class Party(val id: Int, val name: String, val characters: List[Character], val panels: List[Panel]) {

  def toJson: Obj = Obj(
      "id" -> id,
      "name" -> name,
      "isDefeated" -> false,
      "characters" -> (for (character <- characters) yield character.toJson),
      "panels" -> (for (panel <- panels) yield panel.toJson)
    )
}
trait Target
class Character(val id: Int, val name: String, var panel: Panel, img: String) extends Target{
  def toJson() = Obj(
    "id" -> id,
    "name" -> name,
    "img" -> img,
    "mappableId" -> panel.id
  )
  val actions: List[Action] = List()
  def findActionById(id: Int): Option[Character] = None
  def doAction(action: Action, target: Target): Unit = {}
}
trait Action{
  def toJson: Obj
}
trait ActionOnCharacter extends Action
trait ActionOnWeapon extends Action
trait ActionOnPanel extends Action

class Panel(val id: Int, val coordinates: (Int, Int)) extends Target{
  def toJson() = Obj(
    "id" -> id,
    "x" -> coordinates._1,
    "y" -> coordinates._2
  )
}


class GameState {

  val panels1 = List(new Panel(1,(0,0)), new Panel(2,(0,1)), new Panel(3,(1,0)), new Panel(4,(1,1)))
  val panels2 = List(new Panel(5,(0,0)), new Panel(6,(0,1)), new Panel(7,(1,0)), new Panel(8,(1,1)))
  val characters = List(new Character(1, "Character1", panels1(0), "Relm.gif"), new Character(2, "Character2", panels1(1), "Terra.gif"))
  val enemies = List(new Character(3, "Character1", panels2(0), "Wizard.gif"), new Character(4, "Character2", panels2(2), "Crawly.gif"))

  val p1 = new Party(1,"Player", characters, panels1)
  val p2 = new Party(2,"Enemy", enemies, panels2)
  val parties: List[Party] = List(p1,p2)


  var currentCharacter = characters(0)
  def getGameStatus = 1
  def findParty(id: Int): Option[Party] = parties.find(_.id == id)
  def findCharacter(id: Int): Option[Character] = characters.find(_.id == id)
  def findPanel(id: Int): Option[Panel] = (panels1++panels2).find(_.id == id)

}
object GameState {
  private var game = new GameState()
  def apply(): GameState = game
  def reset(): Unit = game = new GameState()
}