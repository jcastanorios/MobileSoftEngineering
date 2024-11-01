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
import com.vinylsmobile.model.Album
import com.vinylsmobile.repository.AlbumRepository
import com.vinylsmobile.view.adapters.AlbumAdapter
import com.vinylsmobile.viewmodels.AlbumDetailViewModel
import com.vinylsmobile.viewmodels.AlbumDetailViewModelFactory
import com.vinylsmobile.viewmodels.AlbumViewModel
import com.vinylsmobile.viewmodels.AlbumViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: AlbumDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val image = findViewById<ImageView>(R.id.albumCover)
        val title = findViewById<TextView>(R.id.albumName)
        val description = findViewById<TextView>(R.id.albumDescription)
        val year = findViewById<TextView>(R.id.albumYear)
        val genre = findViewById<TextView>(R.id.albumGenre)
        val label = findViewById<TextView>(R.id.albumLabel)

        val repository = AlbumRepository()
        viewModel = ViewModelProvider(this, AlbumDetailViewModelFactory(repository)).get(
            AlbumDetailViewModel::class.java)
        viewModel.album.observe(this) { album ->
            Glide.with(this)
                .load(album.cover)
                .into(image)

            title.text=album.name
            description.text=album.description
            year.text=album.releaseDate
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.getDefault())
            val humanReadableFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = isoFormat.parse(album.releaseDate)
            year.text = if (date != null) humanReadableFormat.format(date) else album.releaseDate
            genre.text=album.genre
            label.text=album.recordLabel
        }
        val albumDetailBackButton = findViewById<ImageButton>(R.id.albumDetailBackButton)

        albumDetailBackButton.setOnClickListener {
            finish()
        }

        val albumID = intent.getIntExtra("albumID", 0)
        viewModel.loadAlbum(albumID)
    }
}