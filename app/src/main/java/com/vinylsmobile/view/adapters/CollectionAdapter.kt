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
import com.vinylsmobile.databinding.ItemAlbumBinding
import com.vinylsmobile.databinding.ItemCollectorBinding
import com.vinylsmobile.databinding.ItemPerformerBinding
import com.vinylsmobile.model.Album
import com.vinylsmobile.model.Collector
import com.vinylsmobile.model.IPerformer
import com.vinylsmobile.view.AlbumDetailActivity
import com.vinylsmobile.view.AlbumListFragment
import com.vinylsmobile.view.CollectorDetailActivity
import com.vinylsmobile.view.PerformerListFragment
import com.vinylsmobile.view.PerformerDetailActivity

class CollectionAdapter(
    private val context: Context,
    private val items: List<Any>, // Puede contener álbumes o intérpretes
    private val viewMoreText: String? = "Ver más"
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ALBUM = 0
        private const val VIEW_TYPE_PERFORMER = 1
        private const val VIEW_TYPE_VIEW_MORE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ALBUM -> {
                val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AlbumViewHolder(binding)
            }
            VIEW_TYPE_PERFORMER -> {
                val binding = ItemPerformerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PerformerViewHolder(binding)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_more, parent, false)
                ViewMoreViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> {
                val album = items[position] as Album
                holder.binding.albumName.text = album.name
                Glide.with(holder.binding.root).load(album.cover).into(holder.binding.albumCover)

                holder.binding.albumCard.setOnClickListener {
                    val intent = Intent(context, AlbumDetailActivity::class.java)
                    intent.putExtra("albumID", album.id)
                    context.startActivity(intent)
                }
            }
            is PerformerViewHolder -> {
                val performer = items[position] as IPerformer
                holder.binding.performerName.text = performer.name
                Glide.with(holder.binding.root).load(performer.image).into(holder.binding.performerImage)

                holder.binding.performerCard.setOnClickListener {
                    val intent = Intent(context, PerformerDetailActivity::class.java)
                    intent.putExtra("performerID", performer.id)
                    context.startActivity(intent)
                }
            }
            is CollectorViewHolder -> {
                val collector = items[position] as Collector
                holder.binding.collectorName.text = collector.name

                holder.binding.collectorCard.setOnClickListener {
                    val intent = Intent(context, CollectorDetailActivity::class.java)
                    intent.putExtra("collectorID", collector.id)
                    context.startActivity(intent)
                }
            }
            is ViewMoreViewHolder -> {
                holder.viewMoreTextView.text = viewMoreText

                holder.viewMoreButton.setOnClickListener {
                    val fragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

                    if (viewMoreText?.contains("álbumes", ignoreCase = true) == true) {
                        fragmentTransaction.replace(R.id.fragment_container, AlbumListFragment())
                    } else if (viewMoreText?.contains("artistas", ignoreCase = true) == true) {
                        fragmentTransaction.replace(R.id.fragment_container, PerformerListFragment())
                    }

                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return when {
            position == items.size -> VIEW_TYPE_VIEW_MORE
            items[position] is Album -> VIEW_TYPE_ALBUM
            items[position] is IPerformer -> VIEW_TYPE_PERFORMER
            else -> throw IllegalArgumentException("Tipo de elemento desconocido en la posición $position")
        }
    }

    class AlbumViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)
    class PerformerViewHolder(val binding: ItemPerformerBinding) : RecyclerView.ViewHolder(binding.root)
    class CollectorViewHolder(val binding: ItemCollectorBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ViewMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewMoreButton: CardView = itemView.findViewById(R.id.viewMoreButton)
        val viewMoreTextView: TextView = itemView.findViewById(R.id.collectorName)
    }
}