package org.drako
package actor

import action.Player
import state.PlayerState

import akka.actor.ActorRef

trait GameEvent

case class PlayerJoined(player: String, actor: ActorRef) extends GameEvent

case class PlayerLeft(player: String) extends GameEvent

case class PlayerMoveRequest(player: String, target: String) extends GameEvent

case class PlayersChanged(players: Iterable[Player]) extends GameEvent

case class StartGame(state: PlayerState) extends GameEvent