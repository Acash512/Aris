package com.acash.aris.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acash.aris.R
import com.acash.aris.databinding.ListItemPostBinding
import com.acash.aris.models.Post
import com.acash.aris.utils.getTimeAgo
import com.bumptech.glide.Glide

class PostsAdapter(
    private val listPosts: MutableList<Post>,
    private val setSavedPostIds: MutableSet<String>
) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    var onClick: ((post: Post, insert: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder =
        PostsViewHolder(
            ListItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = listPosts[position]
        holder.binding.apply {
            tvOrgName.text = post.orgName
            tvPostDescription.text = post.postDescription
            tvTime.text = post.createdDate.toDate().getTimeAgo()
            btnSaveToggle.isSelected = setSavedPostIds.contains(post.postId)

            btnSaveToggle.setOnClickListener {
                if (btnSaveToggle.isSelected) {
                    btnSaveToggle.isSelected = false
                    onClick?.invoke(post, false)
                } else {
                    btnSaveToggle.isSelected = true
                    onClick?.invoke(post, true)
                }
            }

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

    override fun getItemCount(): Int = listPosts.size

    class PostsViewHolder(val binding: ListItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

}