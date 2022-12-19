package rekayasaagromarin.ews3swj.ui.menu.operator.datastation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.adapter.SpeciesAdapter
import rekayasaagromarin.ews3swj.databinding.ActivityDetailStationBinding
import rekayasaagromarin.ews3swj.ui.menu.operator.datastation.DataStationFragment
import java.text.DecimalFormat

class DetailStationActivity : AppCompatActivity() {

    private val detailStationViewModel: DetailStationViewModel by viewModels()
    private lateinit var binding: ActivityDetailStationBinding
    private val speciesAdapter: SpeciesAdapter by lazy { SpeciesAdapter(false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        setDataDetailStation()
        initDataSpecies()
        initIndexAdd()
        initMainAbiotic()
        initResult()
        binding.btnDetailStationDelete.setOnClickListener { confirmDeleteStation() }
        deletedAction()
    }

    private fun confirmDeleteStation() {
        val mDeleteStationFragment = DeleteStationFragment()
        val mFragmentManager = supportFragmentManager
        mDeleteStationFragment.show(
            mFragmentManager, DeleteStationFragment::class.java.simpleName
        )
    }

    fun deleteStation() {
        intent.getStringExtra(DataStationFragment.EXTRA_STATION_ID)
            ?.let { detailStationViewModel.deleteStation(it) }
    }

    private fun deletedAction() {
        detailStationViewModel.isDeleted.observe(this) {
            if (it == 1) {
                DataStationFragment.isUpdateItem = true
                finish()
            }
        }
    }

    private fun initToolbar() {
        with(binding.toolbarDetailStation.viewToolbar) {
            title = intent.getStringExtra(DataStationFragment.EXTRA_STATION_ID)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setDataDetailStation() {
        with(detailStationViewModel) {
            intent.getStringExtra(DataStationFragment.EXTRA_STATION_ID)?.let { setDataSpecies(it) }
            intent.getStringExtra(DataStationFragment.EXTRA_STATION_ID)?.let { setIndexAddStation(it) }
            intent.getStringExtra(DataStationFragment.EXTRA_STATION_ID)?.let { setMainAbioticStation(it) }
            intent.getStringExtra(DataStationFragment.EXTRA_STATION_ID)?.let { setResultStation(it) }

            isLoading.observe(this@DetailStationActivity) {
                showLoading(it)
            }

            message.observe(this@DetailStationActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isError.observe(this@DetailStationActivity) {
                showError(it)
            }
        }
    }

    private fun initDataSpecies() {
        detailStationViewModel.getListSpecies().observe(this) {
            if (it != null) {
                speciesAdapter.setSpecies(it)
            } else {
                finish()
            }
        }

        with(binding.rvDetailStationDataSpecies) {
            layoutManager = LinearLayoutManager(context)
            adapter = speciesAdapter
        }
    }

    private fun initIndexAdd() {
        detailStationViewModel.getIndexAddStation().observe(this) { indexAdd ->
            if (indexAdd != null) {
                with(binding.viewDetailStationAbiotic) {
                    tvResultClay.text =
                        if (indexAdd.wClay == 2.0) "Normal" else indexAdd.clay
                    tvResultConductivity.text =
                        if (indexAdd.wConductivity == 2.0) "Normal" else convertDecimal(indexAdd.conductivity)
                    tvResultDiversity.text =
                        if (indexAdd.wDiversity == 10.0) "Normal" else convertDecimal(indexAdd.diversity)
                    tvResultDominance.text =
                        if (indexAdd.wDominance == 10.0) "Normal" else convertDecimal(indexAdd.dominance)
                    tvResultNumSpecies.text = indexAdd.numberSpecies.toString()
                    tvResultRatio.text =
                        if (indexAdd.wRationCn == 2.0) "Normal" else convertDecimal(indexAdd.rationCn)
                    tvResultSand.text =
                        if (indexAdd.wSand == 2.0) "Normal" else convertDecimal(indexAdd.sand)
                    tvResultSilt.text =
                        if (indexAdd.wSilt == 2.0) "Normal" else convertDecimal(indexAdd.silt)
                    tvResultSimilarity.text =
                        if (indexAdd.wSimilarity == 10.0) "Normal" else convertDecimal(indexAdd.similarity)
                    tvResultAbundance.text = convertDecimal(indexAdd.totalAbundance)
                    tvResultTurbidity.text =
                        if (indexAdd.wTurbidity == 2.0) "Normal" else convertDecimal(indexAdd.turbidity)
                    tvResultIndicatorTaxa.text = convertDecimal(indexAdd.taxaIndicator)
                }
            } else {
                finish()
            }
        }
    }

    private fun initMainAbiotic() {
        detailStationViewModel.getMainAbioticStation().observe(this) { mainAbiotic ->
            if (mainAbiotic != null) {
                with(binding.viewDetailStationAbiotic) {
                    tvResultDo.text =
                        if (mainAbiotic.wDoParam == 3.0) "Normal" else convertDecimal(mainAbiotic.doParam)
                    tvResultPh.text =
                        if (mainAbiotic.wPh == 3.0) "Normal" else convertDecimal(mainAbiotic.ph)
                    tvResultSalinity.text =
                        if (mainAbiotic.wSalinity == 3.0) "Normal" else convertDecimal(mainAbiotic.salinity)
                    tvResultTemperature.text =
                        if (mainAbiotic.wTemperature == 3.0) "Normal" else convertDecimal(mainAbiotic.temperature)
                }
            } else {
                finish()
            }
        }
    }

    private fun initResult() {
        detailStationViewModel.getResultStation().observe(this) { result ->
            if (result != null) {
                with(binding.viewDetailStationResult) {
                    tvResultValue.text = convertDecimal(result.result)
                    tvResultStatus.text = result.status
                    tvResultConclusion.text = result.conclusion
                    tvResultRecommendation.text = result.recommendation
                }
            } else {
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingDetailStation.root.visibility = View.VISIBLE
                layoutDetailStation.visibility = View.GONE
            } else {
                loadingDetailStation.root.visibility = View.GONE
                layoutDetailStation.visibility = View.VISIBLE
                rvDetailStationDataSpecies.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                layoutDetailStation.visibility = View.GONE
                tvDetailStationError.visibility = View.VISIBLE
                loadingDetailStation.root.visibility = View.GONE
            } else {
                tvDetailStationError.visibility = View.GONE
            }
        }
    }

    private fun convertDecimal(value: Double): String {
//        val dec = DecimalFormat("##.##")
//        val decFormat = dec.format(value)
        return String.format("%.2f", value)
    }

}