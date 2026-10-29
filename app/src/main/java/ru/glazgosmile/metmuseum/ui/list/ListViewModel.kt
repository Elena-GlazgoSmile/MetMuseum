package ru.glazgosmile.metmuseum.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    private var searchJob: Job? = null

    private val _query = MutableStateFlow("painting")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        viewModelScope.launch {
            val savedQuery = settingsRepository.queryFlow.first()
            val savedHasImages = settingsRepository.hasImagesFlow.first()
            _query.value = savedQuery
            executeSearch(savedQuery, savedHasImages)
        }
    }

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            val currentHasImages = settingsRepository.hasImagesFlow.first()

            executeSearch(newQuery, currentHasImages)

            settingsRepository.saveSettings(newQuery, currentHasImages)
        }
    }

    private fun executeSearch(query: String, hasImages: Boolean) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            try {
                val artworks = getArtworksUseCase(query = query, hasImages = hasImages, limit = 15)
                _uiState.value = ListUiState.Success(artworks)
            } catch (e: Exception) {
                if (e is kotlinx.coroutines.CancellationException) return@launch
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