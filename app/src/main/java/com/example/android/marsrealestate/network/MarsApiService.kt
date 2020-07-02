
package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Network layer
 * API using ViewModel to comunicate with web service
 * */

private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"


private val moshi =
        Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

private val retrofit =
        Retrofit
                .Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()


// Interface that defines how Retrofit talks to the web server using HTTP requests.
interface MarsApiService {
    @GET("realestate")
    fun getPropertiesAsync(): Deferred<List<MarsProperty>>
}


// Object called MarsApi to initialize the Retrofit service.
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        //Create() method creates the Retrofit service itself with the MarsApiService interface
        retrofit.create(MarsApiService::class.java)
    }
}