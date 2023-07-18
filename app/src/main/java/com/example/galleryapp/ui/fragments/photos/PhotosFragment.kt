package com.example.galleryapp.ui.fragments.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.galleryapp.databinding.FragmentPhotosBinding
import com.example.galleryapp.ui.fragments.base.BaseFragment
import com.example.galleryapp.ui.fragments.photos.adapters.ImagesAdapter
import com.example.galleryapp.ui.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    private val viewModel: PhotosViewModel by viewModels()
    private val imagesAdapter: ImagesAdapter by lazy { ImagesAdapter(onItemClick) }
    private lateinit var onItemClick: (String?) -> Unit
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPhotosBinding {
        return FragmentPhotosBinding.inflate(inflater, container, false)
    }

    override fun bindData() {
        super.bindData()
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            showToast(errorMessage)
        }
        viewModel.images.observe(viewLifecycleOwner) { pagingData ->
            viewLifecycleOwner.lifecycleScope.launch {
                imagesAdapter.submitData(pagingData)
            }
        }
    }

    override fun setUI() {
        super.setUI()
        onItemClick = { imageId ->
            findNavController().navigate(PhotosFragmentDirections.fromPhotosToDetail(imageId))
        }
        binding.refresh.setOnRefreshListener {
            viewModel.loadImages()
        }
        binding.imagesRV.adapter = imagesAdapter
    }
}