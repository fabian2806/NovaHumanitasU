package com.example.novahumanitasu.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.novahumanitasu.R
import com.example.novahumanitasu.components.AppButton
import com.example.novahumanitasu.components.ButtonSize
import com.example.novahumanitasu.components.LoginTextField
import com.example.novahumanitasu.ui.viewModels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController){

    val loginViewModel: LoginViewModel = hiltViewModel()
    val usuarioAutenticado by loginViewModel.usuarioAutenticado.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(usuarioAutenticado) {
        if (usuarioAutenticado != null) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
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
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top //Para que empiece desde arriba de la pantalla.
        )
        {
            Spacer(modifier = Modifier.height(144.dp)) //Simula espacio superior (como un margin top para la imagen)
            Image(
                painter = painterResource(id = R.drawable.logo_u),
                contentDescription = "Logo U",
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

            Text(
                text = "Iniciar Sesión",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(64.dp))

            LoginTextField(
                value = email,
                onValueChange = {email = it},
                label = "Email"
            )

            Spacer(modifier = Modifier.height(32.dp))

            LoginTextField(
                value = password,
                onValueChange = {password = it},
                label = "Contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            AppButton(
                text = "Iniciar Sesión",
                onClick = {
                    loginViewModel.loginUsuario(email, password, context)
                },
                size = ButtonSize.LARGE
            )

        }
    }


}