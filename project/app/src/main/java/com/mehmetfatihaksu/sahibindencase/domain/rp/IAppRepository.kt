package com.mehmetfatihaksu.sahibindencase.domain.rp

import android.content.Context
import androidx.paging.PagingData
import com.mehmetfatihaksu.sahibindencase.domain.model.response.detail.QuestionDetailResponse
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IAppRepository {

    suspend fun getQuestions(context: Context, page : Int, pageSize : Int): Flow<PagingData<Item>>

    suspend fun getQuestionDetail(Id : Int) : Response<QuestionDetailResponse>
}