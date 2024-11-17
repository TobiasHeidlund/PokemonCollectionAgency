package me.heidlund.pokemoncollectionagencybackend.Repository

import me.heidlund.pokemoncollectionagencybackend.Models.Location
import me.heidlund.pokemoncollectionagencybackend.Models.LocationArea
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface LocationAreaDepot : JpaRepository<LocationArea, Long> {

    fun findAllBylocationId(location: Location): List<LocationArea>

}
