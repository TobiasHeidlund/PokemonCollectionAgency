package me.heidlund.pokemoncollectionagencybackend.Repository

import me.heidlund.pokemoncollectionagencybackend.Models.EncounterSlots
import me.heidlund.pokemoncollectionagencybackend.Models.VersionGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EncounterSlotDepot: JpaRepository<EncounterSlots, Long> {

    fun findAllByVersionGroup(group: VersionGroup): List<EncounterSlots>

}
