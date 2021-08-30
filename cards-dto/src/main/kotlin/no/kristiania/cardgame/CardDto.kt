package no.kristiania.cardgame

import io.swagger.annotations.ApiModelProperty

class CardDto (
        @get:ApiModelProperty("Card Id")
        var cardId: String? = null,

        @get:ApiModelProperty("Name of the card")
        var name: String? = null,

        @get:ApiModelProperty("Description of the card")
        var description: String? = null,

        @get:ApiModelProperty("Rarity of the card")
        var rarity: Rarity? = null,

        @get:ApiModelProperty("Id of image to card")
        var imageId: String? = null)
