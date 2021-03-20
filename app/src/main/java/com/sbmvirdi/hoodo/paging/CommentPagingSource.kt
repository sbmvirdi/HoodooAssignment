package com.sbmvirdi.hoodo.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sbmvirdi.hoodo.modelClasses.comment.CommentData
import com.sbmvirdi.hoodo.network.PostApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val STARTING_PAGE_INDEX = 0;

class CommentPagingSource(
    private val postApi: PostApi,
    private val postId: String
) : PagingSource<Int, CommentData>() {
    override fun getRefreshKey(state: PagingState<Int, CommentData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentData> {
        Log.d("CommentPagingSource", "load: called")
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = postApi.getCommentOfPost(postId, params.loadSize, position).executeFunc()

            Log.d("CommentPagingSource", "load: ${response.body().toString()}")

            Log.d("CommentPagingSource", "load: $position")

            val nextPage =
                if (response.body()?.data?.size ?: 0 < params.loadSize) null else position + 1

            LoadResult.Page(
                data = response.body()?.data!!,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(Exception("Failed to load results"))
        }
    }

    private suspend fun <T> Call<T>.executeFunc() = suspendCoroutine<Response<T>> { cont ->
        run {
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d("CommentPagingSource", "onFailure: ${t.message}")
                    cont.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Log.d("CommentPagingSource", "onResponse: ${response.body()}")
                    cont.resume(response)
                }

            })
        }
    }
}