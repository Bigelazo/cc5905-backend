package app

import cask.model.Response
import model.GameState
import ujson.Obj
import model.student.{Character, Panel}

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
      "currentCharacter" -> GameState().currentCharacter.id,
      "gameStatus" -> GameState().getGameStatus,
    )
    cask.Response(response, headers = headers)
  }

  @cask.get("/characters/:id")
  def characters(id: Int): Response[List[Obj]] = {
    val response = for (character <- GameState().findParty(id).get.characters) yield character.toJson
    cask.Response(response, headers = headers)
  }

  @cask.get("/panels/:id")
  def panel(id: Int): Response[List[Obj]] = {
    val response = for (panel <- GameState().findParty(id).get.panels) yield panel.toJson
    cask.Response(response, headers = headers)
  }

  @cask.get("/grid/:id")
  def grid(id: Int): Response[Obj] = {
    val response = Obj(
      "panels" -> (for (panel <- GameState().findParty(id).get.panels) yield panel.toJson),
      "units" -> (for (character <- GameState().findParty(id).get.characters) yield character.toJson)
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
      ujson.Obj(
        "message" -> s"${attacker.name} attacked ${receiver.name}",
        "currentUnit" -> GameState().currentCharacter.id
      ),
      headers = headers
    )
  }

  @cask.post("/assign/:charId/:panelId")
  def assign(charId: Int, panelId: Int): Response[Obj] = {
    val character: Character = GameState().findCharacter(charId).get
    val panel: Panel = GameState().findPanel(panelId).get

    character.moveTo(panel)

    val idx = GameState().characters.indexWhere(c => c.id == GameState().currentCharacter.id) + 1
    GameState().currentCharacter = GameState().characters(idx % GameState().characters.length)

    cask.Response(
      ujson.Obj(
        "message" -> s"${character.name} moved to (${panel.coordinates._1},${panel.coordinates._2})",
        "currentUnit" -> GameState().currentCharacter.id
      ),
      headers = headers)
  }

  @cask.get("/reset")
  def reset(): Response[Obj] = {
    GameState.reset()
    sendGameData()
  }

  /* Existen: Selectores de acciones, y acciones.
  * Un selector de acciones puede contener otro selector de acciones, o una lista de acciones.
  * Una acción es una modificación que se realiza por el personaje en turno.
  *
  * Pensando en el ejemplo de un combate por turnos, un personaje en su turno podría:
  * 1. Atacar
  * 2. Lanzar un hechizo
  * 3. Equipar un arma
  * 4. Moverse a otra coordenada
  *
  * Entonces, el selector de acciones sería como sigue:
  * 0. Entregar una lista para seleccionar Ataque | Hechizo | Equipar | Mover
  * 1. Entregar una lista de personajes a los que puede atacar
  * 2. Idéntico a 1.
  * 3. Entregar una lista de armas que puede equiparse
  * 4. Entregar una lista de coordenadas a las que puede moverse
  *
  * Ideas más avanzadas:
  * 2. Entregar una lista de tipos de hechizos a utilizar: Fuego | Hielo | Trueno
  * 3. Generalizar a utilizar objeto: Item | Poción | Arma | Armadura
  * */

  /**
   * Recibe una acción a realizar, y devuelve una lista de las posibles ejecuciones a realizar por
   * la unidad que solicitó esa acción.
   *
   * Por ejemplo, si se deseara equipar un arma, entrega una lista de las posibles armas a
   * equiparse, o si se deseara lanzar un hechizo, entrega una lista de los posibles hechizos que
   * puede utilizar.
   */
  @cask.get("/select-action/:selectorId/:requesterId")
  def selectAction(selectorId: Int, requesterId: Int): Response[String] = {
    val response = selectorId match {
      case 0 => "Entregar menú principal"
      case 1 => "Entregar lista de personajes en JSON"
      case 2 => "Entregar lista de hechizos en JSON"
      case 3 => "Entregar lista de armas en JSON"
      case 4 => "Entregar lista de paneles en JSON"
    }

    cask.Response(response, headers = headers)
  }

  @cask.post("/execute-action/:actionId/:requesterId/:targetId")
  def executeAction(actionId: Int, requesterId: Int, targetId: Int): Response[String] = {
    val response = actionId match {
      case 1 => "Realizar ataque de rId a tId"
      case 2 => ""
    }

    cask.Response(response, headers = headers)
  }


  initialize()
}
