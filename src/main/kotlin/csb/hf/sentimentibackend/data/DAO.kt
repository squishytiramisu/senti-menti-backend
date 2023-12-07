package csb.hf.sentimentibackend.data

import csb.hf.sentimentibackend.apis.NewsRapidAPI
import csb.hf.sentimentibackend.apis.NewsResponseData
import csb.hf.sentimentibackend.converter.parseDateStringToDate
import csb.hf.sentimentibackend.converter.parseRapidDateStringToDate
import csb.hf.sentimentibackend.converter.sentimentEnumToDouble
import csb.hf.sentimentibackend.data.entities.*
import csb.hf.sentimentibackend.data.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Predicate

@Component
class DAO(@Autowired private val userRepository: UserRepository,
          @Autowired private val newsRepository: NewsRepository,
          @Autowired private val tickerRepository: TickerRepository ) {



    fun getAllUsers(): List<User>{
        return userRepository.findAll().toList()
    }

    fun getUserWatchlist(userName: String): List<Ticker>? {
        return userRepository.findByUserName(userName)?.watchList?.toList() ?: listOf()
    }

    fun getUser(name: String): User?{
        return userRepository.findByUserName(name)
    }

    fun createUser(userName: String): Result<User>{
        if(userRepository.findByUserName(userName) != null){
            return Result.failure(Exception("User already exists"))
        }
        val user = User(userName)
        val savedUser = userRepository.save(user)
        return Result.success(savedUser)
    }

    fun getWatchlistTickers(): Set<Ticker> {
        return tickerRepository.findAll()
            .filter { it.users.isNotEmpty() }
            .toSet()
    }

    fun addNews(newsItem: NewsResponseData, ticker: Ticker) {
        val alreadyAdded: News? = newsRepository.findByDescription(newsItem.text)
        if(alreadyAdded == null){

            val news = News(
                parseDateStringToDate(newsItem.date),
                SentimentEnum.Undefined,
                newsItem.text,
                newsItem.newsUrl)
            ticker.addNews(news)
            tickerRepository.save(ticker)
        }
    }

    fun addNews(newsItem: NewsRapidAPI, ticker: Ticker) {
        val alreadyAdded: News? = newsRepository.findByDescription(newsItem.articleTitle)
        if(alreadyAdded == null){
            val news = News(
                parseRapidDateStringToDate(newsItem.postTimeUtc),
                SentimentEnum.Undefined,
                newsItem.articleTitle,
                newsItem.articleUrl)
            ticker.addNews(news)
            tickerRepository.save(ticker)
        }
    }


    fun getUnanalyzedNews(): List<News> {
        return newsRepository.findAll().filter { it.sentiment == SentimentEnum.Undefined }

    }

    fun saveNews(news: News) {
        newsRepository.save(news)
    }

    fun getHeadlines(predicate: Predicate<News>): List<AggregatedNews> {
        val news = newsRepository.findAll().filter { predicate.test(it) }

        val headlines: List<AggregatedNews> = news.groupBy { it.ticker?.ticker }
            .entries.map { entry ->
                val ticker = entry.key
                val newsEntry = entry.value
                if(newsEntry.isEmpty()){
                    AggregatedNews(ticker?: "", 0.0, "No headlines found")
                }
                val sentiment = newsEntry.map { sentimentEnumToDouble(it.sentiment) }.average()
                val headline = newsEntry.find { it.sentiment in listOf(SentimentEnum.VeryNegative, SentimentEnum.VeryPositive) }?.description ?: ""
                AggregatedNews(ticker?: "", sentiment, headline)
        }
        return headlines
    }

    fun getNewsForTicker(ticker: String): List<News> {
        val news = newsRepository.findAll().filter { it.ticker?.ticker == ticker }
        return news
    }

    fun addToWatchlist(username: String, tickerName: String): Result<String> {
        val user = userRepository.findByUserName(username) ?: return Result.failure(Exception("User not found"))
        val ticker = tickerRepository.findByTicker(tickerName) ?: return Result.failure(Exception("Ticker not found"))
        if(user.watchList.contains(ticker)){
            return Result.success("Ticker already on watchlist")
        }
        user.addTicker(ticker)
        userRepository.save(user)
        return Result.success("Added ticker to watchlist")
    }

    fun removeFromWatchlist(username: String, tickerName: String): Result<String> {
        val user = userRepository.findByUserName(username) ?: return Result.failure(Exception("User not found"))
        val ticker = tickerRepository.findByTicker(tickerName) ?: return Result.failure(Exception("Ticker not found"))
        if(!user.watchList.contains(ticker)){
            return Result.success("Ticker not on watchlist")
        }
        user.removeTicker(ticker)
        userRepository.save(user)
        return Result.success("Removed ticker from watchlist")
    }

    fun getAllTickers(): List<Ticker> {
        val allowedTickers = tickerRepository.findAll().toList()
        if(allowedTickers.isEmpty()){
            createDummyDB()
        }
        return tickerRepository.findAll().toList()
    }

    fun createDummyDB(): Unit{

        val allowedTickers: List<Ticker> = listOf(
            Ticker("TSLA"),
            Ticker("AAPL"),
            Ticker("MSFT"),
            Ticker("AMZN"),
            Ticker("GOOG"))

        tickerRepository.saveAll(allowedTickers)
    }


}