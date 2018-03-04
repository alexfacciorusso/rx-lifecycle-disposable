@file:JvmName("RxLifecycle")

package com.alexfacciorusso.rxlifecycledisposable

import io.reactivex.disposables.Disposable

/**
 * Interface that provides a [RxLifecycleDisposable].
 *
 * @author [Alex Facciorusso](https://github.com/alexfacciorusso/)
 */
interface RxLifecycleDisposableProvider {
    val lifecycleDisposable: RxLifecycleDisposable
}

/**
 * Sugar syntax for [RxLifecycleDisposableProvider].lifecycleDisposable.addOnDestroy(`disposable`)
 */
fun Disposable.disposeOnLifecycleDestroy(rxLifecycleDisposableProvider: RxLifecycleDisposableProvider) {
    rxLifecycleDisposableProvider.lifecycleDisposable.addOnDestroy(this)
}

/**
 * Sugar syntax for [RxLifecycleDisposableProvider].lifecycleDisposable.addOnStop(`disposable`)
 */
fun Disposable.disposeOnLifecycleStop(rxLifecycleDisposableProvider: RxLifecycleDisposableProvider) {
    rxLifecycleDisposableProvider.lifecycleDisposable.addOnStop(this)
}