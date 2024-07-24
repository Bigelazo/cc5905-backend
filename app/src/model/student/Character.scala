package model.student

import model.action.spell.{Fireball, Icebolt}
import model.action.{Action, Equip, Move, Punch}
import model.{GameState, Movable, Section, Target}

class Character(override val id: Int,
                val name: String,
                var hp: Int,
                val attack: Int,
                val img: String = "") extends Movable with Target {

  var weapon: Option[Weapon] = None

  var currentSection: Option[Section] = None

  val actions: List[Action] = List(new Punch, new Fireball, new Icebolt, new Move, new Equip(List(new Weapon(1, "E", 10))))

  /**
   * Dado un id de acción, encuentra la acción correspondiente en la lista de acciones.
   */
  def findActionById(id: Int): Action = {
    val ids = actions.map(a => a.id)
    val actionIndex = ids.indexOf(id)
    actions(actionIndex)
  }

  def doAction(actionId: Int, target: Target): Unit = {
    //this.actions(actionId).execute(this, target)
    this.findActionById(actionId) match {
      case p: Punch => target.receivePunchAction(this)
      case f: Fireball => target.receiveFireballAction(this)
      case i: Icebolt => target.receiveIceboltAction(this)
      case m: Move => target.receiveMoveAction(this)
      case e: Equip => target.receiveEquipAction(this)
    }
  }

  override def receivePunchAction(source: Target): Unit = {
    receiveDamage(source.asInstanceOf[Character])
  }

  override def receiveFireballAction(source: Target): Unit = {
    receiveDamage(source.asInstanceOf[Character])
  }

  override def receiveIceboltAction(source: Target): Unit = {
    receiveDamage(source.asInstanceOf[Character])
  }

  override def moveTo(section: Section): Unit = {
    this.currentSection = Some(section)
  }

  def toJson: ujson.Obj = {
    ujson.Obj(
      "id" -> id,
      "name" -> name,
      "hp" -> hp,
      "attack" -> attack,
      "img" -> img,
      "mappableId" -> currentSection.map(_.id)
    )
  }

  def attack(c: Character): Unit = {
    c.receiveDamage(this)
  }

  private def receiveDamage(c: Character): Unit = {
    hp -= c.attack
  }


}
