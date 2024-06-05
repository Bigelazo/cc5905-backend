package model.student

import model.{Section, Movable}

class Panel(override val id: Int,
            override val coordinates: (Int, Int),
            override val next: List[Section] = List()) extends Section {

  override var storage: List[Movable] = List()

  override def toJson: ujson.Obj = ujson.Obj(
    "id" -> id,
    "x" -> coordinates._1,
    "y" -> coordinates._2)
}
