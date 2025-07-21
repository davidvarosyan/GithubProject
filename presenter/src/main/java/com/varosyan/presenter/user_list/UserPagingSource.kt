package com.varosyan.presenter.user_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.varosyan.domain.model.User
import com.varosyan.domain.usecase.GetUsersUseCase

class UserPagingSource(
    private val getPage: GetUsersUseCase
) : PagingSource<Long, User>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, User> {
        val since = params.key ?: 0L

        return getPage(since).safeHandle<List<User>, LoadResult<Long, User>>(
            success = { users ->
                val nextKey = if (users.size < params.loadSize) null else users.last().id
                LoadResult.Page(
                    data = users, prevKey = null, nextKey = nextKey
                )
            },
            handleError = { exception -> LoadResult.Error(exception) })
    }

    override fun getRefreshKey(state: PagingState<Long, User>): Long? =
        state.anchorPosition?.let { pos ->
            state.closestItemToPosition(pos)?.id
        }
}