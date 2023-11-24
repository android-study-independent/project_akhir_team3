import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.ui.home.article.ArticleActivity
import com.example.finalproject_chilicare.ui.home.article.card.CardAdapter
import com.example.finalproject_chilicare.ui.home.article.card.CardClass


class DetailArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
        val cardClass: CardClass? = intent.getParcelableExtra<CardClass>("DataDetailArticle")
        if (cardClass != null) {
            val dataImage: ImageView = findViewById(R.id.ivArticle)
            val txtTitle: TextView = findViewById(R.id.tvJudulartikel)
            val txtDetailArticle: TextView = findViewById(R.id.tvDecArticle)
            val tvTab: TextView = findViewById(R.id.tvTab)

            txtTitle.text = cardClass.dataSubtitle
            txtDetailArticle.text = cardClass.dataDescription
            tvTab.text = cardClass.dataTitle
            dataImage.setImageResource(cardClass.dataImage)
        }
        override fun onBindViewHolder(holder: CardAdapter.CardViewHolder, position: Int) {
            val currentItem = filteredList[position]

            holder.rvTitle.text = currentItem.dataTitle
            holder.rvSubtitle.text = currentItem.dataSubtitle
            holder.rvDescription.text = currentItem.dataDescription
            holder.rvImage.setImageResource(currentItem.dataImage)

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailArticleActivity::class.java)
                intent.putExtra("DataDetailArticle", currentItem)
                holder.itemView.context.startActivity(intent)
            }
            val backButton: ImageView = findViewById(R.id.ivKembali)
            backButton.setOnClickListener {
                val intent = Intent(this, ArticleActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

