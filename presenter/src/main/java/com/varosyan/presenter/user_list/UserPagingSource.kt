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

        val users = getPage(since)
        val nextKey = if (users.size < params.loadSize) null else users.last().id
        return LoadResult.Page(
            data = users,
            prevKey = null,
            nextKey = nextKey
        )

    }

    override fun getRefreshKey(state: PagingState<Long, User>): Long? =
        state.anchorPosition?.let { pos ->
            state.closestItemToPosition(pos)?.id
        }
}