package uz.mobidev.wallpaperapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mobidev.wallpaperapp.data.RepositoryImpl
import uz.mobidev.wallpaperapp.domain.Repository

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

   @Binds
   fun provideRepository(
      repositoryImpl: RepositoryImpl
   ): Repository
}