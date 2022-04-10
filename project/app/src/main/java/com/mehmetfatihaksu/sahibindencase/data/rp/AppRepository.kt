package com.mehmetfatihaksu.sahibindencase.data.rp

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mehmetfatihaksu.sahibindencase.data.local.db.DatabaseBuilder
import com.mehmetfatihaksu.sahibindencase.data.local.db.DatabaseHelperImpl
import com.mehmetfatihaksu.sahibindencase.data.remote.api.AppApi
import com.mehmetfatihaksu.sahibindencase.data.remote.mediator.QuestionMediator
import com.mehmetfatihaksu.sahibindencase.data.remote.mediator.data_source.QuestionDataSource
import com.mehmetfatihaksu.sahibindencase.domain.model.request.QuestionRequest
import com.mehmetfatihaksu.sahibindencase.domain.model.response.Item
import com.mehmetfatihaksu.sahibindencase.domain.rp.IAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalPagingApi
class AppRepository @Inject constructor(private val appApi: AppApi) : IAppRepository {

    override suspend fun getQuestions(
        context: Context,
        page: Int,
        pageSize: Int
    ): Flow<PagingData<Item>> {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
        dbHelper.clearKeys()
        return Pager(
            config = PagingConfig(pageSize = 25, maxSize = 100, enablePlaceholders = false),
            pagingSourceFactory = {
                QuestionDataSource(
                    dbHelper = dbHelper,
                    appApi = appApi,
                    questionRequest = QuestionRequest(page, pageSize)
                )
            },
            remoteMediator = QuestionMediator(
                appApi = appApi,
                dbHelper = dbHelper,
                questionRequest = QuestionRequest(page, pageSize)
            )
        ).flow
    }
}