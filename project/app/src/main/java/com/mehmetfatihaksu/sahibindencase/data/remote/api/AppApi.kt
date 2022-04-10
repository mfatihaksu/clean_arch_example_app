package com.mehmetfatihaksu.sahibindencase.data.remote.api

import com.mehmetfatihaksu.sahibindencase.common.Constants
import com.mehmetfatihaksu.sahibindencase.domain.model.response.QuestionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {

    @GET(Constants.QUESTIONS)
    suspend fun getQuestions(
        @Query(Constants.QUERY_PAGE) page: Int,
        @Query(Constants.QUERY_PAGE_SIZE) pageSize: Int,
        @Query(Constants.QUERY_TAGGED) tagged: String,
        @Query(Constants.QUERY_SITE) site : String
    ): Response<QuestionsResponse>
}