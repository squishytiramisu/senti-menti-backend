package csb.hf.sentimentibackend.apis.gpt
import kotlinx.serialization.Serializable



fun constructGPTRequest(news: String, ticker: String): String {
    // Protect JSON from special characters
    val cleanedNews = news.replace("\"", "")
        .replace("\\","")
        .replace("\n", " ")
    return """
        {
            "messages" : [
                {
                    "role" : "user",
                    "content" : "Analyze a headline's sentiment for the ticker: '$ticker'. Headline: \"$cleanedNews\".Only respond with either VeryPositive, Positive, Natural, Negative, VeryNegative. If the headline is unrelated to the ticker, answer Natural."
                }
            ],
            "temperature" : 1,
            "max_tokens": 96,
            "top_p" : 1,
            "frequency_penalty" : 0.0,
            "presence_penalty" : 0.0,
            "model" : "gpt-3.5-turbo"
        }
    """.trimIndent()
}


@Serializable
data class ChatCompletion(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage
)

@Serializable
data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

@Serializable
data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)
