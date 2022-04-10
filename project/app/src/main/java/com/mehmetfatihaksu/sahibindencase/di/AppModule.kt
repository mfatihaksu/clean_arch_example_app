package com.mehmetfatihaksu.sahibindencase.di

import androidx.paging.ExperimentalPagingApi
import com.mehmetfatihaksu.sahibindencase.data.remote.api.ApiBuilder
import com.mehmetfatihaksu.sahibindencase.data.remote.api.AppApi
import com.mehmetfatihaksu.sahibindencase.data.rp.AppRepository
import com.mehmetfatihaksu.sahibindencase.domain.use_case.GetQuestions
import com.mehmetfatihaksu.sahibindencase.domain.use_case.QuestionUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesAppApi() : AppApi {
        return ApiBuilder.appApi()
    }

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun providesAppRepository(appApi: AppApi) : AppRepository{
        return AppRepository(appApi)
    }

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideQuestionUseCases(appRepository: AppRepository):QuestionUseCases{
        return QuestionUseCases(getQuestions = GetQuestions(appRepository))
    }
}