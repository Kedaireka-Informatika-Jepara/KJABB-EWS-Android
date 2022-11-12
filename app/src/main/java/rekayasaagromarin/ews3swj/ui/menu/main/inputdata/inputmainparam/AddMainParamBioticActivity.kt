package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityAddMainParamBioticBinding
import rekayasaagromarin.ews3swj.model.Species
import rekayasaagromarin.ews3swj.model.Station
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment

class AddMainParamBioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMainParamBioticBinding
    private val addMainParamBioticViewModel: AddMainParamBioticViewModel by viewModels()
    private var indexFamily = 0
    private var family = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMainParamBioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initFamily()

        with(binding) {
            btnInputMainAdd.setOnClickListener { addBiotic() }
            btnInputMainCancel.setOnClickListener { onBackPressed() }
        }
    }

    private fun initToolbar() {
        with(binding.addMainBioticToolbar.viewToolbar) {
            title = getString(R.string.add_biotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initFamily() {
        val listFamilyName = arrayListOf<String>()
        with(addMainParamBioticViewModel) {
            setFamily()
            getFamilyName().observe(this@AddMainParamBioticActivity) { list ->
                listFamilyName.clear()
                listFamilyName.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            this,
            R.layout.item_text,
            listFamilyName
        )
        (binding.tilInputMainFamily.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownInputMainFamily.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position).toString()
                indexFamily = position + 1
                family = item
            }

        with(addMainParamBioticViewModel) {
            message.observe(this@AddMainParamBioticActivity) {
                if (it != null && it != "") {
                    showMessage(it)
                }
            }

            isError.observe(this@AddMainParamBioticActivity) {
                showError(it)
            }

            isLoading.observe(this@AddMainParamBioticActivity) {
                showLoading(it)
            }
        }
    }

    private fun addBiotic() {
        binding.apply {
            when {
                edtInputMainSpecies.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@AddMainParamBioticActivity,
                        "Species is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                indexFamily == 0 -> {
                    Toast.makeText(
                        this@AddMainParamBioticActivity,
                        "Family is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainAbundance.text?.isEmpty() == true -> {
                    Toast.makeText(
                        this@AddMainParamBioticActivity,
                        "Abundance is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputMainAbundance.text.toString() == "." -> {
                    Toast.makeText(
                        this@AddMainParamBioticActivity,
                        "Fill value with numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val station =
                        intent.getParcelableExtra<Station>(InputStationFragment.EXTRA_STATION)

                    var taxaIndicator = 0.0

                    addMainParamBioticViewModel.getFamilyWeight().observe(this@AddMainParamBioticActivity) {
                        taxaIndicator = it[indexFamily - 1]
                    }

                    addMainParamBioticViewModel.isSuccess.observe(this@AddMainParamBioticActivity) {
                        if (it) {
                            val species = Species(
                                species = edtInputMainSpecies.text.toString(),
                                family = this@AddMainParamBioticActivity.family,
                                abundance = edtInputMainAbundance.text.toString().toDouble(),
                                userId = station!!.userId,
                                stationId = station.stationId,
                                taxaIndicator = taxaIndicator
                            )
                            InputMainParamBioticActivity.listSpecies.add(species)
                        }
                    }

                    InputMainParamBioticActivity.isUpdateItem = true
                    onBackPressed()
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutInputAddIndex.visibility = View.GONE
            binding.loadingAddIndex.root.visibility = View.VISIBLE
        } else {
            binding.loadingAddIndex.root.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.layoutInputAddIndex.visibility = View.GONE
            binding.tvAddIndexError.visibility = View.VISIBLE
        } else {
            binding.layoutInputAddIndex.visibility = View.VISIBLE
            binding.tvAddIndexError.visibility = View.GONE
        }
    }

}