package com.aseegpsproject.openbook.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.aseegpsproject.openbook.OpenBookApplication
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import kotlinx.coroutines.launch

class WorksViewModel(
    private val repository: Repository,
    private val application: OpenBookApplication
) : ViewModel() {
    val worksInLibrary = repository.worksInLibrary
    var user: User? = null
        set(value) {
            field = value
            repository.setUserid(value!!.userId)
        }

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    fun changeFavoriteWork(work: Work) {
        viewModelScope.launch {
            if (work.isFavorite) {
                work.isFavorite = false
                repository.deleteWorkFromLibrary(work, user?.userId!!)
                _toast.value = application.getString(R.string.remove_fav)
            } else {
                work.isFavorite = true
                repository.workToLibrary(work, user?.userId!!)
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
                val application = checkNotNull(extras[APPLICATION_KEY])

                return (application as OpenBookApplication).appContainer.repository?.let {
                    WorksViewModel(it, application)
                } as T
            }
        }
    }
}