package com.mehmetfatihaksu.sahibindencase.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.mehmetfatihaksu.sahibindencase.domain.use_case.QuestionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.mehmetfatihaksu.sahibindencase.domain.model.response.detail.QuestionDetailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
@ExperimentalPagingApi
class DetailViewModel @Inject constructor(private val questionUseCases : QuestionUseCases): ViewModel() {


    sealed class ViewState{
        class ShowLoading(val isShow : Boolean) : ViewState()
        class ShowErrorMessage(val message : String?) : ViewState()
        class QuestionLoaded(val response : QuestionDetailResponse) : ViewState()
        object Empty : ViewState()
    }

   private val _detailUIState = MutableStateFlow<ViewState>(ViewState.Empty)
    val detailUIState : StateFlow<ViewState> = _detailUIState

    fun getQuestionDetail(Id: Int){
        viewModelScope.launch {
            _detailUIState.value = ViewState.ShowLoading(true)
            questionUseCases.questionDetail.invoke(Id).let { response->
                _detailUIState.value = ViewState.ShowLoading(false)
                if (response.isSuccessful){
                    _detailUIState.value = ViewState.QuestionLoaded(response.body()!!)
                }else{
                    _detailUIState.value = ViewState.ShowErrorMessage(response.message())
                }
            }
        }
    }
}