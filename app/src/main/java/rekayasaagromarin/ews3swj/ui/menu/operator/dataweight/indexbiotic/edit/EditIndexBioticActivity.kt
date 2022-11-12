package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityEditIndexBioticBinding
import rekayasaagromarin.ews3swj.model.IndexBiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.IndexBioticActionActivity

class EditIndexBioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditIndexBioticBinding
    private val editIndexBioticViewModel: EditIndexBioticViewModel by viewModels()
    private var parameterName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditIndexBioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initEditField()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionEditIndexToolbar.viewToolbar) {
            title = getString(R.string.edit_index_biotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initParameterName(indexParam: String?) {
        parameterName = indexParam!!
        val spinnerArrayAdapter = ArrayAdapter.createFromResource(
            baseContext,
            R.array.index_biotic_param,
            R.layout.item_text,
        )
        (binding.tilEditIndexParam.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownEditIndexParam.setText(indexParam, false)

        binding.dropdownEditIndexParam.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                parameterName = parent?.getItemAtPosition(position).toString()
            }
    }

    private fun initEditField() {
        val indexBiotic =
            intent.getParcelableExtra<IndexBiotic>(IndexBioticActionActivity.EXTRA_INDEX)

        with(binding) {
            initParameterName(indexBiotic?.name)
            edtEditIndexInitial.setText(indexBiotic?.initialValue.toString())
            edtEditIndexFinal.setText(indexBiotic?.finalValue.toString())
            edtEditIndexWeight.setText(indexBiotic?.weight.toString())
        }
    }

    private fun actionButton() {
        editIndexBiotic()
        backButton()
    }

    private fun editIndexBiotic() {
        val index =
            intent.getParcelableExtra<IndexBiotic>(IndexBioticActionActivity.EXTRA_INDEX)
        binding.btnEditIndexEdit.setOnClickListener {
            binding.apply {
                when {
                    edtEditIndexInitial.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Initial value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditIndexFinal.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Final value is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditIndexWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditIndexInitial.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditIndexFinal.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditIndexWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val indexBiotic = IndexBiotic(
                            id = index!!.id,
                            name = parameterName,
                            initialValue = edtEditIndexInitial.text.toString().toDouble(),
                            finalValue = edtEditIndexFinal.text.toString().toDouble(),
                            weight = edtEditIndexWeight.text.toString().toDouble()
                        )

                        editIndexBioticViewModel.editIndexBiotic(indexBiotic)
                    }
                }
            }
        }

        with(editIndexBioticViewModel) {
            isLoading.observe(this@EditIndexBioticActivity) {
                showLoading(it)
            }

            message.observe(this@EditIndexBioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@EditIndexBioticActivity) {
                if (it == 200) {
                    IndexBioticActionActivity.isUpdate = true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnEditIndexCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingEditIndex.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}