package controller

import model.section.Panel
import model.unit.{Fighter, Party, Weapon, Character}

class GameState {

  private val parties: List[Party] = List(
    new Party(2, "Ally Player"),
    new Party(1, "Enemy Player")
  )

  private val allies: List[Character] = List(
    new Character(1, "Relm", 100, 10, "Relm.gif"),
    new Fighter(2, "Setzer", 50, 5, "Setzer.gif"),
    new Character(3, "Terra", 51, 6, "Terra.gif"),
    new Character(4, "Gogo", 52, 7, "Gogo.gif")
  )

  private val enemies: List[Character] = List(
    new Character(5, "Mag Roader", 53, 8, "Mag Roader.gif"),
    new Character(6, "Nohrabbit", 54, 9, "Nohrabbit.gif"),
    new Character(7, "Primordite", 55, 11, "Primordite.gif"),
    new Character(8, "Wizard", 56, 4, "Wizard.gif"),
    new Character(9, "Crawly", 57, 3, "Crawly.gif")
  )

  parties.head.characters = allies
  parties(1).characters = enemies

  val characters: List[Character] = allies ++ enemies

  def findCharacter(id: Int): Option[Character] = characters.find(_.id == id)

  def findPanel(id: Int): Option[Panel] = panels.find(_.id == id)

  var currentCharacter: Character = characters.head

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

  private val alliesSide: List[Panel] = List(
    new Panel(1, (1, 1)),
    new Panel(2, (1, 2)),
    new Panel(3, (1, 3)),
    new Panel(4, (2, 1)),
    new Panel(5, (2, 2)),
    new Panel(6, (2, 3)),
    new Panel(7, (3, 1)),
    new Panel(8, (3, 2)),
    new Panel(9, (3, 3))
  )

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

  val w1: Weapon = new Weapon(1, "Excalibur", 10)
  val w2: Weapon = new Weapon(2, "Sword", 20)
  val w3: Weapon = new Weapon(3, "Axe", 30)
  val w4: Weapon = new Weapon(4, "Staff", 40)
  val w5: Weapon = new Weapon(5, "Wand", 50)

  val weapons = List(w1, w2, w3, w4, w5)

  def findWeapon(id: Int): Option[Weapon] = weapons.find(_.id == id)

}

object GameState {
  private var game = new GameState()
  def apply(): GameState = game
  def reset(): Unit = game = new GameState()
}
