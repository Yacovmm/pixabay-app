package com.example.pixabayapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabayapp.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    lateinit var imagesAdapter: ImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()

        setupObservers()

        viewModel.getData()

    }

    private fun setupObservers() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) { state ->

            when(state.loading) {
                true -> binding.progress.visibility = View.VISIBLE
                else -> binding.progress.visibility = View.GONE
            }

            if (state.error.not() &&  state.data != null) {
                binding.progress.visibility = View.GONE
                imagesAdapter.setList(state.data)
            }

        }
    }

    private fun setupRV() {
        imagesAdapter = ImagesAdapter()
        binding.recyclerview.adapter = imagesAdapter
        binding.recyclerview.addOnScrollListener(scrollLister)
    }

    private val scrollLister = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            (recyclerView.layoutManager as? LinearLayoutManager)?.let { linearLayout ->
                if (dy > 0 && viewModel.isPaginating.not() && viewModel.noMoreItems.not()) { // Check to scrool down
                    val visibleItemCount = linearLayout.childCount // Items visible in the recycler
                    val firstVisibleItem = linearLayout.findFirstCompletelyVisibleItemPosition() // Position of the first visible Item
                    val totalItemCount = imagesAdapter.itemCount // Items total of the adapter

                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        viewModel.getData()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}