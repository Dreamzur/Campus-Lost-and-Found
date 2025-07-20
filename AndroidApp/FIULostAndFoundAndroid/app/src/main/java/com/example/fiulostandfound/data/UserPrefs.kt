
package com.example.fiulostandfound.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFS_NAME = "user_prefs"

private val Context.dataStore by preferencesDataStore(name = PREFS_NAME)

object UserPrefs {
    private val TOKEN_KEY = stringPreferencesKey("jwt_token")


    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    fun tokenFlow(context: Context): Flow<String?> {
        return context.dataStore.data
            .map { prefs ->
                prefs[TOKEN_KEY]
            }
    }
}
