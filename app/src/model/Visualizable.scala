package model

/**
 * Represents an object that can be visualized in the user interface.
 */
trait Visualizable {

  /**
   * The unique identifier of the object. It will be used to identify the object in the client.
   */
  val id: Int

  /**
   * Converts the object to a JSON object. This method is used to send information of the object
   * to the client. The client will receive the JSON object and will be able to render the object
   * in the user interface.
   * @return a JSON representing the object
   */
  def toJson: ujson.Obj
}
