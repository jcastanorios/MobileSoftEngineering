package com.vinylsmobile.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinylsmobile.repository.AlbumRepository
import com.vinylsmobile.databinding.FragmentAlbumBinding
import com.vinylsmobile.view.adapters.AlbumAdapter
import com.vinylsmobile.viewmodels.AlbumViewModel
import com.vinylsmobile.viewmodels.AlbumViewModelFactory
import com.vinylsmobile.R
import android.widget.Toast


class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val repository = AlbumRepository()
        viewModel = ViewModelProvider(
            this,
            AlbumViewModelFactory(repository)
        ).get(AlbumViewModel::class.java)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.progressBar.visibility = View.VISIBLE

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.resetErrorMessage()
            }
        }
        val context = requireContext()
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            if (albums.isEmpty()) {
                viewModel.albums.observe(viewLifecycleOwner) { errorMessage ->
                    errorMessage?.let {
                        Toast.makeText(context, "No hay álbumes disponibles", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                binding.recyclerView.adapter = AlbumAdapter(context, albums)
            }
        }
        binding.albumForwardButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AlbumListFragment())
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
}