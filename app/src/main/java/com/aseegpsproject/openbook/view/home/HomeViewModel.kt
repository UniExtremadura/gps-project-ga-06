package com.aseegpsproject.openbook.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.WorkList

class HomeViewModel : ViewModel() {
    private val _user = MutableLiveData<User>(null)
    val user: LiveData<User>
        get() = _user

    private val _navigateToWork = MutableLiveData<Work>(null)
    val navigateToWork: LiveData<Work>
        get() = _navigateToWork

    private val _navigateToAuthor = MutableLiveData<Author>(null)
    val navigateToAuthor: LiveData<Author>
        get() = _navigateToAuthor

    private val _navigateToSettings = MutableLiveData(false)
    val navigateToSettings: LiveData<Boolean>
        get() = _navigateToSettings

    private val _navigateToWorkList = MutableLiveData<WorkList>(null)
    val navigateToWorkList: LiveData<WorkList>
        get() = _navigateToWorkList

    var userInSession: User? = null
        set(value) {
            field = value
            _user.value = value!!
        }

    fun onWorkClick(work: Work) {
        _navigateToWork.value = work
    }

    fun onAuthorClick(author: Author) {
        _navigateToAuthor.value = author
    }

    fun onSettingsClick() {
        _navigateToSettings.value = true
    }

    fun onWorkListClick(workList: WorkList) {
        _navigateToWorkList.value = workList
    }
}