package rekayasaagromarin.ews3swj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ItemSpeciesBinding
import rekayasaagromarin.ews3swj.model.Species
import java.text.DecimalFormat

class SpeciesAdapter(private val isEnableDelete : Boolean) : RecyclerView.Adapter<SpeciesAdapter.ListViewHolder>() {

    private val species = ArrayList<Species>()
    private var onActionClickCallback: OnActionClickCallback? = null

    fun setOnActionClickCallback(onActionClickCallback: OnActionClickCallback) {
        this.onActionClickCallback = onActionClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSpecies(species: ArrayList<Species>) {
        this.species.clear()
        this.species.addAll(species)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpeciesAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_species, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpeciesAdapter.ListViewHolder, position: Int) {
        holder.bind(species[position])
    }

    override fun getItemCount(): Int {
        return species.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSpeciesBinding.bind(itemView)
        fun bind(species: Species) {
            val abundance = species.abundance

            with(binding) {
                tvItemSpecies.text = species.species
                tvItemFamily.text = species.family
                tvItemAbundance.text = String.format("Abundance : %s", abundance)

                if(isEnableDelete){
                    btnSpeciesDelete.setOnClickListener {
                        onActionClickCallback?.deleteDataOnClick(layoutPosition)
                    }
                }else{
                    btnSpeciesDelete.visibility = View.GONE
                }

            }
        }
    }

    private fun convertDecimal(value: Double): String {
        val dec = DecimalFormat("##.##")
        val decFormat = dec.format(value)
        return String.format("%.2f", decFormat.toDouble())
    }

    interface OnActionClickCallback {
        fun deleteDataOnClick(position: Int)
    }

}