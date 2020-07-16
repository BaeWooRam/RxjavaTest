package com.test.rxjavatest.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

open class BaseActivity : AppCompatActivity() {
    var clickDisposableList: HashMap<Int, Disposable> = hashMapOf()
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        if (!clickDisposableList.isNullOrEmpty()) {
            for (disposable in clickDisposableList.toList()) {
                disposable.second.dispose()
            }
        }

        compositeDisposable.clear()
    }

    protected fun <T:View>settingClickObserver(view: T, clickEvent: (T) -> Unit) {
        val disposable = Observable
            .create(ObservableOnSubscribe<View> {
                view.setOnClickListener(it::onNext)
            })
            .debounce(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                clickEvent(it as T)
            }

        clickDisposableList[view.id] = disposable
    }

    protected fun removeClickObserver(view: View){
        clickDisposableList[view.id]?.dispose()
    }
}