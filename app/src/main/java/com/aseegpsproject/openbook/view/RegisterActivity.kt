package com.aseegpsproject.openbook.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.database.OpenBookDatabase
import com.aseegpsproject.openbook.databinding.ActivityRegisterBinding
import com.aseegpsproject.openbook.util.CredentialCheck
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var db: OpenBookDatabase

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

        db = OpenBookDatabase.getInstance(this)!!

        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btnRegister.setOnClickListener {
                register()
            }
        }
    }

    private fun register() {
        with(binding) {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etRepeatPassword.text.toString()

            val check = CredentialCheck.join(username, password, confirmPassword)

            if (check.fail) notifyInvalidCredentials(check.msg)
            else {
                lifecycleScope.launch {
                    val user = User(null, username, password)
                    val id = db.userDao().insert(user)

                    navigateBackWithResult(User(id, username, password))
                }
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

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}