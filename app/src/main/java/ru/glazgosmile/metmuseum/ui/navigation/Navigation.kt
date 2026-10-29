package ru.glazgosmile.metmuseum.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object List : Screen("list")
    data object Home : Screen("home")
    data object Settings : Screen("settings")
    data object Detail : Screen("detail/{objectID}") {
        fun createRoute(objectID: Int) = "detail/$objectID"
    }
    data object Favorites : Screen("favorites")
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screen.Settings.route,
        label = "Настройки",
        icon = Icons.Default.Settings
    ),
    BottomNavItem(
        route = Screen.List.route,
        label = "Список",
        icon = Icons.Default.List
    ),
    BottomNavItem(
        route = Screen.Favorites.route,
        label = "Избранное",
        icon = Icons.Default.Favorite
    ),
    BottomNavItem(
        route = Screen.Home.route,
        label = "Домой",
        icon = Icons.Default.Home
    ),
)