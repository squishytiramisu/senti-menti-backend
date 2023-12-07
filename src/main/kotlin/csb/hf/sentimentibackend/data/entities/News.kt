package csb.hf.sentimentibackend.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.BeanDescription
import csb.hf.sentimentibackend.data.SentimentEnum
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "news")
 class News (date:Date,sentimentEnum: SentimentEnum, description: String, url: String){

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null

        var date: Date = date

        var sentiment: SentimentEnum = sentimentEnum

        var description: String = description

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnoreProperties("news", "users")
        var ticker: Ticker? = null

}