package controllers

import models.{Photo, Photos}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
 * @author emiliocornejo
 * @version 01/05/15
 *          @(#)MapController.scala
 */
object MapController extends Controller {
  val photoForm = Form(
    mapping(
      "path" -> nonEmptyText,
      "place" -> nonEmptyText
    )(Photo.apply)(Photo.unapply)
  )
  def index = Action { implicit request =>
    Ok(views.html.map.index())
  }
  def addPoint = Action { implicit request =>
    Ok(views.html.map.addPoint(photoForm))
  }

  def savePoint = Action(parse.json) { implicit request =>
    (request.body \ "pt").asOpt[String].map { json =>
      println(json)
      Photos point_= json
      Redirect(routes.PhotosController.addMarker(json))
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }
}
