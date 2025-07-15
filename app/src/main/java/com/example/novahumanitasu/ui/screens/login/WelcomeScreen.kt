package com.example.novahumanitasu.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.novahumanitasu.R
import com.example.novahumanitasu.components.*
import com.example.novahumanitasu.model.SessionManager
import kotlinx.coroutines.flow.first

@Composable
fun WelcomeScreen(navController: NavController) {
    val context = LocalContext.current
    val isLoggedInFlow = SessionManager.isLoggedIn(context)
    val isLoggedIn by isLoggedInFlow.collectAsState(initial = false)

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("welcome") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        //Acá colocamos el patrón del fondo:
        Image(
            painter = painterResource(id = R.drawable.background_pattern),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(192.dp)) //Simula espacio superior (como un margin top para la imagen)
            Image(
                painter = painterResource(id = R.drawable.logo_u_novahumanitas),
                contentDescription = "Logo Nova Humanitas U",
                modifier = Modifier
                    .size(180.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = false // deja que la sombra se expanda
                    )
            )
            Spacer(modifier = Modifier.height(32.dp))


            //Lo del buildAnnotatedString es para concatenar varios textos con diferentes estilos en un solo
            //bloque de texto. Si observan, la oración de "bienvenidos..." tiene un fontweight diferente al nombre de la U.
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)){
                        append("Bienvenido al portal móvil de\n")
                    }
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)){
                        append("Nova Humanitas U")
                    }
                },
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Accede de forma rápida y segura a todos tus\nservicios académicos.\nConsulta tus cursos, " +
                        "calificaciones, horarios y más; donde estés.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(32.dp))

            AppButton(
                text = "Continuar",
                onClick = {
                    navController.navigate("login")
                },
            )


        }

    }

}





