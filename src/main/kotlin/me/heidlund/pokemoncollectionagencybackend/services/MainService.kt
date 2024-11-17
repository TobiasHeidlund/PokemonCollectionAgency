package me.heidlund.pokemoncollectionagencybackend.services

import me.heidlund.pokemoncollectionagencybackend.Models.*
import me.heidlund.pokemoncollectionagencybackend.Repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MainService() {
    @Autowired
    private lateinit var pokemonDepot: PokemonDepot

    @Autowired
    private lateinit var locationAreaDepot: LocationAreaDepot;
    @Autowired
    private lateinit var locationDepot: LocationDepot;
    @Autowired
    private lateinit var versionRepo: VersionsRepo;
    @Autowired
    private lateinit var encounterSlotDepot: EncounterSlotDepot
    @Autowired
    private lateinit var encountersDepo: EncounterDepot;

    fun getAll(): List<Version> {
        return versionRepo.findAll()
    }
    fun getRouteByRegionName(regionId:Long): Array<Location>{
        return locationDepot.findAllByRegionId(regionId).toTypedArray()
    }

    fun getAreas(locationId:Int): Array<LocationArea> {
        return locationAreaDepot.findAllBylocationId(Location(locationId)).toTypedArray()
    }

    fun getAllEncounterSlots(versionGroupId:Int):List<EncounterSlots>{
        return encounterSlotDepot.findAllByVersionGroup(VersionGroup(versionGroupId))
    }

    fun getAllEncountersbyLocation(locationId:Int,versionId: Int): List<Encounters> {
        return encountersDepo.findAllByLocationAreaAndVersion(LocationArea(locationId), Version(versionId))
        //return encountersDepo.findAllByLocationArea(LocationArea(locationId))
    }

    fun getAllSlotsByLocation(locationAreaId: Int, versionId: Int): List<EncounterMethod> {
        return encountersDepo.findDistinctEncouterMethodByLocationareaAndVersion(
            LocationArea(locationAreaId),
            Version(versionId)
        )
    }

    fun getAllPokemonsByLocationVersionSlot(locationAreaId: Int, versionId: Int, slot: EncounterMethod): List<Pokemon> {
           return encountersDepo.findDistinctPokemonsByLocationareaAndVersionAndSlot(
               LocationArea(locationAreaId),
               Version(versionId),slot)


    }

    fun getAllEncountersByLocationAndVersionAndSlotAndPokemon(locationAreaId: Int, versionId: Int, slot: EncounterMethod, pokemon: Pokemon): List<Encounters> {
        return encountersDepo.findDistinctEncoutersByLocationareaAndVersionAndSlot(
            LocationArea(locationAreaId),
            Version(versionId),slot,pokemon)

    }

    fun getAllEncountersInVersion(versionId:Int):List<Encounters>{
        var version = versionRepo.findById(versionId.toLong())

        var enc:List<Encounters> = encountersDepo.findAllByVersionId(versionId,version.get().versionGroup!!.id)
        return enc

    }

    fun getAllPokemonsInVersion(versionId: Int): List<Pokemon> {
        var version = versionRepo.findById(versionId.toLong())
        var id: Int? = version.get().versionGroup?.generationId?.id ?: return emptyList()
        return pokemonDepot.findByVersion(id!!)

    }


}