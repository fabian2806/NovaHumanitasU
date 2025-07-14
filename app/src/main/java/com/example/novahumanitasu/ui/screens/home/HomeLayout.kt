package com.example.novahumanitasu.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.novahumanitasu.ui.screens.cursos.CursosScreen
import com.example.novahumanitasu.ui.screens.cursos.DetalleCursoScreen
import com.example.novahumanitasu.ui.screens.horarios.HorariosScreen
import com.example.novahumanitasu.ui.screens.notificaciones.NotificacionesScreen


@Composable
fun HomeLayout(navController: NavController){
    val bottomNavController = rememberNavController()
    val items = listOf(
        BottomNavItem.Courses,
        BottomNavItem.Calendar,
        BottomNavItem.Home,
        BottomNavItem.Notifications,
        BottomNavItem.More
    )

    Scaffold(
        bottomBar = {
            NavigationBar (
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                bottomNavController.navigate(item.route) {
                                    popUpTo("home/main") { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                tint = if (isSelected) Color.Gray else MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = null, //Para que no haya label debajo del ícono
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            //1. Cursos:
            composable(BottomNavItem.Courses.route) {
                CursosScreen(bottomNavController)
            }

            composable("detalleCurso/{codigo}") { backStackEntry ->
                val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
                DetalleCursoScreen(codigo = codigo, navController = bottomNavController)
            }

            //2. Calendario:
            composable(BottomNavItem.Calendar.route) {
                HorariosScreen(navController)
            }

            //3. Home:
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController)
            }

            //4. Notificaciones:
            composable(BottomNavItem.Notifications.route) {
                NotificacionesScreen(navController)
            }

            //5. Más:
            composable(BottomNavItem.More.route) {
                HomeScreen(navController)
            }
        }

    }

}


