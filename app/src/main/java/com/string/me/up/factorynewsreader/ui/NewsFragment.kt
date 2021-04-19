package com.string.me.up.factorynewsreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.adapters.NewsAdapter
import com.string.me.up.factorynewsreader.adapters.OnItemClickListener
import com.string.me.up.factorynewsreader.databinding.FragmentNewsBinding
import com.string.me.up.factorynewsreader.news.data.Article
import com.string.me.up.factorynewsreader.util.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news), OnItemClickListener {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_news,
            container,
            false
        )
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        setLifecycle(binding, sharedViewModel)
        sharedViewModel.fetchNewsData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articlesAdapter = NewsAdapter(this@NewsFragment).also {
            binding.newsRecyclerView.adapter = it
            binding.newsRecyclerView.setHasFixedSize(true)
            binding.newsRecyclerView.itemAnimator = null
        }

        sharedViewModel.error.observe(viewLifecycleOwner, { errorMessage ->
            errorMessage?.let {
                Helper.displayDialog(
                    it,
                    requireContext()
                )
            }
        })

        sharedViewModel.newsList.observe(viewLifecycleOwner, { newsList ->
            newsList?.let { articlesAdapter.newsList = it as ArrayList<Article> }
                .also { articlesAdapter.notifyDataSetChanged() }
        })
    }

    override fun onItemClicked(currentPosition: Int, currentTitle: String) {
        NewsFragmentDirections.actionNewsFragmentToSingleArticleFragment(
            currentPosition,
            currentTitle
        ).also { findNavController().navigate(it) }
    }

    private fun setLifecycle(
        binding: FragmentNewsBinding,
        sharedViewModel: SharedViewModel
    ) {
        binding.run {
            viewmodel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}