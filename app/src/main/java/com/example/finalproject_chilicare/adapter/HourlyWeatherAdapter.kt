package com.example.finalproject_chilicare.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.models.Hourlyweather
import com.squareup.picasso.Picasso


class HourlyWeatherAdapter (private val hourlyWeatherList : List<Hourlyweather>) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder> () {


    inner class HourlyWeatherHolder (private val view : View) : RecyclerView.ViewHolder(view){

        fun bindView (hourly : Hourlyweather) {

            val textHourlyWeather = view.findViewById<TextView>(R.id.textHourlyWeather)
            val iconHourlyWeather = view.findViewById<ImageView>(R.id.iconCardSuhu)
            val textSuhuHourlyWeather = view.findViewById<TextView>(R.id.textCardSuhu)


            textHourlyWeather.text = hourly.time
            textSuhuHourlyWeather.text = hourly.temperature.toString()

            val path = buildIconTemperature(hourly.icon)
            Picasso.get().load(path).into(iconHourlyWeather)
            Log.d("iconweather",path)

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyWeatherAdapter.HourlyWeatherHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.card_hourly_weather,parent,false)
        return HourlyWeatherHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherAdapter.HourlyWeatherHolder, position: Int) {
        holder.bindView(hourlyWeatherList[position])

    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.size
    }

    private fun buildIconTemperature(iconTemperature: String?): String {
        return iconTemperature ?: ""
    }
}