package org.drako
package character

import action.Position

case class DwarfWarrior(
                         var health: Int,
                         maxHealth: Int,
                         var position: Position,
                         var alive: Boolean
                       ) {
//  override val getHealth: Int = health
//  override val getMaxHealth: Int = maxHealth
//
//  override def setPosition(x: Int, y: Int, z: Int): Unit = position = Position(x, y, z)

}
