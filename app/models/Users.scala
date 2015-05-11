package models

import java.sql.DriverManager
import com.roundeights.hasher.Implicits._
import scala.language.postfixOps
/**
 * @author emiliocornejo
 * @version 01/05/15
 *          @(#)Users.scala
 */

case class User(name: String, email: String, passwords: (String, String), bio: String)

object Users {
  val db = DriverManager.getConnection("jdbc:postgresql://localhost/Photourism")

  val st = db.createStatement()

  def findPassword(email: String): Option[String] = {
//    SELECT password from "User" where email = 'emiliocornejo2@gmail.com';
    val query = st.executeQuery("SELECT password from \"User\" where email = '" + email  +"';")
    if (!query.next()) None
    else Some(query.getString(1))
  }

  def add(user: User): Unit = {
    println(user.passwords._1)
    st.executeUpdate("INSERT into \"User\"(email, password, name, bio) " +
      "VALUES('" + user.email + "', '" + user.passwords._1.bcrypt.hex + "', '" + user.name + "', '" + user.bio + "');")
  }


}
