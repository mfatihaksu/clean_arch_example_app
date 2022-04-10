package com.mehmetfatihaksu.sahibindencase.domain.model.response.detail

data class QuestionDetailResponse(
    val has_more: Boolean,
    val items: List<Item>,
    val quota_max: Int,
    val quota_remaining: Int
)