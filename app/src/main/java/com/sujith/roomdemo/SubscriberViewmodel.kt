package com.sujith.roomdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujith.roomdemo.db.Subscriber
import com.sujith.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewmodel(private val repository: SubscriberRepository) : ViewModel() {
    val subscribers = repository.subscribers
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateBtnText = MutableLiveData<String>()
    val clearOrDeleteBtnText = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    init {
        saveOrUpdateBtnText.value = "Save"
        clearOrDeleteBtnText.value = "Clear all"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.name = inputName.value.toString()
            subscriberToUpdateOrDelete.email = inputEmail.value.toString()
            update(subscriberToUpdateOrDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0, name, email))
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    fun clearOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else clearAll()
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(subscriber)
        withContext(Dispatchers.Main){
            message.value = "Inserted!"
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(subscriber)
        withContext(Dispatchers.Main) {
            isUpdateOrDelete = false
            inputName.value = ""
            inputEmail.value = ""
            saveOrUpdateBtnText.value = "Save"
            clearOrDeleteBtnText.value = "Clear all"
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(subscriber)
        withContext(Dispatchers.Main) {
            isUpdateOrDelete = false
            inputName.value = ""
            inputEmail.value = ""
            saveOrUpdateBtnText.value = "Save"
            clearOrDeleteBtnText.value = "Clear all"
        }
    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        subscriberToUpdateOrDelete = subscriber
        isUpdateOrDelete = true
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        saveOrUpdateBtnText.value = "Update"
        clearOrDeleteBtnText.value = "Delete"
    }
}