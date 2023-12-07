package csb.hf.sentimentibackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SentimentiBackendApplication

fun main(args: Array<String>) {
    runApplication<SentimentiBackendApplication>(*args)
}
