package com.example.novahumanitasu.ui.screens.otros

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuUniversitarioScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(WindowInsets.statusBars.asPaddingValues())) {
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Menu Universitario",
                    fontSize = 22.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("menuRegular") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA))
            ) {
                Text("Menu regular", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Filled.Restaurant, contentDescription = null, tint = Color.White)
            }
            Button(
                onClick = { navController.navigate("menuVegetariano") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA))
            ) {
                Text("Menu vegetariano", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Filled.Eco, contentDescription = null, tint = Color.White)
            }
            Button(
                onClick = { navController.navigate("otrosMenus") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA))
            ) {
                Text("Otros menus", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Filled.MoreHoriz, contentDescription = null, tint = Color.White)
            }
        }
    }
} 