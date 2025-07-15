package com.example.novahumanitasu.ui.screens.notificaciones


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color // Importación necesaria para colores específicos
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
            .padding(vertical = 16.dp)
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
            Text(
                text = "No hay recordatorios registrados aún.",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // Asegura que la lista ocupe el espacio disponible
                contentPadding = PaddingValues(horizontal = 16.dp), // Añade el padding aquí
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(reminders, key = { it.id }) { reminder ->
                    ReminderLogItem(reminder = reminder)
                }
            }
        }
    }
}

@Composable
fun ReminderLogItem(reminder: ReminderLogEntity) {
    val firstLetter = reminder.tipoActividad
        .substringAfter(": ", " ")
        .firstOrNull()
        ?.uppercaseChar()
        ?.toString() ?: "C"

    Card(
        modifier = Modifier.fillMaxWidth(), // Simplemente ocupa el ancho disponible
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                // Usamos Alignment.Top para que el icono se alinee con la primera línea de texto si el texto ocupa varias líneas
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Icono circular con la letra
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        // Usamos un color azul específico para asegurar la visibilidad
                        .background(Color(0xFF0D6EFD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "P",
                        // Usamos Color.White para asegurar el contraste
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        // Aumentamos el tamaño de la fuente para que se vea mejor
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Columna para los detalles de texto
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = reminder.reminderMessage,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        // Eliminamos maxLines y overflow para permitir que el texto se ajuste
                    )

                    Text(
                        text = reminder.horarioHoraInicio.format(DateTimeFormatter.ofPattern("HH:mm")),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = reminder.horarioCodigoCurso,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }



            /*
            Text(
                text = "Activado a: ${reminder.reminderScheduledTime.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            */
        }
    }
}