package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "pokemon")
class Pokemon() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    var identifier:String = "";
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "species_id")
    var species: PokemonSpecies? = null;
    var height:Int = 0;
    var weight:Int = 0;
    @Column(name = "base_experience")
    var baseXp:Int = 0;

}