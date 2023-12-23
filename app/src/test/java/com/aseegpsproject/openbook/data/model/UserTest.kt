package com.aseegpsproject.openbook.data.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class UserTest {
    private lateinit var user: User

    @Before
    fun setUp() {
        user = User(0L, "username", "password")
    }

    @Test
    fun getUserId() {
        assertEquals(0L, user.userId)
    }

    @Test
    fun getUsername() {
        assertEquals("username", user.username)
    }

    @Test
    fun getPassword() {
        assertEquals("password", user.password)
    }
}