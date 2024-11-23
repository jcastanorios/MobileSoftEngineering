package com.vinylsmobile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vinylsmobile.R
import com.vinylsmobile.model.Comment
import android.widget.RatingBar


class CommentAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.commentDescription.text = comment.description

        holder.commentRatingBar.rating = comment.rating.toFloat()
    }

    override fun getItemCount(): Int = comments.size

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentDescription: TextView = itemView.findViewById(R.id.commentDescription)
        val commentRatingBar: RatingBar = itemView.findViewById(R.id.commentRatingBar)
    }
}
