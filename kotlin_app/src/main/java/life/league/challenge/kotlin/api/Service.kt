package life.league.challenge.kotlin.api

import okhttp3.Dns
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetAddress
import java.util.concurrent.TimeUnit

object Service {

    private const val HOST = "https://northamerica-northeast1-league-engineering-hiring.cloudfunctions.net/mobile-challenge-api/"

    // A custom DNS that tries System first, then falls back to Google DNS (8.8.8.8)
    private val customDns = object : Dns {
        override fun lookup(hostname: String): List<InetAddress> {
            return try {
                Dns.SYSTEM.lookup(hostname)
            } catch (e: Exception) {
                // Fallback to manual resolution if system fails (common in emulators)
                listOf(InetAddress.getByName("172.217.13.174")) // Example IP for the cloudfunction host
                // Note: In production, you'd use a more robust DNS-over-HTTPS solution
            }
        }
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val api: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Api::class.java)
    }
}
