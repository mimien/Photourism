package models

/**
 * @author emiliocornejo
 * @version 30/04/15
 *          @(#)Photos.scala
 */
case class Photo(path: String, place: String/*, priv: Boolean, point: String*/)
object Photos {
  private var _point: String = _

  def point_=(latLng: String) {
    _point = latLng
  }
}
