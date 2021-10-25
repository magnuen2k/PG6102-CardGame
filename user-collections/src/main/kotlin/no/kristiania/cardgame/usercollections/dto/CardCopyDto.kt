package no.kristiania.cardgame.usercollections.dto

import io.swagger.annotations.ApiModelProperty
import no.kristiania.cardgame.usercollections.db.User

data class CardCopyDto(
        @get:ApiModelProperty("")
        var id: Long? = null,

        @get:ApiModelProperty("")
        var user: User? = null,

        @get:ApiModelProperty("")
        var cardId: String? = null,

        @get:ApiModelProperty("")
        var numberOfCopies: Int = 0
)