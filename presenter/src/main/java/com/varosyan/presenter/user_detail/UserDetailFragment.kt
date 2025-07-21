package com.varosyan.presenter.user_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.varosyan.presenter.R
import com.varosyan.presenter.databinding.FragmentUserDetailBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
    private val args: UserDetailFragmentArgs by navArgs()
    private val viewModel: UserDetailViewModel by viewModel { parametersOf(args.userId) }
    private lateinit var viewBinding: FragmentUserDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewBinding = FragmentUserDetailBinding.bind(view)
        (requireActivity() as AppCompatActivity).setSupportActionBar(viewBinding.toolbar)
        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayHomeAsUpEnabled(true)


        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userDetail.collect { detail ->
                viewBinding.apply {
                    locationTextView.text = detail.location ?: "No Info"
                    followersTextView.text = detail.followersCount.toString()
                    followingTextView.text = detail.followingCount.toString()
                    bioTextView.text = detail.bio ?: "No Info"
                    nameTextView.text = detail.fullName ?: detail.userName
                    reposCountTextView.text = detail.repoCount.toString()
                    gistsCountTextView.text = detail.gistCount.toString()
                    detail.lastUpdated?.let {
                        val instant = Instant.parse(it)
                        val formatter = DateTimeFormatter
                            .ofPattern("dd/MM/yyyy")
                            .withZone(ZoneId.systemDefault())
                        formatter.format(instant)
                        updatedTextView.text = formatter.format(instant)
                    } ?: run { updatedTextView.text = "No Info" }
                    Glide.with(avatarImageView)
                        .load(detail.avatar)
                        .placeholder(R.drawable.account_circle)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .circleCrop()
                        .into(avatarImageView)
                }
            }
        }
    }
}