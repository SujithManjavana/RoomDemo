package com.sujith.roomdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sujith.roomdemo.databinding.ActivityMainBinding
import com.sujith.roomdemo.db.Subscriber
import com.sujith.roomdemo.db.SubscriberDatabase
import com.sujith.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewmodel: SubscriberViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewmodelFactory(repository)
        subscriberViewmodel =
            ViewModelProvider(this@MainActivity, factory)[SubscriberViewmodel::class.java]

        binding.viewModel = subscriberViewmodel
        binding.lifecycleOwner = this
        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewmodel.subscribers.observe(this@MainActivity, Observer {
            binding.subscriberRecyclerView.adapter = MyRecyclerviewAdapter(it) { selectedItem: Subscriber ->
                clickListener(selectedItem)
            }
        })
    }

    private fun clickListener(subscriber: Subscriber){
        Toast.makeText(this,subscriber.name,Toast.LENGTH_SHORT).show()
    }
}

