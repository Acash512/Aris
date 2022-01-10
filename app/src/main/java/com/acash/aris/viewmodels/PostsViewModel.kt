package com.acash.aris.viewmodels

import androidx.lifecycle.*
import com.acash.aris.database.SavedPostsDao
import com.acash.aris.models.Post
import com.acash.aris.models.PostEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class PostsViewModel(private val postsDao:SavedPostsDao): ViewModel() {
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

    fun savePost(postEntity: PostEntity) {
        viewModelScope.launch {
            postsDao.insertPost(postEntity)
        }
    }

    fun removeSavedPost(postEntity: PostEntity){
        viewModelScope.launch {
            postsDao.deletePost(postEntity)
        }
    }

    fun getSavedPostIds() = postsDao.fetchSavedPostsIds()

    fun getAllSavedPosts() = postsDao.fetchAllSavedPosts()
}

class PostsViewModelFactory(private val postsDao: SavedPostsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel(postsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}