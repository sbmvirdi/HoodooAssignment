package com.sbmvirdi.hoodo.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sbmvirdi.hoodo.R
import com.sbmvirdi.hoodo.adapters.PostPagingAdapter
import com.sbmvirdi.hoodo.databinding.ActivityMainBinding
import com.sbmvirdi.hoodo.viewModels.MainActivityViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var postPagingDataAdapter: PostPagingAdapter? = null
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var postJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        // setting up layout manager
        activityMainBinding.postsRecycler.layoutManager = LinearLayoutManager(this)

        // initializing view model
        mainActivityViewModel = ViewModelProvider(this, MainActivityViewModelFactory())
            .get(MainActivityViewModel::class.java)


        // initiating the coroutine job and cancelling old one
        postJob?.cancel()
        postJob = lifecycleScope.launch {
            mainActivityViewModel.getLivePosts().collect {
                // sending data to adapter
                postPagingDataAdapter = PostPagingAdapter(this@MainActivity)
                activityMainBinding.postsRecycler.adapter = postPagingDataAdapter
                postPagingDataAdapter?.submitData(it)
            }
        }

    }

    class MainActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}