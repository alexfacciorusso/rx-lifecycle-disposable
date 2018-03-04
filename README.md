# RxLifecycleDisposable

Disposing disposables automatically and with style!


## Usage

All you need to do is to inherit your class from `RxLifecycleDisposable` and implement the 
`lifecycleDisposable` property or the getLifecycleDisposable method if you are using Java.

Pass your `Lifecycle` or, if your class is a `LifecycleOwner`, just `this`, to the 
`RxLifecycleDisposable` constructor (Activity, Fragment and many other classes inherits that by 
default if you are using AppCompat libraries since 26.1.0).

After the initialization, you can simply use `disposeOnLifecycleStop` on a `Disposable` instance,
or, in Java, `RxLifecycle.disposeOnLifecycleDestroy(disposable, this)`.

#### Kotlin

    class MainActivity : AppCompatActivity(), RxLifecycleDisposableProvider {
        override lateinit var lifecycleDisposable: RxLifecycleDisposable
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
    
            lifecycleDisposable = RxLifecycleDisposable(this)
    
            Observable.interval(1, TimeUnit.SECONDS)
                    .subscribe()
                    .disposeOnLifecycleStop(this)
        }
    }

#### Java
    
    public class JavaActivity extends AppCompatActivity implements RxLifecycleDisposableProvider {
        private RxLifecycleDisposable lifecycleDisposable;
    
        @NotNull
        @Override
        public RxLifecycleDisposable getLifecycleDisposable() {
            return lifecycleDisposable;
        }
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_java);
    
            lifecycleDisposable = new RxLifecycleDisposable(this);
    
            Disposable disposable = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribe();
    
            RxLifecycle.disposeOnLifecycleDestroy(disposable, this);
        }
    }
    

## Installation

Add one of the following lines depending to your Gradle Android plugin version in your
`dependencies` section:

    // For plugin version < 3.x
    compile 'com.alexfacciorusso:rxlifecycledisposable:0.1.0'

    // For plugin version >= 3.x
    implementation 'com.alexfacciorusso:rxlifecycledisposable:0.1.0'


## License

Copyright 2017 Alex Facciorusso.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
