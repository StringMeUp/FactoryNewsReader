package com.string.me.up.factorynewsreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.string.me.up.factorynewsreader.R
import com.string.me.up.factorynewsreader.adapters.ArticlesAdapter
import com.string.me.up.factorynewsreader.databinding.FragmentSingleArticleBinding
import com.string.me.up.factorynewsreader.news.data.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleArticleFragment : Fragment(R.layout.fragment_single_article) {

    private lateinit var binding: FragmentSingleArticleBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: SingleArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_single_article,
            container,
            false
        )
        setLifecycle(binding, sharedViewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = ArticlesAdapter(arrayListOf())
        binding.singleArticlePager.apply {
            adapter = pagerAdapter
        }
        sharedViewModel.newsList.observe(viewLifecycleOwner, { singleArticles ->
            singleArticles?.let {
                pagerAdapter.updateArticles(singleArticles as ArrayList<Article>)
                lifecycleScope.launch {
                    delay(10)
                    binding.singleArticlePager.currentItem = sharedViewModel.currentPosition!!
                }
            }
        })


//        sharedViewModel.currentPosition.observe(viewLifecycleOwner, { currentPosition ->
//            currentPosition?.let {
//                lifecycleScope.launch {
//                    delay(10)
//                    binding.singleArticlePager.currentItem = it
//                }
//            }
//        })
    }

    private fun setLifecycle(
        binding: FragmentSingleArticleBinding,
        sharedViewModel: SharedViewModel
    ) {
        binding.run {
            viewmodel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}