package controllers

import models.{Users, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import com.roundeights.hasher.Implicits._
import scala.language.postfixOps

/**
 * @author emiliocornejo
 * @version 07/05/15
 *          @(#)Auth.scala
 */
object Auth extends Controller {

  val loginForm = Form(
    mapping(
      "name" -> text,
      "password" -> text,
      "id" -> optional(number)
    )(User.apply)(User.unapply)
      verifying("Contraseña o nombre de usuario invalido", user => check(user.name, user.password))
  )

  def check(name: String, password: String) = {
    val dbPassword = Users.find(name)
    if (password.bcrypt hash= dbPassword) true
    else false
  }
}
