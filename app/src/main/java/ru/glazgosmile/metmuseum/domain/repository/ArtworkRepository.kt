package ru.glazgosmile.metmuseum.domain.repository

import ru.glazgosmile.metmuseum.domain.model.Artwork

interface ArtworkRepository {
    suspend fun getArtworks(query: String, hasImages: Boolean, limit: Int): List<Artwork>
    suspend fun getArtworkById(objectID: Int): Artwork
}