package np.prashant.dev.evlib.di

import np.prashant.dev.evlib.platform.EVStationRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object EVLibDI : KoinComponent {
    val evStationRepository by inject<EVStationRepository>()
}