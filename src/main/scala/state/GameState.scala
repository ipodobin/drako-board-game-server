package org.drako
package state

import action.Position
import actor.GameRole
import cards.{Card, DragonCard, DwarfCard}
import character._

import scala.util.Random.shuffle

case class GameState(
                      dwarfCardDeck: List[Card],
                      dragonCardDeck: List[Card],
                      dwarfs: DwarfsInfo,
                      drako: DrakoInfo
                    ) {

}

object GameState {
  def initialState(): GameState = {
    val dwarfCardDeck: List[Card] = shuffle(DwarfCard.deck())
    val dragonCardDeck: List[Card] = shuffle(DragonCard.deck())
    val dwarfs: DwarfsInfo = DwarfsInfo(
      dwarfCardDeck.take(4),
      4,
      DwarfWarrior(4, 4, Position(-2, 0, 2), true),
      DwarfCrossbowman(4, 4, Position(2, -2, 0), true),
      DwarfNetter(4, 4, Position(0, 2, -2), true)
    )
    val drako: DrakoInfo = DrakoInfo(
      dwarfCardDeck.take(4),
      4,
      Dragon(4, 4, Position(0, 0, 0), true)
    )
    GameState(dwarfCardDeck.drop(4), dragonCardDeck.drop(4), dwarfs, drako)
  }

  def toDwarfsPlayerState(gameState: GameState): PlayerState = {
    PlayerState(
      GameRole.Dwarfs,
      gameState.dwarfs,
      gameState.drako.copy(cards = Nil)
    )
  }

  def toDrakoPlayerState(gameState: GameState): PlayerState = {
    PlayerState(
      GameRole.Dragon,
      gameState.dwarfs.copy(cards = Nil),
      gameState.drako
    )
  }
}
