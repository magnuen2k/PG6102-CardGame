package no.kristiania

import no.kristiania.db.UserStats
import no.kristiania.dto.UserStatsDto

object DtoConverter {
    fun transform(stats: UserStats) : UserStatsDto = stats.run {
        UserStatsDto(userId, victories, defeats, draws, score)
    }

    fun transform(scores: Iterable<UserStats>) : List<UserStatsDto> = scores.map { transform(it) }
}