package com.aseegpsproject.openbook.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.aseegpsproject.openbook.OpenBookApplication
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.util.CredentialCheck
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: Repository,
    private val application: OpenBookApplication
) : ViewModel() {
    private val _user = MutableLiveData<User>(null)
    val user: LiveData<User>
        get() = _user

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    fun register(username: String, password: String, confirmPassword: String) {
        val check = CredentialCheck.join(username, password, confirmPassword)

        if (check.fail) _toast.value = check.msg
        else {
            viewModelScope.launch {
                val user = User(0L, username, password)
                val id = repository.insertUser(user)

                if (id > 0L) {
                    _user.value = User(id, username, password)
                    _toast.value = application.getString(R.string.register_success)
                } else {
                    _toast.value = application.getString(R.string.register_fail)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return (application as OpenBookApplication).appContainer.repository?.let {
                    RegisterViewModel(it, application)
                } as T
            }
        }
    }
}