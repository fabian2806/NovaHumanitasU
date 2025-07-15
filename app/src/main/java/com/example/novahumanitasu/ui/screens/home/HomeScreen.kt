package com.example.novahumanitasu.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.novahumanitasu.R
import com.example.novahumanitasu.components.AnuncioCard
import com.example.novahumanitasu.model.DataAnuncio

@Composable
fun HomeScreen(navController: NavController){

    //Cambiar al implementar MVVC:

    val nombreCompleto = "Gabriela Jimena Rodriguez Mora"
    val carrera = "Ingeniería Ambiental"
    val ciclo = "2025-1"
    val codigoNHU = "20210834"
    val dni = "72849313"

    val anuncios = listOf(
        DataAnuncio(
            titulo = "¡Hoy comienza la Feria del Libro NHU!",
            descripcion = "La feria que tanto estabas esperando volvió...",
            imagen = R.drawable.ic_launcher_background
        ),
        DataAnuncio(
            titulo = "¡Santa Misa: Papa Francisco!",
            descripcion = "El equipo rectoral invita a toda la comunidad...",
            imagen = R.drawable.ic_launcher_background
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    )
    {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.foto_perfil_fgm),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = nombreCompleto, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = carrera, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Light)
                    Text(text = ciclo, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Light)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
            //Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("Código NHU: ")
                    }
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal)){
                        append("$codigoNHU")
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("DNI: ")
                    }
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal)){
                        append("$dni")
                    }
                },
            )
            //Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.codigo_barras_fmg),
                contentDescription = "Código de barras",
                modifier = Modifier
                    .fillMaxWidth(0.95f) // o incluso 1f
                    .height(244.dp)
            )
        }


        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))



        Text(
            text = "Anuncios del día:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
        anuncios.forEach { anuncio ->
            // Aquí es donde aplicamos el padding horizontal a cada tarjeta
            // Esto crea el margen lateral para cada Card, y como están dentro de un Column
            // con scroll, funcionará perfectamente.
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                AnuncioCard(anuncio = anuncio)
            }
            // Añadimos un Spacer para el espacio vertical entre tarjetas
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(32.dp))
    }

}