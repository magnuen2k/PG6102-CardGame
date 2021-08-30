package no.kristiania.cardgame

import io.swagger.annotations.ApiModelProperty

class CollectionDto (

        @get:ApiModelProperty("")
        var cards: MutableList<CardDto> = mutableListOf(),

        @get:ApiModelProperty("")
        var prices: MutableMap<Rarity, Int> = mutableMapOf(),

        @get:ApiModelProperty("")
        var millValues: MutableMap<Rarity, Int> = mutableMapOf(),

        @get:ApiModelProperty("")
        var rarityProbabilities: MutableMap<Rarity, Double> = mutableMapOf()
)