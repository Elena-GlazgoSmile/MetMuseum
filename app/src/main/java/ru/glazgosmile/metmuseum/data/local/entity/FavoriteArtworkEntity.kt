package ru.glazgosmile.metmuseum.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.glazgosmile.metmuseum.domain.model.Artwork

@Entity(tableName = "favorites")
data class FavoriteArtworkEntity(
    @PrimaryKey val objectID: Int,
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

fun Artwork.toEntity(): FavoriteArtworkEntity = FavoriteArtworkEntity(
    objectID = objectID,
    title = title,
    artistDisplayName = artistDisplayName,
    objectDate = objectDate,
    primaryImageSmall = primaryImageSmall,
    primaryImage = primaryImage,
    medium = medium,
    dimensions = dimensions,
    creditLine = creditLine,
    department = department,
    culture = culture,
    country = country,
    classification = classification
)

fun FavoriteArtworkEntity.toDomain(): Artwork = Artwork(
    objectID = objectID,
    title = title,
    artistDisplayName = artistDisplayName,
    objectDate = objectDate,
    primaryImageSmall = primaryImageSmall,
    primaryImage = primaryImage,
    medium = medium,
    dimensions = dimensions,
    creditLine = creditLine,
    department = department,
    culture = culture,
    country = country,
    classification = classification
)