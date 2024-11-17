package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "encounters")
class Encounters() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    @Column(name = "min_level")
    var minLevel:Int = 0
    @Column(name = "max_level")
    var maxLevel:Int = 0
    @ManyToOne
    @JoinColumn(name = "encounter_slot_id", nullable = false)
    var encounterSlots: EncounterSlots? = null;
    @ManyToOne
    @JoinColumn(name = "location_area_id", nullable = false)
    var locationArea: LocationArea? = null;
    @ManyToOne
    @JoinColumn(name = "version_id", nullable = false)
    var version: Version? = null;
    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    var pokemon: Pokemon? = null;

}