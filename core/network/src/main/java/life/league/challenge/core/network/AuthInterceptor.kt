package life.league.challenge.core.network

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * OKHttp Interceptor that automatically injects the authentication token into outgoing requests.
 * It retrieves the token from [TokenProvider] and adds it as an 'x-access-token' header.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Retrieve token synchronously using runBlocking as Interceptor.intercept is a synchronous call.
        val token = runBlocking { tokenProvider.getToken() }
        val request = chain.request().newBuilder()
        
        if (token != null) {
            request.addHeader("x-access-token", token)
        }
        
        return chain.proceed(request.build())
    }
}

/**
 * Interface to be implemented by a class that provides authentication tokens (e.g., TokenManager).
 */
interface TokenProvider {
    suspend fun getToken(): String?
}
