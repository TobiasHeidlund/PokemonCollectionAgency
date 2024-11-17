package me.heidlund.pokemoncollectionagencybackend.Models

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "versions")
class Version() {

    constructor(id: Int) : this() {
        this.id = id
    }
    @Id
    @Column(name="id")
    var id: Int = 0

    @ManyToOne
    @JoinColumn(name = "version_group_id", nullable = false)
    var versionGroup: VersionGroup? = null

    var identifier: String? = null

}