package controllers

import java.io.File

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
      "path" -> nonEmptyText,
      "place" -> nonEmptyText
    )(Photo.apply)(Photo.unapply)
  )

  def addMarker(marker: String) = Action {
    Ok("hola")
  }
  def add = Action { implicit request =>
    Ok(views.html.photo.addPhoto(photoForm))
  }


  def save = Action(parse.multipartFormData) { implicit request =>
    println("hola")
    val ph : Option[Photo] = photoForm.bindFromRequest().fold (
      errFrm => {
        println(errFrm.errorsAsJson)
        None
      },
      Photo => Some(Photo)
    )
    println(ph.get.place)

    request.body.file("picture").map { file =>
      ph.map { photo =>
        val filename = file.filename
        println(file.filename)
        val contentType = file.contentType
        file.ref.moveTo(new File(s"/tmp/picture/$filename"))
        Ok("File uploaded")
      }.getOrElse{
        BadRequest("Form binding error.")
      }
    }.getOrElse {
      BadRequest("File not attached.")
    }
    Redirect("/world")
  }
}
