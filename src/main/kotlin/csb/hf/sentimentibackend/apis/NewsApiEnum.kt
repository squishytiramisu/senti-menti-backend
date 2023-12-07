package csb.hf.sentimentibackend.apis

enum class NewsApiEnum {

    RapidAPI,
    StockNewsAPI;

    companion object {
        fun fromString(value: String): NewsApiEnum? {
            return values().find { it.name == value }
        }
    }
}