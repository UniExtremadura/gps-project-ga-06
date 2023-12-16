package com.aseegpsproject.openbook.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.aseegpsproject.openbook.OpenBookApplication
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.User
import kotlinx.coroutines.launch

class AuthorDetailViewModel(
    private val repository: Repository,
    private val application: OpenBookApplication
) : ViewModel() {
    var author: Author? = null
        set(value) {
            field = value
            getAuthor()
        }

    var user: User? = null

    private val _authorDetail = MutableLiveData<Author>(null)
    val authorDetail: LiveData<Author>
        get() = _authorDetail

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    private fun getAuthor() {
        if (author != null)
            viewModelScope.launch {
                try {
                    _authorDetail.value = repository.fetchAuthorDetails(author!!)
                } catch (error: APIError) {
                    _toast.value = error.message
                }
            }
    }

    fun changeFavoriteAuthor() {
        viewModelScope.launch {
            if (author?.isFavorite == true) {
                author!!.isFavorite = false
                repository.deleteAuthorFromLibrary(author!!, user?.userId!!)
                _toast.value = application.getString(R.string.remove_fav)
            } else {
                author?.isFavorite = true
                repository.authorToLibrary(author!!, user?.userId!!)
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
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return (application as OpenBookApplication).appContainer.repository?.let {
                    AuthorDetailViewModel(it, application)
                } as T
            }
        }
    }
}