package com.example.gestionouvrier.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestionouvrier.ui.screens.AddOuvrierScreen
import com.example.gestionouvrier.ui.screens.EditOuvrierScreen
import com.example.gestionouvrier.ui.screens.HomeScreen
import com.example.gestionouvrier.ui.screens.ListOuvriersScreen
import com.example.gestionouvrier.ui.screens.PresenceScreen
import com.example.gestionouvrier.ui.screens.RapportScreen
import com.example.gestionouvrier.viewmodel.OuvrierViewModel
import com.example.gestionouvrier.viewmodel.OuvrierViewModelFactory

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object List : Screen("list")
    object Presence : Screen("presence")
    object Rapport : Screen("rapport")
    object Edit : Screen("edit/{ouvrierId}") {
        fun createRoute(id: Int) = "edit/$id"
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext as Application

    // Initialise ViewModel via factory
    val factory = OuvrierViewModelFactory(context)
    val viewModel: OuvrierViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Add.route) {
            AddOuvrierScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Screen.List.route) {
            ListOuvriersScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(Screen.Presence.route) {
            PresenceScreen(
                viewModel = viewModel
            )
        }
        composable(Screen.Rapport.route) {
            RapportScreen(
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.Edit.route,
            arguments = listOf(navArgument("ouvrierId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("ouvrierId") ?: return@composable
            EditOuvrierScreen(
                navController = navController,
                viewModel = viewModel,
                ouvrierId = id
            )
        }
    }
}
