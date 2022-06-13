package com.example.task72

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        swipeRefresh = findViewById(R.id.swipe_refresh)

        val sharedPreferences = getSharedPreferences("json", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        swipeRefresh.setOnRefreshListener {
            requestToApi()
        }
        
        if (sharedPreferences.contains("heroes")){
            val jsonString = sharedPreferences.getString("heroes", "")
            val type: Type = object : TypeToken<ArrayList<Hero?>?>() {}.type
            recyclerView.adapter = Adapter(Gson().fromJson(jsonString, type), this@MainActivity)
            Toast.makeText(this@MainActivity,"fromShared" , Toast.LENGTH_LONG).show()

        } else {
            requestToApi()
        }

    }

    private fun requestToApi() {
        val httpClient = OkHttpClient.Builder()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://akabab.github.io/superhero-api/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        val apiService: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<ArrayList<Hero>>? = apiService.loadHero("all.json")
        call!!.enqueue(object : Callback<ArrayList<Hero>> {

            override fun onResponse(call: Call<ArrayList<Hero>>, response: Response<ArrayList<Hero>>) {
                this@MainActivity.runOnUiThread {
                    val responseBody = response.body()!!

                    editor.putString("heroes", Gson().toJson(responseBody).toString())
                    editor.apply()
                    Toast.makeText(this@MainActivity,"fromApi" , Toast.LENGTH_LONG).show()
                    recyclerView.adapter = Adapter(responseBody, this@MainActivity)
                    swipeRefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ArrayList<Hero>>, t: Throwable) {
            }
        })
    }
}