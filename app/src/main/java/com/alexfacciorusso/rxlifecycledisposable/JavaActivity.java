package com.alexfacciorusso.rxlifecycledisposable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class JavaActivity extends AppCompatActivity implements RxLifecycleDisposableProvider {

    private RxLifecycleDisposable lifecycleDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        lifecycleDisposable = new RxLifecycleDisposable(this);

        Disposable subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe();

        RxLifecycle.disposeOnLifecycleDestroy(subscribe, this);
    }

    @NotNull
    @Override
    public RxLifecycleDisposable getLifecycleDisposable() {
        return lifecycleDisposable;
    }
}
