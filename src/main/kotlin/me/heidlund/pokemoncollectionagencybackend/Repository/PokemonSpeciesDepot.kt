package me.heidlund.pokemoncollectionagencybackend.Repository

import me.heidlund.pokemoncollectionagencybackend.Models.PokemonSpecies
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PokemonSpeciesDepot : JpaRepository<PokemonSpecies, Int> {

    // fun findAllByevolvesFromSpecies(evolvesFromSpecies: PokemonSpecies): List<PokemonSpecies>
    @Query("SELECT e FROM PokemonSpecies e WHERE e.evolvesFomSpecies = :fomSpecis AND e.generationId = :genId")
    fun findAllByevolvesFomSpeciesAndgenerationId(fomSpecis: PokemonSpecies, genId:Int): List<PokemonSpecies>
    @Query("SELECT e FROM PokemonSpecies e WHERE e.evolvesFomSpecies = :fomSpecis")
    fun findAllByevolvesFomSpecies(fomSpecis: PokemonSpecies): List<PokemonSpecies>

    @Query("SELECT p FROM PokemonSpecies p WHERE p.id = :id")
    override fun findById(@Param("id") id: Int): Optional<PokemonSpecies>
    @Query("SELECT p FROM PokemonSpecies p WHERE p.identifier = :pokemonId")
    fun findByIdentifier(pokemonId: String): Optional<PokemonSpecies>
}
/*org.springframework.beans.factory.UnsatisfiedDependencyException:
Error creating bean with name 'pokedex': Unsatisfied dependency expressed through field 'pokedexService':
Error creating bean with name 'pokedexService': Unsatisfied dependency expressed through field 'pokemonSpeciesDepot':
Error creating bean with name 'pokemonSpeciesDepot' defined in me.heidlund.pokemoncollectionagencybackend.Repository.PokemonSpeciesDepot
defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration:
Could not create query for public abstract java.util.List me.heidlund.pokemoncollectionagencybackend.Repository.PokemonSpeciesDepot.
findAllByevolvesFromSpeciesAndgenerationId(me.heidlund.pokemoncollectionagencybackend.Models.PokemonSpecies,int);
Reason: Failed to create query for method public abstract
java.util.List
me.heidlund.pokemoncollectionagencybackend.Repository.PokemonSpeciesDepot.findAllByevolvesFromSpeciesAndgenerationId
(me.heidlund.pokemoncollectionagencybackend.Models.PokemonSpecies,int); No property 'andgenerationId' found for type 'PokemonSpecies';
Traversed path: PokemonSpecies.evolvesFromSpecies
*/