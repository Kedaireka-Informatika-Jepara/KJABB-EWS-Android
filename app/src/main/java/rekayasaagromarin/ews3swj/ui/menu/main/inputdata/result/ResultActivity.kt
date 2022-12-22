package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.adapter.SpeciesAdapter
import rekayasaagromarin.ews3swj.databinding.ActivityResultBinding
import rekayasaagromarin.ews3swj.model.IndexAddStation
import rekayasaagromarin.ews3swj.model.MainAbioticStation
import rekayasaagromarin.ews3swj.model.Result
import rekayasaagromarin.ews3swj.model.Species
import rekayasaagromarin.ews3swj.model.Station
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputaddparam.InputAdditionalParamAbioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamAbioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.edit.EditResultHistoryActivity
import java.text.DecimalFormat
import java.util.ArrayList

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val resultViewModel: ResultViewModel by viewModels()
    private val speciesAdapter: SpeciesAdapter by lazy { SpeciesAdapter(false) }
    private val resultCount = arrayListOf<Double>()
    private var value = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initResultData()
        getCountData()
        binding.btnResultSave.setOnClickListener {
            saveStation()
            saveSpecies()
            saveIndexAdd()
            saveMain()
            saveResult()
        }
        backToStationInput()
    }

    private fun backToStationInput() {
        resultViewModel.isSavedResult.observe(this@ResultActivity) {
            if (it) {
                Toast.makeText(
                    this@ResultActivity,
                    "Berhasil menyimpan hasil",
                    Toast.LENGTH_SHORT
                ).show()
                InputAdditionalParamAbioticActivity.isFinish = true
                finish()
            }
        }
    }

    private fun initToolbar() {
        with(binding.toolbarResult.viewToolbar) {
            title = getString(R.string.result)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setCountData() {
        val index =
            intent.getParcelableExtra<IndexAddStation>(InputAdditionalParamAbioticActivity.EXTRA_INDEX_ADD)
        val main =
            intent.getParcelableExtra<MainAbioticStation>(InputMainParamAbioticActivity.EXTRA_MAIN_ABIOTIC)
        if (index != null) {
            with(resultViewModel) {
                indexBioticCount(index)
                isSuccessIndexCount.observe(this@ResultActivity) {
                    if (it) {
                        additionalAbioticCount(index)
                    }
                }
            }
        }

        if (main != null) {
            with(resultViewModel) {
                isSuccessAddCount.observe(this@ResultActivity) {
                    if (it) {
                        mainAbioticCount(main)
                    }
                }
            }
        }

    }

    private fun getCountData() {
        with(resultViewModel) {
            getIndexCount().observe(this@ResultActivity) {
                resultCount.add(it.weight)
            }

            getAdditionalCount().observe(this@ResultActivity) {
                resultCount.add(it.weight)
            }

            getMainCount().observe(this@ResultActivity) {
                resultCount.add(it.weight)
                setResult(resultCount)
            }

            isLoading.observe(this@ResultActivity) {
                showLoading(it)
            }

            isError.observe(this@ResultActivity) {
                showError(it)
            }

            message.observe(this@ResultActivity) {
                if (it != null) {
                    showMessage(it)
                }
            }

            isLoading.observe(this@ResultActivity) {
                showLoading(it)
            }
        }
    }

    private fun initResultData() {
        val station =
            intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)
        val listSpecies =
            intent.getParcelableArrayListExtra<Species>(InputMainParamBioticActivity.EXTRA_SPECIES)
        val indexAdd =
            intent.getParcelableExtra<IndexAddStation>(InputAdditionalParamAbioticActivity.EXTRA_INDEX_ADD)
        val main =
            intent.getParcelableExtra<MainAbioticStation>(InputMainParamAbioticActivity.EXTRA_MAIN_ABIOTIC)

        setBiotic(listSpecies)
        setAbiotic(indexAdd, main)
        setCountData()
        setStation(station)
    }

    private fun setStation(station: Station?) {
        with(binding.viewStation) {
            if (station != null) {
                tvStationId.text = station.stationId
                tvStationZone.text = station.geographicalZone
                tvStationWater.text = station.typeOfWater
            }
        }
    }

    private fun setResult(resultCount: ArrayList<Double>) {
        val indexAdd =
            intent.getParcelableExtra<IndexAddStation>(InputAdditionalParamAbioticActivity.EXTRA_INDEX_ADD)

        resultCount.forEach {
            this.value += it
        }

        value -= indexAdd!!.taxaIndicator

        binding.viewResult.tvResultValue.text = convertDecimal(value)
        when {
            value in 55.51..74.00 -> {
                with(binding.viewResult) {
                    tvResultStatus.text = resources.getStringArray(R.array.status)[0]
                    tvResultConclusion.text = resources.getStringArray(R.array.conclusion)[0]
                    tvResultRecommendation.text =
                        resources.getStringArray(R.array.recommendation)[0]
                }
            }
            value in 37.01..55.50 -> {
                with(binding.viewResult) {
                    tvResultStatus.text = resources.getStringArray(R.array.status)[1]
                    tvResultConclusion.text = resources.getStringArray(R.array.conclusion)[1]
                    tvResultRecommendation.text =
                        resources.getStringArray(R.array.recommendation)[1]
                }
            }
            value in 18.51..37.00 -> {
                with(binding.viewResult) {
                    tvResultStatus.text = resources.getStringArray(R.array.status)[2]
                    tvResultConclusion.text = resources.getStringArray(R.array.conclusion)[2]
                    tvResultRecommendation.text =
                        resources.getStringArray(R.array.recommendation)[2]
                }
            }
            value < 18.51 -> {
                with(binding.viewResult) {
                    tvResultStatus.text = resources.getStringArray(R.array.status)[3]
                    tvResultConclusion.text = resources.getStringArray(R.array.conclusion)[3]
                    tvResultRecommendation.text =
                        resources.getStringArray(R.array.recommendation)[3]
                }
            }
        }
    }

    private fun setBiotic(listSpecies: ArrayList<Species>?) {
        listSpecies?.let { speciesAdapter.setSpecies(it) }

        with(binding.rvResultDataSpecies) {
            layoutManager = LinearLayoutManager(context)
            adapter = speciesAdapter
        }
    }

    private fun setAbiotic(indexAdd: IndexAddStation?, main: MainAbioticStation?) {
        with(binding.viewAbiotic) {
            if (indexAdd != null && main != null) {
                tvResultClay.text = indexAdd.clay
                tvResultConductivity.text = indexAdd.conductivity
                tvResultDo.text = main.doParam
                tvResultDiversity.text = indexAdd.diversity
                tvResultDominance.text = indexAdd.dominance
                tvResultNumSpecies.text = indexAdd.numberSpecies.toString()
                tvResultPh.text = main.ph
                tvResultRatio.text = indexAdd.rationCn
                tvResultSalinity.text = main.salinity
                tvResultSand.text = indexAdd.sand
                tvResultSilt.text = indexAdd.silt
                tvResultSimilarity.text = indexAdd.similarity
                tvResultTemperature.text = main.temperature
                tvResultAbundance.text = convertDecimal(indexAdd.totalAbundance)
                tvResultTurbidity.text = indexAdd.turbidity
                tvResultIndicatorTaxa.text = convertDecimal(indexAdd.taxaIndicator)
            }
        }
    }

    private fun saveStation() {
        val station =
            intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)

        if (EditResultHistoryActivity.isEdit) {
            station?.let { resultViewModel.editStation(it) }
        } else {
            station?.let { resultViewModel.saveStation(it) }
        }
    }

    private fun saveSpecies() {
        val listSpecies =
            intent.getParcelableArrayListExtra<Species>(InputMainParamBioticActivity.EXTRA_SPECIES)
        if (EditResultHistoryActivity.isEdit) {
            resultViewModel.isSavedStation.observe(this@ResultActivity) {
                if (it && listSpecies != null) {
                    resultViewModel.deleteSpecies(listSpecies)
                }
            }

            resultViewModel.isDeletedSpecies.observe(this@ResultActivity) {
                if (it && listSpecies != null) {
                    resultViewModel.saveSpecies(listSpecies)
                }
            }
        } else {
            resultViewModel.isSavedStation.observe(this@ResultActivity) {
                if (it && listSpecies != null) {
                    resultViewModel.saveSpecies(listSpecies)
                }
            }
        }
    }

    private fun saveIndexAdd() {
        val indexAdd =
            intent.getParcelableExtra<IndexAddStation>(InputAdditionalParamAbioticActivity.EXTRA_INDEX_ADD)

        if (EditResultHistoryActivity.isEdit) {
            resultViewModel.isSavedSpecies.observe(this@ResultActivity) {
                if (it && indexAdd != null) {
                    resultViewModel.editIndexAdd(indexAdd)
                }
            }
        } else {
            resultViewModel.isSavedSpecies.observe(this@ResultActivity) {
                if (it && indexAdd != null) {
                    resultViewModel.saveIndexAdd(indexAdd)
                }
            }
        }
    }

    private fun saveMain() {
        val main =
            intent.getParcelableExtra<MainAbioticStation>(InputMainParamAbioticActivity.EXTRA_MAIN_ABIOTIC)

        if (EditResultHistoryActivity.isEdit) {
            resultViewModel.isSavedIndexAdd.observe(this@ResultActivity) {
                if (it && main != null) {
                    resultViewModel.editMain(main)
                }
            }
        } else {
            resultViewModel.isSavedIndexAdd.observe(this@ResultActivity) {
                if (it && main != null) {
                    resultViewModel.saveMain(main)
                }
            }
        }
    }

    private fun saveResult() {
        val station =
            intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)

        val result = Result(
            result = this.value,
            status = binding.viewResult.tvResultStatus.text.toString(),
            conclusion = binding.viewResult.tvResultConclusion.text.toString(),
            recommendation = binding.viewResult.tvResultRecommendation.text.toString(),
            stationId = station!!.stationId,
            userId = station.userId
        )

        if (EditResultHistoryActivity.isEdit) {
            resultViewModel.isSavedMain.observe(this@ResultActivity) {
                resultViewModel.editResult(result)
            }
        } else {
            resultViewModel.isSavedMain.observe(this@ResultActivity) {
                resultViewModel.saveResult(result)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                layoutResult.visibility = View.GONE
                loadingResult.root.visibility = View.VISIBLE
            } else {
                loadingResult.root.visibility = View.GONE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                layoutResult.visibility = View.GONE
                tvResultError.visibility = View.VISIBLE
            } else {
                layoutResult.visibility = View.VISIBLE
                rvResultDataSpecies.visibility = View.VISIBLE
                tvResultError.visibility = View.GONE
            }
        }
    }

    private fun convertDecimal(value: Double): String {
        return String.format("%.2f", value)
    }
}