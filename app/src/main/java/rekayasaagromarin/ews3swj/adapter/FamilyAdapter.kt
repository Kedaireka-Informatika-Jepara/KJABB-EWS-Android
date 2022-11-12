package rekayasaagromarin.ews3swj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ItemFamilyBinding
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import java.text.DecimalFormat

class FamilyAdapter : RecyclerView.Adapter<FamilyAdapter.ListViewHolder>() {

    private val family = ArrayList<FamilyBiotic>()
    private var onActionClickCallback: OnActionClickCallback? = null

    fun setOnActionClickCallback(onActionClickCallback: OnActionClickCallback) {
        this.onActionClickCallback = onActionClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFamily(familyBiotic: ArrayList<FamilyBiotic>) {
        family.clear()
        family.addAll(familyBiotic)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FamilyAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_family, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FamilyAdapter.ListViewHolder, position: Int) {
        holder.bind(family[position])
    }

    override fun getItemCount(): Int {
        return family.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFamilyBinding.bind(itemView)
        fun bind(familyBiotic: FamilyBiotic) {
            val weight = convertDecimal(familyBiotic.weight)
            with(binding) {
                tvItemFamily.text = familyBiotic.family
                tvItemWeight.text = String.format("Weight : %s", weight)

                btnFamilyEdit.setOnClickListener {
                    onActionClickCallback?.editFamilyOnClick(familyBiotic)
                }

                btnFamilyDelete.setOnClickListener {
                    onActionClickCallback?.deleteFamilyOnClick(familyBiotic)
                }
            }
        }
    }

    private fun convertDecimal(value: Double): String {
//        val dec = DecimalFormat("##.##")
//        val decFormat = dec.format(value)
//        return String.format("%.2f", decFormat.toDouble())
        return String.format("%.2f", value)
    }

    interface OnActionClickCallback {
        fun editFamilyOnClick(familyBiotic: FamilyBiotic)
        fun deleteFamilyOnClick(familyBiotic: FamilyBiotic)
    }
}