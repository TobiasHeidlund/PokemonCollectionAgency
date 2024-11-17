package me.heidlund.pokemoncollectionagencybackend.Models

import com.squareup.moshi.JsonClass
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "encounter_methods")
class EncounterMethod() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    var identifier:String = "";
}