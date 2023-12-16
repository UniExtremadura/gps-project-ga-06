package com.aseegpsproject.openbook.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }
    private lateinit var binding: ActivityRegisterBinding

    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"

        fun start(context: Context, responseLauncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(context, RegisterActivity::class.java)
            responseLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.user.observe(this) { user ->
            user?.let {
                navigateBackWithResult(it)
            }
        }
        viewModel.toast.observe(this) { text ->
            text?.let {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnRegister.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etRepeatPassword.text.toString()
                viewModel.register(username, password, confirmPassword)
            }
            etRepeatPassword.setOnEditorActionListener { _, _, _ ->
                btnRegister.performClick()
                true
            }
        }
    }

    private fun navigateBackWithResult(user: User) {
        val intent = Intent().apply {
            putExtra(USERNAME, user.username)
            putExtra(PASSWORD, user.password)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}