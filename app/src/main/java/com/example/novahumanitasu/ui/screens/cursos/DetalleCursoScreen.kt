package com.example.novahumanitasu.ui.screens.cursos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.novahumanitasu.R

@Composable
fun DocumentosTab(codigo: String){
    Text("Documentos")
}

@Composable
fun CalificacionesTab(codigo: String){
    Text("Calificaciones")
}

@Composable
fun DiscusionTab(codigo: String){
    Text("Discusión")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCursoScreen(codigo: String, navController: NavController){
    val tabs = listOf("Documentos", "Calificaciones", "Discusión")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TopAppBar(
            title = {
                Text(text = "$codigo",
                    fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                }
            }
        )

        Image(
            painter = painterResource(id = R.drawable.foto_clase_fgm), // luego será dinámico
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentScale = ContentScale.Crop //Por las dimensiones de la imagen
        )

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> DocumentosTab(codigo)
            1 -> CalificacionesTab(codigo)
            2 -> DiscusionTab(codigo)
        }

    }

}