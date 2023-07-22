package com.example.dealerapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dealerapp.model.Dealer
import com.example.dealerapp.repository.DealerRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DealerappViewModel(val repository: DealerRepository): ViewModel() {
    val allDealer: LiveData<List<Dealer>> = repository.allDealer.asLiveData()

    fun insert(dealer: Dealer ) = viewModelScope.launch {
        repository.insertDealer(dealer)
    }

    fun delete(dealer: Dealer) = viewModelScope.launch {
        repository.deleteDealer(dealer)
    }

    fun update(dealer: Dealer) = viewModelScope.launch {
        repository.updateDealer(dealer)
    }
}

class DealerViewModelFactory(private val repository: DealerRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DealerappViewModel::class.java)) {
            return DealerappViewModel(repository) as T
        }
        throw IllegalArgumentException(" UnknowN ViewModel ")

    }
}