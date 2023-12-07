package csb.hf.sentimentibackend.data.repositories

import csb.hf.sentimentibackend.data.entities.Ticker
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface TickerRepository  : CrudRepository<Ticker,Int> {

    fun findByTicker(ticker: String) : Ticker?
}