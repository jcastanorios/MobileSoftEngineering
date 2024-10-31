package com.vinylsmobile.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinylsmobile.model.Album
import com.vinylsmobile.databinding.ItemAlbumBinding

class AlbumAdapter(private val albums: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums[position]
        holder.binding.albumName.text = album.name
        Glide.with(holder.binding.root)
            .load(album.cover)
            .into(holder.binding.albumCover)
    }

    override fun getItemCount() = albums.size

    class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)
}