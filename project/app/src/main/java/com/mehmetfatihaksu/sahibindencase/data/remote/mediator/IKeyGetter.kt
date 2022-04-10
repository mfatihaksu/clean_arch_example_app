package com.mehmetfatihaksu.sahibindencase.data.remote.mediator

import androidx.paging.PagingState
import com.mehmetfatihaksu.sahibindencase.data.local.entity.Key
import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item

interface IKeyGetter {

    fun getRemoteKeyClosestToCurrentPosition(state : PagingState<Int, Item>): Key?

    fun getRemoteKeyForFirstItem(state : PagingState<Int, Item>) :  Key?

    fun getRemoteKeyForLastItem(state : PagingState<Int, Item>) : Key?
}