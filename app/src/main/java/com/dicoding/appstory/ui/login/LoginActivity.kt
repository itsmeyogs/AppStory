package com.dicoding.appstory.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.appstory.R
import com.dicoding.appstory.data.model.Token
import com.dicoding.appstory.data.model.UserModel
import com.dicoding.appstory.databinding.ActivityLoginBinding
import com.dicoding.appstory.ui.ViewModelFactory
import com.dicoding.appstory.ui.main.MainActivity
import com.dicoding.appstory.ui.state.ResultState

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30F,30F).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val emailEdit = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val pass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val passEdit = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }

        AnimatorSet().apply {
            playSequentially(title, message, email, emailEdit, pass, passEdit, login)
            start()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password  = binding.passwordEditText.text.toString()

            viewModel.login(email, password).observe(this) {
                if (it != null) {
                    when (it) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            viewModel.saveSession(UserModel(email, "${it.data.loginResult?.token}"))
                            showLoading(false)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            val token = Token(
                                "${it.data.loginResult?.token}"
                            )
                            intent.putExtra(MainActivity.EXTRA_TOKEN, token)
                            startActivity(intent)
                            finish()

                        }

                        is ResultState.Error -> {
                            AlertDialog.Builder(this).apply {
                                setTitle(getString(R.string.alert_error))
                                setMessage(it.error)
                                setPositiveButton(getString(R.string.alert_error_btn_positive), null)
                                create()
                                show()
                            }
                            showLoading(false)
                        }

                    }
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}