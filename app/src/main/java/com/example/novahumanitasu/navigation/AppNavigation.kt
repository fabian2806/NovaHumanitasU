package com.example.novahumanitasu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.novahumanitasu.ui.screens.cursos.DetalleCursoScreen
import com.example.novahumanitasu.ui.screens.home.HomeLayout
import com.example.novahumanitasu.ui.screens.login.LoginScreen
import com.example.novahumanitasu.ui.screens.login.WelcomeScreen
import com.example.novahumanitasu.ui.screens.otros.ReservaCubiculoScreen
import com.example.novahumanitasu.ui.screens.reserva.HistorialReservasScreen
import com.example.novahumanitasu.ui.screens.otros.CuotasAcademicasScreen
import com.example.novahumanitasu.ui.screens.otros.MenuUniversitarioScreen
import com.example.novahumanitasu.ui.screens.otros.MenuRegularScreen
import com.example.novahumanitasu.ui.screens.otros.MenuVegetarianoScreen
import com.example.novahumanitasu.ui.screens.otros.OtrosMenusScreen

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

        // En tu NavHost:
        composable("ReservaCubiculoScreen") {
            ReservaCubiculoScreen(navController)
        }
        composable("historialReservas") {
            HistorialReservasScreen(navController)
        }
        composable("cuotasAcademicas") {
            CuotasAcademicasScreen(navController)
        }
        composable("menuUniversitario") {
            MenuUniversitarioScreen(navController)
        }
        composable("menuRegular") {
            MenuRegularScreen(navController)
        }
        composable("menuVegetariano") {
            MenuVegetarianoScreen(navController)
        }
        composable("otrosMenus") {
            OtrosMenusScreen(navController)
        }


    }
}