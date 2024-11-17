package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "location_areas")
class LocationArea() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    var identifier:String = "";
    var game_index:Int= 0 ;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    var locationId: Location? = null
}
