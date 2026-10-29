package ru.glazgosmile.metmuseum.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.data.local.SettingsRepositoryImpl
import ru.glazgosmile.metmuseum.domain.usecase.GetArtworksUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getArtworksUseCase: GetArtworksUseCase,
    private val settingsRepository: SettingsRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    val query: StateFlow<String> = settingsRepository.queryFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "painting")

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.queryFlow,
                settingsRepository.hasImagesFlow
            ) { q, hasImages ->
                Pair(q, hasImages)
            }.collect { (q, hasImages) ->
                executeSearch(q, hasImages)
            }
        }
    }

    fun search(newQuery: String) {
        viewModelScope.launch {
            val currentHasImages = settingsRepository.hasImagesFlow.first()
            settingsRepository.saveSettings(newQuery, currentHasImages)
        }
    }

    private fun executeSearch(query: String, hasImages: Boolean) {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            try {
                val artworks = getArtworksUseCase(query = query, hasImages = hasImages, limit = 15)
                _uiState.value = ListUiState.Success(artworks)
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun retry() {
        viewModelScope.launch {
            val currentQuery = settingsRepository.queryFlow.first()
            val currentHasImages = settingsRepository.hasImagesFlow.first()
            executeSearch(currentQuery, currentHasImages)
        }
    }
}