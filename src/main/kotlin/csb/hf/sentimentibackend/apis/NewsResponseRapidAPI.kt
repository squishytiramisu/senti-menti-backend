package csb.hf.sentimentibackend.apis

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseRapidAPI(
    val status: String,
    @SerialName("request_id")
    val requestId: String,
    val data: Data,
)

@Serializable
data class Data(
    val symbol: String,
    val type: String,
    val news: List<NewsRapidAPI>,
)

@Serializable
data class NewsRapidAPI(
    @SerialName("article_title")
    val articleTitle: String,
    @SerialName("article_url")
    val articleUrl: String,
    @SerialName("article_photo_url")
    val articlePhotoUrl: String,
    val source: String,
    @SerialName("post_time_utc")
    val postTimeUtc: String,
)
