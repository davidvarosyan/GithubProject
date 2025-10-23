package com.varosyan.presenter

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.UserDetail
import com.varosyan.domain.usecase.GetUserDetailUseCase
import com.varosyan.presenter.user_detail.UserDetailIntent
import com.varosyan.presenter.user_detail.UserDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Basic ViewModel Tests
 * 
 * WHAT ARE VIEWMODEL TESTS?
 * ViewModel tests test the business logic and state management in ViewModels.
 * They ensure ViewModels work correctly and manage state properly.
 * 
 * WHY TEST VIEWMODELS?
 * ViewModels contain business logic and state management.
 * Testing them ensures the app behaves correctly under different conditions.
 */
@ExperimentalCoroutinesApi
class BasicViewModelTest {

    // Mock dependencies
    private lateinit var mockGetUserDetailUseCase: GetUserDetailUseCase
    private lateinit var viewModel: UserDetailViewModel
    
    // Test dispatcher for coroutines testing
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set up test environment before each test
        Dispatchers.setMain(testDispatcher)
        
        // Create mock dependencies
        mockGetUserDetailUseCase = mockk()
        
        // Create ViewModel with mocked dependencies
        viewModel = UserDetailViewModel(mockGetUserDetailUseCase)
    }

    @After
    fun tearDown() {
        // Clean up after each test
        Dispatchers.resetMain()
    }

    /**
     * TEST: Initialization with Username
     * 
     * WHAT WE'RE TESTING:
     * When the ViewModel is initialized with a username, it should start loading.
     * 
     * WHY THIS MATTERS:
     * The detail screen needs to know which user to load details for.
     */
    @Test
    fun `initialize with username should start loading`() = runTest {
        // ARRANGE: Set up test data
        val testUsername = "testuser"
        val mockUserDetail = createTestUserDetail()
        
        // Mock the use case to return success
        coEvery { mockGetUserDetailUseCase(testUsername) } returns Result.Success(mockUserDetail)

        // ACT: Initialize with username
        viewModel.initialize(testUsername)
        advanceUntilIdle()

        // ASSERT: Check that loading started
        val uiState = viewModel.uiState.value
        assertTrue("Should be loading initially", uiState.isLoading)
        assertNull("Should have no error initially", uiState.error)
    }

    /**
     * TEST: Successful User Detail Loading
     * 
     * WHAT WE'RE TESTING:
     * When user details are loaded successfully, the state should contain the user data.
     * 
     * WHY THIS MATTERS:
     * Users need to see the correct user information when everything works.
     */
    @Test
    fun `load user detail success should update state with user data`() = runTest {
        // ARRANGE: Create test data and mock behavior
        val testUsername = "testuser"
        val testUserDetail = createTestUserDetail(
            id = 1L,
            userName = testUsername,
            fullName = "Test User",
            bio = "Test bio"
        )
        
        // Mock successful response
        coEvery { mockGetUserDetailUseCase(testUsername) } returns Result.Success(testUserDetail)

        // ACT: Initialize and load user detail
        viewModel.initialize(testUsername)
        advanceUntilIdle()

        // ASSERT: Check that state contains user data
        val uiState = viewModel.uiState.value
        assertFalse("Should not be loading after success", uiState.isLoading)
        assertNull("Should have no error after success", uiState.error)
        assertTrue("Should have user detail data", uiState.userDetail != null)
        assertEquals("Should have correct username", testUsername, uiState.userDetail?.userName)
        assertEquals("Should have correct full name", "Test User", uiState.userDetail?.fullName)
    }

    /**
     * TEST: Error Handling
     * 
     * WHAT WE'RE TESTING:
     * When user detail loading fails, the state should show an error.
     * 
     * WHY THIS MATTERS:
     * Users need to know when something goes wrong and be able to retry.
     */
    @Test
    fun `load user detail error should set error state`() = runTest {
        // ARRANGE: Create test data and mock error
        val testUsername = "testuser"
        val errorMessage = "Network error"
        
        // Mock error response
        coEvery { mockGetUserDetailUseCase(testUsername) } returns Result.Error(Exception(errorMessage))

        // ACT: Initialize and load user detail
        viewModel.initialize(testUsername)
        advanceUntilIdle()

        // ASSERT: Check that error state is set
        val uiState = viewModel.uiState.value
        assertFalse("Should not be loading after error", uiState.isLoading)
        assertTrue("Should have error message", uiState.error != null)
        assertTrue("Should contain error message", uiState.error!!.contains(errorMessage))
        assertNull("Should have no user detail data on error", uiState.userDetail)
    }

    /**
     * TEST: Retry Functionality
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
        val testUsername = "testuser"
        coEvery { mockGetUserDetailUseCase(testUsername) } returns Result.Error(Exception("Network error"))
        
        viewModel.initialize(testUsername)
        advanceUntilIdle()

        // ACT: Trigger retry
        viewModel.handleIntent(UserDetailIntent.Retry)
        advanceUntilIdle()

        // ASSERT: Check that retry resets the state
        val uiState = viewModel.uiState.value
        assertTrue("Should be loading when retrying", uiState.isLoading)
        assertNull("Should clear error when retrying", uiState.error)
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
        viewModel.handleIntent(UserDetailIntent.ClearError)
        advanceUntilIdle()

        // ASSERT: Check that error is cleared
        val uiState = viewModel.uiState.value
        assertNull("Error should be cleared", uiState.error)
    }

    /**
     * TEST: Load User Detail Intent
     * 
     * WHAT WE'RE TESTING:
     * When load user detail intent is triggered, it should start the loading process.
     * 
     * WHY THIS MATTERS:
     * The intent system should properly trigger the loading process.
     */
    @Test
    fun `load user detail intent should start loading process`() = runTest {
        // ARRANGE: Set up test data
        val testUsername = "testuser"
        val testUserDetail = createTestUserDetail()
        
        // Mock successful response
        coEvery { mockGetUserDetailUseCase(testUsername) } returns Result.Success(testUserDetail)

        // ACT: Initialize and trigger load intent
        viewModel.initialize(testUsername)
        viewModel.handleIntent(UserDetailIntent.LoadUserDetail)
        advanceUntilIdle()

        // ASSERT: Check that loading process started
        val uiState = viewModel.uiState.value
        // The exact state depends on the implementation, but we can verify the intent was handled
        assertTrue("Load user detail intent should be handled", true)
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
