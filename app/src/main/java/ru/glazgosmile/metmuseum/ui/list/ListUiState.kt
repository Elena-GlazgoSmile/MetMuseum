package ru.glazgosmile.metmuseum.ui.list

import ru.glazgosmile.metmuseum.domain.model.Artwork

sealed interface ListUiState {
    data object Loading : ListUiState
    data class Success(val artworks: List<Artwork>) : ListUiState
    data class Error(val message: String) : ListUiState
}