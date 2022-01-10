package com.acash.aris.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.acash.aris.R
import com.acash.aris.adapters.PostEntityAdapter
import com.acash.aris.database.SavedPostsDB
import com.acash.aris.databinding.FragmentSavedPostsBinding
import com.acash.aris.models.PostEntity
import com.acash.aris.viewmodels.PostsViewModel
import com.acash.aris.viewmodels.PostsViewModelFactory

class SavedPostsFragment : Fragment() {

    private val db by lazy {
        SavedPostsDB.getDatabase(requireContext())
    }

    private val postsViewModel: PostsViewModel by activityViewModels {
        PostsViewModelFactory(
            db.savedPostsDao()
        )
    }

    private var _binding: FragmentSavedPostsBinding? = null
    private val binding get() = _binding!!
    private val listSavedPosts = mutableListOf<PostEntity>()
    private val postEntityAdapter = PostEntityAdapter(listSavedPosts)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSavedPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            postEntityAdapter.onClick = { postEntity ->
                postsViewModel.removeSavedPost(postEntity)
            }

            rvSavedPosts.adapter = postEntityAdapter
            rvSavedPosts.layoutManager = LinearLayoutManager(requireContext())

            postsViewModel.getAllSavedPosts().asLiveData().observe(viewLifecycleOwner, {
                listSavedPosts.clear()
                listSavedPosts.addAll(it)
                postEntityAdapter.notifyDataSetChanged()

                if(it.isEmpty())
                    Toast.makeText(requireContext(),"Nothing to show",Toast.LENGTH_SHORT).show()
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}