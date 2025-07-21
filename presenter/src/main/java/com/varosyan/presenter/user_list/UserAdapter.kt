package com.varosyan.presenter.user_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.varosyan.domain.model.User
import com.varosyan.presenter.databinding.ItemUserBinding

class UserAdapter(
    private val onClick: (User) -> Unit
) : PagingDataAdapter<User, UserAdapter.UserViewHolder>(USER_DIFF) {

    companion object {
        private val USER_DIFF = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(old: User, new: User) = old.id == new.id
            override fun areContentsTheSame(old: User, new: User) = old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val avatar: ImageView = binding.avatarImageView
        private val login: TextView = binding.usernameTextView
        private val idView: TextView = binding.idTextView

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            login.text = user.userName
            idView.text = "ID: ${user.id}"
            Glide.with(avatar).load(user.avatar).circleCrop().into(avatar)
            binding.arrowImageView.setOnClickListener { onClick(user) }
        }
    }
}
