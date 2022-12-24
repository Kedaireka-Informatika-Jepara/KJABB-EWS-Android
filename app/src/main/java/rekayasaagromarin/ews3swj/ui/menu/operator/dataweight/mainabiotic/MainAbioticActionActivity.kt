package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityMainAbioticActionBinding
import rekayasaagromarin.ews3swj.model.MainAbiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.DataWeightFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.delete.DeleteMainAbioticFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.edit.EditMainAbioticActivity
import java.text.DecimalFormat

class MainAbioticActionActivity : AppCompatActivity(), View.OnClickListener {

    private val mainAbioticActionViewModel: MainAbioticActionViewModel by viewModels()
    private lateinit var binding: ActivityMainAbioticActionBinding
    private var geographicalZoneId = -1
    private var typeOfWaterId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAbioticActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initMainAbiotic()
        actionButton()
    }

    private fun initMainAbiotic() {
        setMainAbiotic()
        getMainAbiotic()
    }

    private fun initToolbar() {
        with(binding.mainAbioticToolbar.viewToolbar) {
            title = getString(R.string.menu_data_weight)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }


    private fun actionButton() {
        with(binding) {
            btnMainAbioticEdit.setOnClickListener(this@MainAbioticActionActivity)
            btnMainAbioticDelete.setOnClickListener(this@MainAbioticActionActivity)
        }
    }

    private fun setMainAbiotic() {
        with(mainAbioticActionViewModel) {
            setMainAbiotic(
                intent.getIntExtra(DataWeightFragment.EXTRA_ID, 0)
            )

            isLoading.observe(this@MainAbioticActionActivity) {
                showLoading(it)
            }

            message.observe(this@MainAbioticActionActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(this@MainAbioticActionActivity) {
                showError(it)
            }
        }
    }

    private fun getMainAbiotic() {
        mainAbioticActionViewModel.getMainAbiotic().observe(this) { mainAbiotic ->
            if (mainAbiotic != MainAbiotic()) {
                with(binding) {
                    geographicalZoneId = mainAbiotic.geographicalZoneId
                    typeOfWaterId = mainAbiotic.typeOfWaterId
                    tvMainAbioticId.text = mainAbiotic.id.toString()
                    tvMainAbioticName.text = mainAbiotic.name
                    tvMainAbioticZone.text = mainAbiotic.geographicalZone
                    tvMainAbioticWater.text = mainAbiotic.typeOfWater
                    tvMainAbioticInitial.text = mainAbiotic.initialValue.toString()
                    tvMainAbioticFinal.text = mainAbiotic.finalValue.toString()
                    tvMainAbioticWeight.text = mainAbiotic.weight.toString()
                }
            } else {
                finish()
                DataWeightFragment.isUpdateItem = true
                Toast.makeText(this, "Data main abiotic tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteMainAbiotic() {
        with(mainAbioticActionViewModel) {
            deleteMainAbiotic(intent.getIntExtra(DataWeightFragment.EXTRA_ID, 0))

            isLoading.observe(this@MainAbioticActionActivity) {
                showLoading(it)
            }

            deleteMessage.observe(this@MainAbioticActionActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isDelete.observe(this@MainAbioticActionActivity) {
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
                cardMainInfo.visibility = View.GONE
                cardMainAction.visibility = View.GONE
                loadingMainAbiotic.visibility = View.VISIBLE
            } else {
                loadingMainAbiotic.visibility = View.GONE
            }
        }
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                loadingMainAbiotic.visibility = View.GONE
                cardMainInfo.visibility = View.GONE
                cardMainAction.visibility = View.GONE
                tvMainAbioticError.visibility = View.VISIBLE
            } else {
                cardMainInfo.visibility = View.VISIBLE
                cardMainAction.visibility = View.VISIBLE
                tvMainAbioticError.visibility = View.GONE
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
            R.id.btn_main_abiotic_edit -> {
                val intentEditMainAbiotic = Intent(this, EditMainAbioticActivity::class.java)
                val mainAbiotic = MainAbiotic(
                    id = binding.tvMainAbioticId.text.toString().toInt(),
                    name = binding.tvMainAbioticName.text.toString(),
                    geographicalZoneId = geographicalZoneId,
                    geographicalZone = binding.tvMainAbioticZone.text.toString(),
                    typeOfWaterId = typeOfWaterId,
                    typeOfWater = binding.tvMainAbioticWater.text.toString(),
                    initialValue = binding.tvMainAbioticInitial.text.toString().toDouble(),
                    finalValue = binding.tvMainAbioticFinal.text.toString().toDouble(),
                    weight = binding.tvMainAbioticWeight.text.toString().toDouble()
                )
                intentEditMainAbiotic.putExtra(EXTRA_MAIN, mainAbiotic)
                startActivity(intentEditMainAbiotic)
            }

            R.id.btn_main_abiotic_delete -> {
                val mDeleteMainFragment = DeleteMainAbioticFragment()
                val mFragmentManager = this.supportFragmentManager
                mDeleteMainFragment.show(
                    mFragmentManager,
                    DeleteMainAbioticFragment::class.java.simpleName
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isUpdate) {
            DataWeightFragment.isUpdateItem = true
            initMainAbiotic()
            isUpdate = false
        }
    }

    companion object {
        const val EXTRA_MAIN = "extra_main"
        var isUpdate = false
    }
}