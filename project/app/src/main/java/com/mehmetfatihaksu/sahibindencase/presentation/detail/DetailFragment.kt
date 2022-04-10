package com.mehmetfatihaksu.sahibindencase.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.mehmetfatihaksu.sahibindencase.R
import com.mehmetfatihaksu.sahibindencase.common.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private var questionId : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionId = arguments?.getInt(Constants.ID)!!
        viewModel.getQuestionDetail(questionId)
    }

}