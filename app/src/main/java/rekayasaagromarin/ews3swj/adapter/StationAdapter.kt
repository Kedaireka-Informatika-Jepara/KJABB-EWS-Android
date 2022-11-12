package rekayasaagromarin.ews3swj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ItemStationBinding
import rekayasaagromarin.ews3swj.model.DataStation
import java.text.DecimalFormat

class StationAdapter(val isShowUser: Boolean) :
    RecyclerView.Adapter<StationAdapter.ListViewHolder>() {
    private val stations = ArrayList<DataStation>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setStations(stations: ArrayList<DataStation>) {
        this.stations.clear()
        this.stations.addAll(stations)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(stations[position])
    }

    override fun getItemCount(): Int {
        return this.stations.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemStationBinding.bind(itemView)
        fun bind(station: DataStation) {
            with(binding) {
                itemTvStationId.text = station.stationId
                itemTvValue.text = convertDecimal(station.value)
                itemTvStationZone.text = station.geographicalZone
                itemTvStationWater.text = station.typeOfWater
                if (isShowUser) {
                    itemTvStationUser.text = station.createdBy
                } else {
                    itemTvStationUser.visibility = View.GONE
                    tvLabelCreatedBy.visibility = View.GONE
                }
            }

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(station)
            }
        }
    }

    private fun convertDecimal(value: Double): String {
//        val dec = DecimalFormat("##.##")
//        val decFormat = dec.format(value)
        return String.format("%.2f", value)
    }

    interface OnItemClickCallback {
        fun onItemClicked(station: DataStation)
    }
}