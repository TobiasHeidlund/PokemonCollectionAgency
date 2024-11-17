package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import kotlin.jvm.Transient


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "pokedex_pokemon")
class PokedexPokemon() {
     constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int = 0;
    @ManyToOne
    @JoinColumn(name = "pokemon_species", nullable = false, unique = true)
    var species: PokemonSpecies? = null;
    var isCollected = 0;
    @Transient
    var canBeEvolvedTo: List<PokedexPokemon>? = null;

}