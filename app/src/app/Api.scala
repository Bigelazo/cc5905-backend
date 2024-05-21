package app

import cask.model.Response
import model.Game.{characters, currentPlayer, panels}
import ujson.Obj
import model.student.Character

object Api extends cask.MainRoutes{

  /*
  Use GET when you need to *retrieve* data from the server.

  Use POST when you need to *create* new data or *submit* data
  for processing that might change the state of the server.
  */

  /* Ideas de endpoints para el sistema actual:
  get("/") -> Entrega TODOS (todos?) los datos para inicializar el juego
  post("/") -> Envía win condition para finalizar el juego

  get("/currentTurn") -> Entrega turno del personaje actual
  post("/currentTurn") -> Actualiza el turno para el siguiente personaje

  get("/")
  post("/")
   */

  /**
   * This route returns all the characters in the game.
   */
  @cask.get("/character")
  def character(): Response[Obj] = {
    val response = ujson.Obj(
      "characters" -> (
        for (character <- characters) yield character.toJson
        ),
      "currentPlayer" -> currentPlayer.id
    )
    cask.Response(
      response,
      headers = Seq(
        "Access-Control-Allow-Origin" -> "*",
        "Content-Type" -> "application/json"
      )
    )
  }

  @cask.get("/panel")
  def panel(): Response[Obj] = {
    val response = ujson.Obj(
      "panels" -> (
        for (panel <- panels) yield panel.toJson
        ),
    )
    cask.Response(
      response,
      headers = Seq(
        "Access-Control-Allow-Origin" -> "*",
        "Content-Type" -> "application/json"
      )
    )
  }

  @cask.get("/character/:id")
  def character(id: Int): Response[Obj] = {
    for (character <- characters) {
      if (character.id == id) {
        val response = ujson.Obj(
          "id" -> character.id,
          "name" -> character.name,
          "hp" -> character.hp,
          "attack" -> character.attack
        )
        return cask.Response(
          response,
          headers = Seq(
            "Access-Control-Allow-Origin" -> "*",
            "Content-Type" -> "application/json"
          )
        )
      }
    }
    cask.Response(
      ujson.Obj("error" -> "Character not found"),
      statusCode = 404,
      headers = Seq(
        "Access-Control-Allow-Origin" -> "*",
        "Content-Type" -> "application/json"
      )
    )
  }

  @cask.get("/attack/:fromId/:toId")
  def attack(fromId: Int, toId: Int): Response[Obj] = {
    var attacker: Character = null
    var receiver: Character = null
    for (character <- characters) {
      if (character.id == fromId) {
        attacker = character
      }
      if (character.id == toId) {
        receiver = character
      }
    }
    receiver.hp -= attacker.attack

    val idx = characters.indexWhere(c => c.id == currentPlayer.id)
    currentPlayer = characters((idx + 1) % characters.length)

    cask.Response(
      ujson.Obj("message" -> s"${attacker.name} attacked ${receiver.name}"),
      headers = Seq(
        "Access-Control-Allow-Origin" -> "*",
        "Content-Type" -> "application/json"
      )
    )
  }

  initialize()
}
