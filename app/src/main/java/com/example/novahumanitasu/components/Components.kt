package com.example.novahumanitasu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.novahumanitasu.model.DataAnuncio
import com.example.novahumanitasu.model.DataCurso

//1: BOTÓN

enum class ButtonStyle{
    PRIMARY,
    SECONDARY,
    OUTLINED,
    DANGER
}

enum class ButtonSize{
    SMALL,
    MEDIUM,
    LARGE
}

@Composable
fun AppButton( //Le pongo este nombre para que no haya conflictos con el Button de JetpackCompose
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyle.PRIMARY,
    size: ButtonSize = ButtonSize.MEDIUM
){
    val colors = when (style) {
        ButtonStyle.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
        ButtonStyle.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black
        )
        ButtonStyle.OUTLINED -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        )
        ButtonStyle.DANGER -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
    }

    //Desde aquí hacemos validaciones de acuerdo al tamaño elegido del botón:

    //Acá cambiamos la altura del botón:
    val buttonHeight = when (size) {
        ButtonSize.SMALL -> 36.dp
        ButtonSize.MEDIUM -> 48.dp
        ButtonSize.LARGE -> 56.dp
    }

    //Acá cambiamos el tamaño del texto:
    val textStyle = when (size){
        ButtonSize.SMALL -> MaterialTheme.typography.bodySmall
        ButtonSize.MEDIUM -> MaterialTheme.typography.bodyLarge
        ButtonSize.LARGE -> MaterialTheme.typography.bodyLarge
    }

    //Aca cambiamos el largo del botón
    //Solo en el caso de LARGE, el botón ocupa el espacio del objeto que lo contiene.
    val widthModifier = when (size) {
        ButtonSize.LARGE -> Modifier.fillMaxWidth()
        ButtonSize.MEDIUM -> Modifier
        ButtonSize.SMALL -> Modifier
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .then(widthModifier)
            .height(buttonHeight),
        enabled = enabled,
        colors = colors
    ) {
        Text(text = text, style = textStyle)
    }

}

//2: INPUT LOGIN

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
){
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(label)},
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible){
            PasswordVisualTransformation()
        } else VisualTransformation.None,
        trailingIcon = {
            if (isPassword){
                val icon = if (passwordVisible) Icons.Filled.Lock else Icons.Filled.Lock
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                )
                {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary
        )

    )

}

//3: CARD DE ANUNCIOS EN HOME

@Composable
fun AnuncioCard(anuncio: DataAnuncio){
    Card(
        modifier = Modifier.fillMaxWidth(), // <-- ¡SIN PADDING AQUÍ!
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = anuncio.imagen),
                contentDescription = anuncio.titulo,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(anuncio.titulo, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(anuncio.descripcion, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

//4: CARD DE CURSOS EN CURSOS
@Composable
fun CursoCard(curso: DataCurso, onClick: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth(), // <-- ¡SIN PADDING AQUÍ!
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = curso.imagen),
                contentDescription = curso.nombre,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(curso.nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(curso.codigo, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Prof. ${curso.profesor}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        NavItem("Inicio", Icons.Filled.Home),
        NavItem("Cursos", Icons.Filled.Book),
        NavItem("Perfil", Icons.Filled.Person),
        NavItem("Ajustes", Icons.Filled.Settings),
        NavItem("Otros", Icons.Filled.MoreHoriz)
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}
data class NavItem(val label: String, val icon: ImageVector)
