import akka.actor.ActorSystem
import play.api.ApplicationLoader.Context
import play.api.libs.ws.WSAPI
import play.api.libs.ws.ning.NingWSComponents
import play.api.{Configuration, Application, ApplicationLoader, BuiltInComponentsFromContext}
import router.Routes
import service.{MyDefaultService, MyService}

class MyApplicationLoader extends ApplicationLoader {
  override def load(context: Context): Application = {
    new environment.ApplicationEnv(context).application
  }
}
package object environment {
  // Environment that contains useful services
  class Env(_application: => Application, val configuration: Configuration, val ws: WSAPI, val akka: ActorSystem, val myService: MyService) {
    def application = _application
  }

  class ApplicationEnv(context: Context)
    extends BuiltInComponentsFromContext(context)
      with NingWSComponents {

    lazy val env = new Env(application, configuration, wsApi, actorSystem, MyDefaultService("geheim"))

    // an injected instance of the application controller
    lazy val applicationController = new controllers.Application(env)
    lazy val assets = new controllers.Assets(httpErrorHandler)

    // routes using injected controllers
    lazy val router = new Routes(httpErrorHandler, applicationController, assets)
  }
}
