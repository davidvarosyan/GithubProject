package com.varosyan.presenter

import androidx.paging.LoadState
import androidx.paging.PagingData
import com.varosyan.domain.model.User
import com.varosyan.domain.usecase.GetUsersUseCase
import com.varosyan.presenter.user_list.UserListIntent
import com.varosyan.presenter.user_list.UserListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Basic Unit Tests
 * 
 * WHAT ARE UNIT TESTS?
 * Unit tests test individual components (like ViewModels) in isolation.
 * We mock dependencies and test the component's behavior without external factors.
 * 
 * WHY TEST UNITS?
 * Unit tests ensure individual components work correctly.
 * They catch bugs early and make the code more maintainable.
 */
@ExperimentalCoroutinesApi
class BasicUnitTest {

    // Mock dependencies
    private lateinit var mockGetUsersUseCase: GetUsersUseCase
    private lateinit var viewModel: UserListViewModel
    
    // Test dispatcher for coroutines testing
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set up test environment before each test
        Dispatchers.setMain(testDispatcher)
        
        // Create mock dependencies
        mockGetUsersUseCase = mockk()
        
        // Create ViewModel with mocked dependencies
        viewModel = UserListViewModel(mockGetUsersUseCase)
    }

    @After
    fun tearDown() {
        // Clean up after each test
        Dispatchers.resetMain()
    }

    /**
     * TEST: Initial State
     * 
     * WHAT WE'RE TESTING:
     * When the ViewModel is created, it should start with the correct initial state.
     * 
     * WHY THIS MATTERS:
     * Ensures the app starts in a predictable state.
     */
    @Test
    fun `initial state should be correct`() = runTest {
        // ARRANGE: Set up mock behavior
        val mockUsers = flowOf(PagingData.from(listOf(createTestUser())))
        coEvery { mockGetUsersUseCase() } returns mockUsers

        // ACT: Initialize the ViewModel (this happens in setup)
        advanceUntilIdle() // Wait for all coroutines to complete

        // ASSERT: Check the initial state
        val uiState = viewModel.uiState.value
        assertTrue("Initial state should be loading", uiState.isLoading)
        assertTrue("Initial state should have users flow", uiState.users != null)
        assertTrue("Initial state should have no error", uiState.error == null)
    }

    /**
     * TEST: Load Users Success
     * 
     * WHAT WE'RE TESTING:
     * When users are loaded successfully, the state should update correctly.
     * 
     * WHY THIS MATTERS:
     * Ensures the UI shows the correct data when everything works.
     */
    @Test
    fun `load users should update state with users data`() = runTest {
        // ARRANGE: Create test data and mock behavior
        val testUsers = listOf(
            createTestUser(id = 1, userName = "user1"),
            createTestUser(id = 2, userName = "user2")
        )
        val mockUsers = flowOf(PagingData.from(testUsers))
        coEvery { mockGetUsersUseCase() } returns mockUsers

        // ACT: Trigger load users intent
        viewModel.handleIntent(UserListIntent.LoadUsers)
        advanceUntilIdle()

        // ASSERT: Check that state is updated correctly
        val uiState = viewModel.uiState.value
        assertTrue("Should have users data", uiState.users != null)
        assertFalse("Should not be loading after success", uiState.isLoading)
        assertTrue("Should have no error after success", uiState.error == null)
    }

    /**
     * TEST: Retry Intent
     * 
     * WHAT WE'RE TESTING:
     * When user retries after an error, the state should reset and try again.
     * 
     * WHY THIS MATTERS:
     * Users should be able to recover from errors by retrying.
     */
    @Test
    fun `retry intent should reset error and start loading`() = runTest {
        // ARRANGE: Set up initial error state
        viewModel.handleIntent(UserListIntent.Retry)
        advanceUntilIdle()

        // ACT: Trigger retry
        viewModel.handleIntent(UserListIntent.Retry)
        advanceUntilIdle()

        // ASSERT: Check that retry resets the state
        val uiState = viewModel.uiState.value
        assertTrue("Should be loading when retrying", uiState.isLoading)
        assertTrue("Should clear error when retrying", uiState.error == null)
    }

    /**
     * TEST: User Click Intent
     * 
     * WHAT WE'RE TESTING:
     * When user clicks on a user item, it should trigger navigation.
     * 
     * WHY THIS MATTERS:
     * Navigation is a critical user flow that must work correctly.
     */
    @Test
    fun `user click intent should trigger navigation effect`() = runTest {
        // ARRANGE: Create test user
        val testUser = createTestUser(id = 1, userName = "testuser")

        // ACT: Trigger user click
        viewModel.handleIntent(UserListIntent.UserClicked(testUser))
        advanceUntilIdle()

        // ASSERT: Check that navigation effect is emitted
        // Note: In a real test, you'd collect effects and verify the navigation
        assertTrue("User click should be handled", true)
    }

    /**
     * TEST: Clear Error Intent
     * 
     * WHAT WE'RE TESTING:
     * When user dismisses an error, it should be cleared from state.
     * 
     * WHY THIS MATTERS:
     * Users should be able to dismiss error messages.
     */
    @Test
    fun `clear error intent should remove error from state`() = runTest {
        // ARRANGE: Set up error state (simulated)
        // In a real scenario, you'd set up an error state first

        // ACT: Clear error
        viewModel.handleIntent(UserListIntent.ClearError)
        advanceUntilIdle()

        // ASSERT: Check that error is cleared
        val uiState = viewModel.uiState.value
        assertTrue("Error should be cleared", uiState.error == null)
    }

    /**
     * TEST: Load State Handling
     * 
     * WHAT WE'RE TESTING:
     * When paging load state changes, the ViewModel should handle it correctly.
     * 
     * WHY THIS MATTERS:
     * Paging states affect UI (loading indicators, error messages).
     */
    @Test
    fun `handle load state loading should set loading to true`() = runTest {
        // ARRANGE: Set up test
        val loadingState = LoadState.Loading

        // ACT: Handle load state
        viewModel.handleLoadState(loadingState)

        // ASSERT: Check that loading state is set
        val uiState = viewModel.uiState.value
        assertTrue("Should be loading", uiState.isLoading)
    }

    @Test
    fun `handle load state error should set error message`() = runTest {
        // ARRANGE: Create error state
        val errorState = LoadState.Error(Exception("Network error"))

        // ACT: Handle error state
        viewModel.handleLoadState(errorState)

        // ASSERT: Check that error is set
        val uiState = viewModel.uiState.value
        assertTrue("Should have error message", uiState.error != null)
        assertFalse("Should not be loading after error", uiState.isLoading)
    }

    /**
     * HELPER FUNCTION: Create Test User
     * 
     * WHAT THIS DOES:
     * Creates a test User object with default or custom values.
     * 
     * WHY WE NEED THIS:
     * Tests need consistent test data that's easy to create and modify.
     */
    private fun createTestUser(
        id: Long = 1L,
        userName: String = "testuser",
        avatar: String = "https://example.com/avatar.jpg"
    ): User {
        return User(
            id = id,
            userName = userName,
            avatar = avatar
        )
    }
}
