package com.mehmetfatihaksu.sahibindencase.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mehmetfatihaksu.sahibindencase.common.Constants
import com.mehmetfatihaksu.sahibindencase.common.DateTimeConverter
import com.mehmetfatihaksu.sahibindencase.common.MaterialDialogHelper
import com.mehmetfatihaksu.sahibindencase.databinding.DetailFragmentBinding
import com.mehmetfatihaksu.sahibindencase.domain.model.response.detail.QuestionDetailResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var questionId : Int = 0
    private lateinit var uiStateJob : Job
    private var _binding : DetailFragmentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater , container , false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionId = arguments?.getInt(Constants.ID)!!
        initCollect()
        viewModel.getQuestionDetail(questionId)

    }

    private fun initCollect() {
        uiStateJob = lifecycleScope.launch {
            viewModel.detailUIState.collect {
                when(it){
                    is DetailViewModel.ViewState.ShowLoading->{
                        binding?.progressBar?.isVisible = it.isShow
                    }
                    is DetailViewModel.ViewState.ShowErrorMessage->{
                        MaterialDialogHelper().showDialog(requireContext() , it.message)
                    }
                    is DetailViewModel.ViewState.QuestionLoaded->{
                        if (it.response.items.isEmpty()){
                            return@collect
                        }
                        bindQuestion(it.response)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun bindQuestion(question: QuestionDetailResponse) {
        Glide.with(requireContext())
            .load(question.items.first().owner.profile_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .centerCrop()
            .into(binding?.imageViewProfile!!)

        binding?.textViewUserName?.text = question.items.first().owner.display_name
        binding?.textViewReputation?.text = question.items.first().owner.reputation.toString()
        binding?.textViewUserProfile?.text = question.items.first().owner.link
        binding?.textViewEyeCount?.text = question.items.first().view_count.toString()
        binding?.textViewAnswerCount?.text = question.items.first().answer_count.toString()
        binding?.textViewScore?.text = question.items.first().score.toString()
        binding?.textViewCreatedDate?.text = DateTimeConverter.getDateTime(question.items.first().creation_date, Constants.DATE_FORMAT)
        binding?.textViewQuestionTitle?.text = question.items.first().title
        binding?.webView?.loadUrl(question.items.first().link)
    }
}