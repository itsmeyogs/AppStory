package com.dicoding.appstory.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.appstory.data.model.DetailStory
import com.dicoding.appstory.data.remote.response.ListStoryItem
import com.dicoding.appstory.databinding.ItemStoryBinding
import com.dicoding.appstory.ui.detail.DetailActivity

class StoryAdapter:PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story =getItem(position)
        holder.bind(story)
        Glide.with(holder.itemView)
            .load("${story?.photoUrl}")
            .into(holder.binding.imageStory)
        holder.itemView.setOnClickListener{
            val detailStory  = DetailStory(
                "${story?.name}",
                "${story?.description}",
                "${story?.photoUrl}"
            )
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, detailStory)
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.imageStory, "image"),
                    Pair(holder.binding.nameStory, "name"),
                    Pair(holder.binding.descStory, "desc")
                )
            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    class MyViewHolder(val binding: ItemStoryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(story: ListStoryItem?){
            binding.apply {
                nameStory.text ="${story?.name}"
                descStory.text = "${story?.description}"
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object :DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem==newItem
            }
        }
    }

}