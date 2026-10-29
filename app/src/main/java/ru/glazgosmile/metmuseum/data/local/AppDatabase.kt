package ru.glazgosmile.metmuseum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.glazgosmile.metmuseum.data.local.entity.FavoriteArtworkEntity

@Database(entities = [FavoriteArtworkEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}