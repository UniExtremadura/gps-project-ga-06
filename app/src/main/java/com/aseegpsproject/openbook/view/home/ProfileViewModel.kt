package com.aseegpsproject.openbook.view.home

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
import com.aseegpsproject.openbook.data.model.WorkList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: Repository,
    private val application: OpenBookApplication
) : ViewModel() {
    val workLists = repository.workLists

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

    fun createWorklist(workListName: String) {
        viewModelScope.launch {
            val worklist = WorkList(0L, workListName, listOf())

            repository.workListToLibrary(worklist, user?.userId!!)
        }

        _toast.value = application.getString(R.string.worklist_created)
    }

    fun deleteWorklist(workList: WorkList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWorkListFromLibrary(workList, user?.userId!!)
        }
        _toast.value = application.getString(R.string.worklist_deleted)
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
                    ProfileViewModel(it, application)
                } as T
            }
        }
    }
}