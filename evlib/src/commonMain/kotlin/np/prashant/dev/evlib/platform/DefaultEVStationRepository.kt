package np.prashant.dev.evlib.platform

import np.prashant.dev.evlib.platform.contract.remote.EVStationRemote

class DefaultEVStationRepository(
    private val evStationRemote: EVStationRemote
): EVStationRepository {
    @Throws(Throwable::class)
    override suspend fun getNearbyEVStations(latitude: Double, longitude: Double) {
        evStationRemote.getNearbyStations(latitude, longitude)
    }
}