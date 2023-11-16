package com.example.openweatherforecast.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.openweatherforecast.R
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.data.retrofit.RetrofitImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getF()
    }

    fun getF(){
        thread(start = true) { val repositoryImpl = RepositoryImpl(RetrofitImpl.getService())
            repositoryImpl.loadForecast(44.34,10.99) }
    }
}