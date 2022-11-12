package rekayasaagromarin.ews3swj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ItemWeightBinding
import rekayasaagromarin.ews3swj.model.ParameterResponse
import java.text.DecimalFormat

class WeightAdapter : RecyclerView.Adapter<WeightAdapter.ListViewHolder>() {
    private val response = ArrayList<ParameterResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWeight(weight: ArrayList<ParameterResponse>) {
        response.clear()
        response.addAll(weight)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeightAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weight, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return response.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(response[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemWeightBinding.bind(itemView)
        fun bind(response: ParameterResponse) {

            val initialValue = convertDecimal(response.initialValue)
            val finalValue = convertDecimal(response.finalValue)
            val value = "$initialValue - $finalValue"

            with(binding) {
                tvWeightName.text = response.name
                tvWeightValue.text = String.format("Value : %s", value)
            }

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(response)
            }
        }
    }

    private fun convertDecimal(value: Double): String {
        return String.format("%.2f", value)
    }

    interface OnItemClickCallback {
        fun onItemClicked(response: ParameterResponse)
    }
}