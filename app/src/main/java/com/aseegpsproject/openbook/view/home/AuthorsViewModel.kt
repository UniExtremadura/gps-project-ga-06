package com.aseegpsproject.openbook.view.home

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.aseegpsproject.openbook.OpenBookApplication
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.User
import kotlinx.coroutines.launch

class AuthorsViewModel(
    private val repository: Repository,
    private val application: OpenBookApplication
): ViewModel() {
    var authors = repository.favAuthors
    var user: User? = null
        set(value) {
            field = value
            repository.setUserid(value!!.userId!!)
        }

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    fun loadFavoriteAuthors() {
        authors = repository.favAuthors
    }

    fun changeFavoriteAuthor(author: Author) {
        viewModelScope.launch {
            if (author.isFavorite) {
                author.isFavorite = false
                repository.deleteAuthorFromLibrary(author, user?.userId!!)
                _toast.value = application.getString(R.string.remove_fav)
            }
            else {
                author.isFavorite = true
                repository.authorToLibrary(author, user?.userId!!)
                _toast.value = application.getString(R.string.add_fav)
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
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return (application as OpenBookApplication).appContainer.repository?.let {
                    AuthorsViewModel(it, application)
                } as T
            }
        }
    }
}