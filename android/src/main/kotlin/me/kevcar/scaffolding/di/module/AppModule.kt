package me.kevcar.scaffolding.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import me.kevcar.scaffolding.BuildConfig
import me.kevcar.scaffolding.api.interceptor.AuthKeyInterceptor
import me.kevcar.scaffolding.api.service.ImageService
import me.kevcar.scaffolding.app.Application
import me.kevcar.scaffolding.core.di.AppScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named

@Module
class AppModule(private val application: Application) {

    @Provides
    @AppScope
    fun provideContext(): Context {
        return application
    }

    @Provides
    @AppScope
    fun provideExecutor(): Executor {
        return Executors.newCachedThreadPool()
    }

    @Provides
    @AppScope
    @Named("authKey")
    fun provideAuthKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @AppScope
    fun provideAuthKeyInterceptor(@Named("authKey") authKey: String): AuthKeyInterceptor {
        return AuthKeyInterceptor(authKey)
    }

    @Provides
    @AppScope
    fun provideClient(authKeyInterceptor: AuthKeyInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(authKeyInterceptor)
                .build()
    }

    @Provides
    @AppScope
    fun provideGson(): Gson {
        return GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create()
    }

    @Provides
    @AppScope
    fun provideRetrofit(client: OkHttpClient, executor: Executor): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.cognitive.microsoft.com/bing/v5.0/images")
                .client(client)
                .callbackExecutor(executor)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @AppScope
    fun provideImageService(retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }

}
