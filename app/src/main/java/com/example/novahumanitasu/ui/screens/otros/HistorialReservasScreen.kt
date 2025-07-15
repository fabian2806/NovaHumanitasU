package com.example.novahumanitasu.ui.screens.reserva

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.novahumanitasu.model.entities.ReservaEntity
import com.example.novahumanitasu.ui.viewModels.ReservaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialReservasScreen(
    navController: NavController,
    viewModel: ReservaViewModel = hiltViewModel()
) {
    val reservas by viewModel.reservas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Reservas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                if (reservas.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay reservas registradas",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                } else {
                    items(reservas) { reserva ->
                        ReservaCard(
                            reserva = reserva,
                            onDelete = { viewModel.eliminarReserva(reserva.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReservaCard(
    reserva: ReservaEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Fecha: ${reserva.fecha}", fontWeight = FontWeight.Bold)
            Text("Hora: ${reserva.hora}")
            Text("Lugar: ${reserva.lugar}")
            Text("Participantes: ${reserva.numeroPersonas}")

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
