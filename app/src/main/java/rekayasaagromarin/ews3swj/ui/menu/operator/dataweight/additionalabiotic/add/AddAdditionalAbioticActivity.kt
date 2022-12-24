package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.add

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
import rekayasaagromarin.ews3swj.databinding.ActivityAddAdditionalAbioticBinding
import rekayasaagromarin.ews3swj.model.AdditionalAbiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.DataWeightFragment
import rekayasaagromarin.ews3swj.ui.parameter.AddParameterActivity

class AddAdditionalAbioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAdditionalAbioticBinding
    private val addAdditionalAbioticViewModel: AddAdditionalAbioticViewModel by viewModels()
    private var parameterName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAdditionalAbioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initParameterName()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionAddAdditionalToolbar.viewToolbar) {
            title = getString(R.string.add_additional_abiotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initParameterName() {
        val listParameter = arrayListOf<String>()
        with(addAdditionalAbioticViewModel){
            setParamAdditionalAbiotic()
            getParamAdditionalAbiotic().observe(this@AddAdditionalAbioticActivity) { list ->
                listParameter.clear()
                listParameter.addAll(list)
            }
        }

//        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
//            baseContext,
//            R.array.additional_abiotic_param,
//            R.layout.item_text,
//        )

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            listParameter
        )

        (binding.tilAddAdditionalParam.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownAddAdditionalParam.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                parameterName = parent?.getItemAtPosition(position).toString()
            }
    }

    private fun actionButton() {
        addAdditionalAbiotic()
        addParameterAdditionalAbiotic()
        backButton()
    }

    private fun addAdditionalAbiotic() {
        binding.btnAddAdditionalAdd.setOnClickListener {
            binding.apply {
                when {
                    parameterName == "" -> {
                        Toast.makeText(
                            baseContext,
                            "Parameter is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddAdditionalInitial.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Initial value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddAdditionalFinal.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Final value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddAdditionalWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddAdditionalInitial.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddAdditionalFinal.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddAdditionalWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val additionalAbiotic = AdditionalAbiotic(
                            name = parameterName,
                            initialValue = edtAddAdditionalInitial.text.toString().toDouble(),
                            finalValue = edtAddAdditionalFinal.text.toString().toDouble(),
                            weight = edtAddAdditionalWeight.text.toString().toDouble()
                        )

                        addAdditionalAbioticViewModel.addAdditionalAbiotic(additionalAbiotic)
                    }
                }
            }
        }

        with(addAdditionalAbioticViewModel) {
            isLoading.observe(this@AddAdditionalAbioticActivity) {
                showLoading(it)
            }

            message.observe(this@AddAdditionalAbioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@AddAdditionalAbioticActivity) {
                if (it == 200) {
                    DataWeightFragment.isUpdateItem = true
                    onBackPressed()
                }
            }
        }
    }

    private fun addParameterAdditionalAbiotic() {
        binding.btnAddParameterAdditionalAbiotic.setOnClickListener {
            intent = Intent(this, AddParameterActivity::class.java).apply {
                putExtra(AddParameterActivity.EXTRA_PARAMETER, 5)
            }
            startActivity(intent)
        }
    }

    private fun backButton() {
        binding.btnAddAdditionalCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingAddAdditional.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}