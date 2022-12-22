package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityInputMainParamAbioticBinding
import rekayasaagromarin.ews3swj.model.MainAbioticStation
import rekayasaagromarin.ews3swj.model.Species
import rekayasaagromarin.ews3swj.model.Station
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputaddparam.InputAdditionalParamBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.edit.EditResultHistoryActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.datastation.detail.DetailStationViewModel

class InputMainParamAbioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputMainParamAbioticBinding
    private val detailStationViewModel: DetailStationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputMainParamAbioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initMainAbiotic()
        initEditField()

        with(binding) {
            btnInputMainNext.setOnClickListener { next() }
            btnInputMainBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun initToolbar() {
        with(binding.inputMainAbioticToolbar.viewToolbar) {
            title = getString(R.string.input_main_abiotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initMainAbiotic() {
        val station = intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)
        if (EditResultHistoryActivity.isEdit && station != null) {
            with(detailStationViewModel) {
                detailStationViewModel.setMainAbioticStation(station.stationId)

                isLoading.observe(this@InputMainParamAbioticActivity) {
                    showLoading(it)
                }

                message.observe(this@InputMainParamAbioticActivity) {
                    if (it != "" && it != null) {
                        showMessage(it)
                    }
                }

                isError.observe(this@InputMainParamAbioticActivity) {
                    showError(it)
                }
            }
        }
    }

    private fun initEditField() {
        detailStationViewModel.getMainAbioticStation().observe(this) {
            with(binding) {
                edtInputMainTemperature.setText(it.temperature)
                edtInputMainSalinity.setText(it.salinity)
                edtInputMainDo.setText(it.doParam)
                edtInputMainPh.setText(it.ph)
            }
        }
    }

    private fun next() {
        binding.apply {
            when {

                edtInputMainTemperature.text.toString() == "." ||
                        edtInputMainSalinity.text.toString() == "." ||
                        edtInputMainDo.text.toString() == "." ||
                        edtInputMainPh.text.toString() == "." -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "Fill value with numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainTemperature.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "Temperature is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainTemperature.text.toString().toDouble() > 100 -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "Value of temperature between 0 to 100",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainSalinity.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "Salinity is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainDo.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "DO is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainDo.text.toString().toDouble() > 13 -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "Value of DO between 1 to 13",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainPh.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "PH is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainPh.text.toString().toDouble() > 14 -> {
                    Toast.makeText(
                        this@InputMainParamAbioticActivity,
                        "Value of PH between 0 to 14",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val station =
                        intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)
                    val listSpecies =
                        intent.getParcelableArrayListExtra<Species>(InputMainParamBioticActivity.EXTRA_SPECIES)
                    val mainAbioticStation = MainAbioticStation(
                        salinity = edtInputMainSalinity.text.toString(),
                        temperature = edtInputMainTemperature.text.toString(),
                        doParam = edtInputMainDo.text.toString(),
                        ph = edtInputMainPh.text.toString(),
                        typeOfWater = station!!.typeOfWaterId,
                        geographicalZone = station.geographicalZoneId,
                        userId = station.userId,
                        stationId = station.stationId,
                    )

                    val intentAddParamBiotic = Intent(
                        this@InputMainParamAbioticActivity,
                        InputAdditionalParamBioticActivity::class.java
                    )

                    with(intentAddParamBiotic) {
                        putExtra(InputStationFragment.EXTRA_STATION, station)
                        putExtra(InputMainParamBioticActivity.EXTRA_SPECIES, listSpecies)
                        putExtra(EXTRA_MAIN_ABIOTIC, mainAbioticStation)
                    }

                    startActivity(intentAddParamBiotic)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingInputMainAbiotic.root.visibility = View.VISIBLE
                layoutInputMainAbiotic.visibility = View.GONE
            } else {
                loadingInputMainAbiotic.root.visibility = View.GONE
                layoutInputMainAbiotic.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                layoutInputMainAbiotic.visibility = View.GONE
                tvInputMainAbioticError.visibility = View.VISIBLE
                loadingInputMainAbiotic.root.visibility = View.GONE
            } else {
                tvInputMainAbioticError.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFinish) {
            InputMainParamBioticActivity.isFinish = true
            isFinish = false
            finish()
        }
    }

    companion object {
        const val EXTRA_MAIN_ABIOTIC = "extra_main_abiotic"
        var isFinish = false
    }

}