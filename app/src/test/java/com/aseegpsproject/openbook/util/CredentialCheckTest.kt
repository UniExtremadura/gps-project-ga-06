package com.aseegpsproject.openbook.util

import org.junit.Assert
import org.junit.Test

class CredentialCheckTest {
    @Test
    fun checkLogin() {
        val result = CredentialCheck.login("espresso", "espresso")
        Assert.assertEquals(result.error, CredentialCheck.CredentialError.Success)
    }

    @Test
    fun checkJoin() {
        val result = CredentialCheck.join("espresso", "espresso", "espresso")
        Assert.assertEquals(result.error, CredentialCheck.CredentialError.Success)
    }

    @Test
    fun checkPasswordOk() {
        val result = CredentialCheck.passwordOk("espresso", "espresso")
        Assert.assertEquals(result.error, CredentialCheck.CredentialError.Success)
    }
}