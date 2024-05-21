package model

import model.student.{Character, Panel}

object Game {
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

  val characters: List[Character] = List(dummy1, dummy2, dummy3, dummy4, dummy5, dummy6, dummy7,
    dummy8, dummy9, dummy10, dummy11, dummy12)

  var currentPlayer: Character = characters.head

  val panel1: Panel = new Panel(1, (1, 1))
  val panel2: Panel = new Panel(2, (1, 2))
  val panel3: Panel = new Panel(3, (1, 3))
  val panel4: Panel = new Panel(4, (2, 1))
  val panel5: Panel = new Panel(5, (2, 2))
  val panel6: Panel = new Panel(6, (2, 3))
  val panel7: Panel = new Panel(7, (3, 1))
  val panel8: Panel = new Panel(8, (3, 2))
  val panel9: Panel = new Panel(9, (3, 3))

  val panels: List[Panel] = List(panel1, panel2, panel3, panel4, panel5,
    panel6, panel7, panel8, panel9)
}
