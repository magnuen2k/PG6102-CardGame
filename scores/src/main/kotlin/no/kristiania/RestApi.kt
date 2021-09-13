package no.kristiania

import io.swagger.annotations.Api
import no.kristiania.db.UserStats
import no.kristiania.db.UserStatsRepository
import no.kristiania.db.UserStatsService
import no.kristiania.dto.UserStatsDto
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.TimeUnit

@Api(value = "/api/scores", description = "Scores and ranks of the players, based on their victories and defeats")
@RequestMapping(
        path = ["/api/scores"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)
@RestController
class RestApi (
        private val statsRepository: UserStatsRepository,
        private val statsService: UserStatsService
) {

    @PutMapping("/{userId}")
    fun createDefaultStats(@PathVariable userId: String): ResponseEntity<WrappedResponse<Void>> {
        val created = statsService.createUser(userId)
        return if(!created) RestResponseFactory.userFailure("User already exists", 400)
        else RestResponseFactory.noPayload(201);
    }

    @GetMapping("/{userId}")
    fun getUserStats(@PathVariable userId: String): ResponseEntity<WrappedResponse<UserStats>> {

        val user = statsRepository.findById(userId).orElse(null)

        if(userId == null) {
            return RestResponseFactory.notFound("User not found")
        }

        return RestResponseFactory.payload(200, user)
    }

    @GetMapping
    fun getAll(
            @RequestParam("keysetId", required = false)
            keysetId: String?,
            @RequestParam("keysetScore", required = false)
            keysetScore: Int?
    ): ResponseEntity<WrappedResponse<PageDto<UserStatsDto>>> {
        val page = PageDto<UserStatsDto>()

        val n = 10
        val scores = DtoConverter.transform(statsService.getNextPage(n, keysetId, keysetScore))
        page.list = scores

        if(scores.size == n) {
            val last = scores.last()
            page.next = "/api/scores?keysetId=${last.userId}&keysetScore=${last.score}"
        }

        return ResponseEntity
                .status(200)
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES).cachePublic())
                .body(WrappedResponse(200, page).validated())
    }
}