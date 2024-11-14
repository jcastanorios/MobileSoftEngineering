package com.vinylsmobile.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinylsmobile.R
import com.vinylsmobile.model.Album
import com.vinylsmobile.view.AlbumDetailActivity
import com.vinylsmobile.databinding.ItemAlbumBinding
import com.vinylsmobile.view.AlbumListFragment
import android.widget.TextView

class AlbumAdapter(
    private val context: Context,
    private val albums: List<Album>,
    private val showViewMoreButton: Boolean = false, // Valor predeterminado a false, haciéndolo opcional
    private val viewMoreText: String? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ALBUM = 0
        private const val VIEW_TYPE_VIEW_MORE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ALBUM) {
            val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AlbumViewHolder(binding)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_more, parent, false)
            ViewMoreViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlbumViewHolder) {
            val album = albums[position]
            holder.binding.albumName.text = album.name
            Glide.with(holder.binding.root)
                .load(album.cover)
                .into(holder.binding.albumCover)

            val albumButton: CardView = holder.binding.albumCard
            albumButton.setOnClickListener {
                val intent = Intent(context, AlbumDetailActivity::class.java)
                intent.putExtra("albumID", album.id)
                context.startActivity(intent)
            }
        } else if (holder is ViewMoreViewHolder) {
            holder.viewMoreTextView.text = viewMoreText ?: "Ver más" // Texto por defecto si no se pasa viewMoreText

            holder.viewMoreButton.setOnClickListener {
                val fragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, AlbumListFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (showViewMoreButton) albums.size + 1 else albums.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (showViewMoreButton && position == albums.size) {
            VIEW_TYPE_VIEW_MORE
        } else {
            VIEW_TYPE_ALBUM
        }
    }

    class AlbumViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)

    inner class ViewMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewMoreButton: CardView = itemView.findViewById(R.id.viewMoreButton)
        val viewMoreTextView: TextView = itemView.findViewById(R.id.collectorName)
    }
}