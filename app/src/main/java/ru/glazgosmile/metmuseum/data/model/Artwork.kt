package ru.glazgosmile.metmuseum.data.model

data class Artwork(
    val objectID: Int,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String,
    val primaryImageSmall: String,
    val primaryImage: String,
    val medium: String,
    val dimensions: String,
    val creditLine: String,
    val department: String,
    val culture: String,
    val country: String,
    val classification: String
)
