package ru.glazgosmile.metmuseum.domain

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BadgeManager @Inject constructor() {
    val isBadgeVisible = MutableStateFlow(false)
}