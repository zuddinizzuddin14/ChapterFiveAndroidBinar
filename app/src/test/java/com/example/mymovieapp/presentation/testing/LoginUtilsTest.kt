package com.example.mymovieapp.presentation.testing

import org.junit.Assert.assertEquals
import org.junit.Test

class LoginUtilsTest {

    @Test
    fun `email and password returns true`() {
        val actualResult = LoginUtils.validateFormLogin(
            "zuddin@gmail.com",
            "123456"
        )
        assertEquals(true, actualResult)
    }

    @Test
    fun `email returns false`() {
        val actualResult = LoginUtils.validateFormLogin(
            "",
            "123456"
        )
        assertEquals(false, actualResult)
    }

    @Test
    fun `password returns false`() {
        val actualResult = LoginUtils.validateFormLogin(
            "zuddin@gmail.com",
            ""
        )
        assertEquals(false, actualResult)
    }

    @Test
    fun `password length returns false`() {
        val actualResult = LoginUtils.validateFormLogin(
            "zuddin@gmail.com",
            "1234"
        )
        assertEquals(false, actualResult)
    }
}