package millbuild

import _root_.mill.runner.MillBuildRootModule

object MiscInfo_build {
  implicit lazy val millBuildRootModuleInfo: _root_.mill.runner.MillBuildRootModule.Info = _root_.mill.runner.MillBuildRootModule.Info(
    Vector("/home/bigel/Documents/5905/minimalApplication-0.9.2/out/mill-launcher/0.11.6.jar").map(_root_.os.Path(_)),
    _root_.os.Path("/home/bigel/Documents/5905/minimalApplication-0.9.2"),
    _root_.os.Path("/home/bigel/Documents/5905/minimalApplication-0.9.2"),
    _root_.scala.Seq()
  )
  implicit lazy val millBaseModuleInfo: _root_.mill.main.RootModule.Info = _root_.mill.main.RootModule.Info(
    millBuildRootModuleInfo.projectRoot,
    _root_.mill.define.Discover[build]
  )
}
import MiscInfo_build.{millBuildRootModuleInfo, millBaseModuleInfo}
object build extends build
class build extends _root_.mill.main.RootModule {

//MILL_ORIGINAL_FILE_PATH=/home/bigel/Documents/5905/minimalApplication-0.9.2/build.sc
//MILL_USER_CODE_START_MARKER
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

}