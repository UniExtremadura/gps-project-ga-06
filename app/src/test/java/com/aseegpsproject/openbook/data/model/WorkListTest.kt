package com.aseegpsproject.openbook.data.model

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class WorkListTest {
    private lateinit var workList: WorkList

    private val work1 = Work("workKey1", "title1", "description1", arrayListOf("authorName1"), arrayListOf("authorKey1"), 2020, arrayListOf("coverPath1"), "rating1", 1, isFavorite = true, true)
    private val work2 = Work("workKey2", "title2", "description2", arrayListOf("authorName2"), arrayListOf("authorKey2"), 2020, arrayListOf("coverPath2"), "rating2", 2, isFavorite = true, true)
    private val work3 = Work("workKey3", "title3", "description3", arrayListOf("authorName3"), arrayListOf("authorKey3"), 2020, arrayListOf("coverPath3"), "rating3", 3, isFavorite = true, true)
    private val work4 = Work("workKey4", "title4", "description4", arrayListOf("authorName4"), arrayListOf("authorKey4"), 2020, arrayListOf("coverPath4"), "rating4", 4, isFavorite = true, true)

    @Before
    fun setUp() {
        workList = WorkList(0L, "name", arrayListOf(work1, work2, work3, work4))
    }

    @Test
    fun getWorkListId() {
        assertEquals(0L, workList.workListId)
    }

    @Test
    fun setWorkListId() {
        workList.workListId = 1L
        assertEquals(1L, workList.workListId)
    }

    @Test
    fun getName() {
        assertEquals("name", workList.name)
    }

    @Test
    fun getWorks() {
        assertEquals(arrayListOf(work1, work2, work3, work4), workList.works)
    }

    @Test
    fun setWorks() {
        val work5 = Work("workKey5", "title5", "description5", arrayListOf("authorName5"), arrayListOf("authorKey5"), 2020, arrayListOf("coverPath5"), "rating5", 5, isFavorite = true, true)
        val work6 = Work("workKey6", "title6", "description6", arrayListOf("authorName6"), arrayListOf("authorKey6"), 2020, arrayListOf("coverPath6"), "rating6", 6, isFavorite = true, true)
        val work7 = Work("workKey7", "title7", "description7", arrayListOf("authorName7"), arrayListOf("authorKey7"), 2020, arrayListOf("coverPath7"), "rating7", 7, isFavorite = true, true)
        val work8 = Work("workKey8", "title8", "description8", arrayListOf("authorName8"), arrayListOf("authorKey8"), 2020, arrayListOf("coverPath8"), "rating8", 8, isFavorite = true, true)
        workList.works = arrayListOf(work5, work6, work7, work8)
        assertEquals(arrayListOf(work5, work6, work7, work8), workList.works)
    }
}