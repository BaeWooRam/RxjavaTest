package com.test.rxjavatest

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class GlobalApplication :DaggerApplication(){

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApp
    }
}