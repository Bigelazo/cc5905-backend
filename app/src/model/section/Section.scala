package model.section

import model.GameObject
import model.unit.GameUnit

/**
 * Represents an object that can be used to build the map of the game. Think of it as a block of
 * grass in the map, or like a tile in monopoly.
 *
 * It serves three main purposes:
 *
 * 1. Build the map: A set of Sections can provide a freely way to represent fixed boards, or open
 * fields.
 *
 * 2. Provide mobility: The game units can displace into the designed next sections, giving them
 * moving actions towards a goal or destination.
 *
 * 3. Give effects: A section can provide certain effects when a unit enters, stays or leaves the
 * section. The cool part is that you can fit these effects to your liking, and by default, there
 * are no effects.
 */
trait Section extends GameObject {

  /**
   * The coordinates of the object in the map. The origin of the coordinates is (1,1) and it's meant
   * to be seen like (x, -y) which means that when you use the coordinates, the y axis is inverted
   * from an original cartesian plane standpoint.
   */
  val coordinates: (Int, Int)

  /**
   * The storage of the game units in the map.
   */
  var storage: List[GameUnit]

  /**
   * The next objects which a game unit can displace into.
   */
  val next: List[Section]
}
