package com.example.finalproject_chilicare.ui.home


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.NetworkWeather
import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.databinding.ActivityWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date

class WeatherActivity : AppCompatActivity() {
    lateinit var binding: ActivityWeatherBinding
    lateinit var btnAdd : ImageView

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private val LOCATION_REQUEST_CODE = 101
    val api_key: String = "0289d34d4ef9cbab143b0cea686697fa"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_weather)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        btnAdd = findViewById(R.id.btnAddCity)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            isLocationPermissionGranted = permission[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranted
        }

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocation()

        btnAdd.setOnClickListener {

            val intent = Intent(this@WeatherActivity, SearchPopularCityActivity::class.java)
            startActivity(intent)
        }


    }

    private fun getCurrentLocation() {

        if (isCheckPermissions()){

            if (isCheckLocationEnabled()) {

                if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION

                )!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION

                ) != PackageManager.PERMISSION_GRANTED
                    ){
                    requestLocationPermissions()
                    return
                }

                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {

                            currentLocation = location

                            checkCurrentLocation(
                                location.latitude.toString(),
                                location.longitude.toString()
                            )
                        }
                    }
            }

            else {
                val intent = Intent (android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_REQUEST_CODE
        )

    }

    private fun isCheckLocationEnabled() : Boolean{

        val locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE)
        as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun isCheckPermissions() : Boolean {

        if (ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED){

            return  true

        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_REQUEST_CODE){

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)

                getCurrentLocation()
        }
        else {

        }

    }

    private fun getCityWeather(city : String) {

        NetworkWeather.getCityApiInterface()?.getCityWeatherData(city, api_key)?.enqueue(
            object : Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful) {

                        response.body()?.let {

                            setData(it)

                        }

                    } else {

                        Toast.makeText(this@WeatherActivity, "No city found",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {

                }

            }
        )

    }

    private fun checkCurrentLocation (latitude : String, longtitude: String) {

        NetworkWeather.getApiInterface()?.getCurrentWeatherData(latitude, longtitude)
            ?.enqueue(object : Callback<CurrentWeather>{
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    response.body()?.let {

                        setData(it)

                    }

                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }

    private fun setData(body: CurrentWeather) {

        binding.apply {

            val currentDate = SimpleDateFormat("dd/MM/yyyy hh:mm").format(Date())


            textLocation.text = body.currentWeather.city
            textSuhu.text = body.currentWeather.temperature.toString() + "°"
            kondisiSuhu.text = body.currentWeather.weatherDescription
            descStatusCuaca.text = body.currentWeather.status
            nilaiKelembapan.text = body.currentWeather.humidity.toString() + "%"
            textTitikEmbun.text = "Titik embun saat ini " +body.currentWeather.dewPoint
            nilaiKecepatanAngin.text = body.currentWeather.windSpeed
            textArahAngin.text = body.currentWeather.windDirection


            textWaktuSekarang.text = (body.hourlyweather[0].time)
            textSuhuSekarang.text = (body.hourlyweather[0].temperature.toString())
            textWaktuKedua.text = (body.hourlyweather[1].time)
            textSuhuKedua.text = (body.hourlyweather[1].temperature.toString())
            textWaktuKetiga.text = (body.hourlyweather[2].time)
            textSuhuKetiga.text = (body.hourlyweather[2].temperature.toString())
            textWaktuKeempat.text = (body.hourlyweather[3].time)
            textSuhuKeempat.text = (body.hourlyweather[3].temperature.toString())


            textHariCuaca1.text = (body.forecast[0].date)
            textHariCuaca2.text = (body.forecast[1].date)
            textHariCuaca3.text = (body.forecast[2].date)
            textKondisiCuaca1.text = (body.forecast[0].weatherDescription)
            textKondisiCuaca2.text = (body.forecast[1].weatherDescription)
            textKondisiCuaca3.text = (body.forecast[2].weatherDescription)
            textSuhu1.text = (body.forecast[0].temperature.toString())+ "°"
            textSuhu2.text = (body.forecast[1].temperature.toString())+ "°"
            textSuhu3.text = (body.forecast[2].temperature.toString())+ "°"

            val path = buildIconPath(body.currentWeather.icon)
            Picasso.get().load(path).into(iconSuhu)
            Log.d("iconweather",path)



        }

        updateTodayIconWeather(body.currentWeather.weatherDescription)
        for (i in 0 until minOf(4, body.hourlyweather.size)) {
            updateHourlyIconWeather(body.hourlyweather[i].weatherDescription)
        }
        for (i in 0 until minOf(5, body.hourlyweather.size)) {
            updateNextDayIconWeather(body.forecast[i].weatherDescription)
        }

    }

    private fun buildIconPath(icon: String?): String {
        return icon?:""
    }

    private fun updateHourlyIconWeather(weatherDescription: String) {

        binding.apply {

            if (weatherDescription == "moderate rain") {

                iconSuhuSekarang.setImageResource(R.drawable.berawan)
                iconSuhuJam11.setImageResource(R.drawable.hujan)
                iconSuhuJam12.setImageResource(R.drawable.hujan)
                iconSuhuJam13.setImageResource(R.drawable.berawan)



            }


        }

    }

    private fun updateNextDayIconWeather(weatherDescription: String) {

        binding.apply {

            if (weatherDescription == "light rain") {


                iconCuaca1.setImageResource(R.drawable.hujan)
                iconCuaca2.setImageResource(R.drawable.hujan)
                iconCuaca3.setImageResource(R.drawable.hujan)

            }


        }


    }


    private fun updateTodayIconWeather(weatherDescription: String) {
            binding.apply {

                if (weatherDescription == "moderate rain") {

                    iconSuhu.setImageResource(R.drawable.sun)

                }


            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ts2td(ts:Long):String{

        val localTime=ts.let {

            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }

        return localTime.toString()


    }

    private fun k2c(t:Double):Double{

        var intTemp=t

        intTemp=intTemp.minus(273)

        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }

}

