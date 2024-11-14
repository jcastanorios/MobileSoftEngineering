package com.vinylsmobile.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinylsmobile.R
import com.vinylsmobile.databinding.ItemPerformerBinding
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.view.PerformerDetailActivity
import com.vinylsmobile.view.PerformerListFragment

class PerformerAdapter(
    private val context: Context,
    private val performers: List<IPerformer>,
    private val showViewMoreButton: Boolean = false,
    private val viewMoreText: String? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_PERFORMER = 0
        private const val VIEW_TYPE_VIEW_MORE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_PERFORMER) {
            val binding =
                ItemPerformerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PerformerViewHolder(binding)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_view_more, parent, false)
            ViewMoreViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PerformerViewHolder) {
            val performer = performers[position]
            holder.binding.performerName.text = performer.name
            Glide.with(holder.binding.root)
                .load(performer.image)
                .into(holder.binding.performerImage)

            val performerButton: CardView = holder.binding.performerCard
            performerButton.setOnClickListener {
                val intent = Intent(context, PerformerDetailActivity::class.java)
                intent.putExtra("performerID", performer.id)
                context.startActivity(intent)
            }
        } else if (holder is ViewMoreViewHolder) {
            holder.viewMoreTextView.text = viewMoreText

            holder.viewMoreButton.setOnClickListener {
                val fragmentTransaction =
                    (context as FragmentActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, PerformerListFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (showViewMoreButton) performers.size + 1 else performers.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (showViewMoreButton && position == performers.size) VIEW_TYPE_VIEW_MORE else VIEW_TYPE_PERFORMER
    }

    class PerformerViewHolder(val binding: ItemPerformerBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ViewMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewMoreButton: CardView = itemView.findViewById(R.id.viewMoreButton)
        val viewMoreTextView: TextView = itemView.findViewById(R.id.collectorName)
    }
}