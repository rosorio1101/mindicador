package com.rosorio.mindicador.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.mindicador.R
import kotlinx.android.synthetic.main.view_holder_indicators.view.*

typealias OnItemSelectedListener = (MainAdapter.Item?) -> Unit

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var data: List<Item>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: OnItemSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        holder.item = item
        listener?.let { listener ->
            holder.itemView.setOnClickListener {
                data?.forEach { data ->
                    data.selected = item?.value == data.value && item?.name == data.name
                    notifyDataSetChanged()
                }
                listener(item)

            }
        }
    }

    class ViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.view_holder_indicators, viewGroup, false)
    ) {
        var item: Item? = null
            set(value) {
                itemView.tvName.text = value?.name ?: ""
                itemView.tvValue.text = value?.value ?: ""
                itemView.isSelected = value?.selected ?: false
                field = value
            }
    }

    data class Item(val name: String?,
                    val value: String?,
                    var selected: Boolean?)
}