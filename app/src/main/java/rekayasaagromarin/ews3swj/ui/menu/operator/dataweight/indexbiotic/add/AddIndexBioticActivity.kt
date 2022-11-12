package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityAddIndexBioticBinding
import rekayasaagromarin.ews3swj.model.IndexBiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.DataWeightFragment

class AddIndexBioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddIndexBioticBinding
    private val addIndexBioticViewModel: AddIndexBioticViewModel by viewModels()
    private var parameterName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIndexBioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initParameterName()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionAddIndexToolbar.viewToolbar) {
            title = getString(R.string.add_index_biotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initParameterName() {
        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
            baseContext,
            R.array.index_biotic_param,
            R.layout.item_text,
        )
        (binding.tilAddIndexParam.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownAddIndexParam.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                parameterName = parent?.getItemAtPosition(position).toString()
            }
    }

    private fun actionButton() {
        addIndexBiotic()
        backButton()
    }

    private fun addIndexBiotic() {
        binding.btnAddIndexAdd.setOnClickListener {
            binding.apply {
                when {
                    parameterName == "" -> {
                        Toast.makeText(
                            baseContext,
                            "Parameter is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddIndexInitial.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Initial value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddIndexFinal.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Final value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddIndexWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddIndexInitial.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddIndexFinal.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddIndexWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val indexBiotic = IndexBiotic(
                            name = parameterName,
                            initialValue = edtAddIndexInitial.text.toString().toDouble(),
                            finalValue = edtAddIndexFinal.text.toString().toDouble(),
                            weight = edtAddIndexWeight.text.toString().toDouble()
                        )

                        addIndexBioticViewModel.addIndexBiotic(indexBiotic)
                    }
                }
            }
        }

        with(addIndexBioticViewModel) {
            isLoading.observe(this@AddIndexBioticActivity) {
                showLoading(it)
            }

            message.observe(this@AddIndexBioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@AddIndexBioticActivity) {
                if (it == 200) {
                    DataWeightFragment.isUpdateItem = true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnAddIndexCancel.setOnClickListener {
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