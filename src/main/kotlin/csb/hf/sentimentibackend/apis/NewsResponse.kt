package csb.hf.sentimentibackend.apis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val data: List<NewsResponseData>,
    @SerialName("total_pages") val totalPages: Int
)
