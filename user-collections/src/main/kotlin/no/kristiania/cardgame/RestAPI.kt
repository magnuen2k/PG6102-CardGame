package no.kristiania.cardgame

import io.swagger.annotations.ApiOperation
import no.kristiania.cardgame.db.UserService
import no.kristiania.cardgame.dto.CardCommand
import no.kristiania.cardgame.dto.PatchResultDto
import no.kristiania.cardgame.dto.PatchUserDto
import no.kristiania.cardgame.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-collections")
class RestAPI (private val userService: UserService) {

    @ApiOperation("Getting information about a user")
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: String) : ResponseEntity<UserDto?> {

        val user = userService.findByIdEager(id)

        if(user == null) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.status(200).body(DtoConverter.transform(user))
    }

    @ApiOperation("Create a new user with given Id")
    @PutMapping("/{id}")
    fun createUser(@PathVariable("id") id: String): ResponseEntity<Void> {
        val user = userService.registerNewUser(id)

        if(!user) return ResponseEntity.status(400).build()

        return ResponseEntity.status(201).build()
    }

    @ApiOperation("Update a users collection")
    @PatchMapping(
            path = ["/{userId}"],
            consumes = [(MediaType.APPLICATION_JSON_VALUE)]
    )
    fun patchCollection(@PathVariable("userId") userId: String, @RequestBody patchUserDto: PatchUserDto) : ResponseEntity<PatchResultDto> {

        if(patchUserDto.command == null) {
            return ResponseEntity.status(400).build()
        }

        when (patchUserDto.command) {
            CardCommand.OPEN_PACK -> {
                // Open a pack
                val cards = try {
                    userService.openPack(userId)
                } catch (e: IllegalArgumentException) {
                    return ResponseEntity.status(400).build()
                }

                return ResponseEntity.status(200).body(PatchResultDto().apply{cardIds.addAll(cards)})
            }
            CardCommand.MILL_CARD -> {
                // Make sure cardId is filled out
                val cardId = patchUserDto.cardId ?: return ResponseEntity.status(400).build()

                // Mill a card
                try {
                    userService.millCard(userId, cardId)
                } catch(e: IllegalArgumentException) {
                    return ResponseEntity.status(400).build()
                }

                return ResponseEntity.status(200).body(PatchResultDto())
            }
            CardCommand.BUY_CARD -> {
                // Make sure cardId is filled out
                val cardId = patchUserDto.cardId ?: return ResponseEntity.status(400).build()

                // Buy a card
                try {
                    userService.buyCard(userId, cardId)
                } catch(e: IllegalArgumentException) {
                    return ResponseEntity.status(400).build()
                }

                return ResponseEntity.status(200).body(PatchResultDto())
            }
        }
    }
}