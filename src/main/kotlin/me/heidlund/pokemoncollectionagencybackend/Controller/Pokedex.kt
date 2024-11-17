package me.heidlund.pokemoncollectionagencybackend.Controller

import me.heidlund.pokemoncollectionagencybackend.Models.PokedexPokemon
import me.heidlund.pokemoncollectionagencybackend.Models.ToCollect
import me.heidlund.pokemoncollectionagencybackend.services.PokedexService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pokedex")
@CrossOrigin(origins = ["http://localhost:5173"])
class Pokedex {
    @Autowired
    private lateinit var pokedexService: PokedexService

    @GetMapping("")
    fun getPokemon(@RequestParam(name = "pokemon_id") pokemonId:Int ): ResponseEntity<PokedexPokemon> {
        var specis = pokedexService.getSpeciesByPokemon(pokemonId)

        return getPokemonbySpecies(specis);
    }
    @GetMapping("species")
    fun getPokemonbySpecies(@RequestParam(name = "pokemon_species_id") pokemonSpeciesId:Int ): ResponseEntity<PokedexPokemon> {
        var pokedexPokemon: PokedexPokemon? = pokedexService.getPokemon(pokemonSpeciesId)
            ?: return ResponseEntity.badRequest().body(PokedexPokemon());
        return ResponseEntity(pokedexPokemon, HttpStatus.OK);
    }

    @PutMapping("")
    fun putPokemon(@RequestParam(required = true, name = "pokemon_id") pokemonId:Int ): ResponseEntity<String> {
        pokedexService.addCollected(pokemonId)
        return ResponseEntity.ok().body("");
    }


    @GetMapping("tocollect")
    fun toCollect(@RequestParam(name = "version") version:Int ): ResponseEntity<List<ToCollect>> {
        val toCollect:List<ToCollect> = pokedexService.toCollect(version)
        return ResponseEntity(toCollect, HttpStatus.OK);
    }
    @GetMapping("collected")
    fun collected(@RequestParam(name = "version") version:Int ): ResponseEntity<List<PokedexPokemon>> {
        val toCollect:List<PokedexPokemon> = pokedexService.getCollected(version)
        return ResponseEntity(toCollect, HttpStatus.OK);
    }
    @PutMapping("evolve")
    fun evolve(@RequestParam(name = "pokedexid") pokedexid:Int, @RequestParam(name = "tospecies") tospecies:Int ):ResponseEntity<String>{
        return if(pokedexService.evolve(pokedexid, tospecies)){
            ResponseEntity.ok("Evolved");
        }else ResponseEntity.badRequest().body("Not evolved");

    }
    @GetMapping("currentcompeate")
    fun currentCompeate(@RequestParam(name = "version") version:Int ): ResponseEntity<List<Int>> {
        val toCollect:List<Int> = pokedexService.numberToCollect(version)
        return ResponseEntity(toCollect, HttpStatus.OK);
    }
    @PutMapping("identifier")
    fun addCustom(@RequestParam(name = "identifier") identifier:String ): ResponseEntity<String> {
        val toCollect:Boolean = pokedexService.addCollected(identifier)
        if(toCollect){
            return ResponseEntity.ok("")
        }else return ResponseEntity.badRequest().body("")
    }


}