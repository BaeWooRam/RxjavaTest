package com.test.rxjavatest.di

import android.app.Application
import com.test.rxjavatest.GlobalApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component()
interface AppComponent : AndroidInjector<GlobalApplication> {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: GlobalApplication):AppComponent
    }
}