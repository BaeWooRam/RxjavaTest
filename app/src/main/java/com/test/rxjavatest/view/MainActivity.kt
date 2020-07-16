package com.test.rxjavatest.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.test.rxjavatest.api.ServiceBuilder
import com.test.rxjavatest.api.User
import com.test.rxjavatest.api.UserService
import com.test.rxjavatest.R
import com.test.rxjavatest.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*





class MainActivity : BaseActivity() {
    var id:Int = 1
    var userService: UserService = ServiceBuilder.getService("https://jsonplaceholder.typicode.com/", UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingClickObserver(mainTitle) {
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.KOREA)
            val time: String = sdf.format(Calendar.getInstance().time)
            it.text = "Clicked : $time"
            getApiByIdData()
        }


    }

    private fun getApiData(){
       userService.getUsers()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(object :Observer<List<User>>{
               override fun onComplete() {

                   Toast.makeText(baseContext, "작업을 완료했습니다. disposable size = ${ compositeDisposable.size()}", Toast.LENGTH_SHORT).show()
               }

               override fun onSubscribe(d: Disposable) {
                   compositeDisposable.add(d)
               }

               override fun onNext(t: List<User>) {
                 for((index, user) in t){
                     Log.i("item $index",user)
                 }
               }

               override fun onError(e: Throwable) {
                   Toast.makeText(baseContext, e.message, Toast.LENGTH_SHORT).show()
               }
           })

    }

    private fun getApiByIdData(){
        userService.getUsers(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<User>{
                override fun onComplete() {
                    id++
                    Toast.makeText(baseContext, "작업을 완료했습니다. disposable size = ${ compositeDisposable.size()}", Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: User) {
                    Log.i("item",t.toString())
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(baseContext, e.message, Toast.LENGTH_SHORT).show()
                }
            })

    }
}