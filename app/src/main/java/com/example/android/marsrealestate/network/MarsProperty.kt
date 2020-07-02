
package com.example.android.marsrealestate.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class MarsProperty(
        val id: String,
        @Json(name = "img_src") val imgSrcUrl: String,
        val type: String,
        val price: Double
)
