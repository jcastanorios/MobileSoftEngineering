package com.vinylsmobile.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinylsmobile.model.Album
import com.vinylsmobile.view.AlbumDetailActivity
import com.vinylsmobile.databinding.ItemAlbumBinding

class AlbumAdapter(private val context: Context) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    //Microoptimizacion: Liberación de memoria
    private val albums: MutableList<Album> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums[position]
        holder.binding.albumName.text = album.name
        Glide.with(holder.binding.albumCover.context)
            .load(album.cover)
            .into(holder.binding.albumCover)

        val albumButton: CardView = holder.binding.albumCard
        albumButton.setOnClickListener {
            navigateToAlbumDetail(context, album.id)
        }
    }

    override fun getItemCount() = albums.size

    //Microoptimizacion: Liberación de memoria
    fun setAlbums(newAlbums: List<Album>) {
        albums.clear()
        albums.addAll(newAlbums)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun unbind() {
            binding.albumCover.setImageDrawable(null) // Libera la referencia a la imagen
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    private fun navigateToAlbumDetail(context: Context, albumId: Int?) {
        val intent = Intent(context, AlbumDetailActivity::class.java)
        intent.putExtra("albumID", albumId)
        context.startActivity(intent)
    }

}