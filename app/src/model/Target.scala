package model

import model.action.Action
import model.unit.Character

trait Target {

  val actions: List[Action] = List()

  def doAction(action: Action, target: Target): Unit

  def receivePunchAction(source: Character): Unit = throw new Exception("Using trait as val")

  def receiveMoveAction(source: Character): Unit = throw new Exception()

  def receiveFireballAction(source: Character): Unit = throw new Exception()

  def receiveIceboltAction(source: Character): Unit = throw new Exception()

  def receiveEquipAction(source: Character): Unit = throw new Exception()
}
