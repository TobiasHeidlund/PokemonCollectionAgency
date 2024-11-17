package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "pokemon_species")
class PokemonSpecies() {
    constructor(id: Int) : this() {
        this.id = id
    }

    @Id
    var id: Int = 0;
    var identifier : String = "";
    @Column(name = "generation_id")
    var generationId : Int = 0;
    @ManyToOne
    @JoinColumn(name = "evolves_from_species_id", nullable = true)
    var evolvesFomSpecies : PokemonSpecies? = null;
    @ManyToOne
    @JoinColumn(name = "evolution_chain_id", nullable = true)
    var evolution_chain : EvolutionChain? = null;
    var color_id : Integer? = null;
    var shape_id : Integer? = null;
    var habitat_id : Integer? = null;
    var gender_rate : Integer? = null;
    var capture_rate : Integer? = null;
    var base_happiness : Integer? = null;
    var is_baby : Boolean  = false;
    var hatch_counter: Integer? = null;
    var has_gender_differences: Boolean  = false;
    var growth_rate_id: Integer? = null;
    var forms_switchable: Boolean  = false;
    var is_legendary: Boolean  = false;
    var is_mythical: Boolean  = false;
    var order: Integer? = null;
    var conquest_order: Integer? = null;
}