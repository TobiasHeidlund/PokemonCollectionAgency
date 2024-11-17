package me.heidlund.pokemoncollectionagencybackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokemonCollectionAgencyBackendApplication

fun main(args: Array<String>) {
    runApplication<PokemonCollectionAgencyBackendApplication>(*args)
}
