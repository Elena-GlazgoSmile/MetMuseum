package ru.glazgosmile.metmuseum.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.glazgosmile.metmuseum.data.local.entity.FavoriteArtworkEntity
import ru.glazgosmile.metmuseum.domain.model.Artwork

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<Artwork>>
    fun isFavorite(objectID: Int): Flow<Boolean>
    suspend fun addFavorite(artwork: Artwork)
    suspend fun removeFavorite(objectID: Int)
}