package np.prashant.dev.evlib.platform


interface EVStationRepository {
    @Throws(Throwable::class)
    suspend fun getNearbyEVStations(latitude: Double, longitude: Double)
}