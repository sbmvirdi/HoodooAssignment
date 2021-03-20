package com.sbmvirdi.hoodo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sbmvirdi.hoodo.databinding.CommentRowBinding
import com.sbmvirdi.hoodo.databinding.PostRowBinding
import com.sbmvirdi.hoodo.modelClasses.comment.CommentData
import java.text.SimpleDateFormat
import java.util.*

class CommentPagingAdapter(_context: Context) :
    PagingDataAdapter<CommentData, CommentPagingAdapter.CommentViewHolder>(callBack) {

    private val context: Context = _context

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            CommentRowBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    class CommentViewHolder(view: CommentRowBinding) : RecyclerView.ViewHolder(view.root) {

        private val binding: CommentRowBinding = view

        fun bind(item: CommentData?) {

            // setting up attributes
            Glide.with(itemView).load(item?.owner?.picture).into(binding.userProfile)
            val name = item?.owner?.firstName + item?.owner?.lastName
            binding.userName.text = name

            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            format.timeZone = TimeZone.getTimeZone("UTC")
            binding.commentTime.text = format.parse(item?.publishDate!!)?.toString()

            binding.commentMessage.text = item.message
        }

    }

    companion object {

        val callBack = object : DiffUtil.ItemCallback<CommentData>() {
            override fun areItemsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommentData, newItem: CommentData): Boolean {
                return true
            }

        }
    }
}