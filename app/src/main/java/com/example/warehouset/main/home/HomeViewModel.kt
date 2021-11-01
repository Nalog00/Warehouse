package com.example.warehouset.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warehouset.core.Resource
import com.example.warehouset.data.ApiInterface
import com.example.warehouset.data.GenericResponse
import com.example.warehouset.data.PostTransaction
import com.example.warehouset.data.Token
import com.example.warehouset.data.home.WarehouseProduct
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(private val api: ApiInterface): ViewModel() {
        private var  compositeDisposable = CompositeDisposable()
    private var mutableProducts: MutableLiveData<Resource<GenericResponse<List<WarehouseProduct>>>> = MutableLiveData()
    val products: LiveData<Resource<GenericResponse<List<WarehouseProduct>>>> get() = mutableProducts

    private var mutableProductSearch: MutableLiveData<Resource<GenericResponse<List<WarehouseProduct>>>> = MutableLiveData()
    val productSearch: LiveData<Resource<GenericResponse<List<WarehouseProduct>>>> get() = mutableProductSearch

    private var mutablePostProducts: MutableLiveData<Resource<GenericResponse<List<String>>>> = MutableLiveData()
    val postProducts: LiveData<Resource<GenericResponse<List<String>>>> get() = mutablePostProducts

    fun getProducts(headerXRW: String, headerAccept: String,token: String){
        mutableProducts.value = Resource.loading()
        compositeDisposable.add(api.getProducts(headerXRW,headerAccept,token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.successful){
                        mutableProducts.value = Resource.success(it)
                    }
                    else
                    {
                        mutableProducts.value = Resource.error(it.message)
                    }
                },
                {
                    mutableProducts.value = Resource.error(it.localizedMessage)
                }
            )
        )

    }

    fun postProducts(headerXRW: String, headerAccept: String,token: String,transaction: PostTransaction){
        mutablePostProducts.value = Resource.loading()
        compositeDisposable.add(api.postProducts(headerXRW,headerAccept,token,transaction)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.successful){
                        mutablePostProducts.value = Resource.success(it)
                    }else{mutablePostProducts.value = Resource.error(it.message)}
                }
                ,
                {
                    mutablePostProducts.value = Resource.error(it.localizedMessage)
                }
            )
        )

    }

    fun searchByNameProductAndBarCodeProduct(headerXRW: String, headerAccept: String,token: String,search: String){
        mutableProductSearch.value = Resource.loading()
        compositeDisposable.add(api.searchByNameAndBarCode(headerXRW,headerAccept,token,search)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.successful)
                    {
                    mutableProductSearch.value = Resource.success(it)
                    }
                    else
                    {
                        mutableProductSearch.value = Resource.error(it.message)
                    }
                }
                ,
                {
                mutableProductSearch.value = Resource.error(it.localizedMessage)
                }
            )
        )
    }
}