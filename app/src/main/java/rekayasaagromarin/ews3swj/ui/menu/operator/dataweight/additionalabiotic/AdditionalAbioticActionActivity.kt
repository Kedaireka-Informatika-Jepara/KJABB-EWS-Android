package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityAdditionalAbioticActionBinding
import rekayasaagromarin.ews3swj.model.AdditionalAbiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.DataWeightFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.delete.DeleteAdditionalAbioticFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.edit.EditAdditionalAbioticActivity
import java.text.DecimalFormat

class AdditionalAbioticActionActivity : AppCompatActivity(), View.OnClickListener {

    private val additionalAbioticActionViewModel: AdditionalAbioticActionViewModel by viewModels()
    private lateinit var binding: ActivityAdditionalAbioticActionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionalAbioticActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdditionalAbiotic()
        initToolbar()
        actionButton()
    }

    private fun initAdditionalAbiotic() {
        setAdditionalAbiotic()
        getAdditionalAbiotic()
    }

    private fun initToolbar() {
        with(binding.additionalAbioticToolbar.viewToolbar) {
            title = getString(R.string.menu_data_weight)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }


    private fun actionButton() {
        with(binding) {
            btnAdditionalAbioticEdit.setOnClickListener(this@AdditionalAbioticActionActivity)
            btnAdditionalAbioticDelete.setOnClickListener(this@AdditionalAbioticActionActivity)
        }
    }

    private fun setAdditionalAbiotic() {
        with(additionalAbioticActionViewModel) {
            setAdditionalAbiotic(intent.getIntExtra(DataWeightFragment.EXTRA_ID, 0))

            isLoading.observe(this@AdditionalAbioticActionActivity) {
                showLoading(it)
            }

            message.observe(this@AdditionalAbioticActionActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(this@AdditionalAbioticActionActivity) {
                showError(it)
            }
        }
    }

    private fun getAdditionalAbiotic() {
        additionalAbioticActionViewModel.getAdditionalAbiotic().observe(this) { additionalAbiotic ->
            if (additionalAbiotic != AdditionalAbiotic()) {
                with(binding) {
                    tvAdditionalAbioticId.text = additionalAbiotic.id.toString()
                    tvAdditionalAbioticName.text = additionalAbiotic.name
                    tvAdditionalAbioticInitial.text = convertDecimal(additionalAbiotic.initialValue)
                    tvAdditionalAbioticFinal.text = convertDecimal(additionalAbiotic.finalValue)
                    tvAdditionalAbioticWeight.text = convertDecimal(additionalAbiotic.weight)
                }
            } else {
                finish()
                DataWeightFragment.isUpdateItem = true
                Toast.makeText(this, "Data additional abiotic tidak ditemukan", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun deleteAdditionalAbiotic() {
        with(additionalAbioticActionViewModel) {
            deleteAdditionalAbiotic(intent.getIntExtra(DataWeightFragment.EXTRA_ID, 0))

            isLoading.observe(this@AdditionalAbioticActionActivity) {
                showLoading(it)
            }

            deleteMessage.observe(this@AdditionalAbioticActionActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isDelete.observe(this@AdditionalAbioticActionActivity) {
                if (it) {
                    DataWeightFragment.isUpdateItem = true
                    onBackPressed()
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                cardAdditionalInfo.visibility = View.GONE
                cardAdditionalAction.visibility = View.GONE
                loadingAdditionalAbiotic.visibility = View.VISIBLE
            } else {
                loadingAdditionalAbiotic.visibility = View.GONE
            }
        }
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                loadingAdditionalAbiotic.visibility = View.GONE
                cardAdditionalInfo.visibility = View.GONE
                cardAdditionalAction.visibility = View.GONE
                tvAdditionalAbioticError.visibility = View.VISIBLE
            }else{
                cardAdditionalInfo.visibility = View.VISIBLE
                cardAdditionalAction.visibility = View.VISIBLE
                tvAdditionalAbioticError.visibility = View.GONE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun convertDecimal(value: Double): String {
        return String.format("%.2f", value)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_additional_abiotic_edit -> {
                val additionalAbiotic = AdditionalAbiotic(
                    id = binding.tvAdditionalAbioticId.text.toString().toInt(),
                    name = binding.tvAdditionalAbioticName.text.toString(),
                    initialValue = binding.tvAdditionalAbioticInitial.text.toString().toDouble(),
                    finalValue = binding.tvAdditionalAbioticFinal.text.toString().toDouble(),
                    weight = binding.tvAdditionalAbioticWeight.text.toString().toDouble()
                )
                val intentEditAdditionalAbiotic =
                    Intent(this, EditAdditionalAbioticActivity::class.java)
                intentEditAdditionalAbiotic.putExtra(EXTRA_ADDITIONAL, additionalAbiotic)
                startActivity(intentEditAdditionalAbiotic)
            }

            R.id.btn_additional_abiotic_delete -> {
                val mDeleteAdditionalAbiotic = DeleteAdditionalAbioticFragment()
                val mFragmentManager = this.supportFragmentManager
                mDeleteAdditionalAbiotic.show(
                    mFragmentManager,
                    DeleteAdditionalAbioticFragment::class.java.simpleName
                )
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (isUpdate) {
            DataWeightFragment.isUpdateItem = true
            initAdditionalAbiotic()
            isUpdate = false
        }
    }

    companion object {
        const val EXTRA_ADDITIONAL = "extra_additional"
        var isUpdate = false
    }
}