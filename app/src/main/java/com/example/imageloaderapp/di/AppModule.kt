package com.example.imageloaderapp.di

import com.example.imageloaderapp.repository.ImageListRepository
import com.example.imageloaderapp.viewmodel.ImageListViewModel
import com.example.wikisearch.network.GenericApiManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(override = true) {

    viewModel { ImageListViewModel() }

    single { GenericApiManager() }
    single { ImageListRepository() }

}