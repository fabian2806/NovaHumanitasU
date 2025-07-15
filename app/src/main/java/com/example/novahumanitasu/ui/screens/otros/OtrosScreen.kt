package com.example.novahumanitasu.ui.screens.otros

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.novahumanitasu.components.BottomNavBar
import androidx.compose.ui.platform.LocalContext
import com.example.novahumanitasu.model.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OtrosScreen(
    navController: NavController,
    selectedIndex: Int = 4,
    onNavSelected: (Int) -> Unit = {}
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(WindowInsets.statusBars.asPaddingValues())) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart)
                        .clickable { navController.popBackStack() },
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Opciones",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("ReservaCubiculoScreen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA))
                ) {
                    Text("Reserva de Cubículo", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Filled.Book, contentDescription = null, tint = Color.White)
                }

                Button(
                    onClick = { navController.navigate("cuotasAcademicas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA))
                ) {
                    Text("Cuotas Académicas", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Filled.Science, contentDescription = null, tint = Color.White)
                }

                Button(
                    onClick = { navController.navigate("menuUniversitario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA))
                ) {
                    Text("Menú Universitario", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Filled.Fastfood, contentDescription = null, tint = Color.White)
                }
            }

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        SessionManager.setLoggedIn(context, false)
                    }
                    navController.navigate("login") {
                        popUpTo("otros") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Cerrar sesión", color = Color.White)
            }
        }
    }
}

