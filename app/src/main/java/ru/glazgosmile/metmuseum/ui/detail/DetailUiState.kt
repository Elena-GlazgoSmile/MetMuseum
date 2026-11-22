package ru.glazgosmile.metmuseum.ui.detail

import ru.glazgosmile.metmuseum.domain.model.Artwork

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val artwork: Artwork) : DetailUiState
    data class Error(val message: String) : DetailUiState
}