package model.section

import model.action.Action
import model.unit.GameUnit
import model.{Target, unit}
import ujson.Obj

class Panel(override val coordinates: (Int, Int),
            override val next: List[Section] = List()) extends Section with Target {

  override var storage: List[GameUnit] = List()

  def toJson: ujson.Obj = Obj(
    "x" -> coordinates._1,
    "y" -> coordinates._2,
    "storage" -> storage.map(_.toJson)
  )

  override def doAction(action: Action, target: Target): Unit = {}

  override def receiveMoveAction(source: unit.Character): Unit = {
    source.moveTo(this)
  }
}
