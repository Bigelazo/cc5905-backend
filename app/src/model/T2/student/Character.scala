package model.T2.student

import model.T2.{GameState, Movable, Section, Target}
import model.T2.action.spell.{Fireball, Icebolt}
import model.T2.action.{Action, Equip, Move, Punch}

class Character(val id: Int,
                val name: String,
                var hp: Int,
                val attack: Int,
                val img: String = "") extends Movable with Target {

  var weapon: Option[Weapon] = None

  var currentSection: Option[Section] = None

  override val actions: List[Action] = List(new Punch, new Fireball, new Icebolt, new Move, new Equip(List(new Weapon(1, "E", 10))))

  /**
   * Dado un id de acción, encuentra la acción correspondiente en la lista de acciones.
   */
  def findActionById(id: Int): Action = {
    val ids = actions.map(a => a.id)
    val actionIndex = ids.indexOf(id)
    actions(actionIndex)
  }

  def doAction(action: Action, target: Target): Unit = {
    //this.actions(actionId).execute(this, target)
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

class Fighter(id: Int,
              name: String,
              hp: Int,
              attack: Int,
              img: String = "") extends Character(id, name, hp, attack, img){
  override val actions: List[Action] = List(new Punch, new Move)
}
