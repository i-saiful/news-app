package com.saiful.newsapp.ui.fragment.tab

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.saiful.newsapp.R
import com.saiful.newsapp.adapter.CardNewsAdapter
import com.saiful.newsapp.constant.Category
import com.saiful.newsapp.constant.Constant
import com.saiful.newsapp.database.NewsArticle
import com.saiful.newsapp.databinding.FragmentBusinessBinding
import com.saiful.newsapp.viewmodel.NewsViewModel

class BusinessFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        // Search menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView: SearchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
//                        viewModel.searchNewsBookmark(p0 ?: "")
                        return false
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onQueryTextChange(msg: String): Boolean {
//                        filter data
                        val queryResult = mutableListOf<NewsArticle>()
                        viewModel.readAllNews.value?.map {
                            if (it.title?.contains(msg, ignoreCase = true) == true) {
                                queryResult.add(it)
                            }
                        }
                        binding.cardNewsRecycler.adapter = CardNewsAdapter(queryResult, viewModel)
//                        Log.d("TAG", "onQueryTextChange: ${queryResult.size}")
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        Constant.category = Category.BUSINESS
        viewModel.readAllNewsFromLocal()

//        val adapterViewState = recyclerView.layoutManager?.onSaveInstanceState()
//        recyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recycler = binding.cardNewsRecycler
        recycler.setHasFixedSize(true)
        viewModel.readAllNews.observe(viewLifecycleOwner) {
//            Log.d("TAG", "onResume:  ${it.size}")
            if (it.isEmpty()) {
                viewModel.loadNewsFromRemote()
                recycler.adapter?.notifyDataSetChanged()
            }
            val adapterViewState = recycler.layoutManager?.onSaveInstanceState()
            recycler.layoutManager?.onRestoreInstanceState(adapterViewState)
            recycler.adapter = CardNewsAdapter(it, viewModel)
        }

        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.loadNewsFromRemote()
            recycler.adapter?.notifyDataSetChanged()
        }
    }
}