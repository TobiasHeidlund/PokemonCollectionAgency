package me.heidlund.pokemoncollectionagencybackend.Repository

import me.heidlund.pokemoncollectionagencybackend.Models.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationDepot : JpaRepository<Location, Long> {

    fun findAllByRegionId(regionId: Long): List<Location>


}


/*class RouteDepot() : BaseRepository(){
    fun getRouteByRegion(region_id:Long): Array<Route> {
        val routes = ArrayList<Route>()
        try {
            val ps: PreparedStatement = connection!!.prepareCall("SELECT * FROM `locations` WHERE `region_id`=?")
            ps.setLong(1,region_id)
            val rs = ps.executeQuery()
            while (rs.next()) {
                routes.add(Route(rs.getLong("id"),rs.getString("identifier"),region_id));
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return routes.toTypedArray()
    }
}*/