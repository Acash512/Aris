package com.acash.aris.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES = "user_preferences"

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES
)

class UserPreferencesDataStore(context: Context) {
    private val USER_NAME = stringPreferencesKey("user_name")

    val userName: Flow<String> = context.datastore.data
        .map { preferences ->
            preferences[USER_NAME] ?: ""
        }

    suspend fun saveNameToPreferencesStore(name: String, context: Context) {
        context.datastore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }
}