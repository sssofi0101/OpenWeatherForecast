package com.example.openweatherforecast.presentation.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.openweatherforecast.R
import com.example.openweatherforecast.databinding.FragmentWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso

class WeatherFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var requestLocationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var binding : FragmentWeatherBinding

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var hasLocationPermition = false
        super.onViewCreated(view, savedInstanceState)

        hasLocationPermition = (ContextCompat.checkSelfPermission(
            this@WeatherFragment.requireContext(),
            "android.permission.ACCESS_COARSE_LOCATION"
        ) == PackageManager.PERMISSION_GRANTED)
        var latitude:Double
        var longitude:Double

        if (hasLocationPermition) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude

                        viewModel.loadForecast(latitude,longitude)

                    } else Toast.makeText(
                        this@WeatherFragment.requireContext(),
                        "Ошибка при получении данных о местоположении. Попробуйте позднее",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this@WeatherFragment.requireContext(), "Разрешите доступ к геолокации",
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
                    activity?.finish() //TODO: Изменить на загрузку данных
                } else {
                    hasLocationPermition = false
                    Toast.makeText(
                        this@WeatherFragment.requireContext(), "Разрешите доступ к геолокации",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        with(binding){
            daysRc.layoutManager = LinearLayoutManager(this@WeatherFragment.requireContext(),LinearLayoutManager.VERTICAL,false)
        }

        viewModel.forecast.observe(viewLifecycleOwner){
            //TODO: Отрисовка данных в RecyclerView
        }

        viewModel.currentWeather.observe(viewLifecycleOwner){
            binding.cityTv.text = it.name
            binding.curTempTv.text = "${it.main.temp} °C"
            binding.descrTv.text = it.weather.first().description
            Picasso.get().load("https://openweathermap.org/img/wn/${it.weather.first().icon}@2x.png").into(binding.iconIv)

        }

    }
}