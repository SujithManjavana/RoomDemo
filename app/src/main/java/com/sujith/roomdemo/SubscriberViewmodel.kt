package com.sujith.roomdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujith.roomdemo.db.Subscriber
import com.sujith.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriberViewmodel(private val repository: SubscriberRepository) : ViewModel() {
    val subscribers = repository.subscribers
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateBtnText = MutableLiveData<String>()
    val clearOrDeleteBtnText = MutableLiveData<String>()

    init {
        saveOrUpdateBtnText.value = "Save"
        clearOrDeleteBtnText.value = "Clear all"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputName.value = ""
        inputEmail.value = ""
    }

    fun clearOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(subscriber)
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(subscriber)
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(subscriber)
    }

    fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}