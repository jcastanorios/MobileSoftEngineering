package com.vinylsmobile.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vinylsmobile.R
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.Toast
import com.vinylsmobile.viewmodels.CommentViewModel
import androidx.activity.viewModels
import com.vinylsmobile.model.Comment

class CommentActivity : AppCompatActivity() {

    private lateinit var albumCover: ImageView
    private lateinit var albumTitle: TextView
    private lateinit var albumYear: TextView
    private lateinit var commentDescription: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private val commentViewModel: CommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val commentDetailBackButton = findViewById<ImageButton>(R.id.commentDetailBackButton)

        commentDetailBackButton.setOnClickListener {
            finish()
        }

        albumCover = findViewById(R.id.albumCover)
        albumTitle = findViewById(R.id.albumTitle)
        albumYear = findViewById(R.id.albumYear)
        commentDescription = findViewById(R.id.commentDescription)
        ratingBar = findViewById(R.id.albumRating)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)

        val albumId = intent.getIntExtra("albumId", -1)
        val albumName = intent.getStringExtra("albumName")
        val albumYearText = intent.getStringExtra("albumYear")
        val albumCoverUrl = intent.getStringExtra("albumCover")

        albumTitle.text = albumName
        albumYear.text = albumYearText

        Glide.with(this)
            .load(albumCoverUrl)
            .into(albumCover)

        saveButton.setOnClickListener {
            saveComment(albumId)
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun saveComment(albumId: Int) {
        try {
            val description = commentDescription.text.toString().trim()
            val rating = ratingBar.rating.toInt()
            if (description.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un comentario.", Toast.LENGTH_SHORT).show()
                return
            }
            val comment = Comment(
                id = 0,
                description = description,
                rating = rating,
                albumId = albumId
            )
            commentViewModel.saveComment(comment)
            Toast.makeText(this, "Comentario guardado exitosamente.", Toast.LENGTH_LONG).show()
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar el comentario: ${e.message}", Toast.LENGTH_LONG)
                .show()
            e.printStackTrace()
        }
    }
}