package com.sujith.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sujith.roomdemo.databinding.ListItemBinding
import com.sujith.roomdemo.db.Subscriber

class MyRecyclerviewAdapter(
    private val subscribers: List<Subscriber>,
    private val myClickListener: (Subscriber) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position], myClickListener)
    }
}

class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber, myClickListener: (Subscriber) -> Unit) {
        binding.nameTxt.text = subscriber.name
        binding.emailTxt.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            myClickListener(subscriber)
        }
    }
}