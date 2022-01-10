package com.acash.aris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @ColumnInfo(name = "postId")
    @PrimaryKey
    var postId: String,
    var orgName: String,
    var postImgUrl: String,
    var orgDpUrl: String,
    var postDescription: String,
    var createdDate: Long
)