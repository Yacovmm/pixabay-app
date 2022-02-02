package com.example.pixabayapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixabayapp.data.PixabayRepository
import com.example.pixabayapp.data.models.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PixabayRepository
) : ViewModel() {

    private val _dataLiveData = MutableLiveData<HomeViewState>()
    val dataLiveData: LiveData<HomeViewState> get() = _dataLiveData


    private val dataList = mutableListOf<ApiResponse.Hit>()
    private var page = 1
    private var perPage = 10
    var noMoreItems = false
    var isPaginating = false

    fun getData() = viewModelScope.launch {

        if (noMoreItems) return@launch

        isPaginating = true

        _dataLiveData.postValue(
            HomeViewState(loading = true, data = dataList)
        )

        val response = repository.getImages(page = page, perPage = perPage)



        if (response == null) {
            _dataLiveData.postValue(
                HomeViewState(
                    loading = false,
                    error = true
                )
            )
            return@launch
        }

        if (response.isSuccessful && response.body() != null) {
            // Pagination
            val hits = response.body()!!.hits

            if (hits.isEmpty()) {
                noMoreItems = true
            }

            page++
            dataList.addAll(response.body()!!.hits)

            _dataLiveData.postValue(
                HomeViewState(
                    loading = false,
                    data = dataList,
                    error = false
                )
            )
        } else {
            _dataLiveData.postValue(
                HomeViewState(
                    loading = false,
                    error = true
                )
            )
        }
    }

}

data class HomeViewState(
    var loading: Boolean = false,
    val data: List<ApiResponse.Hit>? = null,
    var error: Boolean = false
)