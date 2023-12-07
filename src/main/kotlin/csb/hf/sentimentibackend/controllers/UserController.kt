package csb.hf.sentimentibackend.controllers


import csb.hf.sentimentibackend.apis.FinanceNews
import csb.hf.sentimentibackend.apis.NewsResponse
import csb.hf.sentimentibackend.apis.gpt.AnalyzeNews
import csb.hf.sentimentibackend.data.DAO
import csb.hf.sentimentibackend.data.entities.Ticker
import csb.hf.sentimentibackend.data.entities.User
import csb.hf.sentimentibackend.data.repositories.UserRepository
import csb.hf.sentimentibackend.jobs.NewsJob
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*





@RestController
@RequestMapping("/api/user")
class UserController (@Autowired private val dao: DAO,
){

    @GetMapping("")
    fun getAll(): List<String> = dao.getAllUsers().map { it.userName }

    @GetMapping("{username}")
    fun getUser(@PathVariable username: String): User?{
        return dao.getUser(username)
    }

    @GetMapping("{username}/watchlist")
    fun getWatchlist(@PathVariable username: String): List<Ticker>?{
        return dao.getUserWatchlist(username)
    }


    @PostMapping("{username}")
    fun createUser(@PathVariable username:String): ResponseEntity<String>{

        val result: Result<User> = dao.createUser(username)
        when(result.isSuccess) {
            true -> return ResponseEntity("User created", HttpStatus.OK)
            false -> return ResponseEntity("User already exists", HttpStatus.BAD_REQUEST)
        }
    }


    @PostMapping("{username}/watchlist/{ticker}")
    fun addToWatchlist(@PathVariable username: String, @PathVariable ticker: String): ResponseEntity<String>{
        val result: Result<String> = dao.addToWatchlist(username, ticker)
        when(result.isSuccess) {
            true -> return ResponseEntity(result.getOrThrow(), HttpStatus.OK)
            false -> return ResponseEntity(result.toString(), HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("{username}/watchlist/{ticker}")
    fun removeFromWatchlist(@PathVariable username: String, @PathVariable ticker: String): ResponseEntity<String>{
        val result: Result<String> = dao.removeFromWatchlist(username, ticker)
        when(result.isSuccess) {
            true -> return ResponseEntity(result.getOrThrow(), HttpStatus.OK)
            false -> return ResponseEntity(result.toString(), HttpStatus.BAD_REQUEST)
        }
    }


}