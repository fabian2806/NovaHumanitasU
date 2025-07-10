package com.example.novahumanitasu.model

import androidx.annotation.DrawableRes

data class DataAnuncio(
    val titulo: String,
    val descripcion: String,
    @DrawableRes val imagen: Int
)
