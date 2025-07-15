package com.example.novahumanitasu.ui.screens.otros

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.List
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
import com.example.novahumanitasu.ui.screens.reserva.ConfirmacionReservaDialog
import com.example.novahumanitasu.ui.viewModels.ReservaViewModel
import java.util.*

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
    var hora by remember { mutableStateOf("") }
    var showTimePicker by remember { mutableStateOf(false) }
    var edificioSeleccionado by remember { mutableStateOf("") }
    var participantes by remember { mutableStateOf("") }
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
        bottomBar = {
            BottomNavBar(selectedIndex = selectedIndex, onItemSelected = onNavSelected)
        },
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
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
                value = fecha,
                onValueChange = {},
                label = { Text("Fecha") },
                trailingIcon = {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            val calendar = Calendar.getInstance()
                            val datePicker = DatePickerDialog(
                                context,
                                { _: DatePicker, year: Int, month: Int, day: Int ->
                                    fecha = "$day/${month + 1}/$year"
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        }
                    )
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

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
            OutlinedTextField(
                value = participantes,
                onValueChange = { participantes = it },
                label = { Text("N° Participantes") },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón historial
            Button(
                onClick = { navController.navigate("historialReservas") },
                modifier = Modifier.fillMaxWidth(),
                enabled = reservas.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (reservas.isNotEmpty()) Color(0xFF0091EA) else Color.LightGray,
                    contentColor = if (reservas.isNotEmpty()) Color.White else Color.DarkGray
                )
            ) {
                Icon(Icons.Default.List, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Historial de reservas")
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
                enabled = !isLoading && fecha.isNotEmpty() && hora.isNotEmpty() && 
                         edificioSeleccionado.isNotEmpty() && participantes.isNotEmpty()
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
                    val numeroPersonas = participantes.toIntOrNull() ?: 0
                    viewModel.registrarReserva(hora, fecha, edificioSeleccionado, numeroPersonas)
                    
                    // Limpiar campos
                    fecha = ""
                    hora = ""
                    edificioSeleccionado = ""
                    participantes = ""
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

