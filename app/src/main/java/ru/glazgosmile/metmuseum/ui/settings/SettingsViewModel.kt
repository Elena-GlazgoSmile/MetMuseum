package ru.glazgosmile.metmuseum.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.data.local.SettingsRepositoryImpl
import ru.glazgosmile.metmuseum.domain.BadgeManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val badgeManager: BadgeManager
) : ViewModel() {

    fun saveSettings(query: String, hasImages: Boolean, onSuccess: () -> Unit) {
        viewModelScope.launch {
            settingsRepository.saveSettings(query, hasImages)
            val isDefault = (query == "painting" && hasImages)
            badgeManager.isBadgeVisible.value = !isDefault
            onSuccess()
        }
    }
}