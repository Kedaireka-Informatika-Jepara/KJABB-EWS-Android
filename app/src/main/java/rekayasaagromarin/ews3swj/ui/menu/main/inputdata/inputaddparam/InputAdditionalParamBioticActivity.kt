package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputaddparam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityInputAdditionalParamBioticBinding
import rekayasaagromarin.ews3swj.model.*
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamAbioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.edit.EditResultHistoryActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.datastation.detail.DetailStationViewModel

class InputAdditionalParamBioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputAdditionalParamBioticBinding
    private val inputAddParamViewModel: InputAddParamViewModel by viewModels()
    private val detailStationViewModel: DetailStationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputAdditionalParamBioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initFamily()
        initField()
        initAdditionalStation()
        initEditField()

        with(binding) {
            btnInputAddBack.setOnClickListener { onBackPressed() }
            btnInputAddNext.setOnClickListener { addBiotic() }
        }
    }

    private fun initFamily() {
        inputAddParamViewModel.setFamilyBiotic()
    }

    private fun initToolbar() {
        with(binding.inputAdditionalToolbar.viewToolbar) {
            title = getString(R.string.input_add_param)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initAdditionalStation() {
        val station = intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)
        if (EditResultHistoryActivity.isEdit && station != null) {
            with(detailStationViewModel) {
                setIndexAddStation(station.stationId)

                isLoading.observe(this@InputAdditionalParamBioticActivity) {
                    showLoading(it)
                }

                message.observe(this@InputAdditionalParamBioticActivity) {
                    if (it != "" && it != null) {
                        showMessage(it)
                    }
                }

                isError.observe(this@InputAdditionalParamBioticActivity) {
                    showError(it)
                }
            }
        }
    }

    private fun initEditField() {
        detailStationViewModel.getIndexAddStation().observe(this) {
            with(binding) {
                edtInputAddSimilarity.setText(it.similarity.toString())
                edtInputAddDominance.setText(it.dominance)
                edtInputAddDiversity.setText(it.diversity)
            }
        }
    }

    private fun initField() {
        val listSpecies =
            intent.getParcelableArrayListExtra<Species>(InputMainParamBioticActivity.EXTRA_SPECIES)

        var numberOfAbundance = 0.0
        val setSpecies = ArrayList<String>()
        val setFamily = ArrayList<String>()

        lifecycleScope.launch(Dispatchers.Default) {
            for (i in listSpecies!!.indices) {
                numberOfAbundance += listSpecies[i].abundance
                setSpecies.add(listSpecies[i].species)
                setFamily.add(listSpecies[i].family)
            }

            withContext(Dispatchers.Main) {
                var numberOfFamily = 0.0
                for (i in setFamily.toSet().indices) {
                    inputAddParamViewModel.getListFamilyBiotic()
                        .observe(this@InputAdditionalParamBioticActivity) {
                            it.forEach { family ->
                                if (setFamily.toSet().elementAt(i) == family.family) {
                                    numberOfFamily += family.weight
                                }
                            }
                            binding.edtInputAddIndicatorTaxa.setText(numberOfFamily.toString())
                        }
                }

                with(binding) {
                    edtInputAddSumAbundance.setText(numberOfAbundance.toString())
                    edtInputAddSumSpecies.setText(setSpecies.toSet().size.toString())
                }
            }
        }

        with(inputAddParamViewModel) {
            message.observe(this@InputAdditionalParamBioticActivity) {
                if (it != null && it != "") {
                    showMessage(it)
                }
            }

            isError.observe(this@InputAdditionalParamBioticActivity) {
                showError(it)
            }

            isLoading.observe(this@InputAdditionalParamBioticActivity) {
                showLoading(it)
            }
        }

    }

    private fun addBiotic() {
        binding.apply {
            when {

                edtInputAddSimilarity.text.toString() == "." ||
                        edtInputAddDominance.text.toString() == "." ||
                        edtInputAddDiversity.text.toString() == "." -> {
                    Toast.makeText(
                        this@InputAdditionalParamBioticActivity,
                        "Fill value with numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddSimilarity.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamBioticActivity,
                        "Similarity is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddSimilarity.text.toString().toDouble() > 1 -> {
                    Toast.makeText(
                        this@InputAdditionalParamBioticActivity,
                        "Value of similarity between 0 to 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddDominance.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamBioticActivity,
                        "Dominance is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddDominance.text.toString().toDouble() > 1 -> {
                    Toast.makeText(
                        this@InputAdditionalParamBioticActivity,
                        "Value of dominance between 0 to 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddDiversity.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamBioticActivity,
                        "Diversity is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddDiversity.text.toString().toDouble() > 4 -> {
                    Toast.makeText(
                        this@InputAdditionalParamBioticActivity,
                        "Value of temperature between 0 to 4",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val station =
                        intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)
                    val listSpecies =
                        intent.getParcelableArrayListExtra<Species>(InputMainParamBioticActivity.EXTRA_SPECIES)
                    val mainAbioticStation =
                        intent.getParcelableExtra<MainAbioticStation>(InputMainParamAbioticActivity.EXTRA_MAIN_ABIOTIC)
                    val indexAddStation = IndexAddStation(
                        similarity = edtInputAddSimilarity.text.toString().toDouble(),
                        dominance = edtInputAddDominance.text.toString(),
                        diversity = edtInputAddDiversity.text.toString(),
                        numberSpecies = edtInputAddSumSpecies.text.toString().toInt(),
                        totalAbundance = edtInputAddSumAbundance.text.toString().toDouble(),
                        taxaIndicator = edtInputAddIndicatorTaxa.text.toString().toDouble()
                    )
                    val intentAddAbiotic = Intent(
                        this@InputAdditionalParamBioticActivity,
                        InputAdditionalParamAbioticActivity::class.java
                    )

                    with(intentAddAbiotic) {
                        putExtra(InputStationFragment.EXTRA_STATION, station)
                        putExtra(InputMainParamBioticActivity.EXTRA_SPECIES, listSpecies)
                        putExtra(
                            InputMainParamAbioticActivity.EXTRA_MAIN_ABIOTIC,
                            mainAbioticStation
                        )
                        putExtra(EXTRA_INDEX_ADD, indexAddStation)
                    }

                    startActivity(intentAddAbiotic)
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutInputAddBiotic.visibility = View.GONE
            binding.loadingInputAddBiotic.root.visibility = View.VISIBLE
        } else {
            binding.loadingInputAddBiotic.root.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.layoutInputAddBiotic.visibility = View.GONE
            binding.tvInputIndexBioticError.visibility = View.VISIBLE
        } else {
            binding.layoutInputAddBiotic.visibility = View.VISIBLE
            binding.tvInputIndexBioticError.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFinish) {
            InputMainParamAbioticActivity.isFinish = true
            isFinish = false
            finish()
        }
    }

    companion object {
        const val EXTRA_INDEX_ADD = "extra_index_add"
        var isFinish = false
    }
}