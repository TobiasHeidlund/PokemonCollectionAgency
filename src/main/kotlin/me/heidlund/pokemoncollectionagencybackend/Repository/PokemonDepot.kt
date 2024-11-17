package me.heidlund.pokemoncollectionagencybackend.Repository

import me.heidlund.pokemoncollectionagencybackend.Models.Encounters
import me.heidlund.pokemoncollectionagencybackend.Models.Pokemon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PokemonDepot: JpaRepository<Pokemon, Int> {

     @Query("SELECT e FROM Pokemon e WHERE e.species.generationId <= :genId ")
    fun findByVersion(genId: Int): List<Pokemon>
}
