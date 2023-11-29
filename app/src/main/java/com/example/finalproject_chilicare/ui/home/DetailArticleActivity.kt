package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.CardAdapter
import com.example.finalproject_chilicare.data.response.CardResponse
import com.example.finalproject_chilicare.utils.DummyDataArtikel

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var rvCardArticle: RecyclerView
    private lateinit var cardAdapter: CardAdapter

    private lateinit var cardResponses: ArrayList<CardResponse>

    private lateinit var tvWebView: WebView
    private lateinit var ivKembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        // Menginisialisasi view
        ivKembali = findViewById(R.id.ivKembali)
        tvWebView = findViewById(R.id.tvWebView)
        rvCardArticle = findViewById(R.id.rv_cardArticle2)

        // Set listener untuk tombol kembali
        ivKembali.setOnClickListener {
            Intent(this, ArticleActivity::class.java).also {
                startActivity(it)
            }
        }

        // Mendapatkan data dari intent
        val getData = intent.getParcelableExtra<CardResponse>("android")
        if (getData != null) {
            // Menetapkan data ke view
            val dataImageDetails: ImageView = findViewById(R.id.ivArticle)
            val dataTanggalDetails: TextView = findViewById(R.id.tvTanggal)
            val dataWaktuDetails: TextView = findViewById(R.id.tvWaktu)
            val dataTitleDetails: TextView = findViewById(R.id.tvJudulartikel)
            val dataSubtitleDetails: TextView = findViewById(R.id.tvTab)
            val dataWebViewDetails: WebView = findViewById(R.id.tvWebView)

            // Menetapkan data ke view
            dataImageDetails.setImageResource(getData.dataImageDetails)
            dataTanggalDetails.text = getData.dataTanggalDetails
            dataWaktuDetails.text = getData.dataWaktuDetails
            dataTitleDetails.text = getData.dataTitleDetails
            dataSubtitleDetails.text = getData.dataSubtitleDetails
            dataWebViewDetails.loadData(getData.dataWebViewDetails, "text/html", "utf-8")

            // Memuat data HTML ke WebView
            tvWebView.settings.javaScriptEnabled = true
            tvWebView.webViewClient = WebViewClient()
            tvWebView.loadDataWithBaseURL(null, getData.dataWebViewDetails, "text/html", "UTF-8", null)
        }

        tvWebView = findViewById(R.id.tvWebView)
        val htmlText = """
         
                <p>Dengan banyaknya <strong>nutrisi </strong>yang terkandung dalam cabai didukung dengan maraknya makanan pedas sebagai bisnis di bidang makanan dan jajanan pedas yang berdampak kepada para petani cabai. Ingin tanaman cabai Anda cepat berbuah? Simak <strong>beberapa </strong>cara berikut ini.</p>

                <p>&nbsp;</p>

                <ol>
                	<li>test</li>
                	<li>tets</li>
                	<li>teste</li>
                	<li>stete</li>
                	<li>tehsa</li>
                </ol>

                <p><strong>Lorem Ipsum</strong>&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>

                <p>&nbsp;</p>

                <p><strong>Lorem Ipsum</strong>&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only<em> five centuries, but also the leap into electronic typesetting, remaining essentially unch</em>anged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>

                <p>&nbsp;</p>

                <p><strong>Lorem Ipsum</strong>&nbsp;is simply dummy text of the printing and typesetting industry.<u> Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essential</u>ly unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>

                <p>&nbsp;</p>

                <p>😁</p>

                <p>&nbsp;</p>

                <table border="1" cellpadding="1" cellspacing="1" style="width:500px">
                	<tbody>
                		<tr>
                			<td>test</td>
                			<td>aaouaoe</td>
                		</tr>
                		<tr>
                			<td>oeuao</td>
                			<td>oaeuaoe</td>
                		</tr>
                		<tr>
                			<td>aoeuaoe</td>
                			<td>oeu</td>
                		</tr>
                	</tbody>
                </table>

                <p>&nbsp;</p>

          
        """.trimIndent()

        tvWebView.settings.javaScriptEnabled = true
        tvWebView.webViewClient = WebViewClient()
        tvWebView.loadDataWithBaseURL(null, htmlText, "text/html", "UTF-8", null)



        val cardResponses: MutableList<CardResponse> = DummyDataArtikel.getDummyCardResponses().toMutableList()

        val filteredCardResponses = cardResponses.take(2).toMutableList()


        // Inisialisasi dan atur adapter CardAdapter untuk RecyclerView card
        cardAdapter = CardAdapter(filteredCardResponses)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        rvCardArticle.setHasFixedSize(true)
        rvCardArticle.adapter = cardAdapter

        // Menangani klik item di RecyclerView card
        cardAdapter.onItemClick = {
            Log.d("ArticleActivity", "Clicked item: $it")
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }
}
