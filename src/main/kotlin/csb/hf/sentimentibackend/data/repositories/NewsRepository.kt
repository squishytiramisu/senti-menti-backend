package csb.hf.sentimentibackend.data.repositories

import csb.hf.sentimentibackend.data.entities.News
import org.springframework.data.repository.CrudRepository

interface NewsRepository : CrudRepository<News,Int> {


    fun findByDescription(description: String): News?

}