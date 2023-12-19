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
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.article.CardAdapter
import com.example.finalproject_chilicare.adapter.article.CardHomeArtikelAdapter
import com.example.finalproject_chilicare.adapter.ForumAdapter
import com.example.finalproject_chilicare.adapter.forum.MainForumAdapter
import com.example.finalproject_chilicare.adapter.home.BerandaArtikelAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.NetworkWeather
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.data.models.LikeItem
import com.example.finalproject_chilicare.data.response.article.CardArtikelResponse
import com.example.finalproject_chilicare.data.response.login.LoginResponse
import com.example.finalproject_chilicare.databinding.FragmentHomeBinding
import com.example.finalproject_chilicare.dataclass.ForumData
import com.example.finalproject_chilicare.dataclass.HomeArtikel
import com.example.finalproject_chilicare.ui.home.aktivitas.AktivitasActivity
import com.example.finalproject_chilicare.ui.home.article.ArticleActivity
import com.example.finalproject_chilicare.ui.home.article.DetailArticleActivity
import com.example.finalproject_chilicare.ui.home.forum.ForumActivity
import com.example.finalproject_chilicare.ui.login.LoginActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt
import kotlin.random.Random


class HomeFragment : Fragment() {
    private lateinit var recylerView: RecyclerView
    private lateinit var rvartikelrecylerview: RecyclerView
    private lateinit var rvPostinganForum: RecyclerView
    private lateinit var forumadapter: ForumAdapter
    private lateinit var artikeladapter: CardHomeArtikelAdapter
    private lateinit var cityname: TextView
    private lateinit var temp: TextView
    private lateinit var humidity: TextView
    private lateinit var weatherdesc: TextView
    private lateinit var date: TextView
    private lateinit var image: ImageView
    private lateinit var progressBar: ProgressBar



    lateinit var cardAdapter: BerandaArtikelAdapter // adapter artikel
    lateinit var cardForumAdapter: MainForumAdapter // addapter forum

    //    var cardAdapter: CardAdapter? = null // aku rubah jadi kaya gini
    private var cardArtikelResponse = (mutableListOf<CardArtikelResponse>()) // ini buat datanya


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    lateinit var buttonCuaca: CardView
    lateinit var buttonArtikel: CardView
    lateinit var buttonForum: CardView
    lateinit var buttonAktivitas: CardView
    lateinit var cardbutton: CardView
    lateinit var textFullname : TextView


    lateinit var buttonNotification: ImageView


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

        // Inisialisasi adapter terlebih dahulu
        //adapter card Artikel
        cardAdapter = BerandaArtikelAdapter(cardArtikelResponse)

        //adapter card forum

        //progress bar
        progressBar = view.findViewById(R.id.progressBarHome)


        // MENDAPATKAN DATA MENGGUNAKAN KATA KUNCI ARTICLELIST DARI HALAMAN ARTIKEL ACTIVITY
        val articleList =
            requireActivity().intent.getParcelableArrayListExtra<CardArtikelResponse>("articleList")

        // Log untuk memeriksa apakah artikelList tidak null dan berisi data
        Log.d("MyTag", "articleList: $articleList")

//        // Mengatur adapter untuk RecyclerView
        rvartikelrecylerview = view.findViewById(R.id.rv_cardhomeartikel)
        rvartikelrecylerview.adapter = cardAdapter

        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllArtikel()
            result.data.map {
                Log.d("Home", "hasil data ${it}")
                cardArtikelResponse.add(it)
                binding.rvCardhomeartikel.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                binding.rvCardhomeartikel.adapter = cardAdapter
                progressBar.visibility = View.GONE
            }


        }

//        // PENERAPAN UNTUK MENGAMBIL DATANYA
        if (articleList != null && articleList.isNotEmpty()) {
            val randomIndices = List(2) { Random.nextInt(articleList.size) }
            val randomArticles = randomIndices.map { articleList[it] }

            cardAdapter.updateData(randomArticles)

            cardAdapter.onItemClick = { selectedArticle ->
                val intent = Intent(requireContext(), DetailArticleActivity::class.java)
                intent.putExtra("articles", selectedArticle)
                intent.putParcelableArrayListExtra("articleList", articleList)
                startActivity(intent)
            }
        } else {
            Log.d("MyTag", "articleList is empty or null")
        }

        // Setting adapter untuk Forum


        cityname = view.findViewById<TextView>(R.id.txtcity)
        temp = view.findViewById<TextView>(R.id.txttemperature)
        humidity = view.findViewById<TextView>(R.id.txthumidity)
        weatherdesc = view.findViewById<TextView>(R.id.txtweatherdesc)
        date = view.findViewById(R.id.txtdatetime)
        image = view.findViewById(R.id.imgweather)
        textFullname = view.findViewById(R.id.txtavatarname)

        // dapet dari login
        textFullname.text = "${LoginActivity.userFullname} 👋"

        buttonCuaca = view.findViewById(R.id.btnCuaca)
        buttonArtikel = view.findViewById(R.id.btnArtikel)
        buttonForum = view.findViewById(R.id.btnForum)
        buttonAktivitas = view.findViewById(R.id.btnAktivitas)
        cardbutton = view.findViewById(R.id.btnCardWeather)
        buttonNotification = view.findViewById(R.id.imgnotification)

        //button ke activity Notification
        buttonNotification.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

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

        //button ke activity forum
        buttonForum.setOnClickListener {
            val intent = Intent(activity, ForumActivity::class.java)
            startActivity(intent)
        }

        //button card weather dari beranda
        cardbutton.setOnClickListener {
            val intent = Intent(activity, WeatherActivity::class.java)
            startActivity(intent)
        }

        //button ke activity aktivitas
        buttonAktivitas.setOnClickListener {
            val intent = Intent(activity, AktivitasActivity::class.java)
            startActivity(intent)
        }

        // button card artikel
        cardAdapter.onItemClick = {
            Log.d("Homefragment", "klik item  ${it}")
            val intent = Intent(activity, DetailArticleActivity::class.java)
            intent.putExtra("articles", it)
            intent.putParcelableArrayListExtra("articleList", ArrayList(cardArtikelResponse))
            startActivity(intent)
        }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
                isLocationPermissionGranted =
                    permission[android.Manifest.permission.ACCESS_FINE_LOCATION]
                        ?: isLocationPermissionGranted
            }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()

//        val fullnameLogin : LoginResponse
//
//        textFullname.text = fullnameLogin.fullname
//
//        Log.d("Username", "${Log.fullname}")





//        getFullname()

        // menampilkan RecylerView Forum Get API
        forumlist()

    }

    private fun getFullname (fullnameLogin : LoginResponse) {

        textFullname.text = fullnameLogin.fullname

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
            Log.d("iconweather", path)

            /* <<<<< CARD FROM ARTIKEL >>>>> */
        }

    }

    private fun buildIconPath(icon: String?): String {
        return icon ?: ""
    }


    fun forumlist() {
        val retro = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)
        retro.getAllForum().enqueue(object : Callback<AllForumResponse> {
            override fun onResponse(
                call: Call<AllForumResponse>,
                response: Response<AllForumResponse>
            ) {
                response.body()?.let {
                    getItemForum(it)
                }
            }

            override fun onFailure(call: Call<AllForumResponse>, t: Throwable) {
            }
        })
    }

    fun getToken(): String {
        val prefHelper = PreferencesHelper.customForumHome(requireContext())
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    fun getItemForum(body: AllForumResponse) {
        Log.d("HOME", "recyclerview berhasil ${body.allForumItem} ")
        val rvPostingan = binding.rvForum
        rvPostingan.setHasFixedSize(true)
        rvPostingan.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        val adapter = MainForumAdapter(requireContext(), body.allForumItem, object : MainForumAdapter.ItemClickCallback {
            override fun onMore(itemForum: AllForumItem, position: Int) {
                // Handle onMore click here
            }
        }, object : MainForumAdapter.LikeClickCallback {
            override fun onLike(itemLike: LikeItem, position: Int) {
                // Handle onLike click here
            }
        })

        // set adapter to recyclerview
        rvPostingan.adapter = adapter
    }



}