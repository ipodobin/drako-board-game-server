package org.drako
package actor

import state.PlayerState

trait GameMessage {
  val _type: String
}

case class InitGameMessage(state: PlayerState) extends GameMessage {
  override val _type: String = "init"
}
