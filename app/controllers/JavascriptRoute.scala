package controllers

import controllers.routes
import play.api.Routes
import play.api.mvc.{Action, EssentialAction, Controller}
import play.core.Router.JavascriptReverseRoute
import routes.javascript.MapController._
import routes.javascript.PhotosController.add

/**
 * @author emiliocornejo
 * @version 01/05/15
 *          @(#)JavascriptRoutes.scala
 */
object JavascriptRoute extends Controller{
  /* Application related JavascriptReverse Route will goes here */
  val appRoutes: List[JavascriptReverseRoute] = List(index, savePoint, add)

  /* All JavascriptReverse Route will combine here */
  val javascriptRouters = appRoutes

  /**
   * This is use to generate JavascriptReverseRoute for all provided actions
   *
   * @return
   */
  def javascriptRoutes: EssentialAction = Action { implicit request =>
    Ok(Routes.javascriptRouter("jsRoutes")(javascriptRouters: _*)).as("text/javascript")
  }
}
