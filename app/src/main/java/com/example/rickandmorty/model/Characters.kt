package com.example.rickandmorty.model


import java.io.Serializable

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val type: String,
    val gender: String,
    val species: String,
    val image: String,
    val originName: Origin?,
    val locationName: Location?
) : Serializable

data class Origin(
    val name: String
) : Serializable

data class Location(
    val name: String
) : Serializable