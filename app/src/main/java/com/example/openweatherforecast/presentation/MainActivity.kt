package com.example.openweatherforecast.presentation

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.openweatherforecast.R
import com.example.openweatherforecast.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_weather -> {
                    navController.navigate(R.id.weatherFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_air -> {
                    navController.navigate(R.id.airFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_cities -> {
                    navController.navigate(R.id.citiesFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_map -> {
                    navController.navigate(R.id.mapFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    return@setOnItemSelectedListener true
                }

                else -> {true}
            }

        }

    }
}