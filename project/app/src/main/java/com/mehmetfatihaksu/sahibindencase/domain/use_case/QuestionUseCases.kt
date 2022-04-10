package com.mehmetfatihaksu.sahibindencase.domain.use_case

import androidx.paging.ExperimentalPagingApi

@ExperimentalPagingApi
data class QuestionUseCases(
    val getQuestions : GetQuestions
)