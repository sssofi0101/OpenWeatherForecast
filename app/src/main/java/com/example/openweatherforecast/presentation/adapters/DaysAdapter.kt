package com.example.openweatherforecast.presentation.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.openweatherforecast.R
import com.example.openweatherforecast.databinding.MainWeatherItemBinding
import com.example.openweatherforecast.domain.models.MainDayForecastEntity

class DaysAdapter (private val daysList: ArrayList<MainDayForecastEntity>): RecyclerView.Adapter<DaysAdapter.DayHolder>() {
    class DayHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = MainWeatherItemBinding.bind(item)

        fun bind(dayForecast:MainDayForecastEntity) = with(binding){
            dateTv.text = dayForecast.date
            maxMinTempTv.text = "${dayForecast.max_temp} °C /" + "${dayForecast.min_temp} °C"
            cloudTv.text = dayForecast.cloudiness.toString().take(5)
            humidityTv.text = dayForecast.humidity.toString().take(5)
            pressureTv.text = dayForecast.pressure.toString().take(6)
            windTv.text = dayForecast.windSpeed.toString().take(4)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_weather_item, parent,false)

        view.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("dateArg", view.findViewById<TextView>(R.id.date_tv).text.toString())
            view.findNavController().navigate(R.id.action_weatherFragment_to_dayWeatherFragment, bundle)
        }

        return DayHolder(view)
    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.bind(daysList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun cleanList(){
        daysList.removeAll(daysList)
        notifyDataSetChanged()
    }
    fun addDayForecast(dayForecast: MainDayForecastEntity){
        daysList.add(dayForecast)
        notifyItemInserted(itemCount+1)
    }
}