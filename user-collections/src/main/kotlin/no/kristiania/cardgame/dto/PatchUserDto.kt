package no.kristiania.cardgame.dto

import io.swagger.annotations.ApiModelProperty

data class PatchUserDto(

        @ApiModelProperty("Command for patch operation")
        val command: String? = null,

        @ApiModelProperty("Optional card id for operation")
        val cardId: String? = null
)