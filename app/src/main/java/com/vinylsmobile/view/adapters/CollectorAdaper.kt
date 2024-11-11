package com.vinylsmobile.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinylsmobile.databinding.ItemCollectorBinding
import com.vinylsmobile.model.Collector

class CollectorAdaper(private val context: Context, private val collectors: List<Collector>) :
 RecyclerView.Adapter<CollectorAdaper.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCollectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collector = collectors[position]
        val initials = getInitials(collector.name)
        holder.binding.collectorName.text = collector.name
        holder.binding.collectorCover.text = initials
    }

    override fun getItemCount() = collectors.size

    private fun getInitials(name: String): String {
        val parts = name.split(" ")
        val initials = parts.take(2).joinToString("") { it.take(1).uppercase() }
        return initials
    }

    class ViewHolder(val binding: ItemCollectorBinding) : RecyclerView.ViewHolder(binding.root)
}