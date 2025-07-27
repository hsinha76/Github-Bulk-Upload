package com.hsdroid.bulkrepouploadforgithub

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hsdroid.bulkrepouploadforgithub.ui.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.hsdroid.bulkrepouploadforgithub.ui.intro.IntroScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavGraph()
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController, Screen.Intro.title) {
        composable(Screen.Intro.title) {
            IntroScreen(onNextClick = {
                navController.navigate(Screen.Home.title)
            })
        }

        composable(Screen.Home.title) {
            AnimatedVisibility(true) {
                HomeScreen()
            }
        }
    }
}

enum class Screen(val title: String) {
    Intro(title = "Intro"),
    Home(title = "Home")
}