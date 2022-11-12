package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityEditAdditionalAbioticBinding
import rekayasaagromarin.ews3swj.model.AdditionalAbiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.AdditionalAbioticActionActivity

class EditAdditionalAbioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAdditionalAbioticBinding
    private val editAdditionalAbioticViewModel: EditAdditionalAbioticViewModel by viewModels()
    private var parameterName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdditionalAbioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initEditField()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionEditAdditionalToolbar.viewToolbar) {
            title = getString(R.string.edit_additional_abiotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initParameterName(indexParam: String?) {
        parameterName = indexParam!!
        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
            baseContext,
            R.array.additional_abiotic_param,
            R.layout.item_text,
        )
        (binding.tilEditAdditionalParam.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownEditAdditionalParam.setText(indexParam, false)

        binding.dropdownEditAdditionalParam.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                parameterName = parent?.getItemAtPosition(position).toString()
            }
    }

    private fun initEditField() {
        val additionalAbiotic =
            intent.getParcelableExtra<AdditionalAbiotic>(AdditionalAbioticActionActivity.EXTRA_ADDITIONAL)

        with(binding) {
            initParameterName(additionalAbiotic?.name)
            edtEditAdditionalInitial.setText(additionalAbiotic?.initialValue.toString())
            edtEditAdditionalFinal.setText(additionalAbiotic?.finalValue.toString())
            edtEditAdditionalWeight.setText(additionalAbiotic?.weight.toString())
        }
    }

    private fun actionButton() {
        editAdditionalAbiotic()
        backButton()
    }

    private fun editAdditionalAbiotic() {
        val index =
            intent.getParcelableExtra<AdditionalAbiotic>(AdditionalAbioticActionActivity.EXTRA_ADDITIONAL)
        Log.d("IDE", "editAdditionalAbiotic: ${index?.id}")
        binding.btnEditAdditionalEdit.setOnClickListener {
            binding.apply {
                when {
                    edtEditAdditionalInitial.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Initial value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditAdditionalFinal.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Final value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditAdditionalWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditAdditionalInitial.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditAdditionalFinal.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditAdditionalWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val additionalAbiotic = AdditionalAbiotic(
                            id = index!!.id,
                            name = parameterName,
                            initialValue = edtEditAdditionalInitial.text.toString().toDouble(),
                            finalValue = edtEditAdditionalInitial.text.toString().toDouble(),
                            weight = edtEditAdditionalWeight.text.toString().toDouble()
                        )

                        editAdditionalAbioticViewModel.editAdditionalAbiotic(additionalAbiotic)
                    }
                }
            }
        }

        with(editAdditionalAbioticViewModel) {
            isLoading.observe(this@EditAdditionalAbioticActivity) {
                showLoading(it)
            }

            message.observe(this@EditAdditionalAbioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@EditAdditionalAbioticActivity) {
                if (it == 200) {
                    AdditionalAbioticActionActivity.isUpdate =
                        true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnEditAdditionalCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingEditAdditional.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}