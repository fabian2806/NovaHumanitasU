package com.example.novahumanitasu.ui.screens.notificaciones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.novahumanitasu.R
import com.example.novahumanitasu.components.CursoCard
import com.example.novahumanitasu.model.DataCurso
import com.example.novahumanitasu.ui.viewModels.CursoViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.novahumanitasu.model.entities.ReminderLogEntity
import com.example.novahumanitasu.ui.viewModels.ReminderLogViewModel
import java.time.format.DateTimeFormatter



@Composable
fun NotificacionesScreen(
    navController: NavController,
    viewModel: ReminderLogViewModel = hiltViewModel()
) {
    val reminders by viewModel.allReminders.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 0.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Notificaciones",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        if (reminders.isEmpty()) {
            Text("No hay recordatorios registrados aún.")
        } else {
            LazyColumn {
                items(reminders, key = { it.id }) { reminder -> // Usar 'id' como clave para mejor rendimiento
                    ReminderLogItem(reminder = reminder)
                }
            }
        }
    }
}

@Composable
fun ReminderLogItem(reminder: ReminderLogEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Usa fillMaxWidth en lugar de fillMaxSize en un LazyColumn item
            .padding(vertical = 6.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = reminder.reminderMessage,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Curso: ${reminder.horarioCodigoCurso}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Clase: ${reminder.horarioFecha.format(DateTimeFormatter.ofPattern("dd/MM"))} a las ${reminder.horarioHoraInicio.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Activado a: ${reminder.reminderScheduledTime.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Registrado en DB: ${reminder.timestampLogged.format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss"))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}