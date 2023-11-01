package belajar.android.kotlinapi.retrofit

import belajar.android.kotlinapi.MainModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {

    @GET("data.php")
    fun getData(): Call<MainModel>
}