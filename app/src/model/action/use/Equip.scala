package model.action.use

import model.action.{Action, ActionOnWeapon}
import model.unit.Weapon
import ujson.Obj

class Equip(val weapons: List[Weapon]) extends Action with ActionOnWeapon {
  override val id: Int = 10

  override def toJson: Obj = Obj(
    "id" -> id,
    "action" -> ("Equip→" + (for (weapon <- weapons) yield weapon.toJson))
  )
}
