package no.kristiania

import no.kristiania.cardgame.CardDto
import no.kristiania.cardgame.CollectionDto
import no.kristiania.cardgame.Rarity

object CardCollection {

    fun get() : CollectionDto {

        val dto = CollectionDto()

        dto.prices.run {
            put(Rarity.BRONZE, 100)
            put(Rarity.SILVER, 500)
            put(Rarity.GOLD,   1_000)
            put(Rarity.PINK_DIAMOND, 100_000)
        }

        dto.prices.forEach { dto.millValues[it.key] = it.value / 4 }

        dto.rarityProbabilities.run {
            put(Rarity.SILVER, 0.10)
            put(Rarity.GOLD, 0.01)
            put(Rarity.PINK_DIAMOND, 0.001)
            put(Rarity.BRONZE, 1 - get(Rarity.SILVER)!! - get(Rarity.GOLD)!! - get(Rarity.PINK_DIAMOND)!!)
        }

        addCards(dto)

        return dto
    }

    private fun addCards(dto: CollectionDto){

        dto.cards.run {
            add(CardDto("c000", "Green Mold", "lore ipsum", Rarity.BRONZE, "035-monster.svg"))
            add(CardDto("c001", "Opera Singer", "lore ipsum", Rarity.BRONZE, "056-monster.svg"))
            add(CardDto("c002", "Not Stitch", "lore ipsum", Rarity.BRONZE, "070-monster.svg"))
            add(CardDto("c003", "Assault Hamster", "lore ipsum", Rarity.BRONZE, "100-monster.svg"))
            add(CardDto("c004", "WTF?!?", "lore ipsum", Rarity.BRONZE, "075-monster.svg"))
            add(CardDto("c005", "Stupid Lump", "lore ipsum", Rarity.BRONZE, "055-monster.svg"))
            add(CardDto("c006", "Sad Farter", "lore ipsum", Rarity.BRONZE, "063-monster.svg"))
            add(CardDto("c007", "Smelly Tainter", "lore ipsum", Rarity.BRONZE, "050-monster.svg"))
            add(CardDto("c008", "Hårboll", "lore ipsum", Rarity.BRONZE, "019-monster.svg"))
            add(CardDto("c009", "Blue Horned", "lore ipsum", Rarity.BRONZE, "006-monster.svg"))
            add(CardDto("c010", "Børje McTrumf", "lore ipsum", Rarity.SILVER, "081-monster.svg"))
            add(CardDto("c011", "Exa Nopass", "lore ipsum", Rarity.SILVER, "057-monster.svg"))
            add(CardDto("c012", "Dick Tracy", "lore ipsum", Rarity.SILVER, "028-monster.svg"))
            add(CardDto("c013", "Marius Mario", "lore ipsum", Rarity.SILVER, "032-monster.svg"))
            add(CardDto("c014", "Patrick Stew", "lore ipsum", Rarity.SILVER, "002-monster.svg"))
            add(CardDto("c015", "Fluffy The Hugger of Death", "lore ipsum", Rarity.GOLD, "036-monster.svg"))
            add(CardDto("c016", "Gary The Wise", "lore ipsum", Rarity.GOLD, "064-monster.svg"))
            add(CardDto("c017", "Grump-Grump The Grumpy One", "lore ipsum", Rarity.GOLD, "044-monster.svg"))
            add(CardDto("c018", "Bert-ho-met The Polite Guy", "lore ipsum", Rarity.GOLD, "041-monster.svg"))
            add(CardDto("c019", "Bengt The Destroyer", "lore ipsum", Rarity.PINK_DIAMOND, "051-monster.svg"))
        }

        assert(dto.cards.size == dto.cards.map { it.cardId }.toSet().size)
        assert(dto.cards.size == dto.cards.map { it.name }.toSet().size)
        assert(dto.cards.size == dto.cards.map { it.imageId }.toSet().size)
    }

}