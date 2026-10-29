package ru.glazgosmile.metmuseum.domain.usecase

import ru.glazgosmile.metmuseum.domain.model.Artwork
import ru.glazgosmile.metmuseum.domain.repository.ArtworkRepository
import javax.inject.Inject

class GetArtworkByIdUseCase @Inject constructor(
    private val repository: ArtworkRepository
) {
    suspend operator fun invoke(objectID: Int): Artwork =
        repository.getArtworkById(objectID)
}