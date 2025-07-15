package com.example.novahumanitasu.ui.screens.otros

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val comedores = listOf(
    "Comedor Central",
    "Comedor de Artes",
    "Comedor de Ciencias",
    "Comedor de Letras",
    "Comedor de Derecho"
)

val menuRegular = mapOf(
    "Comedor Central" to listOf(
        "Entradas" to listOf("Sopa de pollo", "Causa rellena", "Tequeños"),
        "Segundo" to listOf("Seco de ternera", "Garbanzos con pescado frito y arroz", "Puré de papa con pollo asado"),
        "Información Adicional" to listOf("Garbanzos contiene sulfitos"),
        "Precio" to listOf("S/ 9.99", "Incluye igv")
    ),
    "Comedor de Artes" to listOf(
        "Entradas" to listOf("Ensalada rusa", "Sopa de verduras"),
        "Segundo" to listOf("Pollo al horno", "Tallarines verdes"),
        "Información Adicional" to listOf("Contiene gluten"),
        "Precio" to listOf("S/ 8.50", "Incluye igv")
    ),
    "Comedor de Ciencias" to listOf(
        "Entradas" to listOf("Crema de zapallo", "Papa a la huancaína"),
        "Segundo" to listOf("Bistec a lo pobre", "Arroz chaufa"),
        "Información Adicional" to listOf("Contiene huevo"),
        "Precio" to listOf("S/ 10.00", "Incluye igv")
    ),
    "Comedor de Letras" to listOf(
        "Entradas" to listOf("Sopa de quinua", "Ensalada fresca"),
        "Segundo" to listOf("Ají de gallina", "Tortilla de verduras"),
        "Información Adicional" to listOf("Apto para celíacos"),
        "Precio" to listOf("S/ 9.00", "Incluye igv")
    ),
    "Comedor de Derecho" to listOf(
        "Entradas" to listOf("Sopa criolla", "Ensalada de garbanzos"),
        "Segundo" to listOf("Lomo saltado", "Arroz tapado"),
        "Información Adicional" to listOf("Contiene carne de res"),
        "Precio" to listOf("S/ 11.00", "Incluye igv")
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuRegularScreen(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedComedor by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(WindowInsets.statusBars.asPaddingValues())) {
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Menu Regular",
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Lugar", fontSize = 16.sp)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = if (selectedComedor.isEmpty()) "Seleccione un comedor" else selectedComedor,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    comedores.forEach { comedor ->
                        DropdownMenuItem(
                            text = { Text(comedor) },
                            onClick = {
                                selectedComedor = comedor
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (selectedComedor.isNotEmpty()) {
                val menu = menuRegular[selectedComedor] ?: menuRegular["Comedor Central"]!!
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    menu.forEach { (titulo, items) ->
                        Text(titulo, fontSize = 16.sp, style = MaterialTheme.typography.titleMedium)
                        items.forEach { item ->
                            Text(item, fontSize = 15.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
} 