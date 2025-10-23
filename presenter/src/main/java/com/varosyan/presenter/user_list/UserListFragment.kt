package com.varosyan.presenter.user_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.varosyan.presenter.R
import com.varosyan.presenter.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private val viewModel: UserListViewModel by viewModels ()
    private lateinit var viewBinding: FragmentUserListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentUserListBinding.bind(view)
        val adapter = UserAdapter { user ->
            val action = UserListFragmentDirections
                .actionListToDetails(user.userName)
            findNavController().navigate(action)
        }

        val recyclerView = viewBinding.usersRecyclerView
        recyclerView.adapter = adapter.withLoadStateFooter(UserLoadStateAdapter { adapter.retry() })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        adapter.addLoadStateListener { loadState ->
            viewBinding.progressBar.isVisible = loadState.refresh is LoadState.Loading

            val refreshError = loadState.refresh as? LoadState.Error
            viewBinding.errorText.isVisible = refreshError != null
            refreshError?.let {
                viewBinding.errorText.text = when (it.error) {
                    is IOException -> "No internet connection"
                    else -> "An error occurred"
                }
            }

            viewBinding.usersRecyclerView.isVisible =
                loadState.refresh is LoadState.NotLoading && adapter.itemCount > 0

            viewBinding.loadMoreProgress.isVisible = loadState.append is LoadState.Loading

            val appendError = loadState.append as? LoadState.Error
            appendError?.let {
                Toast.makeText(
                    context,
                    "Error loading more: ${it.error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Collect and submit data
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}