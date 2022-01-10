package com.acash.aris.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acash.aris.R
import com.acash.aris.databinding.ListItemPostBinding
import com.acash.aris.models.PostEntity
import com.acash.aris.utils.getTimeAgo
import com.bumptech.glide.Glide
import java.util.*

class PostEntityAdapter(private val listSavedPosts: MutableList<PostEntity>) :
    RecyclerView.Adapter<PostEntityAdapter.PostEntityViewHolder>() {

    var onClick: ((postEntity: PostEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostEntityViewHolder =
        PostEntityViewHolder(
            ListItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PostEntityViewHolder, position: Int) {
        val postEntity = listSavedPosts[position]

        holder.binding.apply {
            tvOrgName.text = postEntity.orgName
            tvPostDescription.text = postEntity.postDescription
            tvTime.text = Date(postEntity.createdDate).getTimeAgo()
            btnSaveToggle.isSelected = true

            btnSaveToggle.setOnClickListener {
                btnSaveToggle.isSelected = false
                onClick?.invoke(postEntity)
            }

            Glide.with(orgDp).load(postEntity.orgDpUrl)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(orgDp)

            Glide.with(postImg).load(postEntity.postImgUrl)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(postImg)
        }
    }

    override fun getItemCount() = listSavedPosts.size

    class PostEntityViewHolder(val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root)
}