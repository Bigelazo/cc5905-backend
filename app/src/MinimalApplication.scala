package app
object MinimalApplication extends cask.MainRoutes{
  @cask.get("/")
  def hello() = {
    "Hello World123!"
  }

  @cask.post("/do-thing")
  def doThing(request: cask.Request) = {
    request.text().reverse
  }

  @cask.get("/attack") // variable path segment, e.g. HOST/user/lihaoyi
  def attack(fromId: Int, toId: Int, attack: Option[String] = None) = {
    println("fromId: " + fromId + " toId: " + toId + " attack: " + attack)
    val response = ujson.Obj("fromId" -> fromId, "toId" -> toId, "attack" -> attack)
    cask.Response(
      response,
      headers = Seq(
        "Access-Control-Allow-Origin" -> "*",
        "Content-Type" -> "application/json"
      )
    )
  }

  initialize()
}
