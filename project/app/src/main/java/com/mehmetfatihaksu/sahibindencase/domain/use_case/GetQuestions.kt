package com.mehmetfatihaksu.sahibindencase.domain.use_case

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.mehmetfatihaksu.sahibindencase.data.rp.AppRepository
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class GetQuestions @Inject constructor(private val appRepository: AppRepository){

    suspend operator fun invoke(context: Context, page: Int, pageSize: Int):Flow<PagingData<Item>>{
        return appRepository.getQuestions(context, page, pageSize)
    }

}