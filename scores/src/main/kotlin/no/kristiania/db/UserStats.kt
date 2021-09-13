package no.kristiania.db

import io.swagger.annotations.ApiModelProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class UserStats(

    @ApiModelProperty("User id")
    @Id
    @NotBlank
    val userId: String? = null,

    @ApiModelProperty("Victories for user")
    @NotNull
    @Min(0)
    val victories: Int = 0,

    @ApiModelProperty("Defeats for user")
    @NotNull
    @Min(0)
    val defeats: Int = 0,

    @ApiModelProperty("Draws")
    @NotNull
    @Min(0)
    val draws: Int = 0,

    @ApiModelProperty("Score")
    @NotNull
    @Min(0)
    val score: Int = 0
)
