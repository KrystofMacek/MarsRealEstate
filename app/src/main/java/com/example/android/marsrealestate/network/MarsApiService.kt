
package com.example.android.marsrealestate.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"

private val retrofit =
        Retrofit
                .Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()


// Interface that defines how Retrofit talks to the web server using HTTP requests.
interface MarsApiService {
    @GET("realestate")
    fun getProperties(): Call<String>
}


// Object called MarsApi to initialize the Retrofit service.
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        //Create() method creates the Retrofit service itself with the MarsApiService interface
        retrofit.create(MarsApiService::class.java)
    }
}