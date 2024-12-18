package com.vinylsmobile.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinylsmobile.databinding.ItemPerformerBinding
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.view.PerformerDetailActivity

class PerformerAdapter(private val context: Context, private val performers: List<IPerformer> ) :
    RecyclerView.Adapter<PerformerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPerformerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val performer = performers[position]
        holder.binding.performerName.text = performer.name
        Glide.with(holder.binding.root)
            .load(performer.image)
            .into(holder.binding.performerImage)

        val performerButton: CardView = holder.binding.performerCard
        performerButton.setOnClickListener {
            val intent = Intent(context, PerformerDetailActivity::class.java)

            intent.putExtra("performerID",performer.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = performers.size

    class ViewHolder(val binding: ItemPerformerBinding) : RecyclerView.ViewHolder(binding.root)
}