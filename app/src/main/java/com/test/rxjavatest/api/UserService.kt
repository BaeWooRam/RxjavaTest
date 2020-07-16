package com.test.rxjavatest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users")
    fun getUsers(): Observable<List<User>>

    @GET("users/{id}")
    fun getUsers(@Path("id") id:Int): Observable<User>
}