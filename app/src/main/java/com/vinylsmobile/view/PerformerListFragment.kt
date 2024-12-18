package com.vinylsmobile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vinylsmobile.R
import com.vinylsmobile.databinding.FragmentListPerformerBinding
import com.vinylsmobile.view.adapters.PerformerAdapter
import com.vinylsmobile.viewmodels.PerformerViewModel

class PerformerListFragment : Fragment() {
    private var _binding: FragmentListPerformerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PerformerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPerformerBinding.inflate(inflater, container, false)

        /*Nueva forma de llamar al Album*/
        viewModel = ViewModelProvider(
            this,
            PerformerViewModel.PerformerViewModelFactory(requireActivity().application)
        ).get(PerformerViewModel::class.java)

        val spanCount = calculateSpanCount(162) // 162dp es el ancho de cada ítem en item_album.xml
        binding.recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.progressBar.visibility = View.VISIBLE

        val context = requireContext()
        viewModel.performers.observe(viewLifecycleOwner) { performers ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.adapter = PerformerAdapter(context, performers)
        }
        binding.performerBackButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CollectionFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.loadPerformers()

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