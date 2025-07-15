package com.example.novahumanitasu.ui.screens.horarios

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.novahumanitasu.ui.viewModels.CursoViewModel
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.novahumanitasu.model.entities.HorarioEntity
import com.example.novahumanitasu.ui.viewModels.HorarioViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer

import com.example.novahumanitasu.components.DateCarousel
import com.example.novahumanitasu.components.MonthNavigator
import com.example.novahumanitasu.components.CalendarOverlay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.IconButton
import androidx.compose.runtime.produceState

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HorariosScreen(
    navController: NavController, // Si usas navegación
    horarioViewModel: HorarioViewModel = hiltViewModel(),
    cursoViewModel: CursoViewModel = hiltViewModel() // <--- NUEVO: Inyecta el CursoViewModel
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()

    // Estado para la fecha seleccionada
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showCalendarOverlay by remember { mutableStateOf(false) }
    val allCursos by cursoViewModel.cursos.collectAsState()
    var selectedCourseCode by remember { mutableStateOf("Todos") } // "Todos" para ver todos los horarios, o un código de curso específico

    // Observar los horarios basados en la fecha y el curso seleccionado
    val horarios by produceState(initialValue = emptyList<HorarioEntity>(), selectedDate, selectedCourseCode) {
        value = emptyList() // Limpia mientras espera
        val flow = if (selectedCourseCode == "Todos") {
            horarioViewModel.getHorariosPorFecha(selectedDate)
        } else {
            horarioViewModel.getHorariosPorCursoYFecha(selectedCourseCode, selectedDate)
        }
        flow.collect { value = it }
    }
    // Formateador para mostrar la fecha y hora
    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "ES")) }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (showCalendarOverlay) {
                        Modifier
                            .blur(radius = 8.dp) // Aplicamos el desenfoque
                            .graphicsLayer(alpha = 0.5f) // Opcional: Atenuamos un poco
                    } else Modifier
                )
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(horizontal = 0.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Horarios",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            HorizontalDivider()
            MonthNavigator(
                currentDate = selectedDate,
                onPreviousMonthClicked = {
                    val today = LocalDate.now()
                    val previousMonthDate = selectedDate.minusMonths(1)

                    // <-- CAMBIO CLAVE: Comprobar si el mes anterior es el mes actual
                    selectedDate =
                        if (previousMonthDate.year == today.year && previousMonthDate.month == today.month) {
                            // Si es el mes actual, selecciona el día de hoy
                            today
                        } else {
                            // Si no, selecciona el primer día de ese mes
                            previousMonthDate.withDayOfMonth(1)
                        }
                },
                onNextMonthClicked = {
                    val today = LocalDate.now()
                    val nextMonthDate = selectedDate.plusMonths(1)

                    // <-- CAMBIO CLAVE: Comprobar si el mes siguiente es el mes actual
                    selectedDate =
                        if (nextMonthDate.year == today.year && nextMonthDate.month == today.month) {
                            // Si es el mes actual, selecciona el día de hoy
                            today
                        } else {
                            // Si no, selecciona el primer día de ese mes
                            nextMonthDate.withDayOfMonth(1)
                        }
                },
                // --- NUEVO: Al hacer clic en el mes/año, mostramos el overlay ---
                onMonthYearClicked = {
                    showCalendarOverlay = true
                }
            )
            HorizontalDivider()
            Spacer(modifier = Modifier.height(15.dp))
            DateCarousel(
                selectedDate = selectedDate,
                onDateSelected = { newDate -> selectedDate = newDate }
            )

            //HorizontalDivider()

            // Lista de Horarios para la fecha y curso seleccionados
            if (horarios.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // 1. Icono de alerta grande y azul
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "No hay actividades",
                        modifier = Modifier.size(120.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    // 2. Espacio entre el icono y el texto
                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. Texto de mensaje, en negrita y centrado
                    Text(
                        text = "No tienes actividades\nacadémicas asignadas.",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(horarios) { horario ->
                        val cursoNombre =
                            allCursos.find { it.codigo == horario.codigoCurso }?.nombre
                                ?: horario.codigoCurso
                        HorarioCard(horario, cursoNombre, timeFormatter, onToggleRecordatorio ={horarioViewModel.toggleRecordatorio(horario)})
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = showCalendarOverlay,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            CalendarOverlay(
                initialDate = selectedDate,
                onDateSelected = { newDate ->
                    selectedDate = newDate
                    showCalendarOverlay = false // Cerramos el overlay
                },
                onDismiss = {
                    showCalendarOverlay = false // Cerramos el overlay
                }
            )
        }
    }
}

@Composable
fun HorarioCard(
    horario: HorarioEntity,
    cursoNombre: String,
    timeFormatter: DateTimeFormatter,
    onToggleRecordatorio: () -> Unit // Nuevo parámetro para el callback
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Fila para el título y el icono de la campana
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically // Alinea el texto y el icono
            ) {
                // Título del curso
                Text(
                    text = cursoNombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f) // Para que no se salga si el texto es muy largo
                )

                // Botón con el icono de la campana
                IconButton(onClick = onToggleRecordatorio) {
                    Icon(
                        imageVector = if (horario.recordatorioActivo) {
                            Icons.Filled.Notifications // Icono "lleno"
                        } else {
                            Icons.Outlined.Notifications // Icono "vacío"
                        },
                        contentDescription = if (horario.recordatorioActivo) "Desactivar recordatorio" else "Activar recordatorio",
                        tint = if (horario.recordatorioActivo) {
                            MaterialTheme.colorScheme.primary // Color azul cuando está activo
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant // Color gris cuando está inactivo
                        }
                    )
                }
            }

            // Tipo de clase (e.g., "Examen 1")
            // Le damos un poco menos de espacio superior porque el título ya lo tiene
            Text(
                text = horario.tipo,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fila inferior para la hora y el salón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${horario.horaInicio.format(timeFormatter)} - ${horario.horaFin.format(timeFormatter)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = horario.salon,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHorarioScreen() {
    MaterialTheme {
        Column {
            Text("Horario Screen Preview (sin datos reales)")
        }
    }
}



