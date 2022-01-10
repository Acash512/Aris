package com.acash.aris.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.acash.aris.models.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPostsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post:PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)

    @Query("Select * from PostEntity")
    fun fetchAllSavedPosts(): Flow<List<PostEntity>>

    @Query("Select postId from PostEntity")
    fun fetchSavedPostsIds():Flow<List<String>>

}