package com.aseegpsproject.openbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.api.OpenLibraryAPI
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.UserAuthorCrossRef
import com.aseegpsproject.openbook.data.model.UserWithAuthors
import com.aseegpsproject.openbook.data.model.UserWithWorklists
import com.aseegpsproject.openbook.data.model.UserWithWorks
import com.aseegpsproject.openbook.data.model.UserWorkCrossRef
import com.aseegpsproject.openbook.data.model.UserWorkListCrossRef
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.WorkList
import com.aseegpsproject.openbook.database.dao.AuthorDao
import com.aseegpsproject.openbook.database.dao.UserDao
import com.aseegpsproject.openbook.database.dao.WorkDao
import com.aseegpsproject.openbook.database.dao.WorkListDao

class Repository(
    private val userDao: UserDao,
    private val workDao: WorkDao,
    private val authorDao: AuthorDao,
    private val workListDao: WorkListDao,
    private val networkService: OpenLibraryAPI
) {
    val works = workDao.getWorks()
    val authors = authorDao.getAuthors()

    private var lastUpdateTimeMillis: Long = 0L
    private val userFilter = MutableLiveData<Long>()
    private val workListFilter = MutableLiveData<Long>()
    private val trendingFreq: MutableLiveData<String> = MutableLiveData("daily")

    val favAuthors: LiveData<UserWithAuthors> =
        userFilter.switchMap { userid -> authorDao.getUserWithAuthors(userid) }

    val worksInLibrary: LiveData<UserWithWorks> =
        userFilter.switchMap { userid -> workDao.getUserWithWorks(userid) }

    val workLists: LiveData<UserWithWorklists> =
        userFilter.switchMap { userid -> workListDao.getUserWithWorkLists(userid) }

    val workList: LiveData<WorkList> =
        workListFilter.switchMap { worklistId -> workListDao.getById(worklistId) }

    fun setUserid(userid: Long) {
        userFilter.value = userid
    }

    fun setWorkListId(worklistId: Long) {
        workListFilter.value = worklistId
    }

    fun setTrendingFreq(freq: String) {
        if (freq != trendingFreq.value) {
            trendingFreq.value = freq
            lastUpdateTimeMillis = 0L
        }
    }

    suspend fun workToLibrary(work: Work, userId: Long) {
        workDao.insertAndRelate(work, userId)
    }

    suspend fun deleteWorkFromLibrary(work: Work, userId: Long) {
        workDao.delete(UserWorkCrossRef(userId, work.workKey))
        workDao.update(work)
    }

    suspend fun workListToLibrary(workList: WorkList, userId: Long) {
        workListDao.insertAndRelate(workList, userId)
    }

    suspend fun deleteWorkListFromLibrary(workList: WorkList, userId: Long) {
        workListDao.delete(UserWorkListCrossRef(userId, workList.workListId))
        workListDao.delete(workList)
    }

    suspend fun workToWorkList(work: Work, workList: WorkList) {
        workList.works = workList.works + work
        workListDao.update(workList)
    }

    suspend fun deleteWorkFromWorkList(work: Work, workList: WorkList) {
        workList.works = workList.works - work
        workListDao.update(workList)
    }

    suspend fun authorToLibrary(author: Author, userId: Long) {
        authorDao.insertAndRelate(author, userId)
    }

    suspend fun deleteAuthorFromLibrary(author: Author, userId: Long) {
        authorDao.delete(UserAuthorCrossRef(userId, author.authorKey))
        authorDao.update(author)
    }

    suspend fun fetchWorkDetails(work: Work): Work {
        return if (work.description != null) {
            work
        } else {
            val workDetails = getNetworkService().getWorkInfo(work.workKey)
            work.description = workDetails.description
            work.rating = getNetworkService().getWorkRatings(work.workKey).toStr()
            workDao.update(work)

            work
        }
    }

    suspend fun searchWorks(title: String) {
        try {
            disableAllWorks()
            val searchWorks =
                getNetworkService().getSearchBooksByTitle(title, 1).docs.map { it.toWork() }
            val worksInLibrary = worksInLibrary.value?.works
            workDao.insertAll(searchWorks)
            if (worksInLibrary != null) {
                workDao.insertAllAndRelate(worksInLibrary, userFilter.value!!)
            }
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun searchAuthors(name: String) {
        try {
            disableAllAuthors()
            val searchAuthors = getNetworkService().getSearchAuthorsByName(name, 1).docs
                .map { it.toAuthor() }
                .filter { it.birthDate != null }
                .filter { it.numWorks != 0 }
                .filter { it.checkPhotoPath() }
            val authorsInLibrary = favAuthors.value?.authors
            authorDao.insertAll(searchAuthors)
            if (authorsInLibrary != null) {
                authorDao.insertAllAndRelate(authorsInLibrary, userFilter.value!!)
            }
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun fetchAuthorDetails(author: Author): Author {
        return if (author.bio != null) {
            author
        } else {
            val authorDetails = getNetworkService().getAuthorInfo(author.authorKey)
            if (authorDetails.bio != null)
                author.bio = authorDetails.bio
            else
                author.bio = "No bio"
            authorDao.update(author)

            author
        }
    }

    suspend fun tryUpdateRecentWorksCache(forceUpdate: Boolean = false) {
        if (shouldUpdateWorksCache() || forceUpdate) {
            fetchRecentWorks()
        }
    }

    private suspend fun fetchRecentWorks() {
        try {
            disableAllWorks()
            val works =
                trendingFreq.value?.let { it -> networkService.getDailyTrendingBooks(it).trendingWorks.map { it.toWork() } }
            if (works != null) {
                val worksInLibrary = worksInLibrary.value?.works
                workDao.insertAll(works)
                if (worksInLibrary != null) {
                    workDao.insertAllAndRelate(worksInLibrary, userFilter.value!!)
                }
            }
            lastUpdateTimeMillis = System.currentTimeMillis()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    private suspend fun shouldUpdateWorksCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || workDao.getNumberOfWorks() == 0L
    }

    private suspend fun disableAllWorks() {
        val previousWorks = works.value
        if (previousWorks != null) {
            for (work in previousWorks) {
                work.enabled = false
            }
            workDao.updateAll(previousWorks)
        }
    }

    suspend fun reloadAuthors() {
        disableAllAuthors()
    }

    private suspend fun disableAllAuthors() {
        val previousAuthors = authors.value
        if (previousAuthors != null) {
            for (author in previousAuthors) {
                author.enabled = false
            }
            authorDao.updateAll(previousAuthors)
        }
    }

    suspend fun getUser(username: String): User? {
        return userDao.getByUsername(username)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insert(user)
    }

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000
    }
}