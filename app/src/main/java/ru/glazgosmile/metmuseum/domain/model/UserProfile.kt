package ru.glazgosmile.metmuseum.domain.model

data class UserProfile(
    val fullName: String = "",
    val position: String = "",
    val resumeUrl: String = "",
    val avatarPath: String? = null
)