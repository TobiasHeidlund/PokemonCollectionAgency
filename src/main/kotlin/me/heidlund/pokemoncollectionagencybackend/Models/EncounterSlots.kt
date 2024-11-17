package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "encounter_slots")
class EncounterSlots() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    @ManyToOne
    @JoinColumn(name = "version_group_id", nullable = false)
    var versionGroup: VersionGroup? = null;
    @ManyToOne
    @JoinColumn(name = "encounter_method_id", nullable = false)
    var encounterMethodId: EncounterMethod? = null;
    @Column(name ="slot")
    var slot:Integer? = null;
    var rarity:Int = 0;
}
