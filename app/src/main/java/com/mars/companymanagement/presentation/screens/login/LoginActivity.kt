package com.mars.companymanagement.presentation.screens.login

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.widget.doAfterTextChanged
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.ActivityLoginBinding
import com.mars.companymanagement.presentation.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityLoginBinding.inflate(layoutInflater).run {
            setContentView(root)
            initListeners(this)
            initObservers(this)
        }
    }

    private fun initListeners(binding: ActivityLoginBinding) = binding.run {
        emailEditText.doAfterTextChanged {
            viewModel.changeEmail(it.toString())
            emailLayout.isErrorEnabled = false
        }
        passwordEditText.doAfterTextChanged {
            viewModel.changePassword(it.toString())
            passwordLayout.isErrorEnabled = false
        }
        submitButton.setOnClickListener { viewModel.login() }
    }

    private fun initObservers(binding: ActivityLoginBinding) {
        viewModel.loginSuccessLiveData.observe(this) {
            MainActivity.start(this)
        }
        viewModel.loginLoadingLiveData.observe(this) {
            binding.apply {
                emailLayout.isInvisible = it
                passwordLayout.isInvisible = it
                submitButton.isInvisible = it
                progressBar.isInvisible = !it
            }
        }
        viewModel.requestErrorLiveData.observe(this) {
            Toast.makeText(this, it, LENGTH_SHORT).show()
        }
        viewModel.emailNotValidLiveData.observe(this) {
            binding.emailLayout.error = getString(R.string.email_validation_error)
        }
        viewModel.passwordNotValidLiveData.observe(this) {
            binding.passwordLayout.error = getString(R.string.password_validation_error)
        }
        viewModel.requestErrorLiveData.observe(this) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(it)
                .create()
                .show()
        }
    }
}