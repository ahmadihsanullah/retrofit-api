package belajar.android.kotlinapi

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import belajar.android.kotlinapi.adapter.MainAdapter
import belajar.android.kotlinapi.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownServiceException

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var mainAdapter: MainAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun onStart() {
        super.onStart()
        setUpRecyclerView()
        getDataFromApi()
    }

    private fun setUpRecyclerView(){
        mainAdapter = MainAdapter(arrayListOf(), object : MainAdapter.OnAdapterListener{
            override fun onClick(result: MainModel.Result) {
//                Toast.makeText(applicationContext, result.title, Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(this@MainActivity, DetailActivity2::class.java)
                        .putExtra("intent_title", result.title)
                        .putExtra("intent_image", result.image)
                )
            }

        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
    }

    private fun getDataFromApi(){
        progressBar.visibility = View.VISIBLE
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected){
            ApiService.endPoint.getData()
                .enqueue(object : Callback<MainModel>{
                    override fun onResponse(
                        call: Call<MainModel>,
                        response: Response<MainModel>
                    ) {
                        progressBar.visibility = View.GONE
                        if(response.isSuccessful){
                            showDatas(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MainModel>, t: Throwable) {
                        progressBar.visibility = View.GONE
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
        mainAdapter.setData(results)
//        for(result in results){
//                printLog("title: ${result.title}")
//        }
    }

}


