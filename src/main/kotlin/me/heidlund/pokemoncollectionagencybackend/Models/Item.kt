package me.heidlund.pokemoncollectionagencybackend.Models

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
@Table(name = "items")
class Item() {
    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    var id:Int = 0;
    var identifier: String = ""
    var category_id: Int = 0;
    var cost: Int = 0;
    var fling_power: Int = 0;
    var fling_effect_id: Integer? = null;

}
