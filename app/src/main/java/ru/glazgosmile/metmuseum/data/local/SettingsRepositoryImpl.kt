package ru.glazgosmile.metmuseum.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepositoryImpl @Inject constructor(private val context: Context) {
    companion object {
        val QUERY_KEY = stringPreferencesKey("query")
        val HAS_IMAGES_KEY = booleanPreferencesKey("has_images")
    }

    val queryFlow: Flow<String> = context.dataStore.data.map { it[QUERY_KEY] ?: "painting" }
    val hasImagesFlow: Flow<Boolean> = context.dataStore.data.map { it[HAS_IMAGES_KEY] ?: true }

    suspend fun saveSettings(query: String, hasImages: Boolean) {
        context.dataStore.edit {
            it[QUERY_KEY] = query
            it[HAS_IMAGES_KEY] = hasImages
        }
    }
}