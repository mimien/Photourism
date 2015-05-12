package controllers

import models.{Photo, Photos}
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


//  def save = Action(parse.multipartFormData) { implicit request =>
//
////    println("hola")
////    val ph : Option[Photo] = photoForm.bindFromRequest().fold (
////      errFrm => {
////        println(errFrm.errorsAsJson)
////        None
////      },
////      Photo => Some(Photo)
////    )
////    println(ph.get.place)
//
//    request.body.file("picture").map { file =>
//      ph.map { photo =>
//        val filename = file.filename
//        println(file.filename)
//        val contentType = file.contentType
//        file.ref.moveTo(new File(s"/tmp/picture/$filename"))
//        Ok("File uploaded")
//      }.getOrElse{
//        BadRequest("Form binding error.")
//      }
//    }.getOrElse {
//      BadRequest("File not attached.")
//    }
//    Redirect("/world")
//  }

  def add(name: String) = Action {
    Ok(views.html.photo.addPhoto(photoForm, name))
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      import java.io.File
      val filename = picture.filename
      val contentType = picture.contentType
      val path = s"public/images/pictures/$filename"
      picture.ref.moveTo(new File(path))
      Redirect(routes.PhotosController.add(filename))
    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file")
    }
  }


  def save = Action { implicit request =>

    photoForm.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors.errorsAsJson)
        BadRequest(views.html.photo.addPhoto(formWithErrors, ""))
      },
      photo => {
        println(photo)
        val username = request.session.get("username").get
        Photos.add(photo, username)
        Redirect(routes.Application.index)
      }
    )
  }


  def all = Action { implicit request =>
    Ok(Photos.all)
  }
}
