package com.example.novahumanitasu.ui.screens.otros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Datos hardcodeados
private val cuotas = listOf(
    CuotaAcademica(3333.0, "12/12/2025", "Pendiente"),
    CuotaAcademica(4444.0, "12/12/2025", "Pagada"),
    CuotaAcademica(5555.0, "12/12/2025", "Cancelada"),
    CuotaAcademica(6666.0, "12/12/2025", "Pagada")
)

data class CuotaAcademica(val monto: Double, val fechaExp: String, val estado: String)

@Composable
fun CuotasAcademicasScreen(navController: NavController) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Cuotas académicas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cuotas) { cuota ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Monto: $${String.format("%.2f", cuota.monto)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Fecha expiracion: ${cuota.fechaExp}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Estado: ${cuota.estado}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = when (cuota.estado) {
                                "Pagada" -> Color(0xFF388E3C)
                                "Pendiente" -> Color(0xFFFBC02D)
                                "Cancelada" -> Color(0xFFD32F2F)
                                else -> Color.Gray
                            }
                        )
                    }
                }
            }
        }
    }
} 