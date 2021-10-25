package no.kristiania.cardgame.usercollections.dto

import io.swagger.annotations.ApiModelProperty

enum class CardCommand {
        OPEN_PACK, MILL_CARD, BUY_CARD
}

data class PatchUserDto(

        @ApiModelProperty("Command for patch operation")
        val command: CardCommand? = null,

        @ApiModelProperty("Optional card id for operation")
        val cardId: String? = null
)