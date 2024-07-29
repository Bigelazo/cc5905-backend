package controller

import model.section.Panel
import model.unit.{Fighter, Party, Weapon, Character}

class GameState {

  private val allies: List[Character] = List(
    new Character(1, "Relm", 100, 10),
    new Fighter(2, "Setzer", 50, 5),
    new Character(3, "Terra", 51, 6),
    new Character(4, "Gogo", 52, 7)
  )

  private val enemies: List[Character] = List(
    new Character(5, "Mag Roader", 53, 8),
    new Character(6, "Nohrabbit", 54, 9),
    new Character(7, "Primordite", 55, 11),
    new Character(8, "Wizard", 56, 4),
    new Character(9, "Crawly", 57, 3)
  )

  val characters: List[Character] = allies ++ enemies

  def findCharacter(id: Int): Option[Character] = characters.find(_.id == id)

  def findPanel(id: Int): Option[Panel] = panels.find(_.id == id)

  var currentCharacter: Character = characters.head

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

  private val enemiesSide: List[Panel] = List(
    new Panel(10, (1, 1)),
    new Panel(11, (1, 2)),
    new Panel(12, (1, 3)),
    new Panel(13, (2, 1)),
    new Panel(14, (2, 2)),
    new Panel(15, (2, 3)),
    new Panel(16, (3, 1)),
    new Panel(17, (3, 2)),
    new Panel(18, (3, 3))
  )

  val panels: List[Panel] = alliesSide ++ enemiesSide

  private val parties: List[Party] = List(
    new Party(2, "Ally Player"),
    new Party(1, "Enemy Player")
  )

  parties.head.characters = allies
  parties(1).characters = enemies

  parties.head.panels = alliesSide
  parties(1).panels = enemiesSide

  def findParty(id: Int): Option[Party] = parties.find(_.id == id)

  allies.head.moveTo(alliesSide.head)
  allies(1).moveTo(alliesSide(7))
  allies(2).moveTo(alliesSide(1))
  allies(3).moveTo(alliesSide(3))

  enemies.head.moveTo(enemiesSide.head)
  enemies(1).moveTo(enemiesSide(7))
  enemies(2).moveTo(enemiesSide(1))
  enemies(3).moveTo(enemiesSide(3))
  enemies(4).moveTo(enemiesSide(5))

  val weapons: List[Weapon] = List(
    new Weapon(1, "Excalibur", 10),
    new Weapon(2, "Sword", 20),
    new Weapon(3, "Axe", 30),
    new Weapon(4, "Staff", 40),
    new Weapon(5, "Wand", 50)
  )

  def findWeapon(id: Int): Option[Weapon] = weapons.find(_.id == id)
}

object GameState {
  private var game = new GameState()
  def apply(): GameState = game
  def reset(): Unit = game = new GameState()
}
