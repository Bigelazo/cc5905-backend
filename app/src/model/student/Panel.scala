package model.student

import model.action.{Action, Move}
import model.{Movable, Section, Target}

class Panel(override val id: Int,
            override val coordinates: (Int, Int),
            override val next: List[Section] = List()) extends Section with Target {

  override var storage: List[Movable] = List()

  override def toJson: ujson.Obj = ujson.Obj(
    "id" -> id,
    "x" -> coordinates._1,
    "y" -> coordinates._2)

  override val actions: List[Action] = List()

  override def doAction(actionId: Int, target: Target): Unit = {}

  override def receiveMoveAction(source: Target): Unit = {
    source.asInstanceOf[Character].moveTo(this)
  }
}
