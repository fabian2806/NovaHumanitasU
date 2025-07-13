package com.example.novahumanitasu.ui.screens.cursos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.novahumanitasu.R
import com.example.novahumanitasu.model.DataNota
import com.example.novahumanitasu.ui.viewModels.NotaViewModel

@Composable
fun CalificacionesTab(codigo: String){

    val notaViewModel: NotaViewModel = hiltViewModel()
    val notas by notaViewModel.getNotasPorCurso(codigo).collectAsState()
    val notaFinal = remember(notas) {
        notaViewModel.obtenerNotaFinal(notas)
    }

    /*val notas = listOf(
        DataNota("PC1", 8),
        DataNota("PC2", 20),
        DataNota("PC3", 17),
        DataNota("PC4", 10),
        DataNota("EX1", 15),
        DataNota("EX2", 14)
    )*/

    Column(
        modifier = Modifier.fillMaxSize()
            .padding()
    ) {
        notas.forEach{ nota ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = nota.evaluacion,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = nota.nota.toString().padStart(2, '0'),
                    color = if (nota.nota < 10) Color.Red else Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )

            }

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFBBDEFB)), // El azul claro de fondo
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Nota Final",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFBBDEFB))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )

        }

        Text(
            text = "La nota es: $notaFinal.",  // Formatea con un decimal
            color = if (notaFinal < 10) Color.Red else Color.Black,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green)
                .padding(8.dp)
        )



        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Fórmula:", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic ) //XDDD
            Text(
                text = "4PC + 3EX1 + 3EX2",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(text = "10", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        }



    }
}

@Composable
fun DocumentosTab(codigo: String){
    Text("Documentos")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCursoScreen(codigo: String, navController: NavController){
    val tabs = listOf("Documentos", "Calificaciones")
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
        }

    }

}