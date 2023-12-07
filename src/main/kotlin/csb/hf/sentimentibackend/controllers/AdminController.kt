package csb.hf.sentimentibackend.controllers

import csb.hf.sentimentibackend.apis.FinanceNews
import csb.hf.sentimentibackend.apis.NewsApiEnum
import csb.hf.sentimentibackend.apis.gpt.AnalyzeNews
import csb.hf.sentimentibackend.data.DAO
import csb.hf.sentimentibackend.jobs.NewsJob
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/admin")
class AdminController (@Autowired private val dao: DAO,
                       @Autowired private val financeNews: FinanceNews,
                       @Autowired private val analyzeNews: AnalyzeNews,
                       @Autowired private val newsJob: NewsJob){

    @PostMapping("initializeDatabase")
    fun initDataBase(): ResponseEntity<String>{
        dao.createDummyDB()
        return ResponseEntity("Database initialized", HttpStatus.OK)
    }

    @PostMapping("collectNews/{apiVersion}")
    fun collectNews(@PathVariable apiVersion: String): ResponseEntity<String>{
        val newsApiEnum = NewsApiEnum.fromString(apiVersion)
        val collectedAmount = newsJob.collectNews(newsApiEnum ?: NewsApiEnum.RapidAPI)
        return ResponseEntity("News collected: $collectedAmount", HttpStatus.OK)
    }

    @PostMapping("analyzeNews")
    fun analyzeNews(): ResponseEntity<String>{

        val analyzedAmount = newsJob.analyzeNewsSentiment()
        return ResponseEntity("News analyzed: $analyzedAmount", HttpStatus.OK)
    }

}