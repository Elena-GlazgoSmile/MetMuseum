package ru.glazgosmile.metmuseum.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.glazgosmile.metmuseum.data.model.Artwork
import ru.glazgosmile.metmuseum.data.repository.ArtworkRepository

class DetailViewModel : ViewModel() {

    private val favorites = mutableSetOf<Int>()

    var isFavorite by mutableStateOf(false)
        private set

    fun getArtwork(objectID: Int): Artwork? {
        isFavorite = favorites.contains(objectID)
        return ArtworkRepository.getArtworkById(objectID)
    }

    fun toggleFavorite(objectID: Int) {
        if (favorites.contains(objectID)) {
            favorites.remove(objectID)
            isFavorite = false
        } else {
            favorites.add(objectID)
            isFavorite = true
        }
    }
}