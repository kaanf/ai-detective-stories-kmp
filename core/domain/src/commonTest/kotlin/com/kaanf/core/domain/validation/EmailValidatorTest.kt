package com.kaanf.core.domain.validation

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmailValidatorTest {
    @Test
    fun `returns true when email is well formed`() {
        assertTrue(EmailValidator.validate("detective.agent@agency.com"))
    }

    @Test
    fun `returns false when at sign is missing`() {
        assertFalse(EmailValidator.validate("detective.agentagency.com"))
    }

    @Test
    fun `returns false when domain suffix is missing`() {
        assertFalse(EmailValidator.validate("detective.agent@agency"))
    }

    @Test
    fun `returns false when domain contains non ascii characters`() {
        assertFalse(EmailValidator.validate("detective.agent@içışğ.com"))
    }

    @Test
    fun `returns false when email contains whitespace`() {
        assertFalse(EmailValidator.validate("detective agent@agency.com"))
    }
}
