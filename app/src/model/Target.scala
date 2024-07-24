package model

import model.action.Action

trait Target {

  val actions: List[Action]

  def doAction(actionId: Int, target: Target): Unit

  def receivePunchAction(source: Target): Unit = throw new Exception("Using trait as val")

  def receiveMoveAction(source: Target): Unit = throw new Exception()

  def receiveFireballAction(source: Target): Unit = throw new Exception()

  def receiveIceboltAction(source: Target): Unit = throw new Exception()

  def receiveEquipAction(source: Target): Unit = throw new Exception()
}
