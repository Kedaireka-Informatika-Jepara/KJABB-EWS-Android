package rekayasaagromarin.ews3swj.ui.menu.main.inputhistory

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
import rekayasaagromarin.ews3swj.adapter.TaxaIndicatorAdapter
import rekayasaagromarin.ews3swj.databinding.FragmentInputHistoryBinding
import rekayasaagromarin.ews3swj.model.DataStation
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.ResultHistoryActivity

class InputHistoryFragment : Fragment() {

    private val inputHistoryViewModel: InputHistoryViewModel by viewModels()
    private var _binding: FragmentInputHistoryBinding? = null
    private val binding get() = _binding!!
    private val preferences: PreferencesManager by lazy { PreferencesManager(context) }
    private val taxaIndicatorAdapter: TaxaIndicatorAdapter by lazy { TaxaIndicatorAdapter() }
    private val stationAdapter: StationAdapter by lazy { StationAdapter(false) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListData()
        showListTaxaIndicator()
        showListResultHistory()
    }

    private fun setListData() {
        val userId = preferences.getId()
        with(inputHistoryViewModel) {
            if (userId != null) {
                setListTaxaIndicator(userId)
                setListDataStation(userId)
            }

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

    private fun showListTaxaIndicator() {
        inputHistoryViewModel.getListTaxaIndicator()
            .observe(viewLifecycleOwner) { listTaxaIndicator ->
                listTaxaIndicator?.let {
                    if (listTaxaIndicator.isNotEmpty()) {
                        taxaIndicatorAdapter.setTaxaIndicator(listTaxaIndicator)
                        with(binding) {
                            layoutResult.visibility = View.VISIBLE
                            tvNoResultInput.visibility = View.GONE
                        }
                    } else {
                        with(binding) {
                            layoutResult.visibility = View.VISIBLE
                            tvNoResultInput.visibility = View.GONE
                        }
                    }
                }

                binding.rvHistoryTaxaIndicator.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = taxaIndicatorAdapter
                }
            }
    }

    private fun showListResultHistory() {
        inputHistoryViewModel.getListStation().observe(viewLifecycleOwner) { listStation ->
            listStation?.let {
                if (listStation.isNotEmpty()) {
                    stationAdapter.setStations(listStation)
                    with(binding) {
                        layoutResult.visibility = View.VISIBLE
                        tvNoResultInput.visibility = View.GONE
                    }
                } else {
                    with(binding) {
                        layoutResult.visibility = View.GONE
                        tvNoResultInput.visibility = View.VISIBLE
                    }
                }
            }

            binding.rvHistoryDataStation.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = stationAdapter
            }
        }

        stationAdapter.setOnItemClickCallback(object : StationAdapter.OnItemClickCallback {
            override fun onItemClicked(station: DataStation) {
                val intentResultHistory = Intent(activity, ResultHistoryActivity::class.java)
                intentResultHistory.putExtra(EXTRA_STATION_ID, station)
                startActivity(intentResultHistory)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingHistory.visibility = View.VISIBLE
                tvLabelTaxaIndicator.visibility = View.GONE
                rvHistoryTaxaIndicator.visibility = View.GONE
                tvLabelResultHistory.visibility = View.GONE
                rvHistoryDataStation.visibility = View.GONE
            } else {
                loadingHistory.visibility = View.GONE
                tvLabelTaxaIndicator.visibility = View.VISIBLE
                rvHistoryTaxaIndicator.visibility = View.VISIBLE
                tvLabelResultHistory.visibility = View.VISIBLE
                rvHistoryDataStation.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                tvLabelTaxaIndicator.visibility = View.GONE
                rvHistoryTaxaIndicator.visibility = View.GONE
                tvLabelResultHistory.visibility = View.GONE
                rvHistoryDataStation.visibility = View.GONE
                tvHistoryError.visibility = View.GONE
                loadingHistory.visibility = View.GONE
            } else {
                tvHistoryError.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFinish || isUpdateItem) {
            setListData()
            showListTaxaIndicator()
            showListResultHistory()
            isFinish = false
            isUpdateItem = false
        }
    }

    companion object {
        const val EXTRA_STATION_ID = "extra_station_id"
        var isFinish = false
        var isUpdateItem = false
    }

}