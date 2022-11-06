package com.example.mymovieapp.presentation.testing

import org.junit.Assert.*
import org.junit.Test

class RegistrationUtilsTest {

    @Test
    fun `email and password returns true`() {
        val actualResult = RegistrationUtils.validateFormRegister(
            "zuddin@gmail.com",
            "123456",
            "123456",
            "zuddin"
        )
        assertEquals(true, actualResult)
    }

    @Test
    fun `email returns false`() {
        val actualResult = RegistrationUtils.validateFormRegister(
            "",
            "123456",
            "123456",
            "zuddin"
        )
        assertEquals(false, actualResult)
    }

    @Test
    fun `password returns false`() {
        val actualResult = RegistrationUtils.validateFormRegister(
            "zuddin@gmail.com",
            "",
            "123456",
            "zuddin"
        )
        assertEquals(false, actualResult)
    }

    @Test
    fun `confirm password returns false`() {
        val actualResult = RegistrationUtils.validateFormRegister(
            "zuddin@gmail.com",
            "",
            "123456",
            "zuddin"
        )
        assertEquals(false, actualResult)
    }

    @Test
    fun `password and confirm password not match returns false`() {
        val actualResult = RegistrationUtils.validateFormRegister(
            "zuddin@gmail.com",
            "1234567",
            "123456",
            "zuddin"
        )
        assertEquals(false, actualResult)
    }

    @Test
    fun `password length returns false`() {
        val actualResult = RegistrationUtils.validateFormRegister(
            "zuddin@gmail.com",
            "1234",
            "1234",
            "zuddin"
        )
        assertEquals(false, actualResult)
    }

    @Test
    fun `name returns false`() {
        val actualResult = RegistrationUtils.validateFormRegister(
            "zuddin@gmail.com",
            "123456",
            "123456",
            ""
        )
        assertEquals(false, actualResult)
    }

}