package model.T2.student

import model.T2.action.Action
import model.T2.{Target, student}

import java.lang.Character

class Weapon(val id: Int, val name: String, val damage: Int) extends Target {
  def toJson: ujson.Obj = {
    ujson.Obj(
      "id" -> id,
      "name" -> name,
      "damage" -> damage
    )
  }

  override def doAction(action: Action, target: Target): Unit = {}

  override def receiveEquipAction(source: student.Character): Unit = {
    source.weapon = Some(this)
  }
}
