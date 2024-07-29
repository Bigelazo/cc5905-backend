package model.factory

import model.unit.{GameUnit, Character}

trait UnitFactory {
  def apply(): GameUnit
}

class CharacterFactory(val id: Int) extends UnitFactory {
  override def apply(): GameUnit = new Character(1, "Relm", 100, 10)
}

trait PanelFactory {

}

trait PartyFactory {

}