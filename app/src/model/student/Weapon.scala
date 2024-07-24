package model.student

import model.Target
import model.action.Action

class Weapon(val id: Int, val name: String, val damage: Int) extends Target {
  def toJson: ujson.Obj = {
    ujson.Obj(
      "id" -> id,
      "name" -> name,
      "damage" -> damage
    )
  }

  override val actions: List[Action] = List()

  override def doAction(actionId: Int, target: Target): Unit = {}

  override def receiveEquipAction(source: Target): Unit = {
    source.asInstanceOf[Character].weapon = Some(this)
  }
}
