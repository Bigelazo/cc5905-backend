package app

import cask.model.Response
import model.T1._
//import model.T1.action._
//import model.T1.student._
import ujson.Obj

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

  /*@cask.post("/attack/:attackerId/:receiverId")
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
  }*/

  /*@cask.post("/assign/:charId/:panelId")
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
  }*/

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

  def esSubclase[T: Manifest, U: Manifest]: Boolean = {
    manifest[U].runtimeClass.isAssignableFrom(manifest[T].runtimeClass)
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
//    val actionReceiver = if (actionId == 2) GameState().findPanel(targetId).get
//    else GameState().findCharacter(targetId).get

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

    // actionExecutioner.doAction(actionId, actionReceiver)

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


  /*@cask.get("/static/resource/:image")
  def getStatic(image: String) = {
    //val file = new FileInputStream("./app/resources/BlackMage.gif")
    val x = classOf[staticResources].getClassLoader
    val path = "./"+image
    val file = x.getResourceAsStream(path)
    val contentType = java.nio.file.Files.probeContentType(java.nio.file.Paths.get(path))
    println(file, contentType)
    cask.Response(
      file,
      headers =  Seq(
        "Access-Control-Allow-Origin" -> "*",
        "Content-Type" -> contentType
      )
    )
  }*/


  initialize()
}
