package org.drako
package actor

import action.{Player, PlayerWithActor, Spectator, SpectatorWithActor}
import state.{GameState, PlayerState}

import akka.actor.{Actor, ActorRef}


class DrakoGameActor(gameId: Int) extends Actor {

  val players = collection.mutable.LinkedHashMap[String, PlayerWithActor]()
  val spectators = collection.mutable.LinkedHashMap[String, SpectatorWithActor]()
  var dwarfPlayer: Option[String] = None
  var dragonPlayer: Option[String] = None
  var gameState: GameState = GameState.initialState()

  override def receive: Receive = {
    case PlayerJoined(name, actorRef) =>
      if (players.size == 2) {
        println(s"User $name cannot join game, already two players. Joining as spectator.")
        val newSpectator = Spectator(username = name)
        spectators += (name -> SpectatorWithActor(newSpectator, actorRef))
      } else {
        val newPlayer = Player(username = name)
        players += (name -> PlayerWithActor(newPlayer, actorRef))
        notifyPlayersChanged()
        if (players.size == 2) {
          println(s"Begin game. Two players joined.")
          val shuffledPlayers = scala.util.Random.shuffle(players.keys)
          dwarfPlayer = Some(shuffledPlayers.head)
          println(s"User $dwarfPlayer will play as dwarfs.")
          dragonPlayer = Some(shuffledPlayers.drop(1).head)
          println(s"User $dragonPlayer will play as dragon.")
          for {
            dwarfActor <- players.get(dwarfPlayer.get)
            dragonActor <- players.get(dragonPlayer.get)
          } yield {
            initPlayerState(
              dwarfActor.actor,
              GameState.toDwarfsPlayerState(gameState)
            )
            initPlayerState(
              dragonActor.actor,
              GameState.toDrakoPlayerState(gameState)
            )
          }
        }
      }
    case PlayerLeft(name) =>
      println(s"User $name left game.")
      for (player <- dwarfPlayer if name == player) {
        println("Dwarf player left, game ended.")
        // reset game
        dwarfPlayer = None
        dragonPlayer = None
        players -= name
      }
      for (player <- dragonPlayer if name == player) {
        println("Dragon player left, game ended.")
        // reset game
        dwarfPlayer = None
        dragonPlayer = None
        players -= name
      }
      spectators -= name
    case PlayerMoveRequest(player, target) =>
      println(s"Player $player moved to $target.")
  }

  def notifyPlayersChanged(): Unit = players.values.foreach(_.actor ! PlayersChanged(players.values.map(_.player)))

  def initPlayerState(actorRef: ActorRef, state: PlayerState): Unit = actorRef ! StartGame(state)

}