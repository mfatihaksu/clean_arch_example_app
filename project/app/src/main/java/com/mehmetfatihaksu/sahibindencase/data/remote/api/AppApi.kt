package com.mehmetfatihaksu.sahibindencase.data.remote.api

import com.mehmetfatihaksu.sahibindencase.common.Constants
import com.mehmetfatihaksu.sahibindencase.domain.model.response.detail.QuestionDetailResponse
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.QuestionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {

    @GET(Constants.QUESTIONS)
    suspend fun getQuestions(
        @Query(Constants.QUERY_PAGE) page: Int,
        @Query(Constants.QUERY_PAGE_SIZE) pageSize: Int,
        @Query(Constants.QUERY_TAGGED) tagged: String,
        @Query(Constants.QUERY_SITE) site : String
    ): Response<QuestionsResponse>

    @GET(Constants.GET_QUESTION_DETAIL)
    suspend fun getQuestionDetail(@Path(Constants.ID) Id : Int,@Query(Constants.QUERY_SITE) site : String) : Response<QuestionDetailResponse>
}