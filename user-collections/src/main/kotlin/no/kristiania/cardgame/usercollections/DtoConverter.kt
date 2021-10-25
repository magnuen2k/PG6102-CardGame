package no.kristiania.cardgame.usercollections

import no.kristiania.cardgame.usercollections.db.CardCopy
import no.kristiania.cardgame.usercollections.db.User
import no.kristiania.cardgame.usercollections.dto.CardCopyDto
import no.kristiania.cardgame.usercollections.dto.UserDto

object DtoConverter {

    fun transform(user: User): UserDto {
        return UserDto().apply{
            userId = user.userId
            coins = user.coins
            cardPacks = user.cardPacks
            ownedCards = user.ownedCards
        }
    }

    fun transform(cardCopy: CardCopy): CardCopyDto {
        return CardCopyDto().apply {
            cardId = cardCopy.cardId
            numberOfCopies = cardCopy.numberOfCopies
        }
    }
}