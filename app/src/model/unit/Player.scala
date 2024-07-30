package model.unit

import model.GameObject
import model.section.Panel
import ujson.Obj

import java.lang
import scala.collection.immutable.HashMap

class Player(val name: String,
             val units: HashMap[Int, Character],
             val panels: HashMap[Int, Panel]) extends GameObject {

  def toJson: Obj = Obj(
    "name" -> name,
    "units" -> (for ((id, unit) <- units) yield
      Obj(
        "id" -> id,
        "unit" -> unit.toJson,
      )),
    "panels" -> (for ((id, panel) <- panels) yield
      Obj(
        "id" -> id,
        "panel" -> panel.toJson
      )),
  )
}
