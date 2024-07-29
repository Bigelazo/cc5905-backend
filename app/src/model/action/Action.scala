package model.action

import model.GameObject

trait Action extends GameObject {
  val id: Int
}

trait ActionOnCharacter extends Action
trait ActionOnWeapon extends Action
trait ActionOnPanel extends Action
