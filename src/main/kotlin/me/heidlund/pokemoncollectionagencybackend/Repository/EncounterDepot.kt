package me.heidlund.pokemoncollectionagencybackend.Repository

import me.heidlund.pokemoncollectionagencybackend.Models.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EncounterDepot: JpaRepository<Encounters, Long> {

   // fun findAllByLocationAreaAndVersion(AreaId: LocationArea, version: Version): List<Encounters>
    fun findAllByLocationArea(AreaId: LocationArea): List<Encounters>
    fun findAllByLocationAreaAndVersion(Location: LocationArea, version: Version): List<Encounters>
    fun findDByLocationAreaAndVersion(locationArea: LocationArea, version: Version):List<Encounters>

    @Query("SELECT DISTINCT e.pokemon FROM Encounters e WHERE e.locationArea = :locationarea AND e.version = :version")
    fun findDistinctPokemonsByLocationareaAndVersion(locationarea: LocationArea, version: Version): List<Pokemon>
    @Query("SELECT DISTINCT e.encounterSlots.encounterMethodId FROM Encounters e WHERE e.locationArea = :locationarea AND e.version = :version")
    fun findDistinctEncouterMethodByLocationareaAndVersion(locationarea: LocationArea, version: Version): List<EncounterMethod>
    @Query("SELECT DISTINCT e.pokemon FROM Encounters e JOIN FETCH e.pokemon.species WHERE e.locationArea = :locationarea AND e.version = :version AND e.encounterSlots.encounterMethodId = :slot")
    fun findDistinctPokemonsByLocationareaAndVersionAndSlot(locationarea: LocationArea, version: Version, slot: EncounterMethod): List<Pokemon>
    @Query("SELECT DISTINCT e FROM Encounters e WHERE e.locationArea = :locationarea AND e.version = :version AND e.encounterSlots.encounterMethodId = :slot AND e.pokemon = :pokemon")
    fun findDistinctEncoutersByLocationareaAndVersionAndSlot(locationarea: LocationArea, version: Version, slot: EncounterMethod, pokemon: Pokemon): List<Encounters>

    @Query("SELECT e FROM Encounters e WHERE e.version.id = :version AND e.encounterSlots.versionGroup.id >= :versionGroup ORDER BY e.pokemon.id asc, e.encounterSlots.rarity desc ")
    fun findAllByVersionId(version: Int,versionGroup: Int): List<Encounters>
}
/*org.springframework.beans.factory.UnsatisfiedDependencyException:
Error creating bean with name 'index': Unsatisfied dependency expressed through field 'service':
Error creating bean with name 'mainService': Unsatisfied dependency expressed through field 'encountersDepo':
Error creating bean with name 'encounterDepot' defined in me.heidlund.pokemoncollectionagencybackend.Repository.EncounterDepot defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration:
Could not create query for public abstract java.util.List
me.heidlund.pokemoncollectionagencybackend.Repository.EncounterDepot.findDistinctPokemonsByLocationareaAndVersionAndSlot
(me.heidlund.pokemoncollectionagencybackend.Models.LocationArea,
me.heidlund.pokemoncollectionagencybackend.Models.Version,
me.heidlund.pokemoncollectionagencybackend.Models.EncounterMethod);
Reason: Using named parameters for method public abstract java.util.List
me.heidlund.pokemoncollectionagencybackend.Repository.EncounterDepot.findDistinctPokemonsByLocationareaAndVersionAndSlot
(me.heidlund.pokemoncollectionagencybackend.Models.LocationArea,
me.heidlund.pokemoncollectionagencybackend.Models.Version,
me.heidlund.pokemoncollectionagencybackend.Models.EncounterMethod)
but parameter 'Optional[locationArea]' not found in annotated query 'SELECT DISTINCT e.pokemon FROM Encounters e WHERE e.locationArea = :locationarea AND e.version = :version AND e.encounterSlots.encounterMethodId = :slot'
*/