package no.kristiania.cardgame.controllers

import no.kristiania.cardgame.db.User
import no.kristiania.cardgame.db.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (private val userRepository: UserRepository) {

    @GetMapping("")
    fun getUsers() : ResponseEntity<MutableIterable<User>> {
        return ResponseEntity.ok(userRepository.findAll())
    }
}