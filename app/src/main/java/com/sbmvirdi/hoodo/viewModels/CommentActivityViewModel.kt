package com.sbmvirdi.hoodo.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sbmvirdi.hoodo.modelClasses.comment.CommentData
import com.sbmvirdi.hoodo.modelClasses.post.PostData
import com.sbmvirdi.hoodo.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class CommentActivityViewModel : ViewModel() {

    private val postsRepository: PostsRepository = PostsRepository()
    private var currentCommentResult: Flow<PagingData<CommentData>>? = null

    /**
     * function to get comments of a specific post
     */
    fun getLiveComments(postId: String): Flow<PagingData<CommentData>> {
        Log.d("MainActivityViewModel", "getLivePosts: called")
        val newResult: Flow<PagingData<CommentData>> = postsRepository.getComments(postId)
            .cachedIn(viewModelScope)
        currentCommentResult = newResult
        return newResult
    }

}