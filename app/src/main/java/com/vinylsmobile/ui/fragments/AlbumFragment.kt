package com.vinylsmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinylsmobile.data.api.AlbumApi
import com.vinylsmobile.data.repository.AlbumRepository
import com.vinylsmobile.databinding.FragmentAlbumBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.vinylsmobile.ui.adapters.AlbumAdapter
import com.vinylsmobile.ui.viewmodels.AlbumViewModel
import com.vinylsmobile.ui.viewmodels.AlbumViewModelFactory
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    private lateinit var viewModel: AlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)


        val api = Retrofit.Builder().baseUrl("https://backvynils-q6yc.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create()).build().create(AlbumApi::class.java)

        val repository = AlbumRepository(api)
        viewModel = ViewModelProvider(
            this, AlbumViewModelFactory(repository)
        ).get(AlbumViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)

        viewModel.albums.observe(viewLifecycleOwner, Observer { albums ->
            binding.recyclerView.adapter = AlbumAdapter(albums)
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadAlbums()
        }

        return binding.root
    }
}