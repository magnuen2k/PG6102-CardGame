package no.kristiania.cardgame.usercollections.dto

import io.swagger.annotations.ApiModelProperty

data class PatchResultDto(

        @ApiModelProperty("List of card ids after a PATCH operation")
        val cardIds: MutableList<String> = mutableListOf()
)