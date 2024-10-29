package com.vinylsmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinylsmobile.databinding.FragmentAlbumBinding
import com.vinylsmobile.ui.adapters.AlbumAdapter

import com.vinylsmobile.ui.viewmodels.AlbumViewModel
import com.vinylsmobile.ui.viewmodels.AlbumViewModelFactory
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        viewModelAdapter = AlbumAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = viewModelAdapter

        viewModel = ViewModelProvider(
            this,
            AlbumViewModelFactory(requireActivity().application)
        ).get(AlbumViewModel::class.java)


        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            viewModelAdapter?.albums = albums
            binding.progressBar.visibility = View.GONE
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    private fun onNetworkError() {
        // Usamos el operador Elvis para manejar posibles valores nulos
       // if (!(viewModel.isNetworkErrorShown.value ?: false)) {
       //     Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
       //     viewModel.onNetworkErrorShown()
        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
