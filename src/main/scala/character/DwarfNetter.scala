package org.drako
package character

import action.Position

case class DwarfNetter(
                        var health: Int,
                        maxHealth: Int,
                        var position: Position,
                        var alive: Boolean
                      ) {

}
