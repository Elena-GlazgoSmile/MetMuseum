package ru.glazgosmile.metmuseum.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.data.local.SettingsRepositoryImpl
import ru.glazgosmile.metmuseum.domain.BadgeManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val badgeManager: BadgeManager
) : ViewModel() {

    private val _query = MutableStateFlow("painting")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _hasImages = MutableStateFlow(true)
    val hasImages: StateFlow<Boolean> = _hasImages.asStateFlow()

    init {
        viewModelScope.launch {
            _query.value = settingsRepository.queryFlow.first()
            _hasImages.value = settingsRepository.hasImagesFlow.first()
        }
    }

    fun onQueryChange(newQuery: String) { _query.value = newQuery }
    fun onHasImagesChange(newValue: Boolean) {
        _hasImages.value = newValue
        viewModelScope.launch {
            settingsRepository.saveSettings(_query.value, newValue)
        }
    }

    fun saveSettings(onSuccess: () -> Unit) {
        viewModelScope.launch {
            settingsRepository.saveSettings(_query.value, _hasImages.value)

            val isDefault = (_query.value == "painting" && _hasImages.value)
            badgeManager.isBadgeVisible.value = !isDefault

            onSuccess()
        }
    }
}