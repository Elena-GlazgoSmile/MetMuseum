package ru.glazgosmile.metmuseum.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.glazgosmile.metmuseum.data.local.entity.FavoriteArtworkEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY objectID DESC")
    fun getAllFavorites(): Flow<List<FavoriteArtworkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(artwork: FavoriteArtworkEntity)

    @Query("DELETE FROM favorites WHERE objectID = :id")
    suspend fun removeFavorite(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE objectID = :id)")
    fun isFavorite(id: Int): Flow<Boolean>
}