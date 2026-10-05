package ru.glazgosmile.metmuseum.domain.repository

import ru.glazgosmile.metmuseum.domain.model.Artwork

interface ArtworkRepository {
    suspend fun getArtworks(query: String = "painting", limit: Int = 20): List<Artwork>
    suspend fun getArtworkById(objectID: Int): Artwork
}