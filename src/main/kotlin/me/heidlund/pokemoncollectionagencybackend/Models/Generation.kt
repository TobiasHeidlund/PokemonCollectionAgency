package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "generations")
class Generation() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    var identifier:String = "";
    @ManyToOne
    @JoinColumn(name = "main_region_id", nullable = false)
    var region: Region? = null;
}