@file:Suppress("unused", "MemberVisibilityCanPrivate")

package com.alexfacciorusso.rxlifecycledisposable

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * A [LifecycleObserver] that disposes [Disposable]s automatically on stop or on destroy events.
 *
 * @param lifecycle The [Lifecycle] to listen for events
 * @param autoRemoveObserverOnDestroy If this variable is setted, [RxLifecycleDisposable] will
 *    detach from that lifecycle on destroy event. If you set this parameter to `false`, you will
 *    need to call [attachToLifecycle] if you don't recreate a new instance of this class.
 *
 * @author [Alex Facciorusso](https://github.com/alexfacciorusso/)
 */

class RxLifecycleDisposable @JvmOverloads constructor(
        lifecycle: Lifecycle,
        autoRemoveObserverOnDestroy: Boolean = true
) : LifecycleObserver {
    private lateinit var lifecycle: Lifecycle
    private var autoRemoveObserverOnDestroy: Boolean = true
    private val onStopCompositeDisposable = CompositeDisposable()
    private val onDestroyCompositeDisposable = CompositeDisposable()

    /**
     * A [LifecycleObserver] that disposes [Disposable]s automatically on stop or on destroy events.
     *
     * @param lifecycleOwner The [LifecycleOwner] that contains the [Lifecycle] to listen for events
     * @param autoRemoveObserverOnDestroy If this variable is setted, [RxLifecycleDisposable] will
     *    detach from that lifecycle on destroy event. If you set this parameter to `false`, you will
     *    need to call [attachToLifecycle] if you don't recreate a new instance of this class.
     */
    @JvmOverloads
    constructor(lifecycleOwner: LifecycleOwner, autoRemoveObserverOnDestroy: Boolean = true) :
            this(lifecycleOwner.lifecycle, autoRemoveObserverOnDestroy)

    init {
        attachToLifecycle(lifecycle, autoRemoveObserverOnDestroy)
    }

    /**
     * Attaches this [LifecycleObserver] to the specifiec [Lifecycle].
     *
     * @param lifecycle The [Lifecycle] to listen for events.
     * @param autoRemoveObserverOnDestroy see the documentation for [RxLifecycleDisposable]
     */
    fun attachToLifecycle(lifecycle: Lifecycle, autoRemoveObserverOnDestroy: Boolean) {
        this.lifecycle = lifecycle
        this.autoRemoveObserverOnDestroy = autoRemoveObserverOnDestroy
        lifecycle.addObserver(this)
    }

    /**
     * Mark the given disposable to be disposed on lifecycle's *destroy* event.
     */
    fun addOnDestroy(disposable: Disposable) {
        onDestroyCompositeDisposable.add(disposable)
    }

    /**
     * Mark the given disposable to be disposed on lifecycle's *stop* event.
     */
    fun addOnStop(disposable: Disposable) {
        onStopCompositeDisposable.add(disposable)
    }

    /**
     * Disposes the events attached with [addOnDestroy].
     *
     * **Note:** Do not call this manually unless you know what you are doing.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        onDestroyCompositeDisposable.clear()
        if (autoRemoveObserverOnDestroy) lifecycle.addObserver(this)
    }

    /**
     * Disposes the events attached with [addOnStop].
     *
     * **Note:** Do not call this manually unless you know what you are doing.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        onStopCompositeDisposable.clear()
    }
}