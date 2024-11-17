package me.heidlund.pokemoncollectionagencybackend.Controller

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.heidlund.pokemoncollectionagencybackend.Models.*
import me.heidlund.pokemoncollectionagencybackend.services.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class index() {
    @Autowired
    val service = MainService()

    @GetMapping("/version")
    @CrossOrigin(origins = ["http://localhost:5173"])
    fun getVersions(): ResponseEntity<Array<Version>> {
        val versions = service.getAll();
        val resp =  ResponseEntity<Array<Version>>(versions.toTypedArray(), HttpStatus.OK)
        return resp
    }
    @GetMapping("/routes")
    @CrossOrigin(origins = ["http://localhost:5173"])
    fun getRegion(@RequestParam route_id:Long): ResponseEntity<Array<Location>> {
        val name = service.getRouteByRegionName(route_id)
        val resp =  ResponseEntity<Array<Location>>(name, HttpStatus.OK)
        return resp
    }
    @GetMapping("/area")
    @CrossOrigin(origins = ["http://localhost:5173"])
    fun getArea(@RequestParam location_id:Int): ResponseEntity<Array<LocationArea>> {
        val name = service.getAreas(location_id)
        val resp =  ResponseEntity<Array<LocationArea>>(name, HttpStatus.OK)
        return resp
    }
    @GetMapping("/encounters")
    @CrossOrigin(origins = ["http://localhost:5173"])
    fun getEncounters(@RequestParam location_area_id:Int,@RequestParam version_id:Int): ResponseEntity<Array<Encounters>> {
        val encounters = service.getAllEncountersbyLocation(location_area_id,version_id)
        val resp =  ResponseEntity<Array<Encounters>>(encounters.toTypedArray(), HttpStatus.OK)
        return resp;
    }

    @GetMapping("/encounterSlot")
    @CrossOrigin(origins = ["http://localhost:5173"])
    fun getEncounterSlot(@RequestParam version_id:Int): ResponseEntity<Array<EncounterSlots>> {
        val encounters = service.getAllEncounterSlots(version_id)
        val resp =  ResponseEntity<Array<EncounterSlots>>(encounters.toTypedArray(), HttpStatus.OK)
        return resp;
    }

    //ResponseEntity< HashMap<EncounterMethod,HashMap<Pokemon,List<Encounters>>>>
    @GetMapping("/getPokemonsInArea")
    @CrossOrigin(origins = ["http://localhost:5173"])
    fun getPokemonsInArea(@RequestParam location_area_id:Int,@RequestParam version_id:Int): ResponseEntity<String> {

         val slots = service.getAllSlotsByLocation(location_area_id,version_id)
         val map =  HashMap<String,HashMap<String,List<Encounters>>>()
        for (slot in slots) {
             val map2 =  HashMap<String,List<Encounters>>()
             val pokemons = service.getAllPokemonsByLocationVersionSlot(location_area_id,version_id, slot)
             for (pokemon in pokemons){
                 val enc = service.getAllEncountersByLocationAndVersionAndSlotAndPokemon(location_area_id,version_id,slot,pokemon)
                 map2[Gson().toJson(pokemon)] = enc
             }
            map[Gson().toJson(slot)] = map2
        }
        val type = object : TypeToken<HashMap<EncounterMethod, HashMap<Pokemon, List<Encounters>>>>() {}.type
        val resp =  ResponseEntity<String>(Gson().toJson(map,type), HttpStatus.OK)
        return resp;
    }


    /*area{
        slot{
            pokemon{
                encounter{

                }
            }
        }
    }*/

}