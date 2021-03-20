package com.sbmvirdi.hoodo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbmvirdi.hoodo.R
import com.sbmvirdi.hoodo.adapters.CommentPagingAdapter
import com.sbmvirdi.hoodo.adapters.PostPagingAdapter
import com.sbmvirdi.hoodo.databinding.ActivityCommentBinding
import com.sbmvirdi.hoodo.databinding.ActivityMainBinding
import com.sbmvirdi.hoodo.viewModels.CommentActivityViewModel
import com.sbmvirdi.hoodo.viewModels.MainActivityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommentActivity : AppCompatActivity() {

    private lateinit var activityCommentBinding: ActivityCommentBinding
    private lateinit var activityCommentViewModel: CommentActivityViewModel
    private var commentJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCommentBinding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(activityCommentBinding.root)


        // getting the post id of the clicked post
        val postId: String? = intent.extras?.getString("postId", null)

        // settting the layout manager
        activityCommentBinding.commentsRecycler.layoutManager = LinearLayoutManager(this)

        // initializing the view model
        activityCommentViewModel = ViewModelProvider(this, CommentActivityViewModelFactory())
            .get(CommentActivityViewModel::class.java)

        // cancelling the job and initiating the new one
        commentJob?.cancel()
        commentJob = lifecycleScope.launch {
            // sending data to the adapter
            activityCommentViewModel.getLiveComments(postId!!).collect {
                val commentPagingAdapter = CommentPagingAdapter(this@CommentActivity)
                activityCommentBinding.commentsRecycler.adapter = commentPagingAdapter
                commentPagingAdapter.submitData(it)
            }
        }


    }

    class CommentActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CommentActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CommentActivityViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}