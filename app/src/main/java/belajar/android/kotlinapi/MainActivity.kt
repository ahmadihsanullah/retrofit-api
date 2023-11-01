package belajar.android.kotlinapi

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import belajar.android.kotlinapi.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownServiceException

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("coba", "coba lagi")
    }

    override fun onStart() {
        super.onStart()
        getDataFromApi()
    }


    private fun getDataFromApi(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected){
            ApiService.endPoint.getData()
                .enqueue(object : Callback<MainModel>{
                    override fun onResponse(
                        call: Call<MainModel>,
                        response: Response<MainModel>
                    ) {
                        if(response.isSuccessful){
                            showDatas(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MainModel>, t: Throwable) {
                        if (t is SocketTimeoutException) {
                            printLog("Connection Timeout: ${t.message}")
                        } else if (t is UnknownServiceException) {
                            printLog("Unknown Service Exception: ${t.message}")
                        } else {
                            printLog("Failure: ${t.message}")
                        }

                    }

                })
        }else{
            printLog("gk ada koneksi")
        }

    }

    private fun printLog(message: String){
        Log.d(TAG, message)
    }

    private fun showDatas(data: MainModel){
        val results = data.result
        for(result in results){
                printLog("title: ${result.title}")
        }
        printLog("total ${data.result.size}")
    }

}


