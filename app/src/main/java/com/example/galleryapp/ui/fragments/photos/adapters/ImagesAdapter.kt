package com.example.galleryapp.ui.fragments.photos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.core.services.models.PhotoResponse
import com.example.galleryapp.databinding.ImagesItemBinding
import com.squareup.picasso.Picasso


class ImagesAdapter(private val onItemClick: (String?) -> Unit): PagingDataAdapter<PhotoResponse, ImageViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ImagesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<PhotoResponse>() {
    override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
        return oldItem == newItem
    }
}

class ImageViewHolder(
    private val binding: ImagesItemBinding,
    private val onItemClick: (String?) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photoResponse: PhotoResponse?) {
        photoResponse.let {
            Picasso.get().load(photoResponse?.urls?.regular).into(binding.imageView)
        }
        binding.imageView.setOnClickListener {
            onItemClick.invoke(photoResponse?.id)
        }
    }
}