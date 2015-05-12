package controllers

import controllers.UsersController._
import controllers.routes
import models.Users
import play.api.Routes
import play.api.mvc.{Security, Action, EssentialAction, Controller}
import play.core.Router.JavascriptReverseRoute
import routes.javascript.MapController._
import routes.javascript.PhotosController.all

/**
 * @author emiliocornejo
 * @version 01/05/15
 *          @(#)JavascriptRoutes.scala
 */
object JavascriptRoute extends Controller{
  /* Application related JavascriptReverse Route will goes here */
  val appRoutes: List[JavascriptReverseRoute] = List(index, savePoint, all)

  /* All JavascriptReverse Route will combine here */
  val javascriptRouters = appRoutes

  /**
   * This is use to generate JavascriptReverseRoute for all provided actions
   *
   * @return
   */


  def save = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.users.signup(formWithErrors)
        (request2flash(request) +("error", "Please correct errors above."))),
      photo => {
        println(photo.toString)
        Redirect(routes.Application.index)
      }
    )
  }
  def javascriptRoutes: EssentialAction = Action { implicit request =>
    Ok(Routes.javascriptRouter("jsRoutes")(javascriptRouters: _*)).as("text/javascript")
  }
}
