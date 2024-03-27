import mill._, scalalib._

object app extends ScalaModule {
  def scalaVersion = "2.13.10"

  def ivyDeps = Agg(
    ivy"com.lihaoyi::cask:0.9.2",
  )
  object test extends ScalaTests with TestModule.Utest{

    def ivyDeps = Agg(
      ivy"com.lihaoyi::utest::0.8.1",
      ivy"com.lihaoyi::requests::0.8.0",
    )
  }
}
