package np.prashant.dev.evlib.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

fun interface KmpSubscription {
    fun unsubscribe()
}

class KmpFlow<T>(private val source: Flow<T>) : Flow<T> by source {
    fun subscribe(onEach: (T) -> Unit, onCompletion: (Throwable?) -> Unit): KmpSubscription {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        source
            .onEach { onEach(it) }
            .catch { onCompletion(it) }
            .onCompletion { onCompletion(null) }
            .launchIn(scope)
        return KmpSubscription { scope.cancel() }
    }
}

fun <T> Flow<T>.asKmpFlow(): KmpFlow<T> = KmpFlow(this)