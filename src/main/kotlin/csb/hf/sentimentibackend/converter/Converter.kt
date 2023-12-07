package csb.hf.sentimentibackend.converter

import csb.hf.sentimentibackend.data.SentimentEnum
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun parseDateStringToDate(dateString: String): Date {
    val dateFormatPattern = "EEE, dd MMM yyyy HH:mm:ss Z"
    val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone("GMT-05:00")

    return dateFormat.parse(dateString) ?: throw IllegalArgumentException("Invalid date string: $dateString")
}

fun parseRapidDateStringToDate(dateString: String): Date {
    val dateFormatPattern = "yyyy-MM-dd HH:mm:ss"
    val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.ENGLISH)

    return dateFormat.parse(dateString) ?: throw IllegalArgumentException("Invalid date string: $dateString")
}



fun sentimentEnumToDouble(sentiment: SentimentEnum): Double {
    return when (sentiment) {
        SentimentEnum.VeryPositive -> +2.0
        SentimentEnum.Positive -> +1.0
        SentimentEnum.Natural -> 0.0
        SentimentEnum.Negative -> -1.0
        SentimentEnum.VeryNegative -> -2.0
        SentimentEnum.Undefined -> 0.0
    }
}