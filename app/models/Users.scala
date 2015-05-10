package models

/**
 * @author emiliocornejo
 * @version 01/05/15
 *          @(#)Users.scala
 */

case class User(name: String, password: String, id: Option[Int] = None)
object Users {
  def find(name: String): String = ???

}
