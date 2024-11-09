package com.vinylsmobile.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinylsmobile.databinding.ItemPerformerBinding
import com.vinylsmobile.model.Musician

class PerformerAdapter(private val context: Context, private val musicians: List<Musician> ) :
    RecyclerView.Adapter<PerformerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPerformerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val musician = musicians[position]
        holder.binding.performerName.text = musician.name
        Glide.with(holder.binding.root)
            .load(musician.image)
            .into(holder.binding.performerImage)

    }

    override fun getItemCount() = musicians.size

    class ViewHolder(val binding: ItemPerformerBinding) : RecyclerView.ViewHolder(binding.root)
}