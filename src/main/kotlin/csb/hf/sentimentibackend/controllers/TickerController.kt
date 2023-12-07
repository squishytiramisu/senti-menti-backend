package csb.hf.sentimentibackend.controllers

import csb.hf.sentimentibackend.data.AggregatedNews
import csb.hf.sentimentibackend.data.DAO
import csb.hf.sentimentibackend.data.entities.News
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/ticker")
class TickerController (@Autowired private val dao: DAO,){

    @GetMapping("")
    fun getAllTickerNames(): List<String>{
        return dao.getAllTickers().map { it.ticker }
    }


}