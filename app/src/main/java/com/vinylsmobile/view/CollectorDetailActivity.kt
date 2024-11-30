package com.vinylsmobile.view

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.vinylsmobile.R
import com.vinylsmobile.viewmodels.CollectorDetailViewModel

class CollectorDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: CollectorDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_collector_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val cover = findViewById<TextView>(R.id.collectorCover)
        val name = findViewById<TextView>(R.id.collectorName)
        val telephone = findViewById<TextView>(R.id.collectorTelephone)
        val email = findViewById<TextView>(R.id.collectorEmail)
        val albumsCount = findViewById<TextView>(R.id.collectorAlbumsCount)

        /*Nueva forma de llamar al Album*/
        viewModel = ViewModelProvider(
            this,
            CollectorDetailViewModel.CollectorDetailViewModelFactory(application)
        ).get(CollectorDetailViewModel::class.java)

        viewModel.collector.observe(this) { collector ->
            val initials = getInitials(collector.name)
            cover.text = initials
            name.text = collector.name
            telephone.text = collector.telephone
            email.text = collector.email

            val albumCount = collector.collectorAlbums.size
            albumsCount.text = "Álbumes de ${collector.name}: $albumCount"
        }
        val collectorDetailBackButton = findViewById<ImageButton>(R.id.collectorDetailBackButton)

        collectorDetailBackButton.setOnClickListener {
            finish()
        }

        val collectorID = intent.getIntExtra("collectorID", 0)
        viewModel.loadCollector(collectorID)
    }
    private fun getInitials(name: String): String {
        val parts = name.split(" ")
        val initials = parts.take(2).joinToString("") { it.take(1).uppercase() }
        return initials
    }
}


