package ru.glazgosmile.metmuseum.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.glazgosmile.metmuseum.domain.model.UserProfile
import ru.glazgosmile.metmuseum.domain.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    val profile: StateFlow<UserProfile> = repository.profileFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    fun updateProfile(fullName: String, position: String, resumeUrl: String, avatarBytes: ByteArray?) {
        viewModelScope.launch {
            var avatarPath: String? = null
            if (avatarBytes != null) {
                avatarPath = repository.saveAvatar(avatarBytes)
            }

            val currentProfile = profile.value
            val finalPath = avatarPath ?: currentProfile.avatarPath

            repository.updateProfile(
                UserProfile(
                    fullName = fullName,
                    position = position,
                    resumeUrl = resumeUrl,
                    avatarPath = finalPath
                )
            )
        }
    }
}