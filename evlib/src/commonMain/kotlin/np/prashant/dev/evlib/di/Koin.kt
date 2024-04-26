package np.prashant.dev.evlib.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import np.prashant.dev.evlib.platform.DefaultEVStationRepository
import np.prashant.dev.evlib.platform.EVStationRepository
import np.prashant.dev.evlib.platform.contract.remote.DefaultEVStationRemote
import np.prashant.dev.evlib.platform.contract.remote.EVStationRemote
import np.prashant.dev.evlib.EVLibConfig
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(): KoinApplication {
    val koinApplication = startKoin {
        modules(
            remoteModule,
            platformModule,
        )
    }

    return koinApplication
}

@OptIn(ExperimentalSerializationApi::class)
private val remoteModule = module {
    single {
        HttpClient {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = EVLibConfig.API_HOST
                }
                headers {
                    append("X-API-Key", EVLibConfig.API_KEY)
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60_000L
                connectTimeoutMillis = 60_000L
            }
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    encodeDefaults = true
                })
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    single<EVStationRemote> { DefaultEVStationRemote(get()) }
}

private val platformModule = module {
    single<EVStationRepository> {
        DefaultEVStationRepository(get())
    }
}
