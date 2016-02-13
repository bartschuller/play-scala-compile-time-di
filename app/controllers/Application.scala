package controllers

import play.api._
import play.api.mvc._
import environment.Env

class Application(env: Env) extends Controller {
  import env._

  def index = Action {
    Ok(views.html.index(s"Your new application is ready. Secret word is ${myService.secretWord}"))
  }

}
