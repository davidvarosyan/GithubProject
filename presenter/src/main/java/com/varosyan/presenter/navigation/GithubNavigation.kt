package com.varosyan.presenter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varosyan.presenter.user_detail.UserDetailScreen
import com.varosyan.presenter.user_list.UserListScreen

@Composable
fun GithubNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.USER_LIST
    ) {
        composable(NavigationRoutes.USER_LIST) {
            UserListScreen(
                onNavigateToUserDetail = { userName ->
                    navController.navigate(NavigationRoutes.userDetail(userName))
                }
            )
        }
        
        composable(NavigationRoutes.USER_DETAIL) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserDetailScreen(
                username = userId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

