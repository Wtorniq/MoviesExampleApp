package com.example.moviesexampleapp.ui.settings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviesexampleapp.databinding.ItemSelectorBinding

class SelectorAdapter(private val adapterInterface: SelectorInterface): Adapter<SelectorAdapter.SelectorViewHolder>() {

    private lateinit var binding: ItemSelectorBinding
    private var itemsList = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsList(items: ArrayList<String>){
        itemsList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorViewHolder {
        binding = ItemSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectorViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SelectorViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    inner class SelectorViewHolder(itemView: View): ViewHolder(itemView) {
        private val tv = with(binding){
            selectorTv
        }
        fun bind(item: String){
            tv.text = item
            itemView.setOnClickListener {
                adapterInterface.onItemClicked(item)
            }
        }
    }
}

interface SelectorInterface{
    fun onItemClicked(item: String)
}
