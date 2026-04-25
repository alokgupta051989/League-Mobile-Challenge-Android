package life.league.challenge.core.network

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenProvider.getToken() }
        val request = chain.request().newBuilder()
        
        if (token != null) {
            request.addHeader("x-access-token", token)
        }
        
        return chain.proceed(request.build())
    }
}

interface TokenProvider {
    suspend fun getToken(): String?
}
