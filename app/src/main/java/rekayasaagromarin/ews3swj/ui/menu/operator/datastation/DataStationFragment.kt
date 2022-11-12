package rekayasaagromarin.ews3swj.ui.menu.operator.datastation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.adapter.StationAdapter
import rekayasaagromarin.ews3swj.databinding.FragmentDataStationBinding
import rekayasaagromarin.ews3swj.model.DataStation
import rekayasaagromarin.ews3swj.ui.menu.operator.datastation.detail.DetailStationActivity

class DataStationFragment : Fragment() {

    private val dataStationViewModel: DataStationViewModel by viewModels()
    private var _binding: FragmentDataStationBinding? = null
    private val binding get() = _binding!!
    private val stationAdapter: StationAdapter by lazy { StationAdapter(true) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataStationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListStation()
        showListStation()
    }

    private fun setListStation() {
        with(dataStationViewModel) {
            setListDataStation()

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            message.observe(viewLifecycleOwner) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(viewLifecycleOwner) {
                showError(it)
            }
        }
    }

    private fun showListStation() {
        dataStationViewModel.getListStation().observe(viewLifecycleOwner) { listStation ->
            listStation?.let {
                if (listStation.isNotEmpty()) {
                    stationAdapter.setStations(listStation)
                    with(binding) {
                        rvDataStation.visibility = View.VISIBLE
                        tvNoResultStation.visibility = View.GONE
                    }
                } else {
                    with(binding) {
                        rvDataStation.visibility = View.GONE
                        tvNoResultStation.visibility = View.VISIBLE
                    }
                }
            }

            binding.rvDataStation.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = stationAdapter
            }
        }

        stationAdapter.setOnItemClickCallback(object : StationAdapter.OnItemClickCallback {
            override fun onItemClicked(station: DataStation) {
                val intentDetailStation = Intent(activity, DetailStationActivity::class.java)
                intentDetailStation.putExtra(EXTRA_STATION_ID, station.stationId)
                startActivity(intentDetailStation)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingDataStation.visibility = View.VISIBLE
                rvDataStation.visibility = View.GONE
            } else {
                loadingDataStation.visibility = View.GONE
                rvDataStation.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                rvDataStation.visibility = View.GONE
                tvDataStationError.visibility = View.VISIBLE
                loadingDataStation.visibility = View.GONE
            } else {
                tvDataStationError.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isUpdateItem) {
            setListStation()
            showListStation()
            isUpdateItem = false
        }
    }

    companion object {
        const val EXTRA_STATION_ID = "extra_station_id"
        var isUpdateItem = false
    }
}