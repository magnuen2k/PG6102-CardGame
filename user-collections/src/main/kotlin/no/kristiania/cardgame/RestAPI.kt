package no.kristiania.cardgame

import io.swagger.annotations.ApiOperation
import no.kristiania.cardgame.db.UserService
import no.kristiania.cardgame.dto.PatchResultDto
import no.kristiania.cardgame.dto.PatchUserDto
import no.kristiania.cardgame.dto.UserDto
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
    @PatchMapping("/{userId}")
    fun patchCollection(@PathVariable("userId") userId: String, @RequestBody patchUserDto: PatchUserDto) : ResponseEntity<PatchResultDto> {

        if(patchUserDto.command == "OPEN_PACK") {
            // Open a pack
            val cards = try {
                userService.openPack(userId)
            } catch (e: IllegalArgumentException) {
                return ResponseEntity.status(400).build()
            }
            return ResponseEntity.status(200).body(PatchResultDto().apply{cardIds.addAll(cards)})
        } else if(patchUserDto.command == "MILL_CARD") {
            // Mill a card
        } else if (patchUserDto.command == "BUY_CARD") {
            // Buy a card
        }

        return ResponseEntity.status(200).build()
    }
}