package csb.hf.sentimentibackend.apis.gpt


import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AnalyzeNews (private val webClient: WebClient){

    val analyzeNewsUrl = "https://api.openai.com/v1/chat/completions"

    @Value( "\${openai.token}" )
    lateinit var token: String

    fun analyzeSentiment(news: String, ticker: String): String? {
        val json = constructGPTRequest(news, ticker)
        println("Sending request: $json")
        val response =  webClient.post()
            .uri(analyzeNewsUrl)
            .header("Authorization", token)
            .header("Content-Type", "application/json")
            .bodyValue(json)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return response?.let { Json { ignoreUnknownKeys = true }
            .decodeFromString<ChatCompletion>(it) }
            ?.choices?.get(0)?.message?.content
    }
}