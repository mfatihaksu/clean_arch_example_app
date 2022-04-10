package com.mehmetfatihaksu.sahibindencase.data.remote.mediator.helper

import androidx.paging.LoadType
import androidx.paging.PagingState
import com.mehmetfatihaksu.sahibindencase.data.local.db.DatabaseHelperImpl
import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key
import com.mehmetfatihaksu.sahibindencase.data.remote.mediator.KeyGetter
import com.mehmetfatihaksu.sahibindencase.domain.model.request.QuestionRequest
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item
import kotlin.random.Random

class QuestionRequestHelper(private val dbHelper : DatabaseHelperImpl) : IQuestionRequestHelper {
    override fun getRequestObject(
        loadType: LoadType,
        state: PagingState<Int, Item>
    ): QuestionRequest {
        val remoteKeyGetter = KeyGetter(dbHelper)
        val page = when(loadType){
            LoadType.REFRESH ->{
                val remoteKey = remoteKeyGetter.getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1)?:1
            }
            LoadType.PREPEND->{
                val remoteKey = remoteKeyGetter.getRemoteKeyForFirstItem(state)
                if (remoteKey?.prevKey == null){
                    null
                }else{
                    remoteKey.prevKey
                }
            }
            LoadType.APPEND->{
                val remoteKey = remoteKeyGetter.getRemoteKeyForLastItem(state)
                if (remoteKey?.nextKey == null){
                    val newRemoteKey = Key(prevKey = null , nextKey = 1, Id = Random.nextInt(1,99999999))
                    newRemoteKey.nextKey
                }else{
                    remoteKey.nextKey
                }
            }
        }
        return QuestionRequest(page , state.config.pageSize)
    }
}