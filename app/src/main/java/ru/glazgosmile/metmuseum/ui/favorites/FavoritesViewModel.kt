package ru.glazgosmile.metmuseum.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.data.local.FavoriteDao
import ru.glazgosmile.metmuseum.data.local.entity.toDomain
import ru.glazgosmile.metmuseum.domain.model.Artwork
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteDao: FavoriteDao
) : ViewModel() {

    val favorites: StateFlow<List<Artwork>> = favoriteDao.getAllFavorites()
        .map { entities -> entities.map { it.toDomain() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeFavorite(objectID: Int) {
        viewModelScope.launch {
            favoriteDao.removeFavorite(objectID)
        }
    }
}