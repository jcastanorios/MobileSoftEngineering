package com.vinylsmobile.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vinylsmobile.R
import com.vinylsmobile.databinding.FragmentListCollectorBinding
import com.vinylsmobile.repository.CollectorRepository
import com.vinylsmobile.view.adapters.CollectorAdaper
import com.vinylsmobile.viewmodels.CollectorViewModel
import com.vinylsmobile.viewmodels.CollectorViewModelFactory

class CollectorListFragment : Fragment() {
    private var _binding: FragmentListCollectorBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CollectorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCollectorBinding.inflate(inflater, container, false)

        //val repository = CollectorRepository()
        //viewModel = ViewModelProvider(this, CollectorViewModelFactory(repository)).get(CollectorViewModel::class.java)
        val viewModel = ViewModelProvider(
            this,
            CollectorViewModel.CollectorViewModelFactory(requireActivity().application)
        ).get(CollectorViewModel::class.java)

        val spanCount = calculateSpanCount(162) // 162dp es el ancho de cada ítem en item_album.xml
        binding.recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.progressBar.visibility = View.VISIBLE

        val context = requireContext()
        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.adapter = CollectorAdaper(context, collectors)
        }
        binding.collectorBackButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CollectionFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.loadCollectors()

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