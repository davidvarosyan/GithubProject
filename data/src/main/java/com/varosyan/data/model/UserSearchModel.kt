package com.varosyan.data.model

data class UserSearchModel(
    val totalItemCount: Int,
    val incompleteResult: Boolean,
    val items: List<UserModel>
) {
}