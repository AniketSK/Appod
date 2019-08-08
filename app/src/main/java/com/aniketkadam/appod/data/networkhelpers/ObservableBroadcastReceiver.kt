package com.aniketkadam.appod.data.networkhelpers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import java.io.Closeable
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Added the broadcast receiver that gives observables of the network state.
 * I wrote this, and hosted it at https://gist.github.com/AniketSK/ef11112289a32dc81081b5cad2bfc7b2
 */
class ObservableBroadcastReceiver<T> private constructor(
    val context: Context,
    val intentFilter: IntentFilter,
    val intentMapper: (Intent) -> T
) : ObservableOnSubscribe<T>,
    Disposable {

    companion object {
        fun <T> create(context: Context, intentFilter: IntentFilter, intentMapper: (Intent) -> T) =
            Observable.create(ObservableBroadcastReceiver(context, intentFilter, intentMapper))
    }

    private val isDisposed = AtomicBoolean(false)

    override fun isDisposed(): Boolean = isDisposed.get()

    override fun dispose() {
        broadcastBroadcastReceiverTemplate.close()
        isDisposed.set(true)
    }

    private lateinit var broadcastBroadcastReceiverTemplate: BroadcastReceiverTemplate

    override fun subscribe(emitter: ObservableEmitter<T>) {
        broadcastBroadcastReceiverTemplate =
            BroadcastReceiverTemplate(context, { i ->
                isDisposed.get().also { disposed ->
                    if (!disposed) {
                        emitter.onNext(intentMapper(i))
                    }
                }
            }, intentFilter)

        broadcastBroadcastReceiverTemplate.start()
    }

}

private class BroadcastReceiverTemplate(
    context: Context,
    val onEvent: (Intent) -> Unit,
    val intentFilter: IntentFilter
) : BroadcastReceiver(), Closeable {

    private var context: Context? =
        context // Have to allow for nulling out later, despite requiring a non-null one initially

    override fun onReceive(context: Context, intent: Intent) {
        onEvent(intent)
    }

    internal fun start() {
        context?.registerReceiver(this, intentFilter)
    }

    override fun close() {
        context?.unregisterReceiver(this)
        context = null
    }

}