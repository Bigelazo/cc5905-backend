package model.unit

import model.action.Action
import model.{Target, unit}

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

  override def receiveEquipAction(source: unit.Character): Unit = {
    source.weapon = Some(this)
  }
}
