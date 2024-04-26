package np.prashant.dev.evlib.platform.contract.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText

class DefaultEVStationRemote(
    private val httpClient: HttpClient
): EVStationRemote {

    @Throws(Throwable::class)
    override suspend fun getNearbyStations(latitude: Double, longitude: Double) {
        val result = httpClient.get("v3/poi") {
            parameter("latitude", latitude)
            parameter("longitude", longitude)
        }

        println(result.bodyAsText())
    }
}