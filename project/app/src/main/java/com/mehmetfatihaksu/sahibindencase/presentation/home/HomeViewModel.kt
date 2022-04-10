package com.mehmetfatihaksu.sahibindencase.presentation.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item
import com.mehmetfatihaksu.sahibindencase.domain.use_case.QuestionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class HomeViewModel @Inject constructor(private val questionUseCases : QuestionUseCases): ViewModel() {

   private val _state = mutableStateOf(QuestionsState())
    val state : State<QuestionsState> = _state

    suspend fun getQuestions(context: Context, page: Int, pageSize: Int): Flow<PagingData<Item>> {
        return questionUseCases.getQuestions(context, page, pageSize)
    }
}