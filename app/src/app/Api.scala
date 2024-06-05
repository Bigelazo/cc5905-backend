package app

import cask.model.Response
import model.GameState
import ujson.Obj
import model.student.Character

object Api extends cask.MainRoutes {

  private val headers: Seq[(String, String)] = Seq(
    "Access-Control-Allow-Origin" -> "*",
    "Content-Type" -> "application/json"
  )

  @cask.get("/")
  def sendGameData(): Response[Obj] = {
    val response = Obj(
      "parties" -> (
        for (party <- GameState().parties) yield party.toJson
      ),
      "panels" -> (
        for (panel <- GameState().panels) yield panel.toJson
      ),
      "currentCharacter" -> GameState().currentCharacter.id,
      "gameStatus" -> GameState().getGameStatus,
    )
    cask.Response(response, headers = headers)
  }

  @cask.get("/character")
  def character(): Response[Obj] = {
    val response = ujson.Obj(
      "characters" -> (
        for (character <- GameState().characters) yield character.toJson
        ),
      "currentCharacter" -> GameState().currentCharacter.id,
      "gameStatus" -> GameState().getGameStatus
    )
    cask.Response(response, headers = headers)
  }

  @cask.get("/panel")
  def panel(): Response[Obj] = {
    val response = ujson.Obj(
      "panels" -> (
        for (panel <- GameState().panels) yield panel.toJson
        ),
    )
    cask.Response(response, headers = headers)
  }

  @cask.post("/attack/:attackerId/:receiverId")
  def attack(attackerId: Int, receiverId: Int): Response[Obj] = {
    val attacker: Character = GameState().findCharacter(attackerId).get
    val receiver: Character = GameState().findCharacter(receiverId).get

    attacker.attack(receiver)

    val idx = GameState().characters.indexWhere(c => c.id == GameState().currentCharacter.id) + 1
    GameState().currentCharacter = GameState().characters(idx % GameState().characters.length)

    cask.Response(
      ujson.Obj("message" -> s"${attacker.name} attacked ${receiver.name}"),
      headers = headers
    )
  }

  @cask.post("/assign/:charId/:panelId")
  def assign(charId: Int, panelId: Int): Response[Obj] = {
    val response = Obj(
      "character" -> "Hola",
      "panel" -> "Chao"
    )
    cask.Response(response, headers = headers)
  }

  @cask.get("/reset")
  def reset(): Response[Obj] = {
    GameState.reset()
    sendGameData()
  }


  initialize()
}
