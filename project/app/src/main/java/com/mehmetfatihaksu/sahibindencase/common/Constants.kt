package com.mehmetfatihaksu.sahibindencase.common

class Constants {

    companion object INSTANCE{
        const val BASE_URL = "https://api.stackexchange.com/2.3/"
        const val QUERY_PAGE ="page"
        const val QUERY_PAGE_SIZE ="pageSize"
        const val QUERY_TAGGED = "tagged"
        const val ANDROID = "android"
        const val QUESTIONS = "questions"
        const val GET_QUESTION_DETAIL = "questions/{Id}"
        const val QUERY_SITE = "site"
        const val SITE = "stackoverflow"
        const val ID = "Id"
        const val LONG_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm"

    }
}
