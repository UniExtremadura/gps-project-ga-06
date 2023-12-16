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

class LoginViewModel(
    private val repository: Repository,
    private val application: OpenBookApplication
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    fun login(credentials: Pair<String, String>) {
        val check = CredentialCheck.login(credentials.first, credentials.second)

        if (check.fail) _toast.value = check.msg
        else {
            viewModelScope.launch {
                val user = repository.getUser(credentials.first)

                if (user == null) {
                    _toast.value = application.getString(R.string.user_not_found)
                } else {
                    val checkPassword =
                        CredentialCheck.passwordOk(credentials.second, user.password)
                    if (checkPassword.fail) _toast.value = checkPassword.msg
                    else _user.value = user
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
                    LoginViewModel(it, application)
                } as T
            }
        }
    }
}