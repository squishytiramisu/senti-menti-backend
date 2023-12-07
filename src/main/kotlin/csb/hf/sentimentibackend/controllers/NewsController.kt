package csb.hf.sentimentibackend.controllers

import csb.hf.sentimentibackend.data.AggregatedNews
import csb.hf.sentimentibackend.data.DAO
import csb.hf.sentimentibackend.data.entities.News
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/news")
class NewsController (@Autowired private val dao: DAO,){

    @GetMapping("headlines")
    fun getHeadlines(): List<AggregatedNews>{
        // Predicate to get all news
        return dao.getHeadlines { true }
    }

    @GetMapping("ticker/{ticker}")
    fun getNewsForTicker(@PathVariable ticker: String): List<News>{
        return dao.getNewsForTicker(ticker)
    }

    @GetMapping("{username}")
    fun getHeadlinesForUser(@PathVariable username: String): List<AggregatedNews>{
        return dao.getHeadlines{ news -> news.ticker?.users?.any { user -> user.userName == username } ?: false }
    }

}