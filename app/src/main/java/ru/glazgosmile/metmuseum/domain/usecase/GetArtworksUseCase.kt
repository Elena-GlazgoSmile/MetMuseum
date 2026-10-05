package ru.glazgosmile.metmuseum.domain.usecase

import ru.glazgosmile.metmuseum.domain.model.Artwork
import ru.glazgosmile.metmuseum.domain.repository.ArtworkRepository

class GetArtworksUseCase(
    private val repository: ArtworkRepository
) {
    suspend operator fun invoke(query: String = "painting", limit: Int = 20): List<Artwork> =
        repository.getArtworks(query, limit)
}