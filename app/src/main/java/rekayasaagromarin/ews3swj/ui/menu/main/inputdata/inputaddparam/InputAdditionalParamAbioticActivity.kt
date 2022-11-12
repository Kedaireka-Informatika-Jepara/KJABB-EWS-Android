package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputaddparam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.databinding.ActivityInputAdditionalParamAbioticBinding
import rekayasaagromarin.ews3swj.model.IndexAddStation
import rekayasaagromarin.ews3swj.model.MainAbioticStation
import rekayasaagromarin.ews3swj.model.Species
import rekayasaagromarin.ews3swj.model.Station
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamAbioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.result.ResultActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.edit.EditResultHistoryActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.datastation.detail.DetailStationViewModel

class InputAdditionalParamAbioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputAdditionalParamAbioticBinding
    private val detailStationViewModel: DetailStationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputAdditionalParamAbioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initAdditionalStation()
        initEditField()

        with(binding) {
            btnInputAddBack.setOnClickListener { onBackPressed() }
            btnInputAddCount.setOnClickListener { addAbiotic() }
        }
    }

    private fun initToolbar() {
        with(binding.inputAdditionalToolbar.viewToolbar) {
            title = getString(rekayasaagromarin.ews3swj.R.string.input_add_param)
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

                isLoading.observe(this@InputAdditionalParamAbioticActivity) {
                    showLoading(it)
                }

                message.observe(this@InputAdditionalParamAbioticActivity) {
                    if (it != "" && it != null) {
                        showMessage(it)
                    }
                }

                isError.observe(this@InputAdditionalParamAbioticActivity) {
                    showError(it)
                }
            }
        }
    }

    private fun initEditField() {
        detailStationViewModel.getIndexAddStation().observe(this) {
            with(binding) {
                edtInputAddConductivity.setText(it.conductivity.toString())
                edtInputAddRatio.setText(it.rationCn.toString())
                edtInputAddTurbidity.setText(it.turbidity.toString())
                edtInputAddSand.setText(it.sand.toString())
                edtInputAddClay.setText(it.clay.toString())
                edtInputAddSilt.setText(it.silt.toString())
            }
        }
    }

    private fun addAbiotic() {
        binding.apply {
            when {

                edtInputAddConductivity.text.toString() == "." ||
                        edtInputAddRatio.text.toString() == "." ||
                        edtInputAddTurbidity.text.toString() == "." ||
                        edtInputAddSand.text.toString() == "." ||
                        edtInputAddClay.text.toString() == "." ||
                        edtInputAddTurbidity.text.toString() == "." -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Fill value with numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddConductivity.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Conductivity is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddRatio.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Ratio is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddTurbidity.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Turbidity is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddSand.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Sand is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddSand.text.toString().toDouble() > 100 -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Value of clay between 0 to 100",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddClay.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Clay is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                edtInputAddClay.text.toString().toDouble() > 20 -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Value of clay between 0 to 20",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddSilt.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Silt is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputAddSilt.text.toString().toDouble() > 100 -> {
                    Toast.makeText(
                        this@InputAdditionalParamAbioticActivity,
                        "Value of silt between 0 to 100",
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
                    val indexAddBiotic = intent.getParcelableExtra<IndexAddStation>(
                        InputAdditionalParamBioticActivity.EXTRA_INDEX_ADD
                    )
                    val indexAddStation = indexAddBiotic?.copy(
                        conductivity = binding.edtInputAddConductivity.text.toString().toDouble(),
                        rationCn = binding.edtInputAddRatio.text.toString().toDouble(),
                        turbidity = binding.edtInputAddTurbidity.text.toString().toDouble(),
                        sand = binding.edtInputAddSand.text.toString().toDouble(),
                        clay = binding.edtInputAddClay.text.toString().toDouble(),
                        silt = binding.edtInputAddSilt.text.toString().toDouble(),
                        stationId = station!!.stationId,
                        userId = station.userId
                    )

                    val intentResult =
                        Intent(this@InputAdditionalParamAbioticActivity, ResultActivity::class.java)

                    with(intentResult) {
                        putExtra(InputStationFragment.EXTRA_STATION, station)
                        putExtra(InputMainParamBioticActivity.EXTRA_SPECIES, listSpecies)
                        putExtra(
                            InputMainParamAbioticActivity.EXTRA_MAIN_ABIOTIC,
                            mainAbioticStation
                        )
                        putExtra(EXTRA_INDEX_ADD, indexAddStation)
                    }

                    startActivity(intentResult)
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutInputAddAbiotic.visibility = View.GONE
            binding.loadingInputAddAbiotic.root.visibility = View.VISIBLE
        } else {
            binding.loadingInputAddAbiotic.root.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.layoutInputAddAbiotic.visibility = View.GONE
            binding.tvInputAddAbioticError.visibility = View.VISIBLE
        } else {
            binding.layoutInputAddAbiotic.visibility = View.VISIBLE
            binding.tvInputAddAbioticError.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFinish) {
            InputAdditionalParamBioticActivity.isFinish = true
            isFinish = false
            finish()
        }
    }

    companion object {
        const val EXTRA_INDEX_ADD = "extra_index_add"
        var isFinish = false
    }
}