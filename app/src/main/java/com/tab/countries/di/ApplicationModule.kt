package com.tab.countries.di

import com.tab.countries.data.CountryRepositoryImpl
import com.tab.countries.data.mapper.CountryDataMapper
import com.tab.countries.data.mapper.CountryDataMapperImpl
import com.tab.countries.data.remote.service.BASE_URL
import com.tab.countries.data.remote.service.CountryService
import com.tab.countries.domain.CountryRepository
import com.tab.countries.presentation.countries.CountryListViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val networkingModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<CountryService> {
        get<Retrofit>().create(CountryService::class.java)
    }
}

private val dataModule = module {
    factory<CountryDataMapper> {
        CountryDataMapperImpl()
    }

    single<CountryRepository> {
        CountryRepositoryImpl(
            countryService = get(),
            countryDataMapper = get(),
            dispatcher = Dispatchers.IO
        )
    }
}

private val presentationModule = module {
    viewModel {
        CountryListViewModel(
            countryRepository = get()
        )
    }
}

val applicationModules = listOf(
    networkingModule,
    dataModule,
    presentationModule,
)
