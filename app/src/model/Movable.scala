package model

/**
 * Represents an object that can move around the map of the game. Think of it as character token in
 * monopoly.
 */
trait Movable extends Visualizable {

  /**
   * Represents the current standing position of the movable. If there is no mappable in place, it
   * means that the movable isn't currently on the map.
   */
  var mappable: Option[Mappable]

  /**
   * Displaces the movable to the selected mappable. This method needs to verify the following
   * before his execution:
   *
   * 1. The movable has a place on the map.
   *
   * 2. The selected mappable has to be one of the next mappable objects of the current place.
   *
   * @param mappable the mappable object that the movable desires to displace into.
   */
  def moveTo(mappable: Mappable): Unit
}
