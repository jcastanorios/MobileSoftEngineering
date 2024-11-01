package com.vinylsmobile.view

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.vinylsmobile.R
import com.vinylsmobile.model.Album
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val album = intent.getSerializableExtra("album") as Album
        val image = findViewById<ImageView>(R.id.albumCover)
        val title = findViewById<TextView>(R.id.albumName)
        val description = findViewById<TextView>(R.id.albumDescription)
        val year = findViewById<TextView>(R.id.albumYear)
        val genre = findViewById<TextView>(R.id.albumGenre)
        val label = findViewById<TextView>(R.id.albumLabel)
        val albumDetailBackButton = findViewById<ImageButton>(R.id.albumDetailBackButton)

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

        albumDetailBackButton.setOnClickListener {
            finish()
        }
    }
}