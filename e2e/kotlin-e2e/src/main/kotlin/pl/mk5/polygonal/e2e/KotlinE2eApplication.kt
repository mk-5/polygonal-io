package pl.mk5.polygonal.e2e

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinE2eApplication

fun main(args: Array<String>) {
    runApplication<KotlinE2eApplication>(*args)
}
