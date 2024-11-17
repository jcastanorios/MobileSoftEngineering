package com.vinylsmobile.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vinylsmobile.databinding.ActivityPerformerDetailBinding
import com.vinylsmobile.model.Band
import com.vinylsmobile.model.Musician
import com.vinylsmobile.repository.PerformerRepository
import com.vinylsmobile.viewmodels.PerformerDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.lifecycle.ViewModelProvider
import com.vinylsmobile.R

class PerformerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerformerDetailBinding
    private lateinit var viewModel: PerformerDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerformerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            PerformerDetailViewModel.PerformerDetailViewModelFactory(application)
        ).get(PerformerDetailViewModel::class.java)

        viewModel.performer.observe(this) { performer ->
            Glide.with(this)
                .load(performer.image)
                .into(binding.performerImageDetail)

            binding.performerNameDetail.text = performer.name
            binding.performerDescription.text = performer.description

            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.getDefault())
            val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
            val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
            val date = null
            if (performer is Musician) {
                val date = isoFormat.parse(performer.birthDate)
            } else if (performer is Band) {
                val date = isoFormat.parse(performer.creationDate)
            }
            if (date != null) {
                binding.performerYear.text = yearFormat.format(date)
                binding.performerMonth.text = monthFormat.format(date)
                binding.performerDay.text = dayFormat.format(date)
            }

            binding.performerFecNaci.text = getString(R.string.fec_crea_placeholder)
        }

        binding.performerDetailBackButton.setOnClickListener {
            finish()
        }

        val performerID = intent.getIntExtra("performerID", 0)
        viewModel.loadPerformer(performerID)
    }
}

