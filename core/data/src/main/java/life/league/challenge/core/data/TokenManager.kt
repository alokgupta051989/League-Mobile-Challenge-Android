package life.league.challenge.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import life.league.challenge.core.network.TokenProvider
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

/**
 * Manages the persistent storage of authentication tokens using Jetpack DataStore.
 * Implements [TokenProvider] for injection into the network layer.
 */
@Singleton
class TokenManager @Inject constructor(@ApplicationContext private val context: Context) : TokenProvider {

    companion object {
        private val API_KEY = stringPreferencesKey("api_key")
    }

    /**
     * A reactive stream of the current API key.
     */
    val tokenFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[API_KEY]
    }

    /**
     * Persists a new token to the DataStore.
     */
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[API_KEY] = token
        }
    }

    /**
     * Retrieves the current token. Used by [AuthInterceptor].
     */
    override suspend fun getToken(): String? {
        return tokenFlow.first()
    }

    /**
     * Removes the current token, effectively logging out the user.
     */
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(API_KEY)
        }
    }
}
