package csb.hf.sentimentibackend.jobs

import csb.hf.sentimentibackend.apis.FinanceNews
import csb.hf.sentimentibackend.apis.NewsApiEnum
import csb.hf.sentimentibackend.apis.gpt.AnalyzeNews
import csb.hf.sentimentibackend.data.DAO
import csb.hf.sentimentibackend.data.SentimentEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class NewsJob (@Autowired private val financeNews: FinanceNews,
               @Autowired private val analyzeNews: AnalyzeNews,
               @Autowired private val dao: DAO) {


    fun collectNews(apiVersion: NewsApiEnum): Int {
        // Collect the set of watchlist tickers
        // For each ticker, collect the news
        // Save news to database

        val tickers = dao.getWatchlistTickers().filter { it.news.size < 15 }
        var newsCount = 0


        tickers.forEach { ticker ->
            if(apiVersion == NewsApiEnum.StockNewsAPI){
                val news = financeNews.fetchNewsForTicker(ticker.ticker)
                news?.data?.forEach { newsItem ->
                    newsItem.tickers.forEach { tickerStr ->
                        if(tickerStr == ticker.ticker){
                            dao.addNews(newsItem, ticker)
                            newsCount++
                        }
                    }
                }
            }else{
                val response = financeNews.fetchNewsForTickerAPI2(ticker.ticker)
                val news = response?.data?.news?.slice(0..6)

                news?.forEach { newsItem ->
                    if(ticker.ticker == response.data.symbol.split(":")[0]){
                        dao.addNews(newsItem, ticker)
                        newsCount++
                    }
                }
            }

        }
        return newsCount
    }

    fun analyzeNewsSentiment(): Int{
        var analyzedNews = 0
        dao.getUnanalyzedNews().forEach { news ->
            try {
                println("Analyzing news: ${news.description} for ticker: ${news.ticker?.ticker}")
                val sentiment = analyzeNews.analyzeSentiment(news.description, news.ticker?.ticker ?: "")
                analyzedNews++
                if (sentiment != null) {
                    SentimentEnum.fromString(sentiment)?.let {
                        news.sentiment = it
                        dao.saveNews(news)
                    }
                }
            }catch (e: Exception) {
                println("Error analyzing news: ${news.description} for ticker: ${news.ticker?.ticker}")
            }
        }
        return analyzedNews
    }
}