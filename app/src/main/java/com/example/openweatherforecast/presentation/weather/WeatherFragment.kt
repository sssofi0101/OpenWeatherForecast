package com.example.openweatherforecast.presentation.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        var hasLocationPermition: Boolean
        super.onViewCreated(view, savedInstanceState)

        hasLocationPermition = (ContextCompat.checkSelfPermission(
            this@WeatherFragment.requireContext(),
            "android.permission.ACCESS_COARSE_LOCATION"
        ) == PackageManager.PERMISSION_GRANTED)

        if (hasLocationPermition) {
            getCoordinatesAndLoad()
        }
        else {
            requestLocationPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    hasLocationPermition = true
                    getCoordinatesAndLoad()
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
            binding.cityTv.text = it.city
            binding.curTempTv.text = it.temp
            binding.descrTv.text = it.description
            Picasso.get().load(it.icon).into(binding.iconIv)

        }

        viewModel.loadState.observe(viewLifecycleOwner)
        {
            when (it.status){
                MainWeatherState.Status.LOADING -> {
                    with(binding){
                        cityTv.visibility = View.GONE
                        iconIv.visibility = View.GONE
                        curTempTv.visibility = View.GONE
                        descrTv.visibility = View.GONE
                        daysRc.visibility = View.GONE
                        mainProgressBar.visibility = View.VISIBLE
                    }

                }
                MainWeatherState.Status.SUCCESS -> {
                    with(binding){
                        cityTv.visibility = View.VISIBLE
                        iconIv.visibility = View.VISIBLE
                        curTempTv.visibility = View.VISIBLE
                        descrTv.visibility = View.VISIBLE
                        daysRc.visibility = View.VISIBLE
                        mainProgressBar.visibility = View.GONE
                    }

                }
                MainWeatherState.Status.FAILED -> {
                    Toast.makeText(this.requireContext(),it.msg,Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    fun getCoordinatesAndLoad() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude
                    val lon= location.longitude

                    viewModel.loadForecast(lat,lon)

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
}