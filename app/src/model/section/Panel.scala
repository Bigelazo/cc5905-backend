package model.section

import model.action.Action
import model.unit.GameUnit
import model.{Target, unit}

class Panel(override val id: Int,
            override val coordinates: (Int, Int),
            override val next: List[Section] = List()) extends Section with Target {

  override var storage: List[GameUnit] = List()

  override def toJson: ujson.Obj = ujson.Obj(
    "id" -> id,
    "x" -> coordinates._1,
    "y" -> coordinates._2)

  override def doAction(action: Action, target: Target): Unit = {}

  override def receiveMoveAction(source: unit.Character): Unit = {
    source.moveTo(this)
  }
}
