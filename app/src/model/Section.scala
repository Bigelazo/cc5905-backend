package model

/**
 * Represents an object that can be used to build the map of the game. Think of it as a block of
 * grass in the map, or like a tile in monopoly.
 *
 * It serves three main purposes:
 *
 * 1. Build the map: A set of Sections can provide a freely way to represent fixed boards, or open
 * fields.
 *
 * 2. Provide mobility: The movable objects can displace into the designed next mappable objects,
 * giving them moving actions towards a goal or destination.
 *
 * 3. Give effects: A mappable can provide certain effects when a movable object enters, stays or
 * leaves the tile. The cool part is that you can fit these effects to your
 * liking, and by default, there are no effects.
 */
trait Section extends Visualizable {

  /**
   * The coordinates of the object in the map. The origin of the coordinates is (1,1) and it's meant
   * to be seen like (x, -y) which means that when you use the coordinates, the y axis is inverted
   * from an original cartesian plane standpoint.
   */
  val coordinates: (Int, Int)

  /**
   * The storage of the movable objects in the map.
   */
  var storage: List[Movable]

  /**
   * The next mappable objects which a movable object can displace into.
   */
  val next: List[Section]
}
