package ru.glazgosmile.metmuseum.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.glazgosmile.metmuseum.data.mapper.toDomain
import ru.glazgosmile.metmuseum.data.remote.ArtworkApi
import ru.glazgosmile.metmuseum.domain.model.Artwork
import ru.glazgosmile.metmuseum.domain.repository.ArtworkRepository

class ArtworkRepositoryImpl(
    private val api: ArtworkApi
) : ArtworkRepository {

    override suspend fun getArtworks(query: String, limit: Int): List<Artwork> =
        withContext(Dispatchers.IO) {
            val response = api.searchArtworks(
                query = query,
                offset = 0,
                limit = limit
            )
            val ids = response.objectIDs.orEmpty().take(limit)
            ids.map { id -> api.getArtworkById(id).toDomain() }
        }

    override suspend fun getArtworkById(objectID: Int): Artwork =
        withContext(Dispatchers.IO) {
            api.getArtworkById(objectID).toDomain()
        }
}