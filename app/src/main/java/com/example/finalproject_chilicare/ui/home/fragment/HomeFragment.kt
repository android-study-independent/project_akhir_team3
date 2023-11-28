package com.example.finalproject_chilicare.ui.home.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.ForumAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.NetworkWeather
import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.databinding.ActivityWeatherBinding
import com.example.finalproject_chilicare.databinding.FragmentHomeBinding
import com.example.finalproject_chilicare.dataclass.ForumData
import com.example.finalproject_chilicare.ui.home.ArticleActivity
import com.example.finalproject_chilicare.ui.home.WeatherActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private val listforum = ArrayList<ForumData>()
    private lateinit var recylerView: RecyclerView
    private lateinit var forumadapter: ForumAdapter
    private lateinit var cityname: TextView
    private lateinit var temp: TextView
    private lateinit var humidity: TextView
    private lateinit var weatherdesc: TextView
    private lateinit var date: TextView


    lateinit var buttonCuaca: CardView
    lateinit var buttonArtikel: CardView
    lateinit var buttonForum: CardView
    lateinit var buttonAktivitas: CardView
    lateinit var cardbutton: CardView

    private lateinit var binding: FragmentHomeBinding

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val LOCATION_REQUEST_CODE = 101
    val api_key: String = "0289d34d4ef9cbab143b0cea686697fa"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        recylerView = view.findViewById(R.id.rv_Forum)
        recylerView.setHasFixedSize(true)

        forumadapter = ForumAdapter(listforum)
        recylerView.adapter = forumadapter
        listforum.addAll(getListForum())
        recylerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        cityname = view.findViewById<TextView>(R.id.txtcity)
        temp = view.findViewById<TextView>(R.id.txttemperature)
        humidity = view.findViewById<TextView>(R.id.txthumidity)
        weatherdesc = view.findViewById<TextView>(R.id.txtweatherdesc)
        date = view.findViewById(R.id.txtdatetime)


        buttonCuaca = view.findViewById(R.id.btnCuaca)
        buttonArtikel = view.findViewById(R.id.btnArtikel)
        buttonForum = view.findViewById(R.id.btnForum)
        buttonAktivitas = view.findViewById(R.id.btnAktivitas)
        cardbutton = view.findViewById(R.id.btnCardWeather)

        //button ke activity Cuaca
        buttonCuaca.setOnClickListener {
            val intent = Intent(activity, WeatherActivity::class.java)
            startActivity(intent)

        }


        //button ke activity artikel
        buttonArtikel.setOnClickListener {
            val intent = Intent(activity, ArticleActivity::class.java)
            startActivity(intent)
        }


        //button card weather dari beranda
        cardbutton.setOnClickListener {
            val intent = Intent(activity, WeatherActivity::class.java)
            startActivity(intent)
        }

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireActivity())
//        getCurrentLocation()


        /*   <<<<  AMBIL DATA DARI API WEATHER  >>>  */
//        val retro = Network().getRetroClientInstance("http://195.35.32.179:8003/")
//            .create(ApiInterface::class.java)
//
//        val lat = "-7.424278"
//        val lon = "109.239639"
//
//        retro.getWeather(lat, lon).enqueue(object : Callback<CurrentWeather> {
//            override fun onResponse(
//                call: Call<CurrentWeather>,
//                response: Response<CurrentWeather>
//            ) {
//                if (response.isSuccessful) {
//
//                    Log.d(
//                        "weatherRespn",
//                        "weatherData: ${response.body()?.currentWeather?.city.toString()}"
//                    )
//
//
//                    cityname.text = response.body()?.currentWeather?.city.toString()
//                    temp.text = response.body()?.currentWeather?.temperature.toString() + "°C"
//                    humidity.text =
//                        "Humidity " + response.body()?.currentWeather?.humidity.toString() + " %"
//                    weatherdesc.text =
//                        response.body()?.currentWeather?.weatherDescription.toString()
////                    date.text = response.body()?.forecast?.forEach {
////                        date.text = it.date.toString()
////                    }.toString()
//                } else {
//                    Toast.makeText(
//                        requireContext(),
//                        "data kosong",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
//                Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
//            }
//        })
    }
    /*   <<<<  AMBIL DATA DARI API WEATHER  >>>  */


//    private fun getCurrentLocation() {
//
//        if (isCheckPermissions()){
//
//            if (isCheckLocationEnabled()) {
//
//                if (ActivityCompat.checkSelfPermission(
//                        requireContext(),
//                        android.Manifest.permission.ACCESS_FINE_LOCATION
//
//                    )!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        requireContext(),
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION
//
//                    ) != PackageManager.PERMISSION_GRANTED
//                ){
//                    requestLocationPermissions()
//                    return
//                }
//
//                fusedLocationProviderClient.lastLocation
//                    .addOnSuccessListener { location ->
//                        if (location != null) {
//
//                            currentLocation = location
//
//                            checkCurrentLocation(
//                                location.latitude.toString(),
//                                location.longitude.toString()
//                            )
//                        }
//                    }
//            }
//
//            else {
//                val intent = Intent (android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//        }
//        else {
//            requestLocationPermissions()
//        }
//    }
//
//    private fun requestLocationPermissions() {
//
//        requestPermissions(
//            arrayOf(
//                android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ),
//            LOCATION_REQUEST_CODE
//        )
//
//    }
//
//    private fun isCheckLocationEnabled() : Boolean{
//
//        val locationManager : LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE)
//                as LocationManager
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }
//
//    private fun isCheckPermissions() : Boolean {
//
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED){
//
//            return  true
//
//        }
//
//        return false
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == LOCATION_REQUEST_CODE){
//
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//
//                getCurrentLocation()
//        }
//        else {
//
//        }
//
//    }
//
//    private fun checkCurrentLocation (latitude : String, longtitude: String) {
//
//        NetworkWeather.getApiInterface()?.getCurrentWeatherData(api_key, latitude, longtitude)
//            ?.enqueue(object : Callback<CurrentWeather>{
//                override fun onResponse(
//                    call: Call<CurrentWeather>,
//                    response: Response<CurrentWeather>
//                ) {
//                    response.body()?.let {
//
//                        SetDataHome(it)
//
//                    }
//
//                }
//
//                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//
//            })
//
//    }
//
//    private fun SetDataHome(body: CurrentWeather) {
//
//        binding.apply {
//
//            txtcity.text = body.currentWeather.city
//            txtweatherdesc.text = body.currentWeather.weatherDescription
//            temp.text = body.currentWeather.temperature.toString()+"°"
//            humidity.text = body.currentWeather.humidity.toString()+"%"
//
//
//
//
//        }
//
//    }


    private fun getListForum(): ArrayList<ForumData> {
        val dataavatar = resources.obtainTypedArray(R.array.data_avatar)
        val dataName = resources.getStringArray(R.array.data_name)
        val datadesc = resources.getStringArray(R.array.data_desc)
        val datadatetime = resources.getStringArray(R.array.data_datetime)
        val dataimage = resources.obtainTypedArray(R.array.data_image)
        val listforum = ArrayList<ForumData>()
        for (i in dataName.indices) {
            val user = ForumData(
                dataavatar.getResourceId(i, -1), dataName[i],
                datadatetime[i], datadesc[i], dataimage.getResourceId(i, -1)
            )
            listforum.add(user)
        }

        return listforum

    }


}