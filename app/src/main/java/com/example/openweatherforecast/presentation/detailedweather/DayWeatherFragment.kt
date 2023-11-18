package com.example.openweatherforecast.presentation.detailedweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.openweatherforecast.R
import com.example.openweatherforecast.databinding.FragmentDayWeatherBinding
import com.example.openweatherforecast.presentation.weather.WeatherViewModel

class DayWeatherFragment : Fragment() {
    private val viewModel by viewModels<DayDetailedViewModel>()
    private lateinit var binding : FragmentDayWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = arguments?.getString("dateArg")
        binding.dateDetailTv.text = date
        if (date != null) {
            viewModel.loadDetails(date)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigate(R.id.weatherFragment)
        }

        viewModel.dayDetails.observe(viewLifecycleOwner){
            with(binding){
                humidityDetailTv.text = it.humidity.toString()
                windSpeedDetailTv.text = it.wind_speed.toString()
                cloudDetailTv.text = it.clouds.toString()
                pressureDetailTv.text = it.pressure.toString()
                minTempTv.text = "${it.min_temp} °C"
                maxTempTv.text = "${it.max_temp} °C"
                windGustDetailTv.text = it.max_gust.toString()
                visibilityDetailTv.text = it.avg_visibility.toString()
            }
        }
    }

}