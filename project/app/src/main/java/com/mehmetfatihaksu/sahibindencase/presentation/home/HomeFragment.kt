package com.mehmetfatihaksu.sahibindencase.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehmetfatihaksu.sahibindencase.R
import com.mehmetfatihaksu.sahibindencase.common.Constants
import com.mehmetfatihaksu.sahibindencase.databinding.HomeFragmentBinding
import com.mehmetfatihaksu.sahibindencase.presentation.home.adapter.QuestionAdapter
import com.mehmetfatihaksu.sahibindencase.presentation.home.listener.QuestionItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class HomeFragment : Fragment(), QuestionItemClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding : HomeFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var questionAdapter : QuestionAdapter
    private lateinit var uiStateJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setQuestionAdapter()
        addLoadStateListener()
        uiStateJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getQuestions(requireContext() , 1, 25).collectLatest {
                questionAdapter.submitData(viewLifecycleOwner.lifecycle , it)
                binding?.recyclerViewQuestions?.smoothScrollToPosition(0)
            }
        }
    }

    private fun initRecyclerView(){
        binding?.recyclerViewQuestions?.clearFocus()
        binding?.recyclerViewQuestions?.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL, false)
        binding?.recyclerViewQuestions?.itemAnimator = DefaultItemAnimator()
    }

    private fun addLoadStateListener(){
        questionAdapter.addLoadStateListener { loadState ->
            binding?.recyclerViewQuestions?.isVisible = loadState.refresh is LoadState.NotLoading
            binding?.progressBar?.isVisible = loadState.refresh is LoadState.Loading
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
            errorState?.let {
                Toast.makeText(requireContext() , it.error.message , Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setQuestionAdapter(){
        questionAdapter = QuestionAdapter(requireContext(), this)
        binding?.recyclerViewQuestions?.adapter = questionAdapter
    }

    override fun onItemClick(Id: Int) {
        val bundle = Bundle()
        bundle.putInt(Constants.ID, Id)
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment , bundle)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}