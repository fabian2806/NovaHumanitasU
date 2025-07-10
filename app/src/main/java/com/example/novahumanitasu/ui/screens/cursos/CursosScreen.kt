package com.example.novahumanitasu.ui.screens.cursos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.novahumanitasu.R
import com.example.novahumanitasu.components.CursoCard
import com.example.novahumanitasu.model.DataCurso

@Composable
fun CursosScreen(navController: NavController) {
    // Estos datos luego se pasarán desde el ViewModel
    val cursos = listOf(
        DataCurso("Programación 3", "INF132", "Manuel Vargas", R.drawable.foto_clase_fgm),
        DataCurso("Programación 4", "INF120", "Juan Pachas", R.drawable.foto_clase_fgm),
        DataCurso("Redes de Computadoras", "INF210", "Manuel Vargas", R.drawable.foto_clase_fgm),
        DataCurso("Programación Web", "INF675", "Mateo Vargas", R.drawable.foto_clase_fgm),
        DataCurso("Química Orgánica", "INF111", "Walter White", R.drawable.foto_clase_fgm)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Cursos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cursos) { curso ->
                CursoCard(curso = curso, onClick = {
                    navController.navigate("detalleCurso/${curso.codigo}")
                })
            }
        }
    }
}