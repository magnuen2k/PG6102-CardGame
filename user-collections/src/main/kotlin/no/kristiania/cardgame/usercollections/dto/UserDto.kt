package no.kristiania.cardgame.usercollections.dto

import io.swagger.annotations.ApiModelProperty
import no.kristiania.cardgame.usercollections.db.CardCopy

data class UserDto(
        @get:ApiModelProperty("")
        var userId: String? = null,

        @get:ApiModelProperty("")
        var coins: Int = 0,

        @get:ApiModelProperty("")
        var cardPacks: Int = 0,

        @get:ApiModelProperty("")
        var ownedCards: MutableList<CardCopy> = mutableListOf()
)