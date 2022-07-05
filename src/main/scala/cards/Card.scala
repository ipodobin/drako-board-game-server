package org.drako
package cards

import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[DwarfMeleeAttack], name = "dwarf_melee_attack"),
    new JsonSubTypes.Type(value = classOf[DwarfDoubleMeleeAttack], name = "dwarf_double_melee_attack"),
    new JsonSubTypes.Type(value = classOf[DwarfMove], name = "dwarf_move"),
    new JsonSubTypes.Type(value = classOf[DwarfDoubleMove], name = "dwarf_double_move"),
    new JsonSubTypes.Type(value = classOf[DwarfDefense], name = "dwarf_defense"),
    new JsonSubTypes.Type(value = classOf[DwarfRangeAttack], name = "dwarf_range_attack"),
    new JsonSubTypes.Type(value = classOf[DwarfNetAttack], name = "dwarf_net_attack"),
    new JsonSubTypes.Type(value = classOf[DragonMove], name = "dragon_move"),
    new JsonSubTypes.Type(value = classOf[DragonMeleeAttack], name = "dragon_melee_attack"),
    new JsonSubTypes.Type(value = classOf[DragonDefense], name = "dragon_defense"),
    new JsonSubTypes.Type(value = classOf[DragonFly], name = "dragon_fly"),
    new JsonSubTypes.Type(value = classOf[DragonMove], name = "dragon_move"),
    new JsonSubTypes.Type(value = classOf[DragonFireAttack], name = "dragon_fire_attack")
  )
)
sealed trait Card extends Product with Serializable

sealed trait DwarfCard

sealed trait DragonCard

final case class DwarfMeleeAttack(damage: Int) extends Card with DwarfCard

final case class DwarfDoubleMeleeAttack(damage: Int) extends Card with DwarfCard

final case class DwarfMove(distance: Int) extends Card with DwarfCard

final case class DwarfDoubleMove(distance: Int) extends Card with DwarfCard

final case class DwarfDefense() extends Card with DwarfCard

final case class DwarfRangeAttack(damage: Int) extends Card with DwarfCard

final case class DwarfNetAttack() extends Card with DwarfCard

final case class DragonMove(distance: Int) extends Card with DragonCard

final case class DragonMeleeAttack(damage: Int) extends Card with DragonCard

final case class DragonDefense() extends Card with DragonCard

final case class DragonFly() extends Card with DragonCard

final case class DragonFireAttack(damage: Int) extends Card with DragonCard

object DwarfCard {
  def deck(): List[Card] = {
    List(
      DwarfMeleeAttack(1), DwarfMeleeAttack(1), DwarfMeleeAttack(1), DwarfMeleeAttack(1), DwarfMeleeAttack(1), //5
      DwarfMeleeAttack(2), DwarfMeleeAttack(2), //2
      DwarfDoubleMeleeAttack(1), DwarfDoubleMeleeAttack(1), //2
      DwarfDoubleMeleeAttack(2), DwarfDoubleMeleeAttack(2), //2
      DwarfMove(1), DwarfMove(1), DwarfMove(1), DwarfMove(1), DwarfMove(1), DwarfMove(1), //6
      DwarfMove(2), DwarfMove(2), DwarfMove(2), DwarfMove(2), DwarfMove(2), //5
      DwarfDoubleMove(1), DwarfDoubleMove(1), DwarfDoubleMove(1), //3
      DwarfDoubleMove(2), DwarfDoubleMove(2), //2
      DwarfDefense(), DwarfDefense(), DwarfDefense(), DwarfDefense(), DwarfDefense(), //5
      DwarfRangeAttack(1), DwarfRangeAttack(1), DwarfRangeAttack(1), //3
      DwarfRangeAttack(2), DwarfRangeAttack(2), //2
      DwarfNetAttack() //1
    ) //38
  }
}

object DragonCard {
  //  implicit val jsonFormats = List(
  //    jsonFormat1(DragonMeleeAttack.apply),
  //    jsonFormat1(DragonMove.apply),
  //    jsonFormat1(DragonFireAttack.apply),
  //    //    jsonFormat1(DragonFly.apply),
  //    //    jsonFormat1(DragonDefense.apply
  //  )

  def deck(): List[Card] = {
    List(
      DragonMeleeAttack(1), DragonMeleeAttack(1), DragonMeleeAttack(1), DragonMeleeAttack(1), DragonMeleeAttack(1), //5
      DragonMeleeAttack(2), DragonMeleeAttack(2), DragonMeleeAttack(2), DragonMeleeAttack(2), //4
      DragonFly(), DragonFly(), DragonFly(), DragonFly(), //4
      DragonMove(1), DragonMove(1), DragonMove(1), DragonMove(1), DragonMove(1), DragonMove(1), //6
      DragonMove(2), DragonMove(2), DragonMove(2), DragonMove(2), DragonMove(2), //5
      DragonFireAttack(1), DragonFireAttack(1), DragonFireAttack(1), DragonFireAttack(1), //4
      DragonFireAttack(2), DragonFireAttack(2), //2
      DragonDefense(), DragonDefense(), DragonDefense(), DragonDefense(), DragonDefense(), DragonDefense(), DragonDefense(), DragonDefense() //8
    ) //38
  }
}
