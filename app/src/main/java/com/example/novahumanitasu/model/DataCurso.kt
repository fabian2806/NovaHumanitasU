package com.example.novahumanitasu.model

import androidx.annotation.DrawableRes

data class DataCurso(
    val nombre: String,
    val codigo: String,
    val profesor: String,
    @DrawableRes val imagen: Int
)
