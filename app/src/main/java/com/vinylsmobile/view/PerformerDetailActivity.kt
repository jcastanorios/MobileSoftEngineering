package com.vinylsmobile.view

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vinylsmobile.R
import com.vinylsmobile.model.Performer
import com.vinylsmobile.repository.PerformerRepository
import com.vinylsmobile.view.adapters.PerformerAdapter
import com.vinylsmobile.viewmodels.PerformerDetailViewModel
import com.vinylsmobile.viewmodels.PerformerDetailViewModelFactory
import com.vinylsmobile.viewmodels.PerformerViewModel
import com.vinylsmobile.viewmodels.PerformerViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale
import com.vinylsmobile.model.Musician

class PerformerDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PerformerDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_performer_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val image = findViewById<ImageView>(R.id.performerImageDetail)
        val title = findViewById<TextView>(R.id.performerNameDetail)
        val description = findViewById<TextView>(R.id.performerDescription)
        val year = findViewById<TextView>(R.id.performerYear)
        val day = findViewById<TextView>(R.id.performerDay)
        val month = findViewById<TextView>(R.id.performerMonth)

        val repository = PerformerRepository()
        viewModel = ViewModelProvider(this, PerformerDetailViewModelFactory(repository)).get(
            PerformerDetailViewModel::class.java
        )
        viewModel.performer.observe(this) { performer ->

            if (performer is Musician) {
                Glide.with(this)
                    .load(performer.image)
                    .into(image)

                title.text = performer.name
                description.text = performer.description

                val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.getDefault())
                val humanReadableFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
                val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())

                val date = isoFormat.parse(performer.birthDate)

                year.text =  date?.let { yearFormat.format(it) } ?: performer.birthDate
                month.text = date?.let { monthFormat.format(it) } ?: performer.birthDate
                day.text = date?.let { dayFormat.format(it) } ?: performer.birthDate
            }

        }
        val performerDetailBackButton = findViewById<ImageButton>(R.id.performerDetailBackButton)

        performerDetailBackButton.setOnClickListener {
            finish()
        }

        val performerID = intent.getIntExtra("performerID", 0)
        viewModel.loadPerformer(performerID)
    }
}