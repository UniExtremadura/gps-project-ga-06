package com.aseegpsproject.openbook.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.ActivityLoginBinding
import com.aseegpsproject.openbook.util.CredentialCheck
import com.aseegpsproject.openbook.view.home.HomeActivity
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
                        "New user ($name) created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = OpenBookDatabase.getInstance(this)!!

        val preferences = PreferenceManager.getDefaultSharedPreferences(this).all
        if (preferences.containsKey("username") && preferences.containsKey("password")) {
            val username = preferences["username"].toString()
            val password = preferences["password"].toString()

            login(Pair(username, password))
        } else {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setUpListeners()
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btnLogin.setOnClickListener {
                login()
            }
            btnRegister.setOnClickListener {
                navigateToRegister()
            }
            etPassword.setOnEditorActionListener { _, _, _ ->
                login()
                true
            }
        }
    }

    private fun getCredentials(): Pair<String, String> {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        return Pair(username, password)
    }

    private fun login(credentials: Pair<String, String> = getCredentials()) {
        val check = CredentialCheck.login(credentials.first, credentials.second)

        if (check.fail) notifyInvalidCredentials(check.msg)
        else {
            lifecycleScope.launch {
                val user = db.userDao().getByUsername(credentials.first)

                if (user == null) {
                    notifyInvalidCredentials("User not found")
                } else {
                    val checkPassword = CredentialCheck.passwordOk(credentials.second, user.password)
                    if (checkPassword.fail) notifyInvalidCredentials(checkPassword.msg)
                    else navigateToHomeActivity(user)
                }
            }
        }

    }

    private fun navigateToHomeActivity(user: User) {
        HomeActivity.start(this, user)
    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToRegister() {
        RegisterActivity.start(this, responseLauncher)
    }
}