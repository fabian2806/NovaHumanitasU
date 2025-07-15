package com.example.novahumanitasu.ui.screens.cursos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.novahumanitasu.ui.screens.notificaciones.ReminderLogItem
import com.example.novahumanitasu.ui.viewModels.CursoViewModel

@Composable
fun CursosScreen(navController: NavController) {

    val cursoViewModel: CursoViewModel =
        hiltViewModel()
    val cursos by cursoViewModel.cursos.collectAsState()


    /* // Estos datos luego se pasarán desde el ViewModel
    val cursos = listOf(
        DataCurso("Programación 3", "INF132", "Manuel Vargas", R.drawable.foto_clase_fgm),
        DataCurso("Programación 4", "INF120", "Juan Pachas", R.drawable.foto_clase_fgm),
        DataCurso("Redes de Computadoras", "INF210", "Manuel Vargas", R.drawable.foto_clase_fgm),
        DataCurso("Programación Web", "INF675", "Mateo Vargas", R.drawable.foto_clase_fgm),
        DataCurso("Química Orgánica", "INF111", "Walter White", R.drawable.foto_clase_fgm)
    )*/

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



        LazyColumn(
            modifier = Modifier.fillMaxSize(), // Asegura que la lista ocupe el espacio disponible
            contentPadding = PaddingValues(horizontal = 1.dp), // Añade el padding aquí
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cursos) { curso ->
                CursoCard(
                    curso = DataCurso(
                        nombre = curso.nombre,
                        codigo = curso.codigo,
                        profesor = curso.profesor, // puedes usar este campo como "profesor"
                        imagen = R.drawable.foto_clase_fgm // imagen estática por ahora
                    ),
                    onClick = {
                        navController.navigate("detalleCurso/${curso.codigo}")
                    })
            }
        }



    }
}