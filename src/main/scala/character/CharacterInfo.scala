package org.drako
package character

import cards.Card

case class DrakoInfo(
                      var cards: List[Card],
                      cardNumber: Int,
                      dragon: Dragon
                    ) {
}

case class DwarfsInfo(
                       cards: List[Card],
                       cardNumber: Int,
                       warrior: DwarfWarrior,
                       crossbowman: DwarfCrossbowman,
                       netter: DwarfNetter
                     ) {

}
