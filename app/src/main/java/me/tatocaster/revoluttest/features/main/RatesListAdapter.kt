package me.tatocaster.revoluttest.features.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_exchange_rate.view.*
import me.tatocaster.revoluttest.R
import me.tatocaster.revoluttest.entity.Rate

class RatesListAdapter(private var listData: List<Rate>) : RecyclerView.Adapter<RatesListAdapter.ViewHolder>() {

    fun updateData(data: List<Rate>) {
        this.listData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exchange_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Rate) {
            itemView.currencyShortName.text = item.name
            itemView.currencyRate.setText(item.rate.toString())
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