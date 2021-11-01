package com.example.warehouset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warehouset.core.Resource
import com.example.warehouset.data.ApiInterface
import com.example.warehouset.data.GenericResponse
import com.example.warehouset.data.Token
import com.example.warehouset.settings.Settings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityViewModel(private val api: ApiInterface,private val settings: Settings): ViewModel() {

    private var  compositeDisposable = CompositeDisposable()
    private var mutableToken: MutableLiveData<Resource<GenericResponse<Token>>> = MutableLiveData()
    val updatedToken: LiveData<Resource<GenericResponse<Token>>> get() = mutableToken

    fun updatedToken(headerXRW: String, headerAccept: String,token: String){
        mutableToken.value = Resource.loading()
        compositeDisposable.add(api.updatedToken(headerXRW,headerAccept,token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.successful)
                    {
                        mutableToken.value = Resource.success(it)
                        settings.token = it.payload.token
                    }
                    else
                    {
                        mutableToken.value = Resource.error(it.message)
                    }
                }
                ,
                {
                    mutableToken.value = Resource.error(it.localizedMessage)
                }
            )
        )
    }
}