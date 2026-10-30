package ru.glazgosmile.metmuseum.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.glazgosmile.metmuseum.domain.model.UserProfile
import ru.glazgosmile.metmuseum.domain.repository.ProfileRepository
import java.io.File
import java.util.UUID
import javax.inject.Inject

private val Context.profileDataStore by preferencesDataStore(name = "user_profile")

class ProfileRepositoryImpl @Inject constructor(
    private val context: Context
) : ProfileRepository {

    companion object {
        val NAME_KEY = stringPreferencesKey("full_name")
        val RESUME_KEY = stringPreferencesKey("resume_url")
        val AVATAR_KEY = stringPreferencesKey("avatar_path")
        val POSITION_KEY = stringPreferencesKey("position")
    }

    override val profileFlow: Flow<UserProfile> = context.profileDataStore.data.map { prefs ->
        UserProfile(
            fullName = prefs[NAME_KEY] ?: "",
            position = prefs[POSITION_KEY] ?: "",
            resumeUrl = prefs[RESUME_KEY] ?: "",
            avatarPath = prefs[AVATAR_KEY]
        )
    }

    override suspend fun updateProfile(profile: UserProfile) {
        context.profileDataStore.edit { prefs ->
            prefs[NAME_KEY] = profile.fullName
            prefs[POSITION_KEY] = profile.position
            prefs[RESUME_KEY] = profile.resumeUrl
            profile.avatarPath?.let { prefs[AVATAR_KEY] = it }
        }
    }

    override suspend fun saveAvatar(bytes: ByteArray): String {
        val fileName = "avatar_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        file.writeBytes(bytes)
        return file.absolutePath
    }
}