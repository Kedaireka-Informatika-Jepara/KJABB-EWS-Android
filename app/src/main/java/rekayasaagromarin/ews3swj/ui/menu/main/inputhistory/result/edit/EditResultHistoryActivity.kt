package rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityEditResultHistoryBinding
import rekayasaagromarin.ews3swj.model.DataStation
import rekayasaagromarin.ews3swj.model.Station
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationViewModel
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.InputHistoryFragment
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.ResultHistoryActivity

class EditResultHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditResultHistoryBinding
    private val inputStationViewModel: InputStationViewModel by viewModels()
    private val preferencesManager : PreferencesManager by lazy { PreferencesManager(this) }
    private var geographicalZone = ""
    private var typeOfWater = ""
    private var geographicalZoneId = 0
    private var typeOfWaterId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditResultHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initEditField()
        initGeographicalZone()
        initTypeOfWater()
        proceed()
    }

    private fun initToolbar() {
        with(binding.toolbarEditResult.viewToolbar) {
            title = getString(R.string.input_station)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initEditField() {
        val dataStation =
            intent.getParcelableExtra<DataStation>(InputHistoryFragment.EXTRA_STATION_ID)

        if (dataStation != null) {
            geographicalZone = dataStation.geographicalZone
            geographicalZoneId = dataStation.geographicalZoneId
            typeOfWater = dataStation.typeOfWater
            typeOfWaterId = dataStation.typeOfWaterId
        }

        with(binding) {
            dropdownEditStationZone.setText(dataStation?.geographicalZone)
            dropdownEditStationWater.setText(dataStation?.typeOfWater)
            edtEditStationId.setText(dataStation?.stationId)
        }

    }

    private fun initGeographicalZone() {
        val listZone = arrayListOf<String>()
        with(inputStationViewModel) {
            setGeographicalZone()
            getGeographicalZone().observe(this@EditResultHistoryActivity) { list ->
                listZone.clear()
                listZone.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            this,
            R.layout.item_text,
            listZone
        )
        (binding.tilEditStationZone.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownEditStationZone.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                this.geographicalZone = parent.getItemAtPosition(position).toString()
                this.geographicalZoneId = position + 1
            }

        with(inputStationViewModel) {
            message.observe(this@EditResultHistoryActivity) {
                if (it != null && it != "") {
                    showMessage(it)
                }
            }

            isError.observe(this@EditResultHistoryActivity) {
                showError(it)
            }

            isLoading.observe(this@EditResultHistoryActivity) {
                showLoading(it)
            }
        }

    }

    private fun initTypeOfWater() {
        val listWater = arrayListOf<String>()
        with(inputStationViewModel) {
            setTypeOfWater()
            getTypeOfWater().observe(this@EditResultHistoryActivity) { list ->
                listWater.clear()
                listWater.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            this,
            R.layout.item_text,
            listWater
        )
        (binding.tilEditStationWater.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownEditStationWater.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                this.typeOfWater = parent.getItemAtPosition(position).toString()
                this.typeOfWaterId = position + 1
            }
    }

    private fun proceed() {

        val dataStation =
            intent.getParcelableExtra<DataStation>(InputHistoryFragment.EXTRA_STATION_ID)

        binding.btnEditStationProceed.setOnClickListener {
            val station = Station(
                geographicalZone = this.geographicalZone,
                geographicalZoneId = this.geographicalZoneId,
                typeOfWater = this.typeOfWater,
                typeOfWaterId = this.typeOfWaterId,
                stationId = dataStation!!.stationId,
                userId = preferencesManager. getId()!!
            )

            val intentMainBiotic = Intent(this, InputMainParamBioticActivity::class.java)
            intentMainBiotic.putExtra(EXTRA_EDIT_STATION, station)
            isEdit = true
            startActivity(intentMainBiotic)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutEditStation.visibility = View.GONE
            binding.loadingEditStation.root.visibility = View.VISIBLE
        } else {
            binding.loadingEditStation.root.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.layoutEditStation.visibility = View.GONE
            binding.tvEditStationError.visibility = View.VISIBLE
        } else {
            binding.layoutEditStation.visibility = View.VISIBLE
            binding.tvEditStationError.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        isEdit = false

        if (isFinish){
            ResultHistoryActivity.isFinish = true
            isFinish = false
            finish()
        }
    }

    companion object {
        const val EXTRA_EDIT_STATION = "extra_edit_station"
        var isEdit = false
        var isFinish = false
    }
}