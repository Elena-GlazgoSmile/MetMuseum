package ru.glazgosmile.metmuseum.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.data.remote.NetworkModule
import ru.glazgosmile.metmuseum.data.repository.ArtworkRepositoryImpl
import ru.glazgosmile.metmuseum.domain.usecase.GetArtworkByIdUseCase

class DetailViewModel : ViewModel() {

    private val repository = ArtworkRepositoryImpl(NetworkModule.artworkApi)
    private val getArtworkByIdUseCase = GetArtworkByIdUseCase(repository)

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val favorites = mutableSetOf<Int>()
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadArtwork(objectID: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val artwork = getArtworkByIdUseCase(objectID)
                _uiState.value = DetailUiState.Success(artwork)
                _isFavorite.value = favorites.contains(objectID)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun toggleFavorite(objectID: Int) {
        if (favorites.contains(objectID)) {
            favorites.remove(objectID)
            _isFavorite.value = false
        } else {
            favorites.add(objectID)
            _isFavorite.value = true
        }
    }
}