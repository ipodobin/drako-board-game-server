package org.drako
package action

import akka.actor.ActorRef

import java.util.UUID

case class Position(x: Int, y: Int, z: Int) {
}

case class Player(id: String = UUID.randomUUID.toString, username: String)

case class Spectator(id: String = UUID.randomUUID.toString, username: String)

case class PlayerWithActor(player: Player, actor: ActorRef)

case class SpectatorWithActor(spectator: Spectator, actor: ActorRef)