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
    private val workDao: WorkDao,
    private val networkService: OpenLibraryAPI
) {
    private var lastUpdateTimeMillis: Long = 0L
    val works = workDao.getWorks()
    private val userFilter = MutableLiveData<Long>()
    private val trendingFreq: MutableLiveData<String> = MutableLiveData("daily")

    val worksInLibrary: LiveData<UserWithWorks> =
        userFilter.switchMap{ userid -> workDao.getUserWithWorks(userid) }

    fun setUserid(userid: Long) {
        userFilter.value = userid
    }

    fun setTrendingFreq(freq: String) {
        trendingFreq.value = freq
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
            val previousWorks = workDao.getWorks().value ?: listOf()
            for (work in previousWorks) {
                work.isDiscover = false
            }
            workDao.updateAll(previousWorks)

            val works = trendingFreq.value?.let { it -> networkService.getDailyTrendingBooks(it).trendingWorks.map { it.toWork()} }
            if (works != null) {
                workDao.insertAll(works)
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

    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(
            workDao: WorkDao,
            libraryAPI: OpenLibraryAPI
        ): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(workDao, libraryAPI).also { INSTANCE = it }
            }
        }
    }
}