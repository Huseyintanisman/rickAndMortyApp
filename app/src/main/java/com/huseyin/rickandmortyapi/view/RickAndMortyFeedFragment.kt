package com.huseyin.rickandmortyapi.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.huseyin.rickandmortyapi.R
import com.huseyin.rickandmortyapi.adapter.rickAndMortyAdapter
import com.huseyin.rickandmortyapi.viewModel.FeedViewModel


class RickAndMortyFeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private lateinit var rickAndMortyError: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rickAndMortyList: RecyclerView
    private lateinit var rickAndMortyLoadingProgressBar: ProgressBar
    private var rickAndMortyAdapter = rickAndMortyAdapter(arrayListOf())

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rick_and_morty_feed, container, false)

        rickAndMortyError = view.findViewById(R.id.rickAndMortyError)
        rickAndMortyList = view.findViewById(R.id.rickAndMortyList)
        rickAndMortyLoadingProgressBar = view.findViewById(R.id.rickAndMortyLoadingProgressBar)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()
        rickAndMortyList.layoutManager = LinearLayoutManager(context)
        rickAndMortyList.adapter = rickAndMortyAdapter



        swipeRefreshLayout.setOnRefreshListener {
            rickAndMortyList.visibility = View.GONE
            rickAndMortyError.visibility = View.GONE
            rickAndMortyLoadingProgressBar.visibility = View.GONE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.rickAndMorties.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                rickAndMortyList.visibility = View.VISIBLE
                rickAndMortyAdapter.updateRickAndMortyList(it) // Updated method parameter
            }
        })

        viewModel.rickAndMortyError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    rickAndMortyError.visibility = View.VISIBLE
                } else {
                    rickAndMortyError.visibility = View.GONE
                }
            }
        })

        viewModel.rickAndMortyLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    rickAndMortyLoadingProgressBar.visibility = View.VISIBLE
                    rickAndMortyError.visibility = View.GONE
                    rickAndMortyList.visibility = View.VISIBLE
                } else {
                    rickAndMortyLoadingProgressBar.visibility = View.GONE
                }
            }
        })
    }
}

