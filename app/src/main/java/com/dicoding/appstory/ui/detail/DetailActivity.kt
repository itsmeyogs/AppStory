package com.dicoding.appstory.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.appstory.R
import com.dicoding.appstory.data.model.DetailStory
import com.dicoding.appstory.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, DetailStory::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DETAIL)
        }
        if (data != null) {
            Glide.with(this)
                .load(data.photoUrl)
                .into(binding.imageStory)
            binding.apply {
                nameStory.text =data.name
                descStory.text = data.description
            }
        }

    }

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }
}