package ru.glazgosmile.metmuseum.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.glazgosmile.metmuseum.data.mapper.toDomain
import ru.glazgosmile.metmuseum.data.remote.ArtworkApi
import ru.glazgosmile.metmuseum.domain.model.Artwork
import ru.glazgosmile.metmuseum.domain.repository.ArtworkRepository
import kotlinx.coroutines.delay
class ArtworkRepositoryImpl(
    private val api: ArtworkApi
) : ArtworkRepository {

    override suspend fun getArtworks(query: String, hasImages: Boolean, limit: Int): List<Artwork> =
        withContext(Dispatchers.IO) {
            val response = api.searchArtworks(query = query, hasImages = hasImages, limit = limit)
            val ids = response.objectIDs.orEmpty().take(limit)
            ids.mapNotNull { id -> delay(300)
                try { api.getArtworkById(id).toDomain() } catch (e: Exception) { null } }
        }

    override suspend fun getArtworkById(objectID: Int): Artwork =
        withContext(Dispatchers.IO) {
            api.getArtworkById(objectID).toDomain()
        }
}