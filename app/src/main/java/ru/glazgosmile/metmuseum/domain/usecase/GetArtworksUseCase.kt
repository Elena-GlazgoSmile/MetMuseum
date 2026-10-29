package ru.glazgosmile.metmuseum.domain.usecase

import ru.glazgosmile.metmuseum.domain.model.Artwork
import ru.glazgosmile.metmuseum.domain.repository.ArtworkRepository
import javax.inject.Inject

class GetArtworksUseCase @Inject constructor(
    private val repository: ArtworkRepository
) {
    suspend operator fun invoke(query: String, hasImages: Boolean, limit: Int = 20): List<Artwork> =
        repository.getArtworks(query, hasImages, limit)
}