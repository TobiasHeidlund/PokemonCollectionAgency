package me.heidlund.pokemoncollectionagencybackend.Models

import com.squareup.moshi.JsonClass
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "version_groups")
class VersionGroup() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    var identifier:String = "";
    @ManyToOne
    @JoinColumn(name = "generation_id", nullable = false)
    var generationId: Generation? = null;
}