package life.league.challenge.core.network

import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

class AuthInterceptorTest {

    private lateinit var tokenProvider: TokenProvider
    private lateinit var interceptor: AuthInterceptor
    private lateinit var chain: Interceptor.Chain

    @Before
    fun setup() {
        tokenProvider = mock()
        interceptor = AuthInterceptor(tokenProvider)
        chain = mock()
    }

    @Test
    fun `intercept adds x-access-token header when token exists`() = runTest {
        val token = "test_token"
        whenever(tokenProvider.getToken()).thenReturn(token)
        
        val request = Request.Builder().url("https://test.com").build()
        whenever(chain.request()).thenReturn(request)
        
        val mockResponse: Response = mock()
        whenever(chain.proceed(any())).thenAnswer { invocation ->
            val interceptedRequest = invocation.arguments[0] as Request
            assertEquals(token, interceptedRequest.header("x-access-token"))
            mockResponse
        }

        interceptor.intercept(chain)
    }

    @Test
    fun `intercept does not add header when token is null`() = runTest {
        whenever(tokenProvider.getToken()).thenReturn(null)
        
        val request = Request.Builder().url("https://test.com").build()
        whenever(chain.request()).thenReturn(request)
        
        val mockResponse: Response = mock()
        whenever(chain.proceed(any())).thenAnswer { invocation ->
            val interceptedRequest = invocation.arguments[0] as Request
            assertEquals(null, interceptedRequest.header("x-access-token"))
            mockResponse
        }

        interceptor.intercept(chain)
    }
}
