package ru.glazgosmile.metmuseum.data.mapper

import ru.glazgosmile.metmuseum.data.remote.dto.ArtworkDto
import ru.glazgosmile.metmuseum.domain.model.Artwork

fun ArtworkDto.toDomain(): Artwork = Artwork(
    objectID = objectID,
    title = title.orEmpty(),
    artistDisplayName = artistDisplayName.orEmpty(),
    objectDate = objectDate.orEmpty(),
    primaryImageSmall = primaryImageSmall.orEmpty(),
    primaryImage = primaryImage.orEmpty(),
    medium = medium.orEmpty(),
    dimensions = dimensions.orEmpty(),
    creditLine = creditLine.orEmpty(),
    department = department.orEmpty(),
    culture = culture.orEmpty(),
    country = country.orEmpty(),
    classification = classification.orEmpty()
)