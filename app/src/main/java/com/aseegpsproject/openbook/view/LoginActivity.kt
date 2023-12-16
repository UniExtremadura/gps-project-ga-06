package com.aseegpsproject.openbook.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.databinding.ActivityLoginBinding
import com.aseegpsproject.openbook.view.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }
    private lateinit var binding: ActivityLoginBinding

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                with(result.data) {
                    val name = this?.getStringExtra(RegisterActivity.USERNAME).orEmpty()
                    val password = this?.getStringExtra(RegisterActivity.PASSWORD).orEmpty()

                    with(binding) {
                        etPassword.setText(password)
                        etUsername.setText(name)
                    }

                    Toast.makeText(
                        this@LoginActivity,
                        resources.getText(R.string.register_success),
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.login(Pair(name, password))
                }
            }
        }

    override fun onResume() {
        super.onResume()
        if (viewModel.user.value != null) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this).all
        if (preferences.containsKey("username") && preferences.containsKey("password")) {
            val username = preferences["username"].toString()
            val password = preferences["password"].toString()

            viewModel.login(Pair(username, password))
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setUpListeners()
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.user.observe(this) { user ->
            if (user != null) {
                HomeActivity.start(this, user)
            }
        }
        viewModel.toast.observe(this) { message ->
            message?.let {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                viewModel.login(Pair(username, password))
            }
            btnRegister.setOnClickListener {
                navigateToRegister()
            }
            etPassword.setOnEditorActionListener { _, _, _ ->
                btnLogin.performClick()
                true
            }
        }
    }

    private fun navigateToRegister() {
        RegisterActivity.start(this, responseLauncher)
    }
}