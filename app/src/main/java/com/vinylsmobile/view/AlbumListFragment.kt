package com.vinylsmobile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        viewModel = ViewModelProvider(this, AlbumViewModelFactory(repository)).get(AlbumViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.progressBar.visibility = View.VISIBLE

        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.adapter = AlbumAdapter(albums)
        }

        viewModel.loadAlbums()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}