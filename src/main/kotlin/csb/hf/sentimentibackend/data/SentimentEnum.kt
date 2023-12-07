package csb.hf.sentimentibackend.data

enum class SentimentEnum {

    VeryPositive,
    Positive,
    Natural,
    Negative,
    VeryNegative,
    Undefined;

    companion object {
        fun fromString(value: String): SentimentEnum? {
            return values().find { it.name == value }
        }
    }
}