package rekayasaagromarin.ews3swj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ItemTaxaIndicatorBinding
import rekayasaagromarin.ews3swj.model.Species
import java.text.DecimalFormat

class TaxaIndicatorAdapter : RecyclerView.Adapter<TaxaIndicatorAdapter.ListViewHolder>() {

    private val taxaIndicator = ArrayList<Species>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTaxaIndicator(taxaIndicator: ArrayList<Species>) {
        this.taxaIndicator.clear()
        this.taxaIndicator.addAll(taxaIndicator)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaxaIndicatorAdapter.ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_taxa_indicator, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaxaIndicatorAdapter.ListViewHolder, position: Int) {
        holder.bind(taxaIndicator[position])
    }

    override fun getItemCount(): Int {
        return taxaIndicator.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTaxaIndicatorBinding.bind(itemView)
        fun bind(taxaIndicator: Species) {
            with(binding) {
                itemTvSpecies.text = taxaIndicator.species
                itemTvFamily.text = taxaIndicator.family
                itemTvTaxaIndicator.text = convertDecimal(taxaIndicator.taxaIndicator)
                itemTvStationId.text = taxaIndicator.stationId
            }
        }
    }

    private fun convertDecimal(value: Double): String {
//        val dec = DecimalFormat("##.##")
//        val decFormat = dec.format(value)
        return String.format("%.2f", value)
    }
}