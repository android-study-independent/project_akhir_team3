package com.example.finalproject_chilicare.ui.home


import android.app.Activity
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.HourlyWeatherAdapter
import com.example.finalproject_chilicare.data.api.NetworkWeather
import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.data.models.Hourlyweather
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
    lateinit var iconSuhuWeather : ImageView

    private var listHourlyWeather = mutableListOf<Hourlyweather>()
    private lateinit var adapter : HourlyWeatherAdapter
    private lateinit var rvHourlyWeather: RecyclerView
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private val LOCATION_REQUEST_CODE = 101
    private val api_key: String = "cbbf40f3774c6530de41deeff9c54f3c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_weather)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        btnAdd = findViewById(R.id.btnAddCity)

        adapter = HourlyWeatherAdapter(listHourlyWeather)
        listHourlyWeather.addAll(getWeatherHourly())

        rvHourlyWeather = findViewById(R.id.rvHourlyWeather)
        rvHourlyWeather.setHasFixedSize(true)

        rvHourlyWeather.adapter = adapter
        rvHourlyWeather.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        iconSuhuWeather = findViewById(R.id.iconSuhu)





        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
                isLocationPermissionGranted =
                    permission[android.Manifest.permission.ACCESS_FINE_LOCATION]
                        ?: isLocationPermissionGranted
            }

        getCurrentLocation()

        btnAdd.setOnClickListener {

            val intent = Intent(this@WeatherActivity, SearchPopularCityActivity::class.java)
            startActivity(intent)
            startActivityForResult(intent, 123)
        }

//        val intent = Intent(this@WeatherActivity, SearchPopularCityActivity::class.java)
//        startActivity(intent)



    }

    private fun getWeatherHourly() : ArrayList<Hourlyweather> {

        val waktuCuaca = resources.getStringArray(R.array.waktuCuaca)
        val iconCuaca = resources.obtainTypedArray(R.array.iconCuaca)
        val suhuCuaca = resources.getStringArray(R.array.suhuPerJam)
        val listHourly = ArrayList<Hourlyweather>()

        for (i in waktuCuaca.indices) {
            val cuaca = Hourlyweather(

                waktuCuaca[i], iconCuaca.getResourceId(i, -1), suhuCuaca[i]
            )
            listHourly.add(cuaca)



        }
        return listHourly


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                val selectedCity = data?.getStringExtra("selectedCity") ?: ""
                Log.d("debug_activityresult", "get selected city $selectedCity")
                getCityWeather(selectedCity)
            }
        }}


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

    private fun isCheckLocationEnabled() : Boolean {

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


//            textWaktuSekarang.text = (body.hourlyweather[0].time)
//            textSuhuSekarang.text = (body.hourlyweather[0].temperature.toString())
//            textWaktuKedua.text = (body.hourlyweather[1].time)
//            textSuhuKedua.text = (body.hourlyweather[1].temperature.toString())
//            textWaktuKetiga.text = (body.hourlyweather[2].time)
//            textSuhuKetiga.text = (body.hourlyweather[2].temperature.toString())
//            textWaktuKeempat.text = (body.hourlyweather[3].time)
//            textSuhuKeempat.text = (body.hourlyweather[3].temperature.toString())

//            adapter = HourlyWeatherAdapter(listHourlyWeather)
////            rvHourlyWeather.text = findViewById(R.id.rvHourlyWeather)
//            rvHourlyWeather.setHasFixedSize(true)
//
//            rvHourlyWeather.adapter = adapter
//            rvHourlyWeather.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


            textHariCuaca1.text = (body.forecast[0].date)
            textHariCuaca2.text = (body.forecast[1].date)
            textHariCuaca3.text = (body.forecast[2].date)
            textKondisiCuaca1.text = (body.forecast[0].weatherDescription)
            textKondisiCuaca2.text = (body.forecast[1].weatherDescription)
            textKondisiCuaca3.text = (body.forecast[2].weatherDescription)
            textSuhu1.text = (body.forecast[0].temperature.toString())+ "°"
            textSuhu2.text = (body.forecast[1].temperature.toString())+ "°"
            textSuhu3.text = (body.forecast[2].temperature.toString())+ "°"


            val path = iconWeather(body.currentWeather.icon)
            Picasso.get().load(path).into(iconSuhuWeather)
            Log.d("IconPath", path)



        }

//        updateTodayIconWeather(body.currentWeather.weatherDescription)
//        for (i in 0 until minOf(4, body.hourlyweather.size)) {
//            updateHourlyIconWeather(body.hourlyweather[i].weatherDescription)
//        }
//        for (i in 0 until minOf(5, body.hourlyweather.size)) {
//            updateNextDayIconWeather(body.forecast[i].weatherDescription)
//        }

    }

    private fun iconWeather (iconWeather: String?): String {
        return iconWeather ?: ""
        // return "http://195.35.32.179:8003/weather?lat=-6.215&lon=106.845 $iconWeather"
    }

    private fun updateHourlyIconWeather(weatherDescription: String) {

        binding.apply {

            if (weatherDescription == "moderate rain") {

//                iconSuhuSekarang.setImageResource(R.drawable.berawan)
//                iconSuhuJam11.setImageResource(R.drawable.hujan)
//                iconSuhuJam12.setImageResource(R.drawable.hujan)
//                iconSuhuJam13.setImageResource(R.drawable.berawan)



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

