package com.example.pixabayapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pixabayapp.data.models.ApiResponse
import com.example.pixabayapp.databinding.ItemImagePixabayBinding

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageHolder>() {

    inner class ImageHolder(
        private val binding: ItemImagePixabayBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(model: ApiResponse.Hit) {
                binding.imageView.load(model.previewURL)
                binding.tvUsernameLikes.text = "${model.user} + Likes: ${model.likes}"
            }

        }

    private val differCallback: DiffUtil.ItemCallback<ApiResponse.Hit> =
        object : DiffUtil.ItemCallback<ApiResponse.Hit>() {
            override fun areItemsTheSame(
                oldItem: ApiResponse.Hit,
                newItem: ApiResponse.Hit
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ApiResponse.Hit,
                newItem: ApiResponse.Hit
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }

    private val differ = AsyncListDiffer(this, differCallback)


    fun setList(list: List<ApiResponse.Hit>) {
        differ.submitList(list.toList())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesAdapter.ImageHolder {
        val binding = ItemImagePixabayBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}