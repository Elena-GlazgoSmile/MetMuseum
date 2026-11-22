package ru.glazgosmile.metmuseum.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.glazgosmile.metmuseum.domain.model.UserProfile

interface ProfileRepository {
    val profileFlow: Flow<UserProfile>
    suspend fun updateProfile(profile: UserProfile)
    suspend fun saveAvatar(bytes: ByteArray): String
}