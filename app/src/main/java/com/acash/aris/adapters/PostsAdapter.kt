package com.acash.aris.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acash.aris.R
import com.acash.aris.databinding.ListItemPostBinding
import com.acash.aris.models.Post
import com.bumptech.glide.Glide

class PostsAdapter(private val listPosts:MutableList<Post>) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder =
        PostsViewHolder(
            ListItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(listPosts[position])
    }

    override fun getItemCount(): Int = listPosts.size

    class PostsViewHolder(private val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(post:Post){
            binding.apply {
                tvOrgName.text = post.orgName
                tvPostDescription.text = post.postDescription

                Glide.with(orgDp).load(post.orgDpUrl)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(orgDp)

                Glide.with(postImg).load(post.postImgUrl)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(postImg)
            }
        }
    }

}