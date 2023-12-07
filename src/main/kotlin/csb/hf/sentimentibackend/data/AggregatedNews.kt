package csb.hf.sentimentibackend.data

import kotlinx.serialization.Serializable


@Serializable
class AggregatedNews(val topHeadline: String,
                     val headlineScore: Double,
                     val ticker: String) {

}