package model.unit

import controller.GameState
import model.action.move.Move
import model.Target
import model.action.spell.{Fireball, Icebolt}
import model.action.use.Equip
import model.action.{Action, Punch}
import model.section.Section
import ujson.Obj

class Character(val name: String,
                var hp: Int,
                val attack: Int) extends GameUnit with Target {

  val img: String =
    if (hp <= 0) name + "_dead.gif"
    else name + ".gif"

  var weapon: Option[Weapon] = None

  var currentSection: Option[Section] = None

  override val actions: List[Action] = List(
    new Punch, new Fireball, new Icebolt, new Move, new Equip(List(new Weapon(1, "E", 10)))
  )

  def findActionById(id: Int): Action = {
    val ids = actions.map(a => a.id)
    val actionIndex = ids.indexOf(id)
    actions(actionIndex)
  }

  def doAction(action: Action, target: Target): Unit = {
    action match {
      case p: Punch => target.receivePunchAction(this)
      case f: Fireball => target.receiveFireballAction(this)
      case i: Icebolt => target.receiveIceboltAction(this)
      case m: Move => target.receiveMoveAction(this)
      case e: Equip => target.receiveEquipAction(this)
    }
  }

  override def receivePunchAction(source: Character): Unit = {
    receiveDamage(source)
  }

  override def receiveFireballAction(source: Character): Unit = {
    receiveDamage(source)
  }

  override def receiveIceboltAction(source: Character): Unit = {
    receiveDamage(source)
  }

  override def moveTo(section: Section): Unit = {
    section.storage = this :: section.storage
    this.currentSection = Some(section)
  }

  def toJson: ujson.Obj = Obj(
    "name" -> name,
    "hp" -> hp,
    "attack" -> attack,
    "img" -> img
  )

  private def receiveDamage(c: Character): Unit = {
    hp -= c.attack
  }


}
