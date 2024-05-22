package app

import cask.model.Response
import model.Game.{characters, currentCharacter, findCharacter, panels, players}
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
      "players" -> (
        for (player <- players) yield player.toJson
      ),
      "panels" -> (
        for (panel <- panels) yield panel.toJson
      ),
      "currentCharacter" -> currentCharacter.id,
      "winCondition" -> 0,
    )
    cask.Response(response, headers = headers)
  }

  @cask.get("/character")
  def character(): Response[Obj] = {
    val response = ujson.Obj(
      "characters" -> (
        for (character <- characters) yield character.toJson
        ),
      "currentCharacter" -> currentCharacter.id
    )
    cask.Response(response, headers = headers)
  }

  @cask.get("/panel")
  def panel(): Response[Obj] = {
    val response = ujson.Obj(
      "panels" -> (
        for (panel <- panels) yield panel.toJson
        ),
    )
    cask.Response(response, headers = headers)
  }

  @cask.post("/attack/:attackerId/:receiverId")
  def attack(attackerId: Int, receiverId: Int): Response[Obj] = {
    val attacker: Character = findCharacter(attackerId).get
    val receiver: Character = findCharacter(receiverId).get

    attacker.attack(receiver)

    val idx = characters.indexWhere(c => c.id == currentCharacter.id) + 1
    currentCharacter = characters(idx % characters.length)

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

  initialize()
}
