package com.vinylsmobile.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vinylsmobile.databinding.ItemCollectorBinding
import com.vinylsmobile.model.Collector
import com.vinylsmobile.view.CollectorDetailActivity

class CollectorAdapter(private val context: Context, private val collectors: List<Collector>) :
 RecyclerView.Adapter<CollectorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCollectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collector = collectors[position]
        val initials = getInitials(collector.name)
        holder.binding.collectorName.text = collector.name
        holder.binding.collectorCover.text = initials

        val collectorButton: CardView = holder.binding.collectorCard
        collectorButton.setOnClickListener{
            val intent = Intent(context, CollectorDetailActivity::class.java)

            intent.putExtra("collectorID", collector.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = collectors.size

    private fun getInitials(name: String): String {
        val parts = name.split(" ")
        val initials = parts.take(2).joinToString("") { it.take(1).uppercase() }
        return initials
    }

    class ViewHolder(val binding: ItemCollectorBinding) : RecyclerView.ViewHolder(binding.root)
}