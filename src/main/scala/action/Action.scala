package org.drako
package action

sealed trait Action

case object Move extends Action
case object MeleeAttack extends Action
case object FireAttack extends Action
case object RangeAttack extends Action
