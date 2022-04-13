package com.mehmetfatihaksu.sahibindencase.domain.use_case

import androidx.paging.ExperimentalPagingApi
import com.mehmetfatihaksu.sahibindencase.domain.use_case.detail.QuestionDetail
import com.mehmetfatihaksu.sahibindencase.domain.use_case.question.GetQuestions

@ExperimentalPagingApi
data class QuestionUseCases(
    val getQuestions : GetQuestions,
    val questionDetail : QuestionDetail
)