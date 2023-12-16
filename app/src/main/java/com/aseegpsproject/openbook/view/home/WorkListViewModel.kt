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
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.WorkList
import kotlinx.coroutines.launch

class WorkListViewModel(
    private val repository: Repository,
    private val application: OpenBookApplication
) : ViewModel() {
    val works = repository.workList

    var workList: WorkList? = null
        set(value) {
            field = value
            repository.setWorkListId(value!!.workListId)
        }

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    fun removeWorkFromWorklist(work: Work) {
        viewModelScope.launch {
            repository.deleteWorkFromWorkList(work, workList!!)
        }
        _toast.value = application.getString(R.string.removed_from_worklist)
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
                    WorkListViewModel(it, application)
                } as T
            }
        }
    }
}