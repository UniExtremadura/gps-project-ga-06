package com.aseegpsproject.openbook.data.model

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AuthorTest {
    private lateinit var author: Author

    @Before
    fun setUp() {
        author = Author(
            "authorKey",
            "name",
            "fullName",
            "birthDate",
            "deathDate",
            "photoPath",
            "wikipedia",
            "bio",
            1,
            isFavorite = true,
            true
        )
    }

    @Test
    fun getAuthorKey() {
        assertEquals("authorKey", author.authorKey)
    }

    @Test
    fun getName() {
        assertEquals("name", author.name)
    }

    @Test
    fun getFullName() {
        assertEquals("fullName", author.fullName)
    }

    @Test
    fun getBirthDate() {
        assertEquals("birthDate", author.birthDate)
    }

    @Test
    fun getDeathDate() {
        assertEquals("deathDate", author.deathDate)
    }

    @Test
    fun getPhotoPath() {
        assertEquals("photoPath", author.photoPath)
    }

    @Test
    fun setPhotoPath() {
        author.photoPath = "newPhotoPath"
        assertEquals("newPhotoPath", author.photoPath)
    }

    @Test
    fun getWikipedia() {
        assertEquals("wikipedia", author.wikipedia)
    }

    @Test
    fun getBio() {
        assertEquals("bio", author.bio)
    }

    @Test
    fun setBio() {
        author.bio = "newBio"
        assertEquals("newBio", author.bio)
    }

    @Test
    fun getNumWorks() {
        assertEquals(1, author.numWorks)
    }

    @Test
    fun isFavorite() {
        assertTrue(author.isFavorite)
    }

    @Test
    fun setFavorite() {
        author.isFavorite = false
        assert(!author.isFavorite)
    }

    @Test
    fun getEnabled() {
        assertTrue(author.enabled)
    }

    @Test
    fun setEnabled() {
        author.enabled = false
        assert(!author.enabled)
    }
}