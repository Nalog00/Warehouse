package com.example.warehouset.di

import android.content.Context
import com.example.warehouset.MainActivityViewModel
import com.example.warehouset.data.ApiInterface
import com.example.warehouset.login.LoginViewModel
import com.example.warehouset.main.home.HomeAdapter
import com.example.warehouset.main.home.HomeViewModel
import com.example.warehouset.main.transaction.HistoryTransactionAdapter
import com.example.warehouset.main.transaction.HistoryTransactionViewModel
import com.example.warehouset.settings.Settings
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val testUrl: String = "http://back-end.stroyshop.uz/"
private const val url: String = "http://back-end.stroyshop.uz/"
private const val baseUrl = testUrl


val localModule = module {
    single {
        androidApplication().applicationContext.getSharedPreferences(
            androidContext().packageName,
            Context.MODE_PRIVATE
        )
    }
    single { Settings(androidApplication().applicationContext) }
}

val remoteModule = module {
    single {
        GsonBuilder().setLenient().create()
    }
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()

    }
    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get()).build()
    }
    single { get<Retrofit>().create(ApiInterface::class.java) }
}

val adapterModule = module {
    single { HomeAdapter(get()) }
    single { HistoryTransactionAdapter(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(),get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { MainActivityViewModel(get(),get())}
    viewModel { HistoryTransactionViewModel(get())}
}
