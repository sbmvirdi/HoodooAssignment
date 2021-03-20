package com.sbmvirdi.hoodo.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sbmvirdi.hoodo.modelClasses.post.PostData
import com.sbmvirdi.hoodo.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel : ViewModel() {
    private val postsRepository: PostsRepository = PostsRepository()
    private var currentPostResult: Flow<PagingData<PostData>>? = null

    /**
     * function to get all posts as paginated data as a flow
     */
    fun getLivePosts(): Flow<PagingData<PostData>> {
        Log.d("MainActivityViewModel", "getLivePosts: called")
        val newResult: Flow<PagingData<PostData>> = postsRepository.getPosts()
            .cachedIn(viewModelScope)
        currentPostResult = newResult
        return newResult
    }

}