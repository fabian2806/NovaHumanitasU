package com.example.novahumanitasu.ui.screens.otros

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.novahumanitasu.components.BottomNavBar
import com.example.novahumanitasu.components.CalendarOverlay
import com.example.novahumanitasu.ui.screens.reserva.ConfirmacionReservaDialog
import com.example.novahumanitasu.ui.viewModels.ReservaViewModel
import java.util.*
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaCubiculoScreen(
    navController: NavController,
    selectedIndex: Int = 0,
    onNavSelected: (Int) -> Unit = {},
    viewModel: ReservaViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var fecha by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableStateOf<java.time.LocalDate?>(null) }
    var showCalendar by remember { mutableStateOf(false) }
    var hora by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }
    var edificioSeleccionado by remember { mutableStateOf("") }
    var participantes by remember { mutableStateOf(1) }
    var showErrorFecha by remember { mutableStateOf(false) }

    val edificios = listOf("Biblioteca Central", "Edificio CAE", "Edificio de Sociales")
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // Observar estados del ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val reservas by viewModel.reservas.collectAsState()

    // Mostrar error si existe
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            // Aquí podrías mostrar un SnackBar o Toast
            viewModel.limpiarError()
        }
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(WindowInsets.statusBars.asPaddingValues())) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart)
                        .clickable { navController.popBackStack() }
                )
                Text(
                    text = "Reserva de cubículo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fecha
            OutlinedTextField(
                value = fechaSeleccionada?.let { it.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))) } ?: "",
                onValueChange = {},
                label = { Text("Fecha") },
                trailingIcon = {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.clickable { showCalendar = true },
                        tint = Color(0xFF0091EA)
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCalendar = true }
            )
            if (showCalendar) {
                CalendarOverlay(
                    initialDate = fechaSeleccionada ?: java.time.LocalDate.now(),
                    onDateSelected = {
                        fechaSeleccionada = it
                        fecha = it.format(DateTimeFormatter.ofPattern("d/M/yyyy"))
                        showCalendar = false
                    },
                    onDismiss = { showCalendar = false }
                )
            }

            // Hora
            OutlinedTextField(
                value = hora,
                onValueChange = {},
                label = { Text("Hora") },
                trailingIcon = {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.clickable { showTimePicker = true }
                    )
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().clickable { showTimePicker = true }
            )
            if (showTimePicker) {
                val context = LocalContext.current
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        hora = String.format("%02d:%02d", hourOfDay, minute)
                        showTimePicker = false
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).apply { setOnDismissListener { showTimePicker = false } }.show()
            }

            // Edificio (dropdown)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = edificioSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Edificio") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    edificios.forEach { edificio ->
                        DropdownMenuItem(
                            text = { Text(edificio) },
                            onClick = {
                                edificioSeleccionado = edificio
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Participantes
            Text("N° Participantes", fontSize = 16.sp, modifier = Modifier.align(Alignment.Start))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                IconButton(
                    onClick = { if (participantes > 1) participantes-- },
                    modifier = Modifier
                        .size(36.dp)
                        .padding(2.dp)
                        .then(Modifier)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Disminuir",
                        tint = Color(0xFF0091EA)
                    )
                }
                OutlinedTextField(
                    value = participantes.toString(),
                    onValueChange = { value ->
                        val num = value.toIntOrNull() ?: 1
                        participantes = if (num < 1) 1 else num
                    },
                    singleLine = true,
                    modifier = Modifier
                        .width(80.dp)
                        .padding(horizontal = 8.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = androidx.compose.ui.text.style.TextAlign.Center),
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF0091EA),
                        unfocusedBorderColor = Color(0xFF0091EA)
                    )
                )
                IconButton(
                    onClick = { participantes++ },
                    modifier = Modifier
                        .size(36.dp)
                        .padding(2.dp)
                        .then(Modifier)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Aumentar",
                        tint = Color(0xFF0091EA)
                    )
                }
            }

            // Botón historial
            Button(
                onClick = { navController.navigate("historialReservas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                enabled = reservas.isNotEmpty(),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (reservas.isNotEmpty()) Color(0xFF0091EA) else Color.LightGray,
                    contentColor = if (reservas.isNotEmpty()) Color.White else Color.DarkGray
                )
            ) {
                Icon(Icons.Default.List, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Historial de reservas", color = Color.White)
            }

            // Botón aceptar
            Button(
                onClick = {
                    // Validar fecha
                    val partes = fecha.split("/")
                    val calendarHoy = Calendar.getInstance()
                    val calendarReserva = Calendar.getInstance()
                    if (partes.size == 3) {
                        val dia = partes[0].toIntOrNull() ?: 0
                        val mes = (partes[1].toIntOrNull() ?: 1) - 1
                        val anio = partes[2].toIntOrNull() ?: 0
                        calendarReserva.set(anio, mes, dia)
                        // Comparar solo fecha (sin hora)
                        calendarHoy.set(Calendar.HOUR_OF_DAY, 0)
                        calendarHoy.set(Calendar.MINUTE, 0)
                        calendarHoy.set(Calendar.SECOND, 0)
                        calendarHoy.set(Calendar.MILLISECOND, 0)
                        calendarReserva.set(Calendar.HOUR_OF_DAY, 0)
                        calendarReserva.set(Calendar.MINUTE, 0)
                        calendarReserva.set(Calendar.SECOND, 0)
                        calendarReserva.set(Calendar.MILLISECOND, 0)
                        if (calendarReserva.before(calendarHoy)) {
                            showErrorFecha = true
                            return@Button
                        }
                    }
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0091EA)),
                enabled = !isLoading && fechaSeleccionada != null && hora.isNotEmpty() && 
                         edificioSeleccionado.isNotEmpty() && participantes > 0
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Text("Aceptar", color = Color.White)
                }
            }
        }

        // Diálogo de confirmación
        if (showDialog) {
            ConfirmacionReservaDialog(
                fecha = fecha,
                hora = hora,
                edificio = edificioSeleccionado,
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    // Registrar la reserva
                    viewModel.registrarReserva(hora, fecha, edificioSeleccionado, participantes)
                    // Limpiar campos
                    fecha = ""
                    fechaSeleccionada = null
                    hora = ""
                    edificioSeleccionado = ""
                    participantes = 1
                }
            )
        }
        // Diálogo de error por fecha inválida
        if (showErrorFecha) {
            AlertDialog(
                onDismissRequest = { showErrorFecha = false },
                title = { Text("Fecha inválida") },
                text = { Text("No puedes reservar en fechas anteriores a hoy.") },
                confirmButton = {
                    TextButton(onClick = { showErrorFecha = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

