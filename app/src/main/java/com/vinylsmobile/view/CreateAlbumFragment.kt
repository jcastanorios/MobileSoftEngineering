package com.vinylsmobile.view

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.vinylsmobile.R
import com.vinylsmobile.databinding.FragmentCreateAlbumBinding
import com.vinylsmobile.viewmodels.AlbumDetailViewModel
import com.vinylsmobile.viewmodels.CreateAlbumViewModel
import java.util.Calendar

class CreateAlbumFragment : Fragment() {
    private lateinit var binding: FragmentCreateAlbumBinding
    private lateinit var viewModel: CreateAlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            CreateAlbumViewModel.CreateAlbumViewModelFactory(requireActivity().application)
        ).get(CreateAlbumViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.albumSaved.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                // Handle album saved, e.g., navigate back or show a message
                requireActivity().finish()
            }
        }

       binding.btnReleaseDate.setOnClickListener {

           val calendar = Calendar.getInstance()
           val datePickerDialog = DatePickerDialog(
               requireContext(),
               { _, year, month, dayOfMonth ->
                   viewModel.releaseDate.value = "$year-${month + 1}-$dayOfMonth"
               },
               calendar.get(Calendar.YEAR),
               calendar.get(Calendar.MONTH),
               calendar.get(Calendar.DAY_OF_MONTH)
           )
           datePickerDialog.show()
       }

        binding.btnCancel.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }
}