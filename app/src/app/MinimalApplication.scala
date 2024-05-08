package app

import cask.model.Response
import ujson.Obj
import model.Character

object MinimalApplication extends cask.MainRoutes{
  /* Qué es lo que quiero hacer:
  - GET información de todos los personajes en juego
  - POST actualización de la vida de un personaje en juego (no me deja con POST)
  */

  val dummy1: Character = new Character(1, "dummy1", 100, 10)
  val dummy2: Character = new Character(2, "dummy2", 50, 5)
  val dummy3: Character = new Character(3, "dummy3", 51, 6)
  val dummy4: Character = new Character(4, "dummy4", 52, 7)
  val dummy5: Character = new Character(5, "dummy5", 53, 8)
  val dummy6: Character = new Character(6, "dummy5", 53, 8)
  val dummy7: Character = new Character(7, "dummy5", 53, 8)
  val dummy8: Character = new Character(8, "dummy5", 53, 8)
  val dummy9: Character = new Character(9, "dummy5", 53, 8)
  val dummy10: Character = new Character(10, "dummy5", 53, 8)
  val dummy11: Character = new Character( 11, "dummy5", 53, 8)
  val dummy12: Character = new Character(12, "dummy5", 53, 8)

  val characters: List[Character] = List(dummy1, dummy2, dummy3, dummy4, dummy5, dummy6, dummy7, dummy8, dummy9, dummy10, dummy11, dummy12)
  var currentPlayer = characters(0)

  // Request -> Response
  // An http server is basically a function that for every request that it receives, it
  // will return a response, we call these functions "routes", and a server is nothing more
  // than a set of these routes chained together.

  @cask.get("/character")
  def character(): Response[Obj] = {
    val response = ujson.Obj(
      "characters" -> (
        for (character <- characters) yield ujson.Obj(
          "id" -> character.id,
          "name" -> character.name,
          "hp" -> character.hp,
          "attack" -> character.attack
        )
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

    //get next player
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
