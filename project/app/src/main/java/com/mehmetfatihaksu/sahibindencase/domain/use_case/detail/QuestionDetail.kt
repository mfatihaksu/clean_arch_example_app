package com.mehmetfatihaksu.sahibindencase.domain.use_case.detail

import androidx.paging.ExperimentalPagingApi
import com.mehmetfatihaksu.sahibindencase.data.rp.AppRepository
import com.mehmetfatihaksu.sahibindencase.domain.model.response.detail.QuestionDetailResponse
import retrofit2.Response
import javax.inject.Inject

@ExperimentalPagingApi
class QuestionDetail @Inject constructor(private val appRepository: AppRepository){

    suspend operator fun invoke(Id : Int) : Response<QuestionDetailResponse>{
        return appRepository.getQuestionDetail(Id)
    }
}