package controllers

import play.api.mvc._
import Auth.loginHtml
object Application extends Controller {

  def index = Action { implicit request =>

    request.session.get(Security.username).map { user =>
      Ok(views.html.map.index())
    }.getOrElse {
      Ok(views.html.index(loginHtml))
    }
  }

}