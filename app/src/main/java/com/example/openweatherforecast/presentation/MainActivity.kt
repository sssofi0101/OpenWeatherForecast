package com.example.openweatherforecast.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.openweatherforecast.R
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.data.retrofit.RetrofitImpl
import com.example.openweatherforecast.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var requestLocationPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        var hasLocationPermition = false
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getF()

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

        hasLocationPermition = (ContextCompat.checkSelfPermission(
            this,
            "android.permission.ACCESS_COARSE_LOCATION"
        ) == PackageManager.PERMISSION_GRANTED)
        var latitude:Double
        var longitude:Double

        if (hasLocationPermition) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude

                    } else Toast.makeText(
                        this@MainActivity,
                        "Ошибка при получении данных о местоположении. Попробуйте позднее",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this@MainActivity, "Разрешите доступ к геолокации",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        else {
            requestLocationPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    hasLocationPermition = true
                } else {
                    hasLocationPermition = false
                    Toast.makeText(
                        this@MainActivity, "Разрешите доступ к геолокации",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    fun getF(){
        thread(start = true) { val repositoryImpl = RepositoryImpl(RetrofitImpl.getService())
            repositoryImpl.loadForecast(44.34,10.99) }
    }

    fun getCoordinates(){

    }

}