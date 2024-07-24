package model.T2.student

import model.T2.{Movable, Section, Target}
import model.T2.action.{Action, Move}

class Panel(override val id: Int,
            override val coordinates: (Int, Int),
            override val next: List[Section] = List()) extends Section with Target {

  override var storage: List[Movable] = List()

  override def toJson: ujson.Obj = ujson.Obj(
    "id" -> id,
    "x" -> coordinates._1,
    "y" -> coordinates._2)

  override def doAction(action: Action, target: Target): Unit = {}

  override def receiveMoveAction(source: Character): Unit = {
    source.moveTo(this)
  }
}
