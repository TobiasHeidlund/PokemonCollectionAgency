package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "evolution_chains")
class EvolutionChain() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    @ManyToOne
    @JoinColumn(name = "baby_trigger_item_id", nullable = true)
    var baby_trigger_item: Item? = null
}
