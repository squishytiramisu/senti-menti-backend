package csb.hf.sentimentibackend.data.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@Table(name = "ticker")
class Ticker(ticker: String) {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? =null

        val ticker: String = ticker

        @JsonIgnoreProperties("ticker")
        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "ticker")
        var news: MutableList<News> = mutableListOf()

        @ManyToMany(cascade = [
                CascadeType.PERSIST,
                CascadeType.MERGE],
                fetch = FetchType.EAGER,
                mappedBy = "watchList")
        @JsonIgnoreProperties("watchList")
        var users: MutableList<User> = mutableListOf()

        fun addNews(newsToAdd: News){
                newsToAdd.ticker=this
                news.add(newsToAdd)
        }


}

