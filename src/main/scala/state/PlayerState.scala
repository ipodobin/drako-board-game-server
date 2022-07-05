package org.drako
package state

import actor.GameRole.GameRole
import actor.GameRoleType
import character.{DrakoInfo, DwarfsInfo}

import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

case class PlayerState(
                        @JsonScalaEnumeration(classOf[GameRoleType]) role: GameRole,
                        dwarfs: DwarfsInfo,
                        drako: DrakoInfo
                      )
