package com.dicoding.appstory.ui.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.appstory.R
import com.dicoding.appstory.data.model.Token
import com.dicoding.appstory.data.remote.response.ListStoryItem
import com.dicoding.appstory.databinding.ActivityMainBinding
import com.dicoding.appstory.ui.ViewModelFactory
import com.dicoding.appstory.ui.add_story.AddStoryActivity
import com.dicoding.appstory.ui.maps.MapsActivity
import com.dicoding.appstory.ui.state.ResultState
import com.dicoding.appstory.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var token:String
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }else{
                if(user.token==""){
                    val getToken = if (Build.VERSION.SDK_INT >= 33) {
                        intent.getParcelableExtra<Token>(EXTRA_TOKEN, Token::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra<Token>(EXTRA_TOKEN)
                    }
                    token = getToken?.token.toString()
                }else{
                    token =  user.token
                }
                getStory(token)
            }

        }



        val layoutManager = LinearLayoutManager(this)

        binding.rvStory.layoutManager = layoutManager



        binding.addStory.setOnClickListener{

            intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(intent)

        }

        setupAction()
    }

    private fun getStory(token:String){
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getStory(token).observe(this) {
            adapter.submitData(lifecycle, it)
        }

    }

    private fun setupAction() {
        binding.topAppBar.setOnMenuItemClickListener { menuitem ->
            when (menuitem.itemId) {
                R.id.m_logout -> {
                    AlertDialog.Builder(this).apply {
                        setTitle(getString(R.string.alert))
                        setMessage(getString(R.string.alert_message_logout))
                        setNegativeButton(getString(R.string.alert_logout_negative_button),null)
                        setPositiveButton(getString(R.string.alert_logout_positive_button)) { _, _, ->
                            viewModel.logout()
                        }
                        create()
                        show()
                    }
                    true
                }
                R.id.m_maps->{
                    val intent = Intent(this@MainActivity, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false

            }
        }
    }


    companion object{
        const val EXTRA_TOKEN = "ekstra_token"
    }
}