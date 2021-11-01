package com.example.warehouset.data

import com.example.warehouset.data.home.WarehouseProduct
import com.example.warehouset.data.login.UserLogin
import com.example.warehouset.data.transaction.History
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiInterface {

    @POST("api/login")
    fun signIn(@Header("X-Requested-With") header: String, @Header("Accept") accept: String, @Body userLogin: UserLogin): Observable<GenericResponse<Token>>

    @GET("api/warehouse")
    fun getProducts(@Header("X-Requested-With") header: String, @Header("Accept") accept: String, @Header("Authorization") token: String): Observable<GenericResponse<List<WarehouseProduct>>>

    @POST("api/warehouse/toclient")
    fun postProducts(@Header("X-Requested-With") header: String, @Header("Accept") accept: String, @Header("Authorization") token: String,@Body transaction: PostTransaction): Observable<GenericResponse<List<String>>>

    @POST("api/refresh")
    fun updatedToken(@Header("X-Requested-With") header: String, @Header("Accept") accept: String, @Header("Authorization") token: String): Observable<GenericResponse<Token>>

    @GET("api/warehouse")
    fun searchByNameAndBarCode(@Header("X-Requested-With") header: String, @Header("Accept") accept: String, @Header("Authorization") token: String,@Query("search") search: String): Observable<GenericResponse<List<WarehouseProduct>>>

    @GET("api/transaction")
    fun getTransaction(@Header("X-Requested-With") header: String, @Header("Accept") accept: String, @Header("Authorization") token: String,@Query("date") date: String): Observable<GenericResponse<List<History>>>
}