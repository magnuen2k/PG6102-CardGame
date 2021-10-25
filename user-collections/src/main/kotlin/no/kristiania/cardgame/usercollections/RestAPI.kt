package no.kristiania.cardgame.usercollections

import io.swagger.annotations.ApiOperation
import no.kristiania.RestResponseFactory
import no.kristiania.WrappedResponse
import no.kristiania.cardgame.usercollections.db.UserService
import no.kristiania.cardgame.usercollections.dto.CardCommand
import no.kristiania.cardgame.usercollections.dto.PatchResultDto
import no.kristiania.cardgame.usercollections.dto.PatchUserDto
import no.kristiania.cardgame.usercollections.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-collections")
class RestAPI (private val userService: UserService) {

    @ApiOperation("Getting information about a user")
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: String) : ResponseEntity<WrappedResponse<UserDto>> {

        val user = userService.findByIdEager(id)

        if(user == null) {
            return RestResponseFactory.notFound("User $id not found")
            // return ResponseEntity.notFound().build()
        }

        return RestResponseFactory.payload(200, DtoConverter.transform(user))
    }

    @ApiOperation("Create a new user with given Id")
    @PutMapping("/{id}")
    fun createUser(@PathVariable("id") id: String): ResponseEntity<WrappedResponse<Void>> {
        val user = userService.registerNewUser(id)

        if(!user) return RestResponseFactory.userFailure("User already exists")

        return RestResponseFactory.noPayload(201)
    }

    @ApiOperation("Update a users collection")
    @PatchMapping(
            path = ["/{userId}"],
            consumes = [(MediaType.APPLICATION_JSON_VALUE)]
    )
    fun patchCollection(@PathVariable("userId") userId: String, @RequestBody patchUserDto: PatchUserDto) : ResponseEntity<WrappedResponse<PatchResultDto>> {

        if(patchUserDto.command == null) {
            return RestResponseFactory.userFailure("NO command", 400)
        }

        when (patchUserDto.command) {
            CardCommand.OPEN_PACK -> {
                // Open a pack
                val cards = try {
                    userService.openPack(userId)
                } catch (e: IllegalArgumentException) {
                    return RestResponseFactory.userFailure("Wrong input", 400)
                }

                return RestResponseFactory.payload(200, PatchResultDto().apply{cardIds.addAll(cards)})
            }
            CardCommand.MILL_CARD -> {
                // Make sure cardId is filled out
                val cardId = patchUserDto.cardId ?: return ResponseEntity.status(400).build()

                // Mill a card
                try {
                    userService.millCard(userId, cardId)
                } catch(e: IllegalArgumentException) {
                    return RestResponseFactory.userFailure("Wrong input", 400)
                }

                return RestResponseFactory.payload(200, PatchResultDto())
            }
            CardCommand.BUY_CARD -> {
                // Make sure cardId is filled out
                val cardId = patchUserDto.cardId ?: return ResponseEntity.status(400).build()

                // Buy a card
                try {
                    userService.buyCard(userId, cardId)
                } catch(e: IllegalArgumentException) {
                    return RestResponseFactory.userFailure("Wrong input", 400)
                }

                return RestResponseFactory.payload(200, PatchResultDto())
            }
        }
    }
}