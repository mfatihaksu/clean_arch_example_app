package com.mehmetfatihaksu.sahibindencase.domain.rp

import android.content.Context
import androidx.paging.PagingData
import com.mehmetfatihaksu.sahibindencase.domain.model.response.Item
import kotlinx.coroutines.flow.Flow

interface IAppRepository {

    suspend fun getQuestions(context: Context, page : Int, pageSize : Int): Flow<PagingData<Item>>
}