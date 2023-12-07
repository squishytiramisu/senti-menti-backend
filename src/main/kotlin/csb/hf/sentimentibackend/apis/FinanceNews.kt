package csb.hf.sentimentibackend.apis

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class FinanceNews (private val webClient: WebClient){

    val newsForTickerUrlAPI1 : String = "https://stocknewsapi.com/api/v1?tickers=*&items=3&page=1&token=yonyosg4yl6sy6jfet6vmbg1or07ylvzjucttymj"

    val newsForTickerUrlAPI2 : String = "https://real-time-finance-data.p.rapidapi.com/stock-news?symbol=*&language=en"

    @Value("\${msnews.api.key}")
    lateinit var msNewsApiKey: String

    fun fetchNewsForTicker(ticker: String): NewsResponse? {
        return fetchNewsForTickerAPI1(ticker)
    }

    fun fetchNewsForTickerAPI1(ticker: String): NewsResponse? {
        /*val response = webClient.get()
            .uri(newsForTickerUrl.replace("*",ticker))
            .retrieve()
            .bodyToMono(String::class.java)
            .block()*/
        val response = """
            {
                "data": [
                    {
                        "news_url": "https://www.fool.com/investing/2023/11/23/will-eli-lilly-be-worth-more-than-tesla-by-2027/",
                        "image_url": "https://cdn.snapi.dev/images/v1/2/n/urlhttps3a2f2fgfoolcdncom2feditorial2fimages2f7558232fwoman-stock-broker-investor-tracking-stocksjpgopresizew400h400-2169988.jpg",
                        "title": "Will Eli Lilly Be Worth More Than Tesla by 2027?",
                        "text": "Eli Lilly's shares climbed rapidly in recent years. The pharma giant's track record of bringing highly successful drugs into the market means there's a long growth runway.",
                        "source_name": "The Motley Fool",
                        "date": "Thu, 23 Nov 2023 09:05:00 -0500",
                        "topics": [],
                        "sentiment": "Positive",
                        "type": "Article",
                        "tickers": [
                            "LLY",
                            "TSLA"
                        ]
                    },
                    {
                        "news_url": "https://247wallst.com/autos/2023/11/23/6-reasons-to-avoid-tesla-model-s-no-matter-what/",
                        "image_url": "https://cdn.snapi.dev/images/v1/k/x/urlhttps3a2f2fgfoolcdncom2feditorial2fimages2f5158312findustrials-autos-tesla-model-3-tsla-2169964.jpg",
                        "title": "6 Reasons To Avoid Tesla Model S No Matter What",
                        "text": "Sometimes it might seem like you're the only person in the world who doesn't have a Tesla (NYSE: TSLA) vehicle.",
                        "source_name": "24/7 Wall Street",
                        "date": "Thu, 23 Nov 2023 08:30:35 -0500",
                        "topics": [],
                        "sentiment": "Negative",
                        "type": "Article",
                        "tickers": [
                            "TSLA"
                        ]
                    },
                    {
                        "news_url": "https://www.fool.com/investing/2023/11/23/3-stocks-i-bought-this-thanksgiving-week-and-you-s/",
                        "image_url": "https://cdn.snapi.dev/images/v1/3/f/urlhttps3a2f2fgfoolcdncom2feditorial2fimages2f7558222fgettyimages-1347340865-1201x800-5b2df79jpgopresizew400h400-2169841.jpg",
                        "title": "3 Stocks I Bought This Thanksgiving Week, and You Should, Too",
                        "text": "Tesla's role as an EV leader makes it a resilient addition to portfolios. Along with an upcoming halving, Bitcoin's role as an inflation hedge positions it for sustained growth in the years ahead.",
                        "source_name": "The Motley Fool",
                        "date": "Thu, 23 Nov 2023 06:50:00 -0500",
                        "topics": [],
                        "sentiment": "Positive",
                        "type": "Article",
                        "tickers": [
                            "BTC",
                            "MGK",
                            "TSLA"
                        ]
                    }
                ],
                "total_pages": 3334
            }
        """.trimIndent()
        return response?.let { Json { ignoreUnknownKeys = true }.decodeFromString<NewsResponse>(it) }
    }

    fun fetchNewsForTickerAPI2(ticker: String) : NewsResponseRapidAPI?
    {
        val response = webClient.get()
            .uri(newsForTickerUrlAPI2.replace("*",ticker))
            .header("X-RapidAPI-Key", msNewsApiKey)
            .header("X-RapidAPI-Host", "real-time-finance-data.p.rapidapi.com")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()



        return response?.let { Json { ignoreUnknownKeys = true }.decodeFromString<NewsResponseRapidAPI>(it) }
    }

}