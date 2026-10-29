package ru.glazgosmile.metmuseum.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.glazgosmile.metmuseum.data.local.FavoriteDao
import ru.glazgosmile.metmuseum.data.local.entity.FavoriteArtworkEntity
import ru.glazgosmile.metmuseum.data.local.entity.toDomain
import ru.glazgosmile.metmuseum.data.local.entity.toEntity
import ru.glazgosmile.metmuseum.domain.model.Artwork
import ru.glazgosmile.metmuseum.domain.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoritesRepository {

    override fun getAllFavorites(): Flow<List<Artwork>> =
        favoriteDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun isFavorite(objectID: Int): Flow<Boolean> =
        favoriteDao.isFavorite(objectID)

    override suspend fun addFavorite(artwork: Artwork) =
        favoriteDao.addFavorite(artwork.toEntity())

    override suspend fun removeFavorite(objectID: Int) =
        favoriteDao.removeFavorite(objectID)
}