package app

import cask.model.Response
import controller.GameState
import model.action.{ActionOnCharacter, ActionOnPanel, ActionOnWeapon}
import ujson.{Arr, Obj}

object View extends cask.MainRoutes {

  private val headers: Seq[(String, String)] = Seq(
    "Access-Control-Allow-Origin" -> "*",
    "Content-Type" -> "application/json"
  )

  @cask.get("/grid/:id")
  def grid(id: Int): Response[Obj] = {

    val panels = "panels" -> (for (panel <- GameState().findParty(id).get.panels) yield panel.toJson)
    val units = "units" -> (for (character <- GameState().findParty(id).get.characters) yield character.toJson)

    val response = Obj(

    )
    cask.Response(response, headers = headers)
  }

  @cask.get("/reset")
  def reset(): Unit = {
    GameState.reset()
  }

  @cask.get("/show-actions/:requesterId")
  def showPossibleActions(requesterId: Int): Response[Obj] = {
    val response = Obj(
      "actions" -> (for (action <- GameState().currentCharacter._2.actions) yield action.toJson)
    )
    cask.Response(response, headers = headers)
  }

  @cask.post("/execute-action/:actionId/:sourceId/:targetId")
  def executeAction(actionId: Int, sourceId: Int, targetId: Int): Response[Obj] = {
    val source = GameState().findCharacter(sourceId).get
    val action = source.findActionById(actionId)

    val msg = action match {
      case a: ActionOnCharacter =>
        source.doAction(a, GameState().findCharacter(targetId).get)
        s"${GameState().findCharacter(targetId).get.name}"
      case a: ActionOnPanel =>
        source.doAction(a, GameState().findPanel(targetId).get)
        s"Panel ${GameState().findPanel(targetId).get.coordinates}"
      case a: ActionOnWeapon =>
        source.doAction(a, GameState().findCharacter(targetId).get)
        s"${GameState().findCharacter(targetId).get.name}"
      case _ => throw new Exception("Invalid action")
    }

    val nextUnitIndex = GameState().currentCharacter._1 + 1
    val module = nextUnitIndex % GameState().characters.size
    GameState().currentCharacter = (module, GameState().characters(module))

    cask.Response(
      Obj(
        "message" -> s"${source.name} executed action ${source.findActionById(actionId).getClass.getName} on $msg",
        "currentUnit" -> GameState().currentCharacter._1
      ),
      headers = headers
    )
  }

  @cask.staticResources("/static/resource")
  def staticResourceRoutes() = "."

  initialize()
}
