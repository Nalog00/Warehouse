package com.example.warehouset.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warehouset.core.Resource
import com.example.warehouset.data.*
import com.example.warehouset.data.login.UserLogin
import com.example.warehouset.settings.Settings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel(private val api: ApiInterface,private val setting: Settings): ViewModel() {
    private var compositeDisposable = CompositeDisposable()

    private var mutableUser: MutableLiveData<Resource<GenericResponse<Token>>> = MutableLiveData()
    val user: LiveData<Resource<GenericResponse<Token>>> get() = mutableUser

    fun signIn(headerXRW: String,headerAccept: String,userLogin: UserLogin){
        mutableUser.value = Resource.loading()
        compositeDisposable.add(
            api.signIn(headerXRW,headerAccept,userLogin)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.successful)
                        {
                         setting.token = it.payload.token
                         setting.role = it.payload.role
                         Log.d("rollerrr",setting.role)
                         mutableUser.value = Resource.success(it)
                        }
                        else
                        {
                            mutableUser.value = Resource.error(it.message)
                        }
                    }
                ,
                    {
                        mutableUser.value = Resource.error(it.localizedMessage)
                    }
                )
        )
    }

}