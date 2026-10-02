package ru.glazgosmile.metmuseum.ui.list

import androidx.lifecycle.ViewModel
import ru.glazgosmile.metmuseum.data.model.Artwork
import ru.glazgosmile.metmuseum.data.repository.ArtworkRepository

class ListViewModel : ViewModel() {

    val artworks: List<Artwork> = ArtworkRepository.getAllArtworks()
}