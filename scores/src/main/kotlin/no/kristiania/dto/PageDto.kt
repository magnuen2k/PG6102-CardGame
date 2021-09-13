package no.kristiania.dto

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

class PageDto<T> (
        @ApiModelProperty("The data contained in the page")
        @NotNull
        var list: List<T> = listOf(),

        @ApiModelProperty("Link to the next page")
        var next: String? = null
)