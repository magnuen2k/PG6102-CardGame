package no.kristiania.dto

import io.swagger.annotations.ApiModelProperty

class UserStatsDto (
        @ApiModelProperty("The id of the player")
        var userId: String? = null,

        @ApiModelProperty("How many victories the player had so far")
        var victories : Int? = null,

        @ApiModelProperty("How many defeats the player had so far")
        var defeats: Int? = null,

        @ApiModelProperty("How many draws the player had so far")
        var draws : Int? = null,

        @ApiModelProperty("The current score of the player")
        var score: Int? = null
)