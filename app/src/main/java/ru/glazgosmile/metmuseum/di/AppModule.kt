package ru.glazgosmile.metmuseum.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.glazgosmile.metmuseum.data.local.AppDatabase
import ru.glazgosmile.metmuseum.data.local.FavoriteDao
import ru.glazgosmile.metmuseum.data.remote.ArtworkApi
import ru.glazgosmile.metmuseum.data.remote.NetworkModule
import ru.glazgosmile.metmuseum.data.repository.ArtworkRepositoryImpl
import ru.glazgosmile.metmuseum.domain.repository.ArtworkRepository
import javax.inject.Singleton
import ru.glazgosmile.metmuseum.data.local.SettingsRepositoryImpl
import ru.glazgosmile.metmuseum.data.repository.FavoritesRepositoryImpl
import ru.glazgosmile.metmuseum.data.repository.ProfileRepositoryImpl
import ru.glazgosmile.metmuseum.domain.repository.FavoritesRepository
import ru.glazgosmile.metmuseum.domain.repository.ProfileRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideArtworkApi(): ArtworkApi = NetworkModule.artworkApi

    @Provides
    @Singleton
    fun provideArtworkRepository(api: ArtworkApi): ArtworkRepository = ArtworkRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "met_museum_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(favoriteDao: FavoriteDao): FavoritesRepository {
        return FavoritesRepositoryImpl(favoriteDao)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(@ApplicationContext context: Context): ProfileRepository {
        return ProfileRepositoryImpl(context)
    }

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()
}