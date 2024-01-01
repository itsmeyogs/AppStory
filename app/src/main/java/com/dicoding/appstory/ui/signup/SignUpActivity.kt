package com.dicoding.appstory.ui.signup

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
import com.dicoding.appstory.databinding.ActivitySignUpBinding
import com.dicoding.appstory.ui.ViewModelFactory
import com.dicoding.appstory.ui.login.LoginActivity
import com.dicoding.appstory.ui.state.ResultState

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30F, 30F).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }
        val nameEdit = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1F).apply {
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
        val passEdit =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).apply {
                duration = 100
                startDelay = 100
            }
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1F).apply {
            duration = 100
            startDelay = 100
        }

        AnimatorSet().apply {
            playSequentially(title, name, nameEdit, email, emailEdit, pass, passEdit, signup)
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
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()


            viewModel.register(name, email, password).observe(this) {
                if (it != null) {
                    when (it) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showAlert("${it.data.message}", true)
                            showLoading(false)
                        }

                        is ResultState.Error -> {
                            showAlert(it.error, false)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    fun showAlert(message: String, isSuccess: Boolean) {
        when (isSuccess) {
            false -> {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.alert_error))
                    setMessage(message)
                    setPositiveButton(getString(R.string.alert_error_btn_positive), null)
                    create()
                    show()
                }
            }
            true -> {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.alert_success))
                    setMessage(message)
                    setNegativeButton(getString(R.string.alert_success_btn_negative),null)
                    setPositiveButton(getString(R.string.alert_success_btn_positive)) { _, _ ->
                        val intent = Intent (context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }

            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}