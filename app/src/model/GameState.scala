package model

import model.student.{Character, Panel, Party}

class GameState{
  val p1: Party = new Party(1, "Ally Player")
  val p2: Party = new Party(2, "Enemy Player")

  val parties: List[Party] = List(p1, p2)

  val dummy1: Character = new Character(1, "Ally 1", 100, 10)
  val dummy2: Character = new Character(2, "Ally 2", 50, 5)
  val dummy3: Character = new Character(3, "Ally 3", 51, 6)
  val dummy4: Character = new Character(4, "Ally 4", 52, 7)

  val allies: List[Character] = List(dummy1, dummy2, dummy3, dummy4)

  p1.characters = allies

  val dummy5: Character = new Character(5, "Enemy 1", 53, 8)
  val dummy6: Character = new Character(6, "Enemy 2", 54, 9)
  val dummy7: Character = new Character(7, "Enemy 3", 55, 11)
  val dummy8: Character = new Character(8, "Enemy 4", 56, 4)
  val dummy9: Character = new Character(9, "Enemy 5", 57, 3)

  val enemies: List[Character] = List(dummy5, dummy6, dummy7, dummy8, dummy9)

  p2.characters = enemies

  val characters: List[Character] = List(dummy1, dummy2, dummy3, dummy4, dummy5, dummy6, dummy7,
    dummy8, dummy9)

  def findCharacter(id: Int): Option[Character] = characters.find(_.id == id)

  def findPanel(id: Int): Option[Panel] = panels.find(_.id == id)

  var currentCharacter: Character = characters.head

  val panel1: Panel = new Panel(1, (1, 1))
  val panel2: Panel = new Panel(2, (1, 2))
  val panel3: Panel = new Panel(3, (1, 3))
  val panel4: Panel = new Panel(4, (2, 1))
  val panel5: Panel = new Panel(5, (2, 2))
  val panel6: Panel = new Panel(6, (2, 3))
  val panel7: Panel = new Panel(7, (3, 1))
  val panel8: Panel = new Panel(8, (3, 2))
  val panel9: Panel = new Panel(9, (3, 3))

  val panel10: Panel = new Panel(10, (1, 1))
  val panel11: Panel = new Panel(11, (1, 2))
  val panel12: Panel = new Panel(12, (1, 3))
  val panel13: Panel = new Panel(13, (2, 1))
  val panel14: Panel = new Panel(14, (2, 2))
  val panel15: Panel = new Panel(15, (2, 3))
  val panel16: Panel = new Panel(16, (3, 1))
  val panel17: Panel = new Panel(17, (3, 2))
  val panel18: Panel = new Panel(18, (3, 3))

  val panels: List[Panel] = List(panel1, panel2, panel3, panel4, panel5, panel6, panel7,
    panel8, panel9, panel10, panel11, panel12, panel13, panel14, panel15, panel16, panel17, panel18)

  val alliesSide: List[Panel] = List(panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9)

  val enemiesSide: List[Panel] = List(panel10, panel11, panel12, panel13, panel14, panel15, panel16, panel17, panel18)

  p1.panels = alliesSide

  p2.panels = enemiesSide

  def findParty(id: Int): Option[Party] = parties.find(_.id == id)

  dummy1.moveTo(panel1)
  dummy2.moveTo(panel8)
  dummy3.moveTo(panel2)
  dummy4.moveTo(panel4)

  dummy5.moveTo(panel10)
  dummy6.moveTo(panel17)
  dummy7.moveTo(panel11)
  dummy8.moveTo(panel13)
  dummy9.moveTo(panel15)

  /**
  * Returns the current game status.
  * -1 if player 1 is defeated
  * 1 if player 2 is defeated
  * 0 if the game is still ongoing
   */
  def getGameStatus: Int = {
    if (p1.isDefeated) {
      -1
    } else if (p2.isDefeated) {
      1
    } else {
      0
    }
  }


}

object GameState {
  private var game = new GameState()
  def apply(): GameState = game
  def reset(): Unit = game = new GameState()
}
