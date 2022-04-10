package com.mehmetfatihaksu.sahibindencase.data.remote.mediator.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mehmetfatihaksu.sahibindencase.common.Constants
import com.mehmetfatihaksu.sahibindencase.data.local.db.DatabaseHelperImpl
import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key
import com.mehmetfatihaksu.sahibindencase.data.remote.api.AppApi
import com.mehmetfatihaksu.sahibindencase.data.remote.mediator.KeyGetter
import com.mehmetfatihaksu.sahibindencase.domain.model.request.QuestionRequest
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item

class QuestionDataSource(private val dbHelper : DatabaseHelperImpl, private val appApi : AppApi, private val questionRequest: QuestionRequest) : PagingSource<Int, Item>() {
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        val keyGetter = KeyGetter(dbHelper)
        return state.anchorPosition?.let {
            keyGetter.getRemoteKeyClosestToCurrentPosition(state)?.prevKey?.plus(1)?:keyGetter.getRemoteKeyClosestToCurrentPosition(state)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val pageNumber = params.key ?: 1
        return try {
            questionRequest.page = if (pageNumber <= 0) {
                1
            } else {
                pageNumber
            }
            val response = appApi.getQuestions(questionRequest.page!! , questionRequest.pageSize!!, Constants.ANDROID, Constants.SITE)
            if (response.body() == null || response.body()?.items?.isEmpty()!!) {
                return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
            }
            var prevKey: Int? = pageNumber.minus(1)
            if (prevKey != null && prevKey <= 0) {
                prevKey = null
            }
            val nextKey = pageNumber.plus(1)
            val questionKeys = response.body()?.items!!.map {
                Key(Id = it.question_id , prevKey = prevKey, nextKey = nextKey)
            }
            if (questionKeys.isNotEmpty()){
                dbHelper.insertKeys(questionKeys)
            }

            return LoadResult.Page(
                data = response.body()?.items!!,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}