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
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
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
import com.example.finalproject_chilicare.ui.home.forum.NewPostForumActivity
import com.example.finalproject_chilicare.ui.home.weather.WeatherChooseCityActivity
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
import kotlin.math.roundToInt

class WeatherActivity : AppCompatActivity() {
    lateinit var binding: ActivityWeatherBinding
    lateinit var btnAdd : ImageView
    lateinit var btnBack : ImageView
    lateinit var iconSuhuWeather : ImageView
    lateinit var btnback : ImageView

    lateinit var progressBar: ProgressBar
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

        progressBar = findViewById(R.id.progressBarWeather)
        btnAdd = findViewById(R.id.btnAddCityUtama)
        btnBack = findViewById(R.id.btnBackInWeatherUtama)

        iconSuhuWeather = findViewById(R.id.iconSuhuUtama)
        btnback = findViewById(R.id.btnBackInWeatherUtama)


        // button back to home
        btnback.setOnClickListener { Intent(this,HomeActivity::class.java).also {
            startActivity(it)
        } }

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

        btnBack.setOnClickListener {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.iconChooseCity.setOnClickListener {
            startActivity(Intent(this, WeatherChooseCityActivity::class.java))
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
        progressBar.visibility = View.VISIBLE
        NetworkWeather.getApiInterface()?.getCityWeatherData(city, api_key)?.enqueue(
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
        progressBar.visibility = View.GONE

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

                }

            })

    }


    private fun setData(body: CurrentWeather) {

        binding.apply {

            val currentDate = SimpleDateFormat("dd/MM/yyyy hh:mm").format(Date())


            textLocationUtama.text = body.currentWeather.city
            textSuhuUtama.text = body.currentWeather.temperature.roundToInt().toString() + "°"
            kondisiSuhuUtama.text = body.currentWeather.weatherDescription
            descStatusCuacaUtama.text = body.currentWeather.status
            nilaiKelembapanUtama.text = body.currentWeather.humidity.toString() + "%"
            textTitikEmbunUtama.text = "Titik embun saat ini " +body.currentWeather.dewPoint
            nilaiKecepatanAnginUtama.text = body.currentWeather.windSpeed
            textArahAnginUtama.text = body.currentWeather.windDirection


            adapter = HourlyWeatherAdapter(body.hourlyweather)
            val rvHourlyWeather = binding.rvHourlyWeatherUtama
            rvHourlyWeather.layoutManager = LinearLayoutManager(this@WeatherActivity, RecyclerView.HORIZONTAL, false)
            rvHourlyWeather.setHasFixedSize(true)

            rvHourlyWeather.adapter = adapter


            Log.d("Debug", "Recyler view berhasil -> ${adapter}")


            adapter.notifyDataSetChanged()




            textHariCuaca1Utama.text = (body.forecast[1].date)
            textHariCuaca2Utama.text = (body.forecast[2].date)
            textHariCuaca3Utama.text = (body.forecast[3].date)
            textKondisiCuaca1Utama.text = (body.forecast[1].weatherDescription)
            textKondisiCuaca2Utama.text = (body.forecast[2].weatherDescription)
            textKondisiCuaca3Utama.text = (body.forecast[3].weatherDescription)
            textSuhu1Utama.text = (body.forecast[1].temperature.toString())+ "°"
            textSuhu2Utama.text = (body.forecast[2].temperature.toString())+ "°"
            textSuhu3Utama.text = (body.forecast[3].temperature.toString())+ "°"

            val path = buildIconPath(body.currentWeather.icon)
            Picasso.get().load(path).into(iconSuhuUtama)
            Log.d("iconweather",path)

            val cuaca1 = buildIconPath(body.forecast[1].icon)
            val cuaca2 = buildIconPath(body.forecast[2].icon)
            val cuaca3 = buildIconPath(body.forecast[3].icon)

            Picasso.get().load(cuaca1).into(iconCuaca1Utama)
            Picasso.get().load(cuaca2).into(iconCuaca2Utama)
            Picasso.get().load(cuaca3).into(iconCuaca3Utama)


        }


    }

    private fun buildIconPath(icon: String?): String {
        return icon?:""
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

