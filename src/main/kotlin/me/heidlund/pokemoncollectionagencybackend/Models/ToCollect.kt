package me.heidlund.pokemoncollectionagencybackend.Models

data class ToCollect (
    var Pokemon:Pokemon,
    var encounters: List<String>
)
