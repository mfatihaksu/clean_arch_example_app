package com.mehmetfatihaksu.sahibindencase.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mehmetfatihaksu.sahibindencase.common.Constants
import com.mehmetfatihaksu.sahibindencase.data.local.db.DatabaseHelperImpl
import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key
import com.mehmetfatihaksu.sahibindencase.data.remote.api.AppApi
import com.mehmetfatihaksu.sahibindencase.data.remote.mediator.helper.QuestionRequestHelper
import com.mehmetfatihaksu.sahibindencase.domain.model.request.QuestionRequest
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class QuestionMediator(private val appApi : AppApi , private val dbHelper : DatabaseHelperImpl , private val questionRequest : QuestionRequest) : RemoteMediator<Int, Item>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Item>
    ): MediatorResult {

        val questionRequestHelper = QuestionRequestHelper(dbHelper)
        val requestObject = questionRequestHelper.getRequestObject(loadType ,state)

        if (requestObject.page == null){
            return MediatorResult.Success(endOfPaginationReached = true)
        }
        try {
            questionRequest.page = requestObject.page
            questionRequest.pageSize = requestObject.pageSize
            val response = appApi.getQuestions(questionRequest.page!! , questionRequest.pageSize!! , Constants.ANDROID, Constants.SITE)
            val endOfPaginationReached = response.body()?.items?.isEmpty()!!

            if(loadType == LoadType.REFRESH){
                dbHelper.clearKeys()
            }
            val prevKey = if(questionRequest.page == 1) null else questionRequest.page!!.minus(1)
            val nextKey = if(endOfPaginationReached) null else questionRequest.page!!.plus(1)

            val questionKeys = response.body()?.items!!.map {
                Key(Id = it.question_id , prevKey = prevKey, nextKey = nextKey)
            }
            if (questionKeys.isNotEmpty()){
                dbHelper.insertKeys(questionKeys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (httpException: HttpException) {
            return MediatorResult.Error(httpException)
        }
    }
}