package np.prashant.dev.evlib

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform