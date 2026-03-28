package com.kaanf.detectiveaistories.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaanf.core.presentation.util.TestTags
import com.kaanf.detectiveaistories.MainActivity
import com.kaanf.detectiveaistories.navigation.ExternalUriHandler
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthCharacterFlowTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun register_activate_login_navigates_to_character_screen() {
        DetectiveAiStoriesTestEnvironment.reset()

        val email = "detective.test@agency.io"
        val password = "Password9"

        waitUntilTagExists(TestTags.LOGIN_SCREEN)
        pauseBetweenSteps()

        composeRule.onNodeWithTag(TestTags.LOGIN_TO_REGISTER, useUnmergedTree = true)
            .performClick()

        waitUntilTagExists(TestTags.REGISTER_SCREEN)
        pauseBetweenSteps()

        composeRule.onNodeWithTag(TestTags.REGISTER_EMAIL, useUnmergedTree = true)
            .performTextInput(email)
        pauseBetweenSteps()
        composeRule.onNodeWithTag(TestTags.REGISTER_PASSWORD, useUnmergedTree = true)
            .performTextInput(password)
        pauseBetweenSteps()
        composeRule.onNodeWithTag(TestTags.REGISTER_RE_PASSWORD, useUnmergedTree = true)
            .performTextInput(password)
        pauseBetweenSteps()
        composeRule.onNodeWithTag(TestTags.REGISTER_SUBMIT, useUnmergedTree = true)
            .performClick()

        waitUntilTagExists(TestTags.VERIFICATION_SENT_SCREEN)
        pauseBetweenSteps()

        val activationDeepLink =
            requireNotNull(DetectiveAiStoriesTestEnvironment.activationDeepLinkFor(email)) {
                "Missing activation link for registered user"
            }

        composeRule.runOnIdle {
            ExternalUriHandler.onNewUri(activationDeepLink)
        }

        waitUntilTagExists(TestTags.VERIFICATION_RESULT_VERIFIED_SCREEN)
        pauseBetweenSteps()

        waitUntilTagExists(TestTags.LOGIN_SCREEN)
        pauseBetweenSteps()

        composeRule.onNodeWithTag(TestTags.LOGIN_EMAIL, useUnmergedTree = true)
            .performTextInput(email)
        pauseBetweenSteps()
        composeRule.onNodeWithTag(TestTags.LOGIN_PASSWORD, useUnmergedTree = true)
            .performTextInput(password)
        pauseBetweenSteps()
        composeRule.onNodeWithTag(TestTags.LOGIN_SUBMIT, useUnmergedTree = true)
            .performClick()

        waitUntilTagExists(TestTags.CHARACTER_SCREEN)
        pauseBetweenSteps()

        composeRule.onNodeWithTag(TestTags.CHARACTER_SCREEN).assertIsDisplayed()
    }

    private fun waitUntilTagExists(tag: String, timeoutMillis: Long = 10_000L) {
        composeRule.waitUntil(timeoutMillis) {
            runCatching {
                composeRule.onAllNodesWithTag(tag, useUnmergedTree = true)
                    .fetchSemanticsNodes().isNotEmpty()
            }.getOrDefault(false)
        }
    }

    private fun pauseBetweenSteps() {
        composeRule.waitForIdle()
        Thread.sleep(1_000L)
    }
}
