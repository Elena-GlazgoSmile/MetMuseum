package ru.glazgosmile.metmuseum.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.glazgosmile.metmuseum.data.repository.ProfileRepositoryImpl
import ru.glazgosmile.metmuseum.domain.repository.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    @Singleton
    abstract fun bindProfileRepository(implementation: ProfileRepositoryImpl): ProfileRepository
}
