package com.vinylsmobile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vinylsmobile.R
import com.vinylsmobile.databinding.FragmentListAlbumBinding
import com.vinylsmobile.view.adapters.AlbumAdapter
import com.vinylsmobile.viewmodels.AlbumViewModel

class AlbumListFragment : Fragment() {
    private var _binding: FragmentListAlbumBinding? = null
    private val binding get() = _binding!!

    //Microoptimizacion: Liberación de memoria
    private lateinit var albumAdapter: AlbumAdapter

    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListAlbumBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.AlbumViewModelFactory(requireActivity().application)
        ).get(AlbumViewModel::class.java)

        val spanCount = calculateSpanCount(162)
        binding.recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.progressBar.visibility = View.VISIBLE

        //Microoptimizacion liberación de memoria
        // Crear una instancia única del adaptador
        albumAdapter = AlbumAdapter(requireContext())
        binding.recyclerView.adapter = albumAdapter

        // Observa los datos y actualiza el adaptador
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            binding.progressBar.visibility = View.GONE
            //Microoptimizacion Liberación de memoria
            albumAdapter.setAlbums(albums) // Actualiza los datos del adaptador
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
        // Crear una instancia única del adaptador
        binding.recyclerView.adapter = null
        _binding = null
    }

    private fun calculateSpanCount(itemWidthDp: Int): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidthPx = displayMetrics.widthPixels
        val itemWidthPx = (itemWidthDp * displayMetrics.density).toInt()
        return (screenWidthPx / itemWidthPx).coerceAtLeast(1)
    }
}