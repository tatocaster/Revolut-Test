package me.tatocaster.revoluttest.features.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_exchange_rate.view.*
import me.tatocaster.revoluttest.R
import me.tatocaster.revoluttest.entity.Rate
import me.tatocaster.revoluttest.utils.GlideApp
import java.util.*

class RatesListAdapter(private val context: Context, private val listener: (Rate) -> Unit) : RecyclerView.Adapter<RatesListAdapter.ViewHolder>() {
    private var selectedItemCurrencyCode = ""
    private val ratesData = mutableListOf<Rate>()

    fun updateData(data: ArrayList<Rate>) {
        when {
            ratesData.isNotEmpty() && ratesData[0].name != data[0].name -> { // clicked and chosen other item
                val foundIndex = ratesData.indexOf(data[0])
                Collections.swap(ratesData, foundIndex, 0)
                notifyItemMoved(foundIndex, 0)
            }
            ratesData.isNotEmpty() -> {
                ratesData.forEachIndexed({ index, rate ->
                    if (rate.name != selectedItemCurrencyCode) {
                        ratesData[index] = data[index]
                        notifyItemChanged(index, data[index].rate)
                    }
                })
            }
            else -> {
                ratesData.addAll(data)
                notifyDataSetChanged()
            }
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
                    .forEach { holder.updateExchangeRate(position, it) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ratesData[position]
        holder.bindView(position, item)
    }

    override fun getItemCount(): Int {
        return ratesData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textChangeListener: TextWatcher

        init {
            textChangeListener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (adapterPosition == 0)
                        listener(Rate(selectedItemCurrencyCode,
                                if (s?.isEmpty() != false) 0.0 else s.toString().toDouble()))
                }
            }

            itemView.currencyRateEditText.addTextChangedListener(textChangeListener)
        }

        fun bindView(position: Int, item: Rate) {
            itemView.setOnClickListener({ v ->
                selectedItemCurrencyCode = item.name
                listener(item)
                v.currencyRateEditText.requestFocus()
            })

            itemView.currencyShortName.text = item.name
            setEditText(position, item.rate)

            GlideApp.with(context)
                    .load("https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${item.name.toLowerCase()}.png")
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemView.currencyIcon)
        }

        fun updateExchangeRate(position: Int, rate: Double) {
            setEditText(position, rate)
        }

        private fun setEditText(position: Int, rate: Double) {
            if (position == 0) {
                itemView.currencyRateEditText.removeTextChangedListener(textChangeListener)
                itemView.currencyRateEditText.text = SpannableStringBuilder(String.format("%.2f", rate))
                itemView.currencyRateEditText.setSelection(itemView.currencyRateEditText.text.length)
                itemView.currencyRateEditText.addTextChangedListener(textChangeListener)
            } else {
                itemView.currencyRateEditText.text = SpannableStringBuilder(String.format("%.2f", rate))
                itemView.currencyRateEditText.setSelection(itemView.currencyRateEditText.text.length)
            }
        }

    }
}