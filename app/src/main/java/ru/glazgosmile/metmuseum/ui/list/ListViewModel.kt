package ru.glazgosmile.metmuseum.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
            ) { query, hasImages ->
                Pair(query, hasImages)
            }.collect { (query, hasImages) ->
                executeSearch(query)
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            val currentHasImages = settingsRepository.hasImagesFlow.first()
            settingsRepository.saveSettings(query, currentHasImages)
        }
    }

    private fun executeSearch(query: String) {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            try {
                val artworks = getArtworksUseCase(query = query, limit = 15)
                _uiState.value = ListUiState.Success(artworks)
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun retry() {
        val currentQuery = query.value
        executeSearch(currentQuery)
    }
}