package controller

import model.section.Panel
import model.unit.{Character, Fighter, Player, Weapon}

import scala.collection.immutable.HashMap
import scala.util.Random


class GameState {

  val allies: HashMap[Int, Character] = HashMap(
    1 -> new Character("Relm", 100, 10),
    2 -> new Fighter("Setzer", 50, 5),
    3 -> new Character("Terra", 51, 6),
    4 -> new Character("Gogo", 52, 7)
  )

  val enemies: HashMap[Int, Character] = HashMap(
    5 -> new Character("Mag Roader", 53, 8),
    6 -> new Character("Nohrabbit", 54, 9),
    7 -> new Character("Primordite", 55, 11),
    8 -> new Character("Wizard", 56, 4),
    9 -> new Character("Crawly", 57, 3)
  )

  val characters: HashMap[Int, Character] = allies ++ enemies

  def findCharacter(id: Int): Option[Character] = characters.get(id)

  var currentCharacter: (Int, Character) = characters.head

  val alliesSide: HashMap[Int, Panel] = HashMap(
    1 -> new Panel((1, 1)),
    2 -> new Panel((1, 2)),
    3 -> new Panel((1, 3)),
    4 -> new Panel((2, 1)),
    5 -> new Panel((2, 2)),
    6 -> new Panel((2, 3)),
    7 -> new Panel((3, 1)),
    8 -> new Panel((3, 2)),
    9 -> new Panel((3, 3))
  )

  val enemiesSide: HashMap[Int, Panel] = HashMap(
    10 -> new Panel((1, 1)),
    11 -> new Panel((1, 2)),
    12 -> new Panel((1, 3)),
    13 -> new Panel((2, 1)),
    14 -> new Panel((2, 2)),
    15 -> new Panel((2, 3)),
    16 -> new Panel((3, 1)),
    17 -> new Panel((3, 2)),
    18 -> new Panel((3, 3))
  )

  val panels: HashMap[Int, Panel] = alliesSide ++ enemiesSide

  def findPanel(id: Int): Option[Panel] = panels.get(id)

  private val players: HashMap[Int, Player] = HashMap(
    1 -> new Player("Ally Player", allies, alliesSide),
    2 -> new Player("Enemy Player", enemies, enemiesSide)
  )

  def findPlayer(id: Int): Option[Player] = players.get(id)

  for ((_, character) <- allies)
    character.moveTo(alliesSide.values.toList(Random.nextInt(alliesSide.size)))

  for ((_, character) <- enemies)
    character.moveTo(enemiesSide.values.toList(Random.nextInt(enemiesSide.size)))

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
