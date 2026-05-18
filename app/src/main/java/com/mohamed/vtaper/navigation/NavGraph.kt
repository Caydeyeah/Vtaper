package com.mohamed.vtaper.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mohamed.vtaper.ui.nutrition.NutritionScreen
import com.mohamed.vtaper.ui.onboarding.OnboardingScreen
import com.mohamed.vtaper.ui.reboot.RebootScreen
import com.mohamed.vtaper.ui.workout.WorkoutScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnboardingScreen(onComplete = {
                navController.navigate("main") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }
        composable("main") {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Workout,
        Screen.Nutrition,
        Screen.Reboot
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Workout.route, Modifier.padding(innerPadding)) {
            composable(Screen.Workout.route) { WorkoutScreen() }
            composable(Screen.Nutrition.route) { NutritionScreen() }
            composable(Screen.Reboot.route) { RebootScreen() }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Workout : Screen("workout", "Workout", Icons.Filled.PlayArrow)
    object Nutrition : Screen("nutrition", "Nutrition", Icons.Filled.Star)
    object Reboot : Screen("reboot", "Reboot", Icons.Filled.DateRange)
}
