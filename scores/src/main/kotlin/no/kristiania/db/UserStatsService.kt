package no.kristiania.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Repository
interface UserStatsRepository : CrudRepository<UserStats, String>

@Service
@Transactional
class UserStatsService(
        val repository: UserStatsRepository,
        val em: EntityManager
) {

    fun createUser(userId: String) : Boolean {
        if(repository.existsById(userId)) {
            return false
        }

        val stats = UserStats(userId, 0, 0, 0, 0)
        repository.save(stats)
        return true
    }
}