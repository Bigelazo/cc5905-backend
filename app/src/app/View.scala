package app

import cask.model.Response
import controller.GameState
import model.action.{ActionOnCharacter, ActionOnPanel, ActionOnWeapon}
import ujson.Obj

object View extends cask.MainRoutes {

  private val headers: Seq[(String, String)] = Seq(
    "Access-Control-Allow-Origin" -> "*",
    "Content-Type" -> "application/json"
  )

  @cask.get("/grid/:id")
  def grid(id: Int): Response[Obj] = {
    val response = Obj(
      "panels" -> (for (panel <- GameState().findParty(id).get.panels) yield panel.toJson),
      "units" -> (for (character <- GameState().findParty(id).get.characters) yield character.toJson)
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
      "actions" -> (for (action <- GameState().currentCharacter.actions) yield action.toJson)
    )
    cask.Response(response, headers = headers)
  }

  @cask.post("/execute-action/:actionId/:sourceId/:targetId")
  def executeAction(actionId: Int, sourceId: Int, targetId: Int): Response[Obj] = {
    val source = GameState().findCharacter(sourceId).get
    val action = source.findActionById(actionId)

    val msg = action match{
      case a: ActionOnCharacter =>
        source.doAction(a, GameState().findCharacter(targetId).get)
        s"${GameState().findCharacter(targetId).get.name}"
      case a: ActionOnPanel =>
        source.doAction(a, GameState().findPanel(targetId).get)
        s"Panel ${GameState().findPanel(targetId).get.id}"
      case a: ActionOnWeapon =>
        source.doAction(a, GameState().findCharacter(targetId).get)
        s"${GameState().findCharacter(targetId).get.name}"
      case _ => throw new Exception("Invalid action")
    }

    val idx = GameState().characters.indexWhere(c => c.id == GameState().currentCharacter.id) + 1
    GameState().currentCharacter = GameState().characters(idx % GameState().characters.length)

    cask.Response(
      Obj(
        "message" -> s"${source.name} executed action ${source.findActionById(actionId).getClass.getName} on $msg",
        "currentUnit" -> GameState().currentCharacter.id
      ),
      headers = headers
    )
  }

  @cask.staticResources("/static/resource")
  def staticResourceRoutes() = "."

  initialize()
}
