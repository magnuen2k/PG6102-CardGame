package no.kristiania.cardgame.usercollections

import no.kristiania.cardgame.usercollections.db.UserService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MOMListener(
    private val userService: UserService
) {

    companion object{
        private val log = LoggerFactory.getLogger(MOMListener::class.java)
    }


    @RabbitListener(queues = ["#{queue.name}"])
    fun receiveFromAMQP(userId: String) {

        val ok = userService.registerNewUser(userId)
        if(ok){
            log.info("Registered new user via MOM: $userId")
        }
    }
}