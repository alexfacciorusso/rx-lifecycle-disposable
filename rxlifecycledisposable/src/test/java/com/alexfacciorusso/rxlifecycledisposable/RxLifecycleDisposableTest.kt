package com.alexfacciorusso.rxlifecycledisposable

import android.arch.lifecycle.Lifecycle
import io.reactivex.disposables.Disposable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class RxLifecycleDisposableTest {
    private lateinit var rxLifecycleDisposable: RxLifecycleDisposable

    @Before
    fun initTest() {
        rxLifecycleDisposable = RxLifecycleDisposable(mock(Lifecycle::class.java))
    }

    @Test
    fun onStopDisposed() {
        val disposable = mock(Disposable::class.java)
        rxLifecycleDisposable.addOnStop(disposable)

        rxLifecycleDisposable.onStop()
        verify(disposable).dispose()
    }

    @Test
    fun onDestroyDisposed() {
        val disposable = mock(Disposable::class.java)
        rxLifecycleDisposable.addOnDestroy(disposable)

        rxLifecycleDisposable.onDestroy()
        verify(disposable).dispose()
    }

    @Test
    fun onStopNotDisposed_withDestroyEvent() {
        val disposable = mock(Disposable::class.java)
        rxLifecycleDisposable.addOnStop(disposable)

        rxLifecycleDisposable.onDestroy()
        verify(disposable, never()).dispose()
    }
    @Test
    fun onDestroyNotDisposed_withStopEvent() {
        val disposable = mock(Disposable::class.java)
        rxLifecycleDisposable.addOnDestroy(disposable)

        rxLifecycleDisposable.onStop()
        verify(disposable, never()).dispose()
    }
}