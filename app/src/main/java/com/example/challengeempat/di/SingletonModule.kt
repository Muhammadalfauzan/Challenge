package com.example.challengeempat.di

import android.app.Application
import com.example.challengeempat.api.ApiRestaurant
import com.example.challengeempat.database.CartDao
import com.example.challengeempat.database.DatabaseCart
import com.example.challengeempat.sharedpref.ViewPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Singleton
    @Provides
    fun provideDatabaseCart(context: Application): DatabaseCart {
        return DatabaseCart.getInstance(context)
    }
    private val interceptor: HttpLoggingInterceptor
        get(){
            val httpLoggingInterceptor = HttpLoggingInterceptor()

            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level  = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(interceptor).build()
    @Singleton
    @Provides
    fun getDataDao(kelolaDB: DatabaseCart): CartDao =
        kelolaDB.cartDao()
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
    @Singleton
    @Provides
    fun provideMasterService(): ApiRestaurant =
        Retrofit.Builder()
            .baseUrl("https://testing.jasa-nikah-siri-amanah-profesional.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiRestaurant::class.java)
    @Singleton
    @Provides
    fun providePreferencesManager(application: Application): ViewPreference {
        return ViewPreference(application)
    }
}
