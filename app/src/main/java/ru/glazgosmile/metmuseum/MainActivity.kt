package ru.glazgosmile.metmuseum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.glazgosmile.metmuseum.ui.FavoritesScreen
import ru.glazgosmile.metmuseum.ui.HomeScreen
import ru.glazgosmile.metmuseum.ui.SettingsScreen
import ru.glazgosmile.metmuseum.ui.detail.DetailScreen
import ru.glazgosmile.metmuseum.ui.list.ListScreen
import ru.glazgosmile.metmuseum.ui.navigation.Screen
import ru.glazgosmile.metmuseum.ui.navigation.bottomNavItems
import ru.glazgosmile.metmuseum.ui.theme.MetMuseumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetMuseumTheme {
                MetMuseumApp()
            }
        }
    }
}

@Composable
fun MetMuseumApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.List.route,
        Screen.Favorites.route,
        Screen.Settings.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.List.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }

            composable(Screen.List.route) {
                ListScreen(
                    onArtworkClick = { objectID ->
                        navController.navigate(Screen.Detail.createRoute(objectID))
                    }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen()
            }

            composable(Screen.Settings.route) {
                SettingsScreen()
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("objectID") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val objectID = backStackEntry.arguments?.getInt("objectID") ?: 0
                DetailScreen(
                    objectID = objectID,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}