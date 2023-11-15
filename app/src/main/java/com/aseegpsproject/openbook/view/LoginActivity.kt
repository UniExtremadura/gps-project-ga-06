package com.aseegpsproject.openbook.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.ActivityLoginBinding
import com.aseegpsproject.openbook.model.User
import com.aseegpsproject.openbook.util.CredentialCheck
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var db: OpenBookDatabase

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
                        "New user ($name/$password) created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = OpenBookDatabase.getInstance(this)!!

        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                login()
            }
            btnRegister.setOnClickListener {
                navigateToRegister()
            }
        }
    }

    private fun login() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        val check = CredentialCheck.login(username, password)

        if (check.fail) notifyInvalidCredentials(check.msg)
        else {
            lifecycleScope.launch {
                val user = db.userDao().getByUsername(username)

                if (user == null) {
                    notifyInvalidCredentials("User not found")
                } else {
                    val checkPassword = CredentialCheck.passwordOk(password, user.password)
                    if (checkPassword.fail) notifyInvalidCredentials(checkPassword.msg)
                    else navigateToHomeActivity(user, checkPassword.msg)
                }
            }
        }

    }

    private fun navigateToHomeActivity(user: User, msg: String) {
        // TODO: Navigate to HomeActivity
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToRegister() {
        RegisterActivity.start(this, responseLauncher)
    }
}