package controllers

import models.{Users, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Security, Action, Controller}
import com.roundeights.hasher.Implicits._
import scala.language.postfixOps

/**
 * @author emiliocornejo
 * @version 07/05/15
 *          @(#)Auth.scala
 */
object Auth extends Controller {

  val loginForm = Form(
    tuple(
      "email" -> email,
      "password" -> nonEmptyText
    ) verifying("Invalid password or email", data => check(data._1, data._2))
  )

  val loginHtml = views.html.users.login(loginForm) // value used on Application controller

  def check(email: String, password: String) = {
    val dbPassword = Users.findPassword(email)
    if (dbPassword.isEmpty) false
    // compare hashed password form with database hashed password
    else if (password.bcrypt hash= dbPassword.get) {
      println("hola")
      true
    }
    else {
      println("falso")
      false
    }
  }

  def login = Action {
    Ok(loginHtml)
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      hasErrors = {
        formWithErrors =>
          BadRequest(views.html.users.login(formWithErrors))
      },
      success = {
        user =>
          Redirect(routes.Application.index).withSession(Security.username -> user._1)
      }
    )
  }

  def logout = Action {
    Redirect(routes.Application.index()).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }
}
