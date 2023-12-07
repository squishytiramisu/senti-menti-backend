package csb.hf.sentimentibackend.data.repositories

import csb.hf.sentimentibackend.data.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository  : CrudRepository<User,Int> {

    fun findByUserName(s : String): User?

}