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
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.data.Repository
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.WorkList
import kotlinx.coroutines.launch

class WorkDetailViewModel (
    private val repository: Repository,
    private val application: OpenBookApplication
): ViewModel() {
    var workListsInLibrary = repository.workLists
    private val _workDetail = MutableLiveData<Work>(null)
    val workDetail: LiveData<Work>
        get() = _workDetail

    private val _workListDialog = MutableLiveData(false)
    val workListDialog: LiveData<Boolean>
        get() = _workListDialog

    var user: User? = null
    var work: Work? = null
        set(value) {
            field = value
            getWork()
        }

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    fun onToastShown() {
        _toast.value = null
    }

    private fun getWork() {
        if (work!=null)
            viewModelScope.launch {
                try{
                    _workDetail.value = repository.fetchWorkDetails(work!!)
                } catch (error: APIError) {
                    _toast.value = error.message
                }
            }
    }

    fun changeFavoriteWork() {
        viewModelScope.launch {
            if (work?.isFavorite == true) {
                work!!.isFavorite = false
                user?.userId?.let { repository.deleteWorkFromLibrary(work!!, it) }
                _toast.value = application.getString(R.string.remove_fav)
            } else {
                work?.isFavorite = true
                user?.userId?.let { repository.workToLibrary(work!!, it) }
                _toast.value = application.getString(R.string.add_fav)
            }
        }
    }

    fun addToWorklist(worklist: WorkList) {
        val workKeys = worklist.works.map { it.workKey }.toSet()
        if (work?.let { workKeys.contains(it.workKey) } == true) {
            _toast.value = application.getString(R.string.already_in_worklist)
        } else {
            viewModelScope.launch {
                repository.workToWorkList(work!!, worklist)
                _toast.value = application.getString(R.string.added_to_worklist)
            }
        }
        _workListDialog.value = false
    }

    fun showWorkListDialog() {
        if (workListsInLibrary.value?.workLists?.isEmpty() == true) {
            _toast.value = application.getString(R.string.no_workLists)
        } else {
            _workListDialog.value = true
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
                    WorkDetailViewModel(it, application)
                } as T
            }
        }
    }

}