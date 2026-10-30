package ru.glazgosmile.metmuseum.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.data.local.entity.toEntity
import ru.glazgosmile.metmuseum.domain.model.Artwork
import ru.glazgosmile.metmuseum.domain.repository.FavoritesRepository
import ru.glazgosmile.metmuseum.domain.usecase.GetArtworkByIdUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getArtworkByIdUseCase: GetArtworkByIdUseCase,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadArtwork(objectID: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val artwork = getArtworkByIdUseCase(objectID)
                _uiState.value = DetailUiState.Success(artwork)

                favoritesRepository.isFavorite(objectID).collect { isFav ->
                    _isFavorite.value = isFav
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun toggleFavorite(artwork: Artwork) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoritesRepository.removeFavorite(artwork.objectID)
            } else {
                favoritesRepository.addFavorite(artwork)
            }
        }
    }
}