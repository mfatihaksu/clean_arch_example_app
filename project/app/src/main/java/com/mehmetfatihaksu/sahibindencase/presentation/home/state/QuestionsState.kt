package com.mehmetfatihaksu.sahibindencase.presentation.home.state

import com.mehmetfatihaksu.sahibindencase.domain.model.response.list.Item

data class QuestionsState(val questions : List<Item> = emptyList())