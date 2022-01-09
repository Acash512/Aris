package com.acash.aris.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.aris.R
import com.acash.aris.adapters.PostsAdapter
import com.acash.aris.data.UserPreferencesDataStore
import com.acash.aris.databinding.FragmentDailyFeedBinding
import com.acash.aris.models.Post
import com.acash.aris.viewmodels.DailyFeedViewModel

class DailyFeedFragment : Fragment() {

    private val dailyFeedViewModel: DailyFeedViewModel by viewModels()
    private var _binding: FragmentDailyFeedBinding? = null
    private val binding get() = _binding!!
    private val listPosts = mutableListOf<Post>()
    private val dailyFeedAdapter = PostsAdapter(listPosts)

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
            rvDailyFeed.adapter = dailyFeedAdapter
            rvDailyFeed.layoutManager = LinearLayoutManager(requireContext())
            btnSavedPosts.setOnClickListener {
                findNavController().navigate(R.id.action_dailyFeedFragment_to_savedPostsFragment)
            }

            UserPreferencesDataStore(requireContext()).userName.asLiveData()
                .observe(viewLifecycleOwner, {
                    tvUserName.text = it
                })

            dailyFeedViewModel.fetchNewsFeed()

            dailyFeedViewModel.listPosts.observe(viewLifecycleOwner, {
                if(it.isNotEmpty()) {
                    listPosts.clear()
                    listPosts.addAll(it)
                    dailyFeedAdapter.notifyDataSetChanged()
                }
            })

            dailyFeedViewModel.failureStatus.observe(viewLifecycleOwner, {
                if (it == true) {
                    Toast.makeText(
                        requireContext(),
                        dailyFeedViewModel.errorMessage.value,
                        Toast.LENGTH_SHORT
                    ).show()
                    dailyFeedViewModel.showedErrorToast()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}