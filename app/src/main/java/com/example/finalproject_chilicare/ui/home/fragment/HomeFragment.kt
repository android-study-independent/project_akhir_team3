package com.example.finalproject_chilicare.ui.home.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.ForumAdapter
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.GetWeatherResponse
import com.example.finalproject_chilicare.data.response.LoginResponse
import com.example.finalproject_chilicare.dataclass.ForumData
import com.example.finalproject_chilicare.ui.home.WeatherActivity
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


    lateinit var buttonCuaca : CardView
    lateinit var buttonArtikel : CardView
    lateinit var buttonForum : CardView
    lateinit var buttonAktivitas : CardView



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
//        date = view.findViewById(R.id.txtdatetime)




        buttonCuaca = view.findViewById(R.id.btnCuaca)
        buttonArtikel = view.findViewById(R.id.btnArtikel)
        buttonForum = view.findViewById(R.id.btnForum)
        buttonAktivitas = view.findViewById(R.id.btnAktivitas)


        buttonCuaca.setOnClickListener { val intent = Intent(activity,WeatherActivity::class.java)
            startActivity(intent)

        }



        /*   <<<<  AMBIL DATA DARI API WEATHER  >>>  */
        val retro = Network().getRetroClientInstance("http://195.35.32.179:8003/").create(UserAPI::class.java)

        val lat = "-7.424278"
        val lon = "109.239639"

        retro.getWeather(lat, lon).enqueue(object : Callback<GetWeatherResponse> {
            override fun onResponse(
                call: Call<GetWeatherResponse>,
                response: Response<GetWeatherResponse>
            ) {
                if (response.isSuccessful) {

                    Log.d("weatherRespn", "weatherData: ${response.body()?.currentWeather?.city.toString()}")

                    cityname.text = response.body()?.currentWeather?.city.toString()
                    temp.text = response.body()?.currentWeather?.temperature.toString() +"°C"
                    humidity.text = "Humidity "+response.body()?.currentWeather?.humidity.toString() +" %"
                    weatherdesc.text =
                        response.body()?.currentWeather?.weatherDescription.toString()
//                    date.text = response.body()?.forecast?.forEach {
//                        date.text = it.date.toString()
//                    }.toString()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "data kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GetWeatherResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        })
    }
   /*   <<<<  AMBIL DATA DARI API WEATHER  >>>  */


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