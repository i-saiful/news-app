package com.saiful.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saiful.newsapp.R
import com.saiful.newsapp.constant.Constant
import com.saiful.newsapp.databinding.FragmentNewsArticleBinding

class NewsArticleFragment : Fragment() {
    private var _binding: FragmentNewsArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Log.d("TAG", "onViewCreated: ${Global.newsArticle?.url}")
        binding.goToWeb.setOnClickListener {
            val url = Constant.newsArticle?.url ?: "https://www.google.com"
            val action = NewsArticleFragmentDirections.actionNewsArticleToWebView(url)
            findNavController().navigate(action)
        }

        binding.newsTitle.text = Constant.newsArticle?.title ?: "UnTitle"
        binding.newsAuthor.text = getString(R.string.author_name, Constant.newsArticle?.author)
        binding.newsPublishedDate.text =
            Constant.newsArticle?.publishedAt?.substring(0, 10) ?: "----:--:--"
        binding.newsDescription.text =
            Constant.newsArticle?.description ?: Constant.newsArticle?.content ?: "----"
        binding.newsSource.text = getString(R.string.source_name, Constant.newsArticle?.sourceName)

        Glide
            .with(requireContext())
            .load(Constant.newsArticle?.urlToImage)
            .centerCrop()
            .thumbnail(
                Glide.with(requireContext())
                    .load(R.drawable.search_thumbnail)
            )
            .into(binding.coverImg)

//        Bottom navigation hide
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
    }
}