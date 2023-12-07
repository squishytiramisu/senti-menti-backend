package csb.hf.sentimentibackend.data.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*


@Entity(name ="userT")
@Table(name = "userT")
class User (userName:String){
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int?=null

        val userName: String = userName

        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinTable(name = "user_watchlist",
                joinColumns =[ JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "ticker_id", referencedColumnName = "id")])
        @JsonIgnoreProperties("users")
        val watchList: MutableList<Ticker> = mutableListOf()

        fun addTicker(ticker: Ticker){
                ticker.users.add(this)
                watchList.add(ticker)
        }

        fun removeTicker(ticker: Ticker){
                ticker.users.remove(this)
                watchList.remove(ticker)
        }



}