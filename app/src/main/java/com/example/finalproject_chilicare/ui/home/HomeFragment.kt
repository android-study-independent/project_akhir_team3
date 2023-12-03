package com.example.finalproject_chilicare.ui.home

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
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.ForumAdapter
import com.example.finalproject_chilicare.data.api.NetworkWeather
import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.databinding.FragmentHomeBinding
import com.example.finalproject_chilicare.dataclass.ForumData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt


class HomeFragment : Fragment() {
    private val listforum = ArrayList<ForumData>()
    private lateinit var recylerView: RecyclerView
    private lateinit var forumadapter: ForumAdapter
    private lateinit var cityname: TextView
    private lateinit var temp: TextView
    private lateinit var humidity: TextView
    private lateinit var weatherdesc: TextView
    private lateinit var date: TextView
    private lateinit var image: ImageView
    private lateinit var cardtitle1: TextView
    private lateinit var cardtitledesc1: TextView
    private lateinit var carddesc1: TextView
    private lateinit var cardlastseen1: TextView
    private lateinit var cardtitle2: TextView
    private lateinit var cardtitledesc2: TextView
    private lateinit var carddesc2: TextView
    private lateinit var cardlastseen2: TextView


//    private lateinit var binding: FragmentHomeBinding

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    lateinit var buttonCuaca: CardView
    lateinit var buttonArtikel: CardView
    lateinit var buttonForum: CardView
    lateinit var buttonAktivitas: CardView
    lateinit var cardbutton: CardView


    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private val LOCATION_REQUEST_CODE = 101
//    val api_key: String = "0289d34d4ef9cbab143b0cea686697fa"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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
        image = view.findViewById(R.id.imgweather)
        cardtitle1 = view.findViewById(R.id.titleMenanam)
        cardtitledesc1 = view.findViewById(R.id.txttitledesc)
        carddesc1 = view.findViewById(R.id.txtdescmenanam)
        cardlastseen1 = view.findViewById(R.id.tvlastseen)
        cardtitle2 = view.findViewById(R.id.titleBibit)
        cardtitledesc2 = view.findViewById(R.id.txtdesctitlebibit)
        carddesc2 = view.findViewById(R.id.txtdescbibit)
        cardlastseen2 = view.findViewById(R.id.tvlastseen2)


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


        // button card artikel
        

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
                isLocationPermissionGranted =
                    permission[android.Manifest.permission.ACCESS_FINE_LOCATION]
                        ?: isLocationPermissionGranted
            }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()

    }


    private fun getCurrentLocation() {

        if (isCheckPermissions()) {

            if (isCheckLocationEnabled()) {

                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION

                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION

                    ) != PackageManager.PERMISSION_GRANTED
                ) {
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
            } else {
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {

        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_REQUEST_CODE
        )

    }

    private fun isCheckLocationEnabled(): Boolean {

        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun isCheckPermissions(): Boolean {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            return true

        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_REQUEST_CODE) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)

                getCurrentLocation()
        } else {

        }

    }

    private fun checkCurrentLocation(latitude: String, longtitude: String) {

        NetworkWeather.getApiInterface()?.getCurrentWeatherData(latitude, longtitude)
            ?.enqueue(object : Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    response.body()?.let {

                        SetDataHome(it)

                    }

                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }

    private fun SetDataHome(body: CurrentWeather) {

        binding.apply {

            /* <<<<< CARD FROM WEATHER >>>>> */
            txtcity.text = body.currentWeather.city
            txtweatherdesc.text = body.currentWeather.weatherDescription
            temp.text = body.currentWeather.temperature.roundToInt().toString() + "°"
            humidity.text = "Kelembapan " + body.currentWeather.humidity.toString() + " %"
            date.text = (body.forecast[0].date)

            val path = buildIconPath(body.currentWeather.icon)
            Picasso.get().load(path).into(image)
            Log.d("iconweather",path)

            /* <<<<< CARD FROM ARTIKEL >>>>> */

        }

    }

    private fun buildIconPath(icon: String?): String {
        return icon?:""
    }


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