package ru.glazgosmile.metmuseum.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.data.remote.NetworkModule
import ru.glazgosmile.metmuseum.data.repository.ArtworkRepositoryImpl
import ru.glazgosmile.metmuseum.domain.usecase.GetArtworksUseCase

class ListViewModel : ViewModel() {

    private val repository = ArtworkRepositoryImpl(NetworkModule.artworkApi)
    private val getArtworksUseCase = GetArtworksUseCase(repository)

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("painting")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        search(_query.value)
    }

    fun search(query: String) {
        _query.value = query
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
        search(_query.value)
    }
}