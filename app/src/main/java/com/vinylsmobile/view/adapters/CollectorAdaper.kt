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
        holder.binding.collectorName.text = collector.name
    }

    override fun getItemCount() = collectors.size

    class ViewHolder(val binding: ItemCollectorBinding) : RecyclerView.ViewHolder(binding.root)
}