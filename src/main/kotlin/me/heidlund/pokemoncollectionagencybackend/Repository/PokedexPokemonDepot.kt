package me.heidlund.pokemoncollectionagencybackend.Repository

import me.heidlund.pokemoncollectionagencybackend.Models.PokedexPokemon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PokedexPokemonDepot : JpaRepository<PokedexPokemon, Int> {



    fun getPokedexPokemonById(id: Int): PokedexPokemon?


    @Query(value = "SELECT p FROM PokedexPokemon p WHERE p.species.id = :id")
    fun findFirstBySpeciesIds(@Param("id") id: Int): PokedexPokemon?
     @Query(value = "SELECT p FROM PokedexPokemon p WHERE p.species.id in :ids")
    fun findAllBySpeciesIds(@Param("ids") ids: List<Int>): ArrayList<PokedexPokemon>?
    @Query(value = "SELECT p.species FROM PokedexPokemon p")
    fun getAllPokemons(): ArrayList<PokedexPokemon>?
    @Query(value = "SELECT p.species.id FROM PokedexPokemon p")
    fun getAllPokemonsSpeciesId(): List<Int>?


}