package com.varosyan.presenter.user_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.varosyan.presenter.R
import com.varosyan.presenter.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
    private val args: UserDetailFragmentArgs by navArgs()
    private val viewModel: UserDetailViewModel by viewModels()
    private lateinit var viewBinding: FragmentUserDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.username = args.userId



        viewBinding = FragmentUserDetailBinding.bind(view)
        (requireActivity() as AppCompatActivity).setSupportActionBar(viewBinding.toolbar)
        (requireActivity() as AppCompatActivity)
            .supportActionBar
            ?.setDisplayHomeAsUpEnabled(true)


        viewBinding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState.collect { state ->
                when (state) {
                    is DetailScreenState.Error -> showErrorMessage(message = state.message)
                    DetailScreenState.Loading -> showLoading()
                    DetailScreenState.Success -> hideLoading()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userDetail.collect { detail ->
                viewBinding.apply {
                    locationTextView.text = detail.location ?: NO_INFO
                    followersTextView.text = detail.followersCount.toString()
                    followingTextView.text = detail.followingCount.toString()
                    bioTextView.text = detail.bio ?: NO_INFO
                    nameTextView.text = detail.fullName ?: detail.userName
                    reposCountTextView.text = detail.repoCount.toString()
                    gistsCountTextView.text = detail.gistCount.toString()
                    detail.lastUpdated?.let {
                        val instant = Instant.parse(it)
                        val formatter = DateTimeFormatter
                            .ofPattern(DATE_FORMAT)
                            .withZone(ZoneId.systemDefault())
                        formatter.format(instant)
                        updatedTextView.text = formatter.format(instant)
                    } ?: run { updatedTextView.text = NO_INFO }
                    avatarImageView.load(detail.avatar) {
                        placeholder(R.drawable.account_circle)
                        crossfade(true)
                        scale(Scale.FILL)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        viewBinding.contentContainer.visibility = View.GONE
        viewBinding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        viewBinding.contentContainer.visibility = View.VISIBLE
        viewBinding.progressCircular.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        viewBinding.progressCircular.visibility = View.GONE
        viewBinding.contentContainer.visibility = View.GONE
        viewBinding.textError.visibility = View.VISIBLE
        viewBinding.textError.text = message
    }

    companion object {
        private const val NO_INFO = "No Info"
        private const val DATE_FORMAT = "dd/MM/yyyy"
    }
}