package com.acash.aris.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.aris.R
import com.acash.aris.adapters.PostsAdapter
import com.acash.aris.data.UserPreferencesDataStore
import com.acash.aris.database.SavedPostsDB
import com.acash.aris.databinding.FragmentDailyFeedBinding
import com.acash.aris.models.Post
import com.acash.aris.models.PostEntity
import com.acash.aris.viewmodels.PostsViewModel
import com.acash.aris.viewmodels.PostsViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DailyFeedFragment : Fragment() {

    private val db by lazy {
        SavedPostsDB.getDatabase(requireContext())
    }

    private val postsViewModel: PostsViewModel by activityViewModels {
        PostsViewModelFactory(
            db.savedPostsDao()
        )
    }

    private var _binding: FragmentDailyFeedBinding? = null
    private val binding get() = _binding!!
    private val listPosts = mutableListOf<Post>()
    private val setSavedPostIds = mutableSetOf<String>()
    private val dailyFeedAdapter = PostsAdapter(listPosts, setSavedPostIds)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDailyFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            dailyFeedAdapter.onClick = { post, insert ->
                val postEntity = PostEntity(
                    post.postId,
                    post.orgName,
                    post.postImgUrl,
                    post.orgDpUrl,
                    post.postDescription,
                    post.createdDate.toDate().time
                )

                if (insert) {
                    postsViewModel.savePost(postEntity)
                } else {
                    postsViewModel.removeSavedPost(postEntity)
                }
            }

            rvDailyFeed.adapter = dailyFeedAdapter
            rvDailyFeed.layoutManager = LinearLayoutManager(requireContext())
            btnSavedPosts.setOnClickListener {
                findNavController().navigate(R.id.action_dailyFeedFragment_to_savedPostsFragment)
            }

            UserPreferencesDataStore(requireContext()).userName.asLiveData()
                .observe(viewLifecycleOwner, {
                    tvUserName.text = it
                })

            viewLifecycleOwner.lifecycleScope.launch {
                setSavedPostIds.clear()
                setSavedPostIds.addAll(postsViewModel.getSavedPostIds().first())
                postsViewModel.fetchNewsFeed()
            }

            postsViewModel.listPosts.observe(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    listPosts.clear()
                    listPosts.addAll(it)
                    dailyFeedAdapter.notifyDataSetChanged()
                }
            })

            postsViewModel.failureStatus.observe(viewLifecycleOwner, {
                if (it == true) {
                    Toast.makeText(
                        requireContext(),
                        postsViewModel.errorMessage.value,
                        Toast.LENGTH_SHORT
                    ).show()
                    postsViewModel.showedErrorToast()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}