package csb.hf.sentimentibackend.apis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseData(
    @SerialName("news_url") val newsUrl: String,
    @SerialName("image_url") val imageUrl: String,
    val title: String,
    val text: String,
    @SerialName("source_name") val sourceName: String,
    val date: String,
    val topics: List<String>,
    val sentiment: String,
    val type: String,
    val tickers: List<String>
)
