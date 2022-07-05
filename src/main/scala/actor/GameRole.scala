package org.drako
package actor

import com.fasterxml.jackson.core.`type`.TypeReference

object GameRole extends Enumeration {
  type GameRole = Value
  val Dwarfs, Dragon = Value
}
class GameRoleType extends TypeReference[GameRole.type]
