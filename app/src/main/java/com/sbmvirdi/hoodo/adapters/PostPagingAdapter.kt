package com.sbmvirdi.hoodo.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sbmvirdi.hoodo.databinding.PostRowBinding
import com.sbmvirdi.hoodo.modelClasses.post.PostData
import com.sbmvirdi.hoodo.ui.activities.CommentActivity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class PostPagingAdapter(_context: Context) :
    PagingDataAdapter<PostData, PostPagingAdapter.PostViewHolder>(callBack) {

    private val context: Context = _context
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostRowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    class PostViewHolder(view: PostRowBinding) : RecyclerView.ViewHolder(view.root) {
        private val binding: PostRowBinding = view
        private var postDataModel: PostData? = null

        init {
            view.root.setOnClickListener {
                Log.d("PostPagingAdapter", " ${postDataModel?.id}")
                val intent = Intent(itemView.context, CommentActivity::class.java)
                intent.putExtra("postId", postDataModel?.id)
                itemView.context.startActivity(intent)
            }
        }

        fun bind(postData: PostData?) {
            // copying instance
            postDataModel = postData

            // setting up attributes
            Glide.with(itemView).load(postData?.owner?.picture).into(binding.userProfile)
            val name = postData?.owner?.firstName + postData?.owner?.lastName
            binding.userName.text = name
            binding.userEmail.text = postData?.owner?.email
            Glide.with(itemView).load(postData?.image).into(binding.postImage)
            binding.postTags.setTags(postData?.tags)
            binding.postTitle.text = postData?.text
            if (postData?.link != null) {
                binding.postLink.visibility = View.VISIBLE
                binding.postLink.setLinkText(postData?.link)
            } else {
                binding.postLink.visibility = View.GONE
            }
            val likes = "${postData?.like} Likes"
            binding.postLikes.text = likes

            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            format.timeZone = TimeZone.getTimeZone("UTC")
            binding.postPublishDate.text = format.parse(postData?.publishDate!!)?.toString()
        }
    }

    companion object {

        val callBack = object : DiffUtil.ItemCallback<PostData>() {
            override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
                return true
            }

        }
    }

}
