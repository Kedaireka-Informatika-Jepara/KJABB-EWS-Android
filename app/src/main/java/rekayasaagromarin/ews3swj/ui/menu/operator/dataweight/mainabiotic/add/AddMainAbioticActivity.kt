package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.add

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
import rekayasaagromarin.ews3swj.databinding.ActivityAddMainAbioticBinding
import rekayasaagromarin.ews3swj.model.MainAbiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.DataWeightFragment
import rekayasaagromarin.ews3swj.ui.parameter.AddParameterActivity

class AddMainAbioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMainAbioticBinding
    private val addMainAbioticViewModel: AddMainAbioticViewModel by viewModels()
    private var parameterName = ""
    private var geographicalZone = 0
    private var typeOfWater = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMainAbioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initParameterName()
        initGeographicalZone()
        initTypeOfWater()
        actionButton()
    }

    override fun onResume() {
        super.onResume()

        if(isUpdateItem){
            initParameterName()
            initGeographicalZone()
            initTypeOfWater()
            isUpdateItem = false
        }
    }

    private fun initToolbar() {
        with(binding.actionAddMainToolbar.viewToolbar) {
            title = getString(R.string.add_main_abiotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

//    private fun initParameterName() {
//        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
//            baseContext,
//            R.array.main_abiotic_param,
//            R.layout.item_text,
//        )
//        (binding.tilAddMainParam.editText as? AutoCompleteTextView)?.setAdapter(
//            spinnerArrayAdapter
//        )
//
//        binding.dropdownAddMainParam.onItemClickListener =
//            AdapterView.OnItemClickListener { parent, _, position, _ ->
//                parameterName = parent?.getItemAtPosition(position).toString()
//            }
//    }

    private fun initParameterName() {
        val listParameter = arrayListOf<String>()
        with(addMainAbioticViewModel){
            setParamMainAbiotic()
            getParamMainAbiotic().observe(this@AddMainAbioticActivity){ list ->
                listParameter.clear()
                listParameter.addAll(list)
            }
        }

//        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
//            baseContext,
//            R.array.main_abiotic_param,
//            R.layout.item_text,
//        )

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            listParameter
        )

        (binding.tilAddMainParam.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownAddMainParam.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                parameterName = parent?.getItemAtPosition(position).toString()
            }
    }

    private fun initGeographicalZone() {
        val listZone = arrayListOf<String>()
        with(addMainAbioticViewModel) {
            setGeographicalZone()
            getGeographicalZone().observe(this@AddMainAbioticActivity) { list ->
                listZone.clear()
                listZone.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            listZone
        )
        (binding.tilAddMainZone.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownAddMainZone.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
//                val item = parent?.getItemAtPosition(position).toString()
                geographicalZone = position + 1
            }
    }

    private fun initTypeOfWater() {
        val listWater = arrayListOf<String>()
        with(addMainAbioticViewModel) {
            setTypeOfWater()
            getTypeOfWater().observe(this@AddMainAbioticActivity) { list ->
                listWater.clear()
                listWater.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            listWater
        )
        (binding.tilAddMainWater.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownAddMainWater.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                typeOfWater = position + 1
            }
    }

    private fun actionButton() {
        addParameterMainAbiotic()
        addParameterGeographicalZone()
        addParameterTypeOfWater()
        addMainAbiotic()
        backButton()
    }

    private fun addParameterMainAbiotic() {
        binding.btnAddParameterMainAbiotic.setOnClickListener {
            intent = Intent(this, AddParameterActivity::class.java).apply {
                putExtra(AddParameterActivity.EXTRA_TYPE, 2)
            }
            startActivity(intent)
        }
    }

    private fun addParameterGeographicalZone() {
        binding.btnAddParameterGeographicalZone.setOnClickListener {
            intent = Intent(this, AddParameterActivity::class.java).apply {
                putExtra(AddParameterActivity.EXTRA_TYPE, 4)
            }
            startActivity(intent)
        }
    }

    private fun addParameterTypeOfWater() {
        binding.btnAddParameterTypeOfWater.setOnClickListener {
            intent = Intent(this, AddParameterActivity::class.java).apply {
                putExtra(AddParameterActivity.EXTRA_TYPE, 3)
            }
            startActivity(intent)
        }
    }

    private fun addMainAbiotic() {
        binding.btnAddMainAdd.setOnClickListener {
            binding.apply {
                when {
                    parameterName == "" -> {
                        Toast.makeText(
                            baseContext,
                            "Parameter is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    geographicalZone == 0 -> {
                        Toast.makeText(
                            baseContext,
                            "Geographical zone is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    typeOfWater == 0 -> {
                        Toast.makeText(
                            baseContext,
                            "Type of water is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddMainInitial.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Initial value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddMainFinal.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Final value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddMainWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddMainInitial.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddMainFinal.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddMainWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val mainAbiotic = MainAbiotic(
                            name = parameterName,
                            geographicalZoneId = geographicalZone,
                            typeOfWaterId = typeOfWater,
                            initialValue = edtAddMainInitial.text.toString().toDouble(),
                            finalValue = edtAddMainFinal.text.toString().toDouble(),
                            weight = edtAddMainWeight.text.toString().toDouble()
                        )

                        addMainAbioticViewModel.addMainAbiotic(mainAbiotic)
                    }
                }
            }
        }

        with(addMainAbioticViewModel) {
            isLoading.observe(this@AddMainAbioticActivity) {
                showLoading(it)
            }

            message.observe(this@AddMainAbioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@AddMainAbioticActivity) {
                if (it == 200) {
                    DataWeightFragment.isUpdateItem = true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnAddMainCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingAddIndex.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        var isUpdateItem = false
    }
}