package controllers

import controllers.Application._
import controllers.routes
import play.api.mvc.{Action, Controller}

/**
 * @author emiliocornejo
 * @version 01/05/15
 *          @(#)MapController.scala
 */
object MapController extends Controller {
  def index = Action {
    Ok(views.html.map.index())
  }
  def addPoint = Action { implicit request =>
    Ok(views.html.map.addPoint())
  }

  def savePoint = Action(parse.json) { implicit request =>
    (request.body \ "pt").asOpt[String].map { json =>
      println("hola")
      println(json)
      Redirect(routes.PhotosController.add(json))
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }
}
