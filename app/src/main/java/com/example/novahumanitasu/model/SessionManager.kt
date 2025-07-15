package com.example.novahumanitasu.model

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SessionManager {
    private val Context.dataStore by preferencesDataStore("user_prefs")
    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    suspend fun setLoggedIn(context: Context, value: Boolean) {
        context.dataStore.edit { prefs -> prefs[IS_LOGGED_IN] = value }
    }

    fun isLoggedIn(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[IS_LOGGED_IN] ?: false }
} 