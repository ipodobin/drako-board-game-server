package org.drako

import actor._

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.{Flow, GraphDSL, Merge, Sink, Source}
import akka.stream.{FlowShape, OverflowStrategy}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

case class DrakoGame(gameId: Int, actorSystem: ActorSystem) {

  private[this] val gameActor = actorSystem.actorOf(Props(classOf[DrakoGameActor], gameId))

  val playerActorSource: Source[GameEvent, ActorRef] = Source.actorRef[GameEvent](5, OverflowStrategy.fail)

  val mapper: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)

  def flow(player: String): Flow[Message, Message, _] = Flow.fromGraph(GraphDSL.create(playerActorSource) { implicit builder =>
    playerActor =>
      import GraphDSL.Implicits._

      val materialization = builder.materializedValue.map(playerActorRef => PlayerJoined(player, playerActorRef))
      val merge = builder.add(Merge[GameEvent](2))

      val messagesToGameEventsFlow = builder.add(Flow[Message].collect {
        case TextMessage.Strict(target) => PlayerMoveRequest(player, target)
      })

      val gameEventsToMessagesFlow = builder.add(Flow[GameEvent].map {
        case PlayersChanged(players) => TextMessage(mapper.writeValueAsString(players))
        case StartGame(state) =>
          val message = InitGameMessage(state)
          TextMessage(mapper.writeValueAsString(message))
      })

      val gameActorSink = Sink.actorRef[GameEvent](gameActor, PlayerLeft(player))

      materialization ~> merge ~> gameActorSink
      messagesToGameEventsFlow ~> merge

      playerActor ~> gameEventsToMessagesFlow

      FlowShape(messagesToGameEventsFlow.in, gameEventsToMessagesFlow.out)
  })
}

object DrakoGames {
  var games: Map[Int, DrakoGame] = Map.empty[Int, DrakoGame]

  def findOrCreate(gameId: Int)(implicit actorSystem: ActorSystem): DrakoGame = games.getOrElse(gameId, createDrakoGame(gameId))

  private def createDrakoGame(gameId: Int)(implicit actorSystem: ActorSystem): DrakoGame = {
    val game = DrakoGame(gameId, actorSystem)
    games += (gameId -> game)
    game
  }
}