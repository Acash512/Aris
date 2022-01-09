package com.acash.aris.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acash.aris.models.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class DailyFeedViewModel: ViewModel() {
    private val _listPosts = MutableLiveData<MutableList<Post>>()
    val listPosts:LiveData<MutableList<Post>> = _listPosts

    private val _failureStatus = MutableLiveData(false)
    val failureStatus:LiveData<Boolean> = _failureStatus

    private val _errorMessage = MutableLiveData("")
    val errorMessage:LiveData<String> = _errorMessage

    fun fetchNewsFeed(){
        FirebaseFirestore.getInstance()
            .collection("DailyFeed")
            .orderBy("createdDate", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {queryDocumentSnapshots->
                if(!queryDocumentSnapshots.isEmpty){
                    _listPosts.value = queryDocumentSnapshots.toObjects(Post::class.java)
                }
            }
            .addOnFailureListener {
                _errorMessage.value = it.message.toString()
                _failureStatus.value = true
            }
    }

    fun showedErrorToast(){
        _failureStatus.value = false
    }
}