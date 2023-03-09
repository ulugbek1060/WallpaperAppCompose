package uz.mobidev.wallpaperapp.di

import android.util.Log
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uz.mobidev.wallpaperapp.data.Api
import javax.inject.Singleton

const val BASE_URL = "https://api.unsplash.com"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

   val TAG = this.javaClass.name

   @Provides
   @Singleton
   fun provideMoshi(): Moshi {
      return Moshi.Builder().build()
   }

   @Provides
   @Singleton
   fun providesRetrofit(
      contentNegotiation: Moshi,
      okHttpClient: OkHttpClient,
   ): Retrofit {
      return Retrofit.Builder()
         .baseUrl(BASE_URL)
         .client(okHttpClient)
         .addConverterFactory(MoshiConverterFactory.create(contentNegotiation))
         .build()
   }

   @Provides
   @Singleton
   fun provideApi(retrofit: Retrofit): Api {
      return retrofit.create(Api::class.java)
   }

   @Singleton
   @Provides
   fun provideClient(): OkHttpClient {
      return OkHttpClient.Builder()
         .addInterceptor(createInterceptor())
         .addInterceptor(createLoggingInterceptor())
         .build()
   }

   private fun createInterceptor(): Interceptor {
      return Interceptor { chain ->
         val newBuilder = chain.request().newBuilder()
         Log.d(TAG, "createInterceptor: ${chain.request().url}")
         return@Interceptor chain.proceed(newBuilder.build())
      }
   }

   private fun createLoggingInterceptor(): Interceptor {
      return HttpLoggingInterceptor()
         .setLevel(HttpLoggingInterceptor.Level.BODY)
   }

}