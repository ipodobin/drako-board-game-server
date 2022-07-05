package org.drako

import akka.actor._
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.Flow

import scala.io.StdIn

object Server extends App with SLF4JLogging {
  implicit val actorSystem: ActorSystem = ActorSystem("drako-board-game")

  val interface = "localhost"
  val port = 8080

  import akka.http.scaladsl.server.Directives._

  val route =
//    pathEndOrSingleSlash {
//      get {
//        getFromResource("public/index.html")
//      }
//    } ~
      pathPrefix("game" / IntNumber) { gameId =>
        parameter("username") { username =>
          get {
            handleWebSocketMessages(DrakoGames.findOrCreate(gameId).flow(username))
          }
        }
      }


  val echoService: Flow[Message, Message, _] = Flow[Message].map {
    case TextMessage.Strict(txt) => TextMessage("ECHO: " + txt)
    case _ => TextMessage("Message type unsupported")
  }

  val binding = Http().newServerAt(interface, port).bind(route)
  println(s"Server is now online at http://$interface:$port\nPress RETURN to stop...")
  StdIn.readLine()

  import actorSystem.dispatcher

  binding.flatMap(_.unbind()).onComplete(_ => actorSystem.terminate())
  println("Server is down...")
}
