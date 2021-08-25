package no.kristiania.cardgame.controllers

import no.kristiania.cardgame.User
import no.kristiania.cardgame.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (private val userRepository: UserRepository) {

    @GetMapping("")
    fun getUsers() : MutableIterable<User> {
        return userRepository.findAll()
    }
}