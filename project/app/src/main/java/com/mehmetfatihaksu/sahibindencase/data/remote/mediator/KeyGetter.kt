package com.mehmetfatihaksu.sahibindencase.data.remote.mediator

import androidx.paging.PagingState
import com.mehmetfatihaksu.sahibindencase.data.local.db.DatabaseHelperImpl
import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key
import com.mehmetfatihaksu.sahibindencase.domain.model.response.Item

class KeyGetter(private val dbHelper : DatabaseHelperImpl): IKeyGetter {
    override fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Item>): Key? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.question_id ?.let { Id ->
                dbHelper.getKey(Id)
            }
        }
    }

    override fun getRemoteKeyForFirstItem(state: PagingState<Int, Item>): Key? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { question->
            dbHelper.getKey(question.question_id)
        }
    }

    override fun getRemoteKeyForLastItem(state: PagingState<Int, Item>): Key? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { question ->
            dbHelper.getKey(question.question_id)
        }
    }
}