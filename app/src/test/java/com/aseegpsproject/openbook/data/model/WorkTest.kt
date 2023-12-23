package com.aseegpsproject.openbook.data.model

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class WorkTest {
    private lateinit var work: Work

    @Before
    fun setUp() {
        work = Work(
            workKey = "workKey",
            title = "title",
            description = "description",
            authorNames = arrayListOf("authorNames"),
            authorKeys = arrayListOf("authorKeys"),
            firstPublishYear = 2020,
            coverPaths = arrayListOf("coverPaths"),
            rating = "rating",
            numEditions = 1,
            isFavorite = true,
            enabled = true
        )
    }

    @Test
    fun getWorkKey() {
        assertEquals("workKey", work.workKey)
    }

    @Test
    fun getTitle() {
        assertEquals("title", work.title)
    }

    @Test
    fun getDescription() {
        assertEquals("description", work.description)
    }

    @Test
    fun setDescription() {
        work.description = "newDescription"
        assertEquals("newDescription", work.description)
    }

    @Test
    fun getAuthorNames() {
        assertEquals(arrayListOf("authorNames"), work.authorNames)
    }

    @Test
    fun getAuthorKeys() {
        assertEquals(arrayListOf("authorKeys"), work.authorKeys)
    }

    @Test
    fun getFirstPublishYear() {
        assertEquals(2020, work.firstPublishYear)
    }

    @Test
    fun getCoverPaths() {
        assertEquals(arrayListOf("coverPaths"), work.coverPaths)
    }

    @Test
    fun getRating() {
        assertEquals("rating", work.rating)
    }

    @Test
    fun setRating() {
        work.rating = "newRating"
        assertEquals("newRating", work.rating)
    }

    @Test
    fun getNumEditions() {
        assertEquals(1, work.numEditions)
    }

    @Test
    fun isFavorite() {
        assertTrue(work.isFavorite)
    }

    @Test
    fun setFavorite() {
        work.isFavorite = false
        assert(!work.isFavorite)
    }

    @Test
    fun getEnabled() {
        assertTrue(work.enabled)
    }

    @Test
    fun setEnabled() {
        work.enabled = false
        assert(!work.enabled)
    }
}