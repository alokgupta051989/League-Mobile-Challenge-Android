package life.league.challenge.core.model

/**
 * A generic class that holds a value with its loading status.
 * Used to communicate data state between layers.
 */
sealed class Resource<out T> {
    /**
     * Represents a successful data retrieval.
     */
    data class Success<out T>(val data: T) : Resource<T>()

    /**
     * Represents an error during data retrieval.
     * @property message User-friendly error message.
     * @property throwable The actual exception that occurred.
     */
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()

    /**
     * Represents that data is currently being fetched.
     */
    object Loading : Resource<Nothing>()
}
