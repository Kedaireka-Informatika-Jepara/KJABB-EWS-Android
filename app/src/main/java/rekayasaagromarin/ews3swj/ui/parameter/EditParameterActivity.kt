package rekayasaagromarin.ews3swj.ui.parameter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.databinding.ActivityEditParameterBinding
import rekayasaagromarin.ews3swj.model.Parameter
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment
import rekayasaagromarin.ews3swj.ui.menu.main.parameter.ParameterFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.add.AddAdditionalAbioticActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.add.AddIndexBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.add.AddMainAbioticActivity

class EditParameterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditParameterBinding
    private var parameter: Parameter? = null
    private val editParameterViewModel : EditParameterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditParameterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parameter = intent.getParcelableExtra<Parameter>(EXTRA_PARAMETER)

        binding.etEditParameterName.setText(parameter?.name)
        binding.etEditParameterDescription.setText(parameter?.description)

        initToolbar()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionEditParameterToolbar.viewToolbar) {
            title = getString(rekayasaagromarin.ews3swj.R.string.add_index_biotic)
            setNavigationOnClickListener {
                onBackPressed()
                finish()
            }
        }
    }

    private fun actionButton() {
        saveParameter()
        backButton()
    }

    private fun saveParameter() {
        binding.btnEditParameterSave.setOnClickListener {
            binding.apply {
                when {
                    etEditParameterName.text.isNullOrEmpty() -> {
                        Toast.makeText(
                            this@EditParameterActivity,
                            "Nama parameter tidak boleh kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    etEditParameterDescription.text.isNullOrEmpty() -> {
                        Toast.makeText(
                            this@EditParameterActivity,
                            "Deskripsi parameter tidak boleh kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        val parameter = Parameter(
                            id = parameter!!.id,
                            name = etEditParameterName.text.toString(),
                            type = parameter!!.type,
                            description = etEditParameterDescription.text.toString(),
                        )
                        editParameterViewModel.editParameter(parameter)

                    }
                }
            }
        }

        with(editParameterViewModel) {
            isLoading.observe(this@EditParameterActivity) {
                showLoading(it)
            }

            message.observe(this@EditParameterActivity) {
                if (it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@EditParameterActivity) {
                if (it == 200) {
                    ParameterFragment.isUpdate = true
                    finish()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnEditParameterCancel.setOnClickListener {
//            onBackPressed()
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingAddAdditional.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_PARAMETER = "extra_parameter"
    }
}