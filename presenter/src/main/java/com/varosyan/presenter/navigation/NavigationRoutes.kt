package com.varosyan.presenter.navigation

/**
 * Navigation routes for the app
 */
object NavigationRoutes {
    const val USER_LIST = "user_list"
    const val USER_DETAIL = "user_detail/{userId}"
    
    fun userDetail(userId: String) = "user_detail/$userId"
}

