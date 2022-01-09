package com.acash.aris.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Post(
    val postId: String,
    val orgName: String,
    val postImgUrl: String,
    val orgDpUrl: String,
    val postDescription: String,
    @ServerTimestamp
    val createdDate: Date?=null
) {
    constructor() : this("", "", "", "", "",null)
}