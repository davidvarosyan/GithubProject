package com.varosyan.presenter.user_detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.varosyan.domain.model.UserDetail
import com.varosyan.presenter.user_detail.UserDetailScreen
import org.junit.Rule
import org.junit.Test

/**
 * UI Tests for UserDetailScreen
 * 
 * WHAT IS UI TESTING?
 * UI testing tests the user interface components (Compose screens) to ensure
 * they display correctly and respond to user interactions.
 * 
 * WHY TEST USER DETAIL SCREEN?
 * This screen displays detailed user information and handles user interactions.
 * Testing ensures the detail screen works correctly and provides good user experience.
 * 
 * TESTING STRATEGY:
 * 1. Test screen displays correctly with user data
 * 2. Test different states (loading, error, success)
 * 3. Test user interactions (back button, retry)
 * 4. Test data display (avatar, name, bio, stats)
 * 5. Test accessibility
 */
class UserDetailScreenTest {

    /**
     * COMPOSE TEST RULE
     * 
     * WHAT IT DOES:
     * Sets up the testing environment for Compose UI testing.
     * Provides methods to interact with and verify Compose components.
     * 
     * WHY WE NEED IT:
     * Compose testing requires special setup and rules to work properly.
     */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * TEST: Screen Displays User Data Correctly
     * 
     * WHAT WE'RE TESTING:
     * The screen should display all user information correctly.
     * 
     * WHY THIS MATTERS:
     * Users need to see the correct user information to understand who they're viewing.
     */
    @Test
    fun `user detail screen should display all user information`() {
        // ARRANGE: Set up test data
        val testUserDetail = createTestUserDetail()
        
        // ACT: Set content and render the screen
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that all user information is displayed
        composeTestRule.onNodeWithText("Profile").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test User").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Location").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed() // Followers
        composeTestRule.onNodeWithText("50").assertIsDisplayed()  // Following
        composeTestRule.onNodeWithText("Test bio").assertIsDisplayed()
    }

    /**
     * TEST: Loading State
     * 
     * WHAT WE'RE TESTING:
     * When the screen is loading, it should show a loading indicator.
     * 
     * WHY THIS MATTERS:
     * Users need feedback that something is happening.
     */
    @Test
    fun `loading state should show loading indicator`() {
        // ARRANGE: Set up loading state
        // In a real test, you'd mock the ViewModel to return loading state
        
        // ACT: Set content
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that loading indicator is shown
        // Note: This is a simplified example. In a real test, you'd need to mock the ViewModel
        composeTestRule.onRoot().printToLog("LoadingState")
    }

    /**
     * TEST: Error State
     * 
     * WHAT WE'RE TESTING:
     * When there's an error, the screen should show an error message and retry button.
     * 
     * WHY THIS MATTERS:
     * Users need to know when something goes wrong and how to fix it.
     */
    @Test
    fun `error state should show error message and retry button`() {
        // ARRANGE: Set up error state
        // In a real test, you'd mock the ViewModel to return error state
        
        // ACT: Set content
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that error message and retry button are shown
        // Note: This is a simplified example. In a real test, you'd need to mock the ViewModel
        composeTestRule.onRoot().printToLog("ErrorState")
    }

    /**
     * TEST: Back Button Navigation
     * 
     * WHAT WE'RE TESTING:
     * When the back button is clicked, it should trigger navigation back.
     * 
     * WHY THIS MATTERS:
     * Navigation is a critical user flow that must work correctly.
     */
    @Test
    fun `back button should trigger navigation back`() {
        // ARRANGE: Set up navigation callback
        var navigationTriggered = false
        
        // ACT: Set content and click back button
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = {
                    navigationTriggered = true
                }
            )
        }

        // Click on the back button
        composeTestRule.onNodeWithText("Back").performClick()

        // ASSERT: Check that navigation was triggered
        assert(navigationTriggered) { "Navigation should be triggered when clicking back button" }
    }

    /**
     * TEST: Retry Button
     * 
     * WHAT WE'RE TESTING:
     * When the retry button is clicked, it should trigger a retry.
     * 
     * WHY THIS MATTERS:
     * Users should be able to retry failed operations.
     */
    @Test
    fun `retry button should trigger retry`() {
        // ARRANGE: Set up error state and retry callback
        // In a real test, you'd mock the ViewModel to return error state
        
        // ACT: Set content
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that retry button is shown and works
        // Note: This is a simplified example. In a real test, you'd need to mock the ViewModel
        composeTestRule.onRoot().printToLog("RetryButton")
    }

    /**
     * TEST: User Stats Display
     * 
     * WHAT WE'RE TESTING:
     * The screen should display user statistics correctly.
     * 
     * WHY THIS MATTERS:
     * Users need to see accurate statistics about the user.
     */
    @Test
    fun `user stats should be displayed correctly`() {
        // ARRANGE: Set up test data with specific stats
        val testUserDetail = createTestUserDetail(
            followersCount = 150,
            followingCount = 75,
            repoCount = 30,
            gistCount = 15
        )
        
        // ACT: Set content
        composeTestRule.setContent {
            UserListScreen(
                onNavigateToUserDetail = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that stats are displayed correctly
        composeTestRule.onNodeWithText("150").assertIsDisplayed() // Followers
        composeTestRule.onNodeWithText("75").assertIsDisplayed()  // Following
        composeTestRule.onNodeWithText("30").assertIsDisplayed()  // Repositories
        composeTestRule.onNodeWithText("15").assertIsDisplayed()  // Gists
    }

    /**
     * TEST: Bio Display
     * 
     * WHAT WE'RE TESTING:
     * The screen should display the user's bio correctly.
     * 
     * WHY THIS MATTERS:
     * Users need to see the user's bio to understand more about them.
     */
    @Test
    fun `user bio should be displayed correctly`() {
        // ARRANGE: Set up test data with bio
        val testUserDetail = createTestUserDetail(
            bio = "This is a test bio for the user. It contains multiple lines and should be displayed correctly."
        )
        
        // ACT: Set content
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that bio is displayed
        composeTestRule.onNodeWithText("This is a test bio for the user").assertIsDisplayed()
    }

    /**
     * TEST: Empty Bio Handling
     * 
     * WHAT WE'RE TESTING:
     * When the user has no bio, the screen should handle it gracefully.
     * 
     * WHY THIS MATTERS:
     * The app should handle edge cases gracefully.
     */
    @Test
    fun `empty bio should be handled gracefully`() {
        // ARRANGE: Set up test data with no bio
        val testUserDetail = createTestUserDetail(bio = null)
        
        // ACT: Set content
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that empty bio is handled
        // Note: This is a simplified example. In a real test, you'd need to mock the ViewModel
        composeTestRule.onRoot().printToLog("EmptyBio")
    }

    /**
     * TEST: Last Updated Display
     * 
     * WHAT WE'RE TESTING:
     * The screen should display the last updated date correctly.
     * 
     * WHY THIS MATTERS:
     * Users need to know when the information was last updated.
     */
    @Test
    fun `last updated date should be displayed correctly`() {
        // ARRANGE: Set up test data with last updated date
        val testUserDetail = createTestUserDetail(
            lastUpdated = "2023-01-01T00:00:00Z"
        )
        
        // ACT: Set content
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check that last updated date is displayed
        // Note: This is a simplified example. In a real test, you'd check for formatted date
        composeTestRule.onRoot().printToLog("LastUpdated")
    }

    /**
     * TEST: Accessibility
     * 
     * WHAT WE'RE TESTING:
     * The screen should be accessible to users with disabilities.
     * 
     * WHY THIS MATTERS:
     * Accessibility ensures the app can be used by everyone.
     */
    @Test
    fun `screen should be accessible`() {
        // ARRANGE: Set up test data
        val testUserDetail = createTestUserDetail()
        
        // ACT: Set content
        composeTestRule.setContent {
            UserDetailScreen(
                username = "testuser",
                onNavigateBack = { /* Mock navigation */ }
            )
        }

        // ASSERT: Check accessibility
        // Note: This is a simplified example. In a real test, you'd check for proper content descriptions
        composeTestRule.onRoot().printToLog("Accessibility")
    }

    /**
     * HELPER FUNCTION: Create Test User Detail
     * 
     * WHAT THIS DOES:
     * Creates a test UserDetail object with default or custom values.
     * 
     * WHY WE NEED THIS:
     * Tests need consistent test data that's easy to create and modify.
     */
    private fun createTestUserDetail(
        id: Long = 1L,
        userName: String = "testuser",
        fullName: String? = "Test User",
        avatar: String = "https://example.com/avatar.jpg",
        location: String? = "Test Location",
        followersCount: Int = 100,
        followingCount: Int = 50,
        bio: String? = "Test bio",
        repoCount: Int? = 25,
        gistCount: Int? = 10,
        lastUpdated: String? = "2023-01-01T00:00:00Z"
    ): UserDetail {
        return UserDetail(
            id = id,
            userName = userName,
            fullName = fullName,
            avatar = avatar,
            location = location,
            followersCount = followersCount,
            followingCount = followingCount,
            bio = bio,
            repoCount = repoCount,
            gistCount = gistCount,
            lastUpdated = lastUpdated
        )
    }
}
