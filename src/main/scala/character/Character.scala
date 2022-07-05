package org.drako
package character

trait Character {

  def setPosition(x: Int, y: Int, z: Int): Unit

  def getHealth: Int

  def getMaxHealth: Int
}
