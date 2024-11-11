package com.vinylsmobile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinylsmobile.repository.AlbumRepository
import com.vinylsmobile.repository.PerformerRepository
import com.vinylsmobile.databinding.FragmentAlbumBinding
import com.vinylsmobile.view.adapters.AlbumAdapter
import com.vinylsmobile.view.adapters.PerformerAdapter
import com.vinylsmobile.viewmodels.AlbumViewModel
import com.vinylsmobile.viewmodels.AlbumViewModelFactory
import com.vinylsmobile.viewmodels.PerformerViewModel
import com.vinylsmobile.viewmodels.PerformerViewModelFactory
import com.vinylsmobile.R
import android.widget.Toast

class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var performerViewModel: PerformerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumRepository = AlbumRepository()
        albumViewModel = ViewModelProvider(
            this,
            AlbumViewModelFactory(albumRepository)
        ).get(AlbumViewModel::class.java)

        val performerRepository = PerformerRepository()
        performerViewModel = ViewModelProvider(
            this,
            PerformerViewModelFactory(performerRepository)
        ).get(PerformerViewModel::class.java)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewArtist.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.progressBar.visibility = View.VISIBLE

        val observer = { isLoading: Boolean ->
            val albumIsLoading = albumViewModel.isLoading.value ?: false
            val performerIsLoading = performerViewModel.isLoading.value ?: false
            binding.progressBar.visibility = if (albumIsLoading || performerIsLoading) View.VISIBLE else View.GONE
        }

        albumViewModel.isLoading.observe(viewLifecycleOwner, observer)
        performerViewModel.isLoading.observe(viewLifecycleOwner, observer)

        albumViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                albumViewModel.resetErrorMessage()
            }
        }

        performerViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                performerViewModel.resetErrorMessage()
            }
        }

        albumViewModel.albums.observe(viewLifecycleOwner) { albums ->
            if (albums.isEmpty()) {
                Toast.makeText(context, "No hay álbumes disponibles", Toast.LENGTH_SHORT).show()
            } else {
                binding.recyclerView.adapter = AlbumAdapter(requireContext(), albums)
            }
        }

        performerViewModel.performers.observe(viewLifecycleOwner) { performers ->
            if (performers.isEmpty()) {
                Toast.makeText(context, "No hay artistas disponibles", Toast.LENGTH_SHORT).show()
            } else {
                binding.recyclerViewArtist.adapter = PerformerAdapter(requireContext(), performers)
            }
        }

        albumViewModel.loadAlbums()
        performerViewModel.loadPerformers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
