package com.example.novahumanitasu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.novahumanitasu.ui.screens.cursos.DetalleCursoScreen
import com.example.novahumanitasu.ui.screens.home.HomeLayout
import com.example.novahumanitasu.ui.screens.login.LoginScreen
import com.example.novahumanitasu.ui.screens.login.WelcomeScreen

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome"){

        //Rutas únicas
        composable("welcome"){
            WelcomeScreen(navController)
        }
        composable("login"){
            LoginScreen(navController)
        }
        composable("home"){
            HomeLayout(navController)
        }

    }
}