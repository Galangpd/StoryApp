package com.example.mystoryapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapp.databinding.ItemRowBinding
import com.example.mystoryapp.response.ListStoryItem

class StoryAdapter: PagingDataAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.tvName.text = story.name
            binding.tvDeskripsi.text = story.description
            Glide.with(itemView)
                .load(story.photoUrl)
                .into(binding.imgStory)

            Log.e("CekData", "${story.name}")

//            itemView.setOnClickListener{
//                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
//                intent.putExtra(DetailStoryActivity.EXTRA_DATA, story.id)
//                itemView.context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}