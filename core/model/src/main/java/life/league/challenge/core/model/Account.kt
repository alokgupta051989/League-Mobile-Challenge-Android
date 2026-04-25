package life.league.challenge.core.model

import com.google.gson.annotations.SerializedName

/**
 * Domain model representing a user account / authentication response.
 * @property apiKey The session token returned by the server upon successful login.
 */
data class Account(@SerializedName("api_key") val apiKey: String? = null)
