package com.example.messenger.ui.feed

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FeedFragment : Fragment() {

    private val tag = "FeedFragment"
    private val viewModel: FeedViewModel by activityViewModels()
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(tag, "onCreateView")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated")

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val fabRefresh = view.findViewById<FloatingActionButton>(R.id.fab_refresh)

        val adapter = MessageAdapter { message -> viewModel.toggleLike(message) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.statusMessage.observe(viewLifecycleOwner) { msg ->
            tvStatus.visibility = if (msg != null) View.VISIBLE else View.GONE
            tvStatus.text = msg
        }

        fabRefresh.setOnClickListener {
            viewModel.refresh()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.refresh()
            }
        }
        requireContext().getSystemService(ConnectivityManager::class.java)
            .registerDefaultNetworkCallback(networkCallback)
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
        requireContext().getSystemService(ConnectivityManager::class.java)
            .unregisterNetworkCallback(networkCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tag, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }
}
