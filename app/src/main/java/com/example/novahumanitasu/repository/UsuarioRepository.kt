package com.example.novahumanitasu.repository

import com.example.novahumanitasu.model.entities.UsuarioEntity
import com.example.novahumanitasu.room.UsuarioDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao
) {
    fun getUsuario(): Flow<UsuarioEntity?> = usuarioDao.getUsuarioActual()

    suspend fun guardarUsuario(usuario: UsuarioEntity) = usuarioDao.insertarUsuario(usuario)

    suspend fun eliminarUsuario(usuario: UsuarioEntity) = usuarioDao.eliminarUsuario(usuario)

    suspend fun cerrarSesion() = usuarioDao.eliminarTodosLosUsuarios()

    suspend fun autenticar(email: String, password: String): UsuarioEntity? {
        return usuarioDao.login(email, password)
    }

}