package com.alexfacciorusso.rxlifecycledisposable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), RxLifecycleDisposableProvider {
    override lateinit var lifecycleDisposable: RxLifecycleDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleDisposable = RxLifecycleDisposable(this)

        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose { Log.d(MainActivity::class.java.simpleName, "Observable disposed!") }
                .subscribe { Log.d(MainActivity::class.java.simpleName, "Next: $it")}
                .disposeOnLifecycleStop(this)
    }

    override fun onStop() {
        super.onStop()
        Log.d(MainActivity::class.java.simpleName, "onStop")
    }
}
