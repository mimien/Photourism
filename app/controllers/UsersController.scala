package controllers

import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller, Security}

/**
 * @author emiliocornejo
 * @version 04/05/15
 *          @(#)UsersController.scala
 */
object UsersController extends Controller {
  val userForm = Form(
    mapping(
      "name" -> nonEmptyText(0, 100),
      "email" -> email,
      "passwords" -> tuple(
        "password" -> nonEmptyText(6),
        "repassword" -> nonEmptyText(6)
      ).verifying(// Add an additional constraint: both passwords must match
          "Passwords don't match", data => {
        println(data._1 + " " + data._2)
        data._1 == data._2
      }
        ),
      "bio" -> text(0, 100)
    )(User.apply)(User.unapply)
  )

  def signup = Action { implicit request =>
    Ok(views.html.users.signup(userForm))
  }

  def save = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
//        println(formWithErrors("email").error.map {
//          error => error.me
//        })
        println(formWithErrors.errorsAsJson)
        BadRequest(views.html.users.signup(formWithErrors)
          (request2flash(request) + ("error", "Please correct errors above.")))
      }
      ,
      user => Redirect(routes.Application.index).withSession(Security.username -> user.email)
    )
  }
}
