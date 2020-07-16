package com.test.rxjavatest.di

import com.test.rxjavatest.api.ServiceBuilder
import com.test.rxjavatest.api.UserService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideUserService():UserService = ServiceBuilder.getService("https://jsonplaceholder.typicode.com/", UserService::class.java)
}