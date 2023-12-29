package com.sujith.roomdemo


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sujith.roomdemo.db.SubscriberRepository
import java.lang.IllegalArgumentException

class SubscriberViewmodelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewmodel::class.java)) {
            return SubscriberViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}