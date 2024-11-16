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
import com.vinylsmobile.view.adapters.CollectorAdaper
import com.vinylsmobile.viewmodels.CollectorViewModel
import com.vinylsmobile.repository.CollectorRepository
import com.vinylsmobile.view.adapters.CollectionAdapter
import com.vinylsmobile.viewmodels.CollectorViewModelFactory

class CollectionFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var performerViewModel: PerformerViewModel
    private lateinit var collectorViewModel: CollectorViewModel

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

        //val collectorRepository = CollectorRepository()
        /*collectorViewModel = ViewModelProvider(
            this,
            CollectorViewModelFactory(collectorRepository)
        ).get(CollectorViewModel::class.java)*/
        val collectorViewModel = ViewModelProvider(
            this,
            CollectorViewModel.CollectorViewModelFactory(requireActivity().application)
        ).get(CollectorViewModel::class.java)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewArtist.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCollector.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.progressBar.visibility = View.VISIBLE

        val observer = { isLoading: Boolean ->
            val albumIsLoading = albumViewModel.isLoading.value ?: false
            val performerIsLoading = performerViewModel.isLoading.value ?: false
            val collectorIsLoading = collectorViewModel.isLoading.value ?: false

            binding.progressBar.visibility =
                if (albumIsLoading || performerIsLoading || collectorIsLoading) View.VISIBLE else View.GONE
        }

        albumViewModel.isLoading.observe(viewLifecycleOwner, observer)
        performerViewModel.isLoading.observe(viewLifecycleOwner, observer)
        collectorViewModel.isLoading.observe(viewLifecycleOwner, observer)

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

        collectorViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                collectorViewModel.resetErrorMessage()
            }
        }

        albumViewModel.albums.observe(viewLifecycleOwner) { albums ->
            if (albums.isEmpty()) {
                Toast.makeText(context, "No hay álbumes disponibles", Toast.LENGTH_SHORT).show()
            } else {
                binding.recyclerView.adapter = CollectionAdapter(requireContext(), albums, viewMoreText = "Ver más álbumes")
            }
        }

        performerViewModel.performers.observe(viewLifecycleOwner) { performers ->
            if (performers.isEmpty()) {
                Toast.makeText(context, "No hay artistas disponibles", Toast.LENGTH_SHORT).show()
            } else {
                binding.recyclerViewArtist.adapter = CollectionAdapter(requireContext(), performers, viewMoreText = "Ver más artistas")
            }
        }

        collectorViewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            if (collectors.isEmpty()) {
                Toast.makeText(context, "No hay coleccionistas disponibles", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.recyclerViewCollector.adapter =
                    CollectorAdaper(requireContext(), collectors)
            }
        }

        albumViewModel.loadAlbums(limit = 2)
        performerViewModel.loadPerformers(limit = 2)
        collectorViewModel.loadCollectors()


        binding.albumForwardButton.setOnClickListener { navigateTo(AlbumListFragment()) }
        binding.albumListTitle.setOnClickListener { navigateTo(AlbumListFragment()) }
        binding.performerForwardButton.setOnClickListener { navigateTo(PerformerListFragment()) }
        binding.performerListTitle.setOnClickListener { navigateTo(PerformerListFragment()) }
        binding.collectorForwardButton.setOnClickListener { navigateTo(CollectorListFragment()) }
        binding.collectorListTitle.setOnClickListener { navigateTo(CollectorListFragment()) }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}