package com.rishabh.foodiesclub.features.restaurants.data.di

import com.rishabh.foodiesclub.features.restaurants.data.remote.RestaurantApiService
import com.rishabh.foodiesclub.features.restaurants.data.repository.RestaurantRepositoryImpl
import com.rishabh.foodiesclub.features.restaurants.domain.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RestaurantDataModule {

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        restaurantRepositoryImpl: RestaurantRepositoryImpl
    ): RestaurantRepository

    companion object {
        @Provides
        @Singleton
        fun provideRestaurantApiService(retrofit: Retrofit): RestaurantApiService {
            return retrofit.create(RestaurantApiService::class.java)
        }
    }
}
