package com.example.galleryapp.ui.fragments.detailed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.galleryapp.R
import com.example.galleryapp.core.network.utils.ResponseResult
import com.example.galleryapp.databinding.FragmentPhotoDetailBinding
import com.example.galleryapp.ui.fragments.base.BaseFragment
import com.example.galleryapp.ui.utils.convertWithK
import com.example.galleryapp.ui.utils.showToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding>() {
    private val args: PhotoDetailFragmentArgs by navArgs()
    private val viewModel: PhotoDetailViewModel by viewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPhotoDetailBinding {
        return FragmentPhotoDetailBinding.inflate(layoutInflater, container, false)
    }

    override fun bindData() {
        super.bindData()
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.image.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseResult.Success -> {
                    with(binding) {
                        Picasso.get().load(it.data?.urls?.regular).into(imageView)
                        desc.text = getString(R.string.desc, it.data?.alt_description)
                        views.text = getString(R.string.views, it.data?.views?.convertWithK())
                        liked.text = getString(R.string.liked, it.data?.likes?.convertWithK())
                        downloaded.text = getString(R.string.downloads, it.data?.downloads?.convertWithK())
                        creator.text = it.data?.user?.name
                        location.text = it.data?.user?.location
                    }
                }
                is ResponseResult.Error -> {
                    it.message?.let { message -> showToast(message) }
                }
            }
        }
    }

    override fun setUI() {
        super.setUI()
        args.id?.let { viewModel.getImageById(it) }
    }
}