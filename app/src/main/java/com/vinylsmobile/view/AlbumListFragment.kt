package com.vinylsmobile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinylsmobile.R
import com.vinylsmobile.repository.AlbumRepository
import com.vinylsmobile.databinding.FragmentAlbumBinding
import com.vinylsmobile.databinding.FragmentListAlbumBinding
import com.vinylsmobile.view.adapters.AlbumAdapter
import com.vinylsmobile.viewmodels.AlbumViewModel
import com.vinylsmobile.viewmodels.AlbumViewModelFactory

class AlbumListFragment : Fragment() {
    private var _binding: FragmentListAlbumBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListAlbumBinding.inflate(inflater, container, false)

        val repository = AlbumRepository()
        viewModel = ViewModelProvider(
            this,
            AlbumViewModelFactory(repository)
        ).get(AlbumViewModel::class.java)

        val spanCount = calculateSpanCount(162)
        binding.recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.progressBar.visibility = View.VISIBLE

        val context = requireContext()
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.adapter = AlbumAdapter(context, albums)
        }
        binding.albumBackButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CollectionFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.loadAlbums()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculateSpanCount(itemWidthDp: Int): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidthPx = displayMetrics.widthPixels
        val itemWidthPx = (itemWidthDp * displayMetrics.density).toInt()
        return (screenWidthPx / itemWidthPx).coerceAtLeast(1)
    }
}