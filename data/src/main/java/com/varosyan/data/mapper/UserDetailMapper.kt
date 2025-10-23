package com.varosyan.data.mapper

import com.varosyan.data.db.entity.UserDetailEntity
import com.varosyan.data.model.UserDetailModel
import com.varosyan.domain.model.UserDetail
import javax.inject.Inject

class UserDetailMapper @Inject constructor() {

    fun userDetailModelToUserDetail(userDetailModel: UserDetailModel): UserDetail {
        return UserDetail(
            id = userDetailModel.id,
            avatar = userDetailModel.avatar,
            userName = userDetailModel.username,
            fullName = userDetailModel.fullName,
            location = userDetailModel.location,
            followersCount = userDetailModel.followersCount,
            followingCount = userDetailModel.followingCount,
            bio = userDetailModel.bio,
            repoCount = userDetailModel.repoCount,
            gistCount = userDetailModel.gistCount,
            lastUpdated = userDetailModel.lastUpdated
        )
    }

    fun userDetailModelToUserDetailEntity(userDetailModel: UserDetailModel): UserDetailEntity {
        return UserDetailEntity(
            id = userDetailModel.id,
            avatar = userDetailModel.avatar,
            userName = userDetailModel.username,
            fullName = userDetailModel.fullName,
            location = userDetailModel.location,
            followersCount = userDetailModel.followersCount,
            followingCount = userDetailModel.followingCount,
            bio = userDetailModel.bio,
            repoCount = userDetailModel.repoCount,
            gistCount = userDetailModel.gistCount,
            lastUpdated = userDetailModel.lastUpdated,
        )
    }

    fun userDetailToUserDetailEntity(userDetailModel: UserDetail): UserDetailEntity {
        return UserDetailEntity(
            id = userDetailModel.id,
            avatar = userDetailModel.avatar,
            userName = userDetailModel.userName,
            fullName = userDetailModel.fullName,
            location = userDetailModel.location,
            followersCount = userDetailModel.followersCount,
            followingCount = userDetailModel.followingCount,
            bio = userDetailModel.bio,
            repoCount = userDetailModel.repoCount,
            gistCount = userDetailModel.gistCount,
            lastUpdated = userDetailModel.lastUpdated,
        )
    }


    fun userDetailEntityToUserDetail(userDetailModel: UserDetailEntity?): UserDetail {
        if (userDetailModel == null) throw RuntimeException("Date is not exist")
        return UserDetail(
            id = userDetailModel.id,
            avatar = userDetailModel.avatar,
            userName = userDetailModel.userName,
            fullName = userDetailModel.fullName,
            location = userDetailModel.location,
            followersCount = userDetailModel.followersCount,
            followingCount = userDetailModel.followingCount,
            bio = userDetailModel.bio,
            repoCount = userDetailModel.repoCount,
            gistCount = userDetailModel.gistCount,
            lastUpdated = userDetailModel.lastUpdated,
        )
    }
}