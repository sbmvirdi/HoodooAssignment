package com.sbmvirdi.hoodo.network

import com.sbmvirdi.hoodo.R
import com.sbmvirdi.hoodo.modelClasses.comment.Comment
import com.sbmvirdi.hoodo.modelClasses.post.Post
import com.sbmvirdi.hoodo.utils.Utilities
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PostApi {

    // GET method to get the list of posts
    @Headers("app-id:60558bda255ecd31c7d96961")
    @GET("post")
    fun getPosts(
        @Query(Utilities.QUERY_PARAM_LIMIT) limit: Int,
        @Query(Utilities.QUERY_PARAM_PAGE) page: Int
    ): Call<Post>

    // GET method to get the list of comments of a specific post
    @Headers("app-id:60558bda255ecd31c7d96961")
    @GET("post/{postId}/comment")
    fun getCommentOfPost(
        @Path(Utilities.PATH_PARAM_POST_ID) postId: String,
        @Query(Utilities.QUERY_PARAM_LIMIT) limit: Int,
        @Query(Utilities.QUERY_PARAM_PAGE) page: Int
    ): Call<Comment>

    // method to return the retrofit object
    companion object {
        fun create(): PostApi {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Utilities.BASE_URL).build()
            return retrofit.create(PostApi::class.java)
        }
    }
}