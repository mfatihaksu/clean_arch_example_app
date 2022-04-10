package com.mehmetfatihaksu.sahibindencase.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.mehmetfatihaksu.sahibindencase.domain.use_case.QuestionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mehmetfatihaksu.sahibindencase.common.Constants
import kotlinx.coroutines.launch

@HiltViewModel
@ExperimentalPagingApi
class DetailViewModel @Inject constructor(private val questionUseCases : QuestionUseCases,savedStateHandle: SavedStateHandle): ViewModel() {

    private val _state = mutableStateOf(QuestionDetailState())
    val state : State<QuestionDetailState> = _state

    fun getQuestionDetail(Id: Int){
        viewModelScope.launch {
            questionUseCases.questionDetail.invoke(Id).let { response->
                _state.value = state.value.copy(question = response.body())
            }
        }
    }
}