package com.aseegpsproject.openbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.aseegpsproject.openbook.api.APIError
import com.aseegpsproject.openbook.api.OpenLibraryAPI
import com.aseegpsproject.openbook.api.getNetworkService
import com.aseegpsproject.openbook.data.model.UserWithWorks
import com.aseegpsproject.openbook.data.model.UserWorkCrossRef
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.database.dao.UserDao
import com.aseegpsproject.openbook.database.dao.WorkDao

class Repository private constructor(
    private val trendingFreq: String,
    private val userDao: UserDao,
    private val workDao: WorkDao,
    private val networkService: OpenLibraryAPI
) {
    private var lastUpdateTimeMillis: Long = 0L
    val works = workDao.getWorks()
    private val userFilter = MutableLiveData<Long>()

    val worksInLibrary: LiveData<UserWithWorks> =
        userFilter.switchMap{ userid -> workDao.getUserWithWorks(userid) }

    fun setUserid(userid: Long) {
        userFilter.value = userid
    }

    suspend fun deleteWorkFromLibrary(work: Work, userId: Long) {
        workDao.delete(UserWorkCrossRef(userId, work.workKey))
        workDao.update(work)
    }
    suspend fun workToLibrary(work: Work, userId: Long) {
        workDao.update(work)
        workDao.insertUserWork(UserWorkCrossRef(userId, work.workKey))
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

    suspend fun tryUpdateRecentWorksCache() {
        if (shouldUpdateWorksCache()) {
            fetchRecentWorks()
        }
    }

    private suspend fun fetchRecentWorks() {
        try {
            val works = networkService.getDailyTrendingBooks(trendingFreq).trendingWorks.map { it.toWork()}
            workDao.insertAll(works)
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

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(
            trendingFreq: String,
            userDao: UserDao,
            workDao: WorkDao,
            libraryAPI: OpenLibraryAPI
        ): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(trendingFreq, userDao, workDao, libraryAPI).also { INSTANCE = it }
            }
        }
    }
}