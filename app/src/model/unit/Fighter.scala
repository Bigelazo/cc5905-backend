package model.unit

import model.action.{Action, Punch}
import model.action.move.Move

class Fighter(name: String,
              hp: Int,
              attack: Int) extends Character(name, hp, attack) {

  override val actions: List[Action] = List(new Punch, new Move)
}
