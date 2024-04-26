package np.prashant.dev.evlib.platform.contract.remote

interface EVStationRemote {
    @Throws(Throwable::class)
    suspend fun getNearbyStations(latitude: Double, longitude: Double)
}