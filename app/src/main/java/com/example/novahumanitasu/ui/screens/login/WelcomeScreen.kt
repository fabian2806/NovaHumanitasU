package com.example.novahumanitasu.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.novahumanitasu.R

@Composable
fun WelcomeScreen(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_u_novahumanitas),
            contentDescription = "Logo Nova Humanitas U",
            modifier = Modifier
                .size(180.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Bienvenido al portal móvil de\nNova Humanitas U",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Accede de forma rápida y segura a todos tus\nservicios académicos.\nConsulta tus cursos, calificaciones, horarios y más; donde estés.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onContinueClicked) {
            Text(text = "Continuar")
        }
    }
}
