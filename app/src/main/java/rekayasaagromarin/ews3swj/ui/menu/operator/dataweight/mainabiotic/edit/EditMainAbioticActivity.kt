package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityEditMainAbioticBinding
import rekayasaagromarin.ews3swj.model.MainAbiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.MainAbioticActionActivity

class EditMainAbioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditMainAbioticBinding
    private val editMainAbioticViewModel: EditMainAbioticViewModel by viewModels()
    private var parameterName = ""
    private var geographicalZoneId = 0
    private var typeOfWaterId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMainAbioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initEditField()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionEditMainToolbar.viewToolbar) {
            title = getString(R.string.edit_main_abiotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initParameterName(indexParam: String?) {
        parameterName = indexParam!!
        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
            baseContext,
            R.array.main_abiotic_param,
            R.layout.item_text,
        )
        (binding.tilEditMainParam.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownEditMainParam.setText(indexParam, false)

        binding.dropdownEditMainParam.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                parameterName = parent?.getItemAtPosition(position).toString()
            }
    }

    private fun initGeographicalZone(idZone: Int?, indexZone: String?) {
        geographicalZoneId = idZone!!
        val listZone = arrayListOf<String>()
        with(editMainAbioticViewModel) {
            setGeographicalZone()
            getGeographicalZone().observe(this@EditMainAbioticActivity) { list ->
                listZone.clear()
                listZone.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            listZone
        )
        (binding.tilEditMainZone.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownEditMainZone.setText(indexZone, false)

        binding.dropdownEditMainZone.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                geographicalZoneId = position + 1
            }
    }

    private fun initTypeOfWater(idWater: Int?, indexWater: String?) {
        typeOfWaterId = idWater!!
        val listWater = arrayListOf<String>()
        with(editMainAbioticViewModel) {
            setTypeOfWater()
            getTypeOfWater().observe(this@EditMainAbioticActivity) { list ->
                listWater.clear()
                listWater.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            listWater
        )
        (binding.tilEditMainWater.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownEditMainWater.setText(indexWater, false)

        binding.dropdownEditMainWater.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                typeOfWaterId = position + 1
            }
    }

    private fun initEditField() {
        val mainAbiotic =
            intent.getParcelableExtra<MainAbiotic>(MainAbioticActionActivity.EXTRA_MAIN)
        initParameterName(mainAbiotic?.name)
        initGeographicalZone(mainAbiotic?.geographicalZoneId, mainAbiotic?.geographicalZone)
        initTypeOfWater(mainAbiotic?.typeOfWaterId, mainAbiotic?.typeOfWater)

        with(binding) {
            edtEditMainInitial.setText(mainAbiotic?.initialValue.toString())
            edtEditMainFinal.setText(mainAbiotic?.finalValue.toString())
            edtEditMainWeight.setText(mainAbiotic?.weight.toString())
        }
    }

    private fun actionButton() {
        editMainAbiotic()
        backButton()
    }

    private fun editMainAbiotic() {
        val main = intent.getParcelableExtra<MainAbiotic>(MainAbioticActionActivity.EXTRA_MAIN)
        binding.btnEditMainEdit.setOnClickListener {
            binding.apply {
                when {
                    parameterName == "" -> {
                        Toast.makeText(
                            baseContext,
                            "Parameter is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    geographicalZoneId == 0 -> {
                        Toast.makeText(
                            baseContext,
                            "Geographical zone is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    typeOfWaterId == 0 -> {
                        Toast.makeText(
                            baseContext,
                            "Type of water is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditMainInitial.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Initial value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditMainFinal.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Final value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditMainWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditMainInitial.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditMainFinal.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditMainWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val mainAbiotic = MainAbiotic(
                            id = main!!.id,
                            name = parameterName,
                            geographicalZoneId = geographicalZoneId,
                            typeOfWaterId = typeOfWaterId,
                            initialValue = edtEditMainInitial.text.toString().toDouble(),
                            finalValue = edtEditMainFinal.text.toString().toDouble(),
                            weight = edtEditMainWeight.text.toString().toDouble()
                        )

                        editMainAbioticViewModel.editMainAbiotic(mainAbiotic)
                    }
                }
            }
        }

        with(editMainAbioticViewModel) {
            isLoading.observe(this@EditMainAbioticActivity) {
                showLoading(it)
            }

            message.observe(this@EditMainAbioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@EditMainAbioticActivity) {
                if (it == 200) {
                    MainAbioticActionActivity.isUpdate = true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnEditMainCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingAddIndex.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}