package com.acash.aris.models

import com.google.firebase.Timestamp
import java.util.*

class Post(
    val postId: String,
    val orgName: String,
    val postImgUrl: String,
    val orgDpUrl: String,
    val postDescription: String,
    val createdDate: Timestamp
) {
    constructor() : this("", "", "", "", "", Timestamp(Date()))
}