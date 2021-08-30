package no.kristiania.cardgame

import io.swagger.annotations.ApiModelProperty

class CollectionDto (

        @get:ApiModelProperty("List of cards in the collection")
        var cards: MutableList<CardDto> = mutableListOf(),

        @get:ApiModelProperty("Prices of the different rarities")
        var prices: MutableMap<Rarity, Int> = mutableMapOf(),

        @get:ApiModelProperty("Values for milling different rarities")
        var millValues: MutableMap<Rarity, Int> = mutableMapOf(),

        @get:ApiModelProperty("Probability for getting the different rarities")
        var rarityProbabilities: MutableMap<Rarity, Double> = mutableMapOf()
)