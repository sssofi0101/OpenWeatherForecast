package com.example.openweatherforecast.di

import com.example.openweatherforecast.ForecastApp
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.data.retrofit.LoggingInterceptor
import com.example.openweatherforecast.data.retrofit.RetrofitImpl
import com.example.openweatherforecast.data.retrofit.WeatherApi
import com.example.openweatherforecast.data.room.AppDatabase
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.presentation.detailedweather.DayDetailedViewModel
import com.example.openweatherforecast.presentation.weather.WeatherViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [DataModule::class, AppBindModule::class, DomainModule::class])
interface AppComponent{
     fun inject(dayDetailedViewModel: DayDetailedViewModel)
     fun inject(weatherViewModel: WeatherViewModel)
    fun inject(retrofitImpl: RetrofitImpl)
//    fun inject(weatherFragment: WeatherFragment)

    val repositoryImpl : RepositoryImpl
}

@Module
class DataModule {

    @Provides
    fun provideRemoteDataSource():WeatherApi {
        return RetrofitImpl.getService()
    }

    @Provides
    fun provideLocalDatabase():AppDatabase {

        return ForecastApp.database
    }
    @Provides
    fun provideRetrofit(interceptor: HttpLoggingInterceptor) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit

    }

    @Provides
    fun provideInterceptor() : HttpLoggingInterceptor {
        return LoggingInterceptor.interceptor
    }

}

@Module
class DomainModule {
//    @Provides
//    fun provideDaysAdapter():DaysAdapter {
//        return DaysAdapter(arrayListOf())
//    }

}

@Module
interface AppBindModule {

    @Binds
    fun bindRepositoryImpl_to_Repository( repositoryImpl: RepositoryImpl) : Repository
}