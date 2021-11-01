package com.example.warehouset.main.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warehouset.core.Resource
import com.example.warehouset.data.ApiInterface
import com.example.warehouset.data.GenericResponse
import com.example.warehouset.data.transaction.History
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HistoryTransactionViewModel(private val api: ApiInterface): ViewModel() {
    private var compositeDisposable = CompositeDisposable()

    private var mutableHisTransaction: MutableLiveData<Resource<GenericResponse<List<History>>>> = MutableLiveData()
    val historyTransaction: LiveData<Resource<GenericResponse<List<History>>>> get() = mutableHisTransaction

    fun getHistoryTransaction(headerXRW: String, headerAccept: String,token: String,date: String){
        mutableHisTransaction.value = Resource.loading()
        compositeDisposable.add(api.getTransaction(headerXRW,headerAccept,token,date)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                 if (it.successful){
                     mutableHisTransaction.value = Resource.success(it)
                 }else{mutableHisTransaction.value = Resource.error(it.message)}
                }
                ,
                {
                mutableHisTransaction.value = Resource.error(it.localizedMessage)
                }
            )
        )
    }
}