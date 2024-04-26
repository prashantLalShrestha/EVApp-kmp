package np.prashant.dev.evapp

import android.app.Application
import np.prashant.dev.evlib.di.initKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}