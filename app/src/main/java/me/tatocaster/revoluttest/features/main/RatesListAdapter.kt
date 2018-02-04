package me.tatocaster.revoluttest.features.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_exchange_rate.view.*
import me.tatocaster.revoluttest.R
import me.tatocaster.revoluttest.entity.Rate

class RatesListAdapter(private var listData: ArrayList<Rate>) : RecyclerView.Adapter<RatesListAdapter.ViewHolder>() {

    fun updateData(data: ArrayList<Rate>) {
        if (listData.isNotEmpty()) {
            listData.forEachIndexed({ index, rate ->
                if (!rate.isSelected) {
                    listData[index] = data[index]
                    notifyItemChanged(index, data[index].rate)
                }
            })

        } else {
            this.listData = data
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exchange_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            // Perform a full update
            onBindViewHolder(holder, position)
        } else {
            // Perform a partial update
            payloads
                    .filterIsInstance<Double>()
                    .forEach { holder.updateExchangeRate(it) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.bindView(position, item)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(position: Int, item: Rate) {
            itemView.currencyShortName.text = item.name
            itemView.currencyRate.setText(item.rate.toString())
            itemView.currencyRate.setOnFocusChangeListener({ view: View?, b: Boolean ->
                // timeout maybe
                listData[position].isSelected = b
            })
        }

        fun updateExchangeRate(rate: Double) {
            itemView.currencyRate.setText(rate.toString())
        }
        /*fun setClickListener(item: City, clickListener: OnItemClickListener) {
            itemView.setOnClickListener(object : View.OnClickListener() {
                fun onClick(view: View) {
                    clickListener.onItemClick(item)
                }
            })
        }*/
    }

    interface OnItemClickListener {
//        fun onItemClick(cityItem: City)
    }
}