package com.sbmvirdi.hoodo.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sbmvirdi.hoodo.modelClasses.comment.CommentData
import com.sbmvirdi.hoodo.modelClasses.post.PostData
import com.sbmvirdi.hoodo.network.PostApi
import com.sbmvirdi.hoodo.paging.CommentPagingSource
import com.sbmvirdi.hoodo.paging.PostPagingSource
import kotlinx.coroutines.flow.Flow

class PostsRepository {

    companion object {
        // page size to be loaded
        private const val NETWORK_PAGE_SIZE = 20
    }

    /**
     * function to get all the posts
     */
    fun getPosts(): Flow<PagingData<PostData>> {
        Log.d("PostsRepository", "getPosts: called")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource(PostApi.create()) }
        ).flow
    }

    /**
     * function to get all comments of a post specified by postId
     */
    fun getComments(postId: String): Flow<PagingData<CommentData>> {
        Log.d("PostsRepository", "getComments: called")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CommentPagingSource(PostApi.create(), postId) }
        ).flow
    }


}