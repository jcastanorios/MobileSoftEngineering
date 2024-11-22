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
import com.vinylsmobile.adapters.CommentAdapter
import com.vinylsmobile.viewmodels.AlbumDetailViewModel
import com.vinylsmobile.viewmodels.CommentViewModel
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.content.Intent
import androidx.activity.viewModels



import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var starRatingBar: RatingBar
    private val commentViewModel: CommentViewModel by viewModels()


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
        val commentsRecyclerView = findViewById<RecyclerView>(R.id.commentsRecyclerView)


        commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        commentAdapter = CommentAdapter(emptyList())
        commentsRecyclerView.adapter = commentAdapter


        viewModel = ViewModelProvider(
            this,
            AlbumDetailViewModel.AlbumDetailViewModelFactory(application)
        ).get(AlbumDetailViewModel::class.java)

        starRatingBar = findViewById(R.id.starRatingBar)

        viewModel.album.observe(this) { album ->
            Glide.with(this)
                .load(album.cover)
                .into(image)

            title.text = album.name
            description.text = album.description


            year.text = album.releaseDate
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.getDefault())
            val humanReadableFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = isoFormat.parse(album.releaseDate)
            year.text = if (date != null) humanReadableFormat.format(date) else album.releaseDate
            genre.text = album.genre
            label.text = album.recordLabel

            commentAdapter = CommentAdapter(album.comments)
            commentsRecyclerView.adapter = commentAdapter
        }

        viewModel.comments.observe(this) { comments ->
            commentAdapter = CommentAdapter(comments)
            commentsRecyclerView.adapter = commentAdapter
        }

        commentViewModel.commentSaved.observe(this) { isSaved: Boolean ->
            if (isSaved) {
                val albumId = intent.getIntExtra("albumID", 0)
                viewModel.loadAlbum(albumId)
            }
        }

        val albumID = intent.getIntExtra("albumID", 0)
        viewModel.loadAlbum(albumID)

        val albumDetailBackButton = findViewById<ImageButton>(R.id.albumDetailBackButton)

        albumDetailBackButton.setOnClickListener {
            finish()
        }

        val commentAlbumButton = findViewById<LinearLayout>(R.id.commentAlbumContainer)
        commentAlbumButton.setOnClickListener {
            val album = viewModel.album.value
            val intent = Intent(this, CommentActivity::class.java)
            intent.putExtra("albumId", album?.id ?: -1)
            intent.putExtra("albumName", album?.name)

            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.getDefault())
            val humanReadableFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = isoFormat.parse(album?.releaseDate)
            intent.putExtra("albumYear",  if (date != null) humanReadableFormat.format(date)
            else album?.releaseDate)
            intent.putExtra("albumCover", album?.cover)
            startActivity(intent)
        }
    }
}