package controllers

import models.Photo
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

/**
 * @author emiliocornejo
 * @version 30/04/15
 *          @(#)PhotoController.scala
 */
object PhotosController extends Controller {
  val photoForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "id" -> optional(number)
    )(Photo.apply)(Photo.unapply)
  )

  def add(marker: String) = Action {
    Ok("hola")
  }
}
