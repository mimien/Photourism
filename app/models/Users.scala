package models

/**
 * @author emiliocornejo
 * @version 01/05/15
 *          @(#)Users.scala
 */

case class User(name: String, email: String, passwords: (String, String), bio: String)

object Users {
  def find(name: String): String = ???
}
