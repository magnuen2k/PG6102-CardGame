package no.kristiania.cardgame

import io.swagger.annotations.ApiModelProperty

class CardDto (
        @get:ApiModelProperty("")
        var cardId: String? = null,

        @get:ApiModelProperty()
        var name: String? = null,

        @get:ApiModelProperty("")
        var description: String? = null,

        @get:ApiModelProperty("")
        var rarity: Rarity? = null,

        @get:ApiModelProperty("")
        var imageId: String? = null)
