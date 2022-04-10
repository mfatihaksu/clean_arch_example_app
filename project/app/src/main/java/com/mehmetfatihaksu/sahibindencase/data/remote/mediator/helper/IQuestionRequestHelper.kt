package com.mehmetfatihaksu.sahibindencase.data.remote.mediator.helper

import androidx.paging.LoadType
import androidx.paging.PagingState
import com.mehmetfatihaksu.sahibindencase.domain.model.request.QuestionRequest
import com.mehmetfatihaksu.sahibindencase.domain.model.response.Item

interface IQuestionRequestHelper {
    fun getRequestObject(loadType: LoadType , state : PagingState<Int, Item>) : QuestionRequest
}