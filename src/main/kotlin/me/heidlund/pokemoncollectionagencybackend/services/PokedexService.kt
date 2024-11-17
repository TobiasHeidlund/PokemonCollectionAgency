package me.heidlund.pokemoncollectionagencybackend.services

import me.heidlund.pokemoncollectionagencybackend.Models.*
import me.heidlund.pokemoncollectionagencybackend.Repository.PokedexPokemonDepot
import me.heidlund.pokemoncollectionagencybackend.Repository.PokemonDepot
import me.heidlund.pokemoncollectionagencybackend.Repository.PokemonSpeciesDepot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class PokedexService() {
    @Autowired
    lateinit var pokedexPokemonDepot: PokedexPokemonDepot
    @Autowired
    lateinit var pokemonDepot: PokemonDepot
    @Autowired
    lateinit var pokemonSpeciesDepot: PokemonSpeciesDepot
    @Autowired
    lateinit var mainService: MainService

    fun getPokemon(pokemonSpeciesId: Int): PokedexPokemon? {
        var pokedexPokemon = pokedexPokemonDepot.findFirstBySpeciesIds(pokemonSpeciesId)
        if(pokedexPokemon== null) { pokedexPokemon = PokedexPokemon(pokemonSpeciesId) }
        var evos = getEvolutions(pokemonSpeciesId)
        pokedexPokemon.canBeEvolvedTo = evos;
        pokedexPokemon.species = pokemonSpeciesDepot.findById(pokemonSpeciesId).get()

        return pokedexPokemon
    }


    //TODO: MAKE SURE THE GENERATIONS MATCH UP
    private fun getEvolutions(
        pokemon: Int
    ): List<PokedexPokemon> {
        var speciesArr = pokemonSpeciesDepot.findAllByevolvesFomSpecies(PokemonSpecies(pokemon))
        var list = pokedexPokemonDepot.findAllBySpeciesIds(speciesArr.map { it.id })?: ArrayList()
        speciesArr.filter { !list.map { it2 -> it2.species?.id }.contains(it.id) }.forEach{ itfe->
            var pok = PokedexPokemon(itfe.id);
            pok.species = speciesArr.find { it.id == itfe.id }
            list.add(pok)
        }

            return list;
    }



    //TODO: ADD NAME AND CHANGE TO SPECIES ID
    fun addCollected(pokemonId: Int):Boolean {
        var species = pokemonSpeciesDepot.findById(pokemonId)
        if(species!!.isEmpty) {
            return false;
        }else {
            var pokedexEntry = pokedexPokemonDepot.findFirstBySpeciesIds(species.get().id)
            if (pokedexEntry == null) {
                pokedexPokemonDepot.save(PokedexPokemon(species.get().id).also { pokemon ->
                    pokemon.isCollected = 1
                    pokemon.species = species.get()
                })
            } else {
                pokedexEntry.isCollected += 1
                pokedexPokemonDepot.save(pokedexEntry)
            }
        }
        return true
    }

    fun getSpeciesByPokemon(pokemonId: Int): Int {
        return pokemonDepot.findById(pokemonId).get().species?.id ?: 0;
    }

    fun toCollect(version: Int): List<ToCollect> {
        val enc = mainService.getAllEncountersInVersion(version)
        val toCollectIds = enc.map { it.pokemon?.species?.id }.distinct()
        val hasCollected = pokedexPokemonDepot.getAllPokemonsSpeciesId();
        val toCollectSpeciesList = toCollectIds.filter { !hasCollected!!.contains(it) }
        val toCollectList = ArrayList<ToCollect>()
        toCollectSpeciesList.forEach{
            val encounters = enc.filter { it2 -> it2.pokemon!!.species!!.id == it }
            toCollectList.add(ToCollect(encounters[0].pokemon!!,encounters.map { it.locationArea!!.locationId!!.identifier }))
        }

        return toCollectList
    }

    fun getCollected(version: Int): List<PokedexPokemon> {
        val enc = mainService.getAllPokemonsInVersion(version)
        val toCollectIds = enc.map { it.species?.id }.distinct()
        val getAll = pokedexPokemonDepot.findAll();
        val filtered =  getAll.filter { it -> toCollectIds.contains(it.species!!.id) }
        filtered.forEach{
            it.canBeEvolvedTo = getEvolutions(it.species!!.id)
        }
        return filtered
    }

    fun evolve(pokedexid: Int, tospecies: Int):Boolean {
        var pokedexEntry = pokedexPokemonDepot.findById(pokedexid);
        if (pokedexEntry.isEmpty) {
            return false;
        } else {
            val ent = pokedexEntry.get()
            if (ent.isCollected > 0) {
                ent.isCollected -= 1
                pokedexPokemonDepot.save(ent)
                return addCollected(tospecies)
            }
        }
        return false
    }

    fun numberToCollect(version: Int): List<Int> {
       val enc = mainService.getAllPokemonsInVersion(version)
        val toCollectIds = enc.map { it.species?.id }.distinct()
        val getAll = pokedexPokemonDepot.findAll();
        val filtered =  getAll.filter { it -> toCollectIds.contains(it.species!!.id) }
        filtered.forEach{
            it.canBeEvolvedTo = getEvolutions(it.species!!.id)
        }

        return listOf(filtered.size,toCollectIds.size)
    }

    fun addCollected(pokemonId: String): Boolean {
        var species = pokemonSpeciesDepot.findByIdentifier(pokemonId)
        if(species.isEmpty) {
            return false;
        }else {
            var pokedexEntry = pokedexPokemonDepot.findFirstBySpeciesIds(species.get().id)
            if (pokedexEntry == null) {
                pokedexPokemonDepot.save(PokedexPokemon(species.get().id).also { pokemon ->
                    pokemon.isCollected = 1
                    pokemon.species = species.get()
                })
            } else {
                pokedexEntry.isCollected += 1
                pokedexPokemonDepot.save(pokedexEntry)
            }
        }
        return true
    }

}