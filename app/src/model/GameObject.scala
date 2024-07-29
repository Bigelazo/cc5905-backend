package model

/**
 * Represents an object that can be visualized in the user interface.
 */
trait GameObject {

  /**
   * Converts the object to a JSON object. This method is used to send information of the object
   * to the client. The client will receive the JSON object and will be able to render the object
   * in the user interface.
   * @return a JSON representing the object
   */
  def toJson: ujson.Obj
}
