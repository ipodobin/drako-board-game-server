package org.drako

import akka.actor.ActorSystem
import akka.http.javadsl.model.ws.BinaryMessage
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.Timeout

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class GameService extends Directives {

  implicit val timeout = Timeout(5 seconds)
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def greeter: Flow[Message, Message, Any] =
    Flow[Message].mapConcat {
      case tm: TextMessage =>
        TextMessage(Source.single("Hello ") ++ tm.textStream ++ Source.single("!")) :: Nil
      case bm: BinaryMessage =>
        // ignore binary messages but drain content to avoid the stream being clogged
//        bm.dataStream.runWith(Sink.ignore)
        Nil
    }

  val websocketRoute =
    path("greeter") {
      handleWebSocketMessages(greeter)
    }
}
