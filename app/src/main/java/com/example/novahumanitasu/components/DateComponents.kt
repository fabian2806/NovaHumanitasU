package com.example.novahumanitasu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

// --- COMPOSABLE MODIFICADO: El carrusel de fechas con centrado por índice ---
@Composable
fun DateCarousel(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val dateList = remember(selectedDate.year, selectedDate.month) {
        val yearMonth = YearMonth.from(selectedDate)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        List(daysInMonth) { dayIndex ->
            firstOfMonth.plusDays(dayIndex.toLong())
        }
    }

    // --- INICIO DE LA LÓGICA DE CENTRADO CORREGIDA ---
    // Este efecto se ejecuta cuando la fecha seleccionada cambia.
    LaunchedEffect(selectedDate) {
        // Obtenemos la información de los elementos actualmente visibles.
        val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
        if (visibleItemsInfo.isEmpty()) {
            // Si la lista no se ha dibujado aún, no podemos hacer cálculos.
            return@LaunchedEffect
        }

        // 1. Índice del elemento que queremos centrar.
        val targetIndex = selectedDate.dayOfMonth - 1
        if (targetIndex < 0 || targetIndex >= dateList.size) {
            return@LaunchedEffect // Índice inválido
        }

        // 2. Cuántos elementos son visibles en pantalla.
        val visibleItemsCount = visibleItemsInfo.size

        // 3. Calculamos a qué índice debemos desplazarnos.
        // La idea es mover el inicio de la lista de tal forma que nuestro
        // targetIndex quede en el medio de los elementos visibles.
        // Por eso restamos la mitad de los elementos visibles al índice objetivo.
        val scrollToIndex = targetIndex - (visibleItemsCount / 2) + 1

        // 4. Nos aseguramos de no intentar ir a un índice negativo.
        val finalScrollIndex = scrollToIndex.coerceAtLeast(0)

        // 5. Ejecutamos el scroll animado al índice calculado.
        coroutineScope.launch {
            listState.animateScrollToItem(finalScrollIndex)
        }
    }
    // --- FIN DE LA LÓGICA DE CENTRADO CORREGIDA ---

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(dateList, key = { it.toEpochDay() }) { date -> // Añadir una 'key' es buena práctica
            DateChip(
                date = date,
                isSelected = date == selectedDate,
                isToday = date == today,
                onClick = { onDateSelected(date) }
            )
        }
    }
}

// --- COMPOSABLE MODIFICADO: Un "chip" individual para una fecha ---
@Composable
fun DateChip(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {
    // La lógica de color de fondo ahora es más simple
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

    // --- MODIFICADOR MEJORADO: Usamos un borde para indicar "hoy" ---
    val chipModifier = Modifier
        // El borde se aplica solo si es 'isToday'
        .then(
            if (isToday) {
                Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            } else {
                Modifier
            }
        )
        .clip(MaterialTheme.shapes.medium) // Clip debe ir después del borde para redondearlo también
        .background(backgroundColor)
        .clickable(onClick = onClick)
        .padding(vertical = 16.dp, horizontal = 20.dp)

    Column(
        modifier = chipModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES")).uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        //Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}


@Composable
fun MonthNavigator(
    currentDate: LocalDate,
    onPreviousMonthClicked: () -> Unit,
    onNextMonthClicked: () -> Unit,
    onMonthYearClicked: () -> Unit
) {
    val monthYearFormatter = remember {
        DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es", "ES"))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón para ir al mes anterior
        IconButton(onClick = onPreviousMonthClicked) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Mes anterior",
                // --- INICIO DE CAMBIOS ---
                modifier = Modifier.size(32.dp), // <-- CAMBIO: Aumentamos el tamaño del icono
                tint = MaterialTheme.colorScheme.primary // <-- CAMBIO: Aplicamos el color primario
                // --- FIN DE CAMBIOS ---
            )
        }

        // Texto del mes y año
        Text(
            modifier = Modifier.clickable(onClick = onMonthYearClicked),
            text = currentDate.format(monthYearFormatter).replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )

        // Botón para ir al mes siguiente
        IconButton(onClick = onNextMonthClicked) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Mes siguiente",
                // --- INICIO DE CAMBIOS ---
                modifier = Modifier.size(32.dp), // <-- CAMBIO: Aumentamos el tamaño del icono
                tint = MaterialTheme.colorScheme.primary // <-- CAMBIO: Aplicamos el color primario
                // --- FIN DE CAMBIOS ---
            )
        }

    }
}



// --- NUEVO COMPOSABLE: El overlay del calendario ---
@Composable
fun CalendarOverlay(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    // Estado interno para la fecha que se está mostrando en el calendario del overlay
    var displayMonth by remember { mutableStateOf(YearMonth.from(initialDate)) }
    // Estado interno para la fecha que el usuario selecciona dentro del overlay
    var newlySelectedDate by remember { mutableStateOf(initialDate) }

    // Fondo oscuro semi-transparente que cierra el overlay al hacer clic
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        // La tarjeta del calendario. El clickable(enabled=false) evita que el clic se propague al fondo.
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clickable(enabled = false, onClick = {}),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Navegador de Mes para el overlay
                CalendarHeader(
                    displayMonth = displayMonth,
                    onPreviousMonth = { displayMonth = displayMonth.minusMonths(1) },
                    onNextMonth = { displayMonth = displayMonth.plusMonths(1) }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Días de la semana (DOM, LUN, etc.)
                WeekDaysHeader()
                Spacer(modifier = Modifier.height(8.dp))

                // Rejilla de días del mes
                MonthGrid(
                    displayMonth = displayMonth,
                    selectedDate = newlySelectedDate,
                    onDayClick = { day -> newlySelectedDate = day }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Confirmar
                Button(
                    onClick = { onDateSelected(newlySelectedDate) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Confirmar",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Componentes auxiliares para el CalendarOverlay

@Composable
fun CalendarHeader(
    displayMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    val formatter = remember { DateTimeFormatter.ofPattern("MMMM 'de' yyyy", Locale("es", "ES")) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Mes anterior", tint = MaterialTheme.colorScheme.primary)
        }
        Text(
            text = displayMonth.format(formatter).replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = onNextMonth) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Mes siguiente", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun WeekDaysHeader() {
    val days = listOf("DOM", "LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        days.forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
// --- COMPOSABLE MODIFICADO: MonthGrid ---
@Composable
fun MonthGrid(
    displayMonth: YearMonth,
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
    val firstDayOfMonth = displayMonth.atDay(1)
    val startDayOfWeek = (firstDayOfMonth.dayOfWeek.value % 7)
    val daysInMonth = displayMonth.lengthOfMonth()

    val gridItems = (1..startDayOfWeek).map { null } + (1..daysInMonth).map { day ->
        displayMonth.atDay(day)
    }

    // --- INICIO DE CAMBIOS ---
    // Obtenemos la fecha de hoy una vez y la recordamos
    val today = remember { LocalDate.now() }
    // --- FIN DE CAMBIOS ---

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(gridItems) { date ->
            if (date != null) {
                DayCell(
                    date = date,
                    isSelected = date == selectedDate,
                    isToday = date == today, // <--- NUEVO: Pasamos la bandera a DayCell
                    onClick = { onDayClick(date) }
                )
            } else {
                Spacer(Modifier.aspectRatio(1f))
            }
        }
    }
}
// --- COMPOSABLE MODIFICADO: DayCell ---
@Composable
fun DayCell(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean, // <--- NUEVO parámetro
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    // La lógica del borde es muy similar a la de tu DateChip
    val cellModifier = Modifier
        .aspectRatio(1f) // Hace que la celda sea cuadrada
        .then(
            // --- INICIO DE CAMBIOS ---
            if (isToday) {
                // Aplicamos el borde si es el día de hoy
                Modifier.border(1.5.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.extraLarge)
            } else {
                // Sin borde en otros días
                Modifier
            }
            // --- FIN DE CAMBIOS ---
        )
        .clip(MaterialTheme.shapes.extraLarge) // El clip debe ir DESPUÉS del borde para redondearlo
        .background(backgroundColor)
        .clickable(onClick = onClick)

    Box(
        modifier = cellModifier, // <--- Usamos el nuevo modificador
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
