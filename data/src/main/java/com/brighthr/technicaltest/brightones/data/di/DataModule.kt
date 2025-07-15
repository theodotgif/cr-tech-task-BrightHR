package com.brighthr.technicaltest.brightones.data.di

import com.brighthr.technicaltest.brightones.data.datasource.PostRemoteDataSource
import com.brighthr.technicaltest.brightones.data.repository.PostDataRepository
import com.brighthr.technicaltest.brightones.data.usecases.post.GetAllPostsUseCaseImpl
import com.brighthr.technicaltest.brightones.data.usecases.post.GetCommentsForPostUseCaseImpl
import com.brighthr.technicaltest.brightones.domain.repository.PostRepository
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetAllPostUseCase
import com.brighthr.technicaltest.brightones.domain.usecases.post.GetCommentsForPostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providePostRepository(
        network: PostRemoteDataSource
    ): PostRepository {
        return PostDataRepository(network)
    }

    @Provides
    @Singleton
    fun provideGetAllPostUseCase(
        postRepository: PostRepository
    ): GetAllPostUseCase {
        return GetAllPostsUseCaseImpl(postRepository)
    }

    @Provides
    @Singleton
    fun provideGetCommentsForPostUseCase(
        postRepository: PostRepository
    ): GetCommentsForPostUseCase {
        return GetCommentsForPostUseCaseImpl(postRepository)
    }
}