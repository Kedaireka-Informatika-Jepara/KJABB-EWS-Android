package rekayasaagromarin.ews3swj.ui.parameter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityAddParameterBinding
import rekayasaagromarin.ews3swj.model.GeographicalZone
import rekayasaagromarin.ews3swj.model.Parameter
import rekayasaagromarin.ews3swj.model.TypeOfWater
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.DataWeightFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.add.AddAdditionalAbioticActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.add.AddIndexBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.add.AddMainAbioticActivity

class AddParameterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddParameterBinding
    private val addParameterViewModel: AddParameterViewModel by viewModels()
    private var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddParameterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.actionAddParameterToolbar.viewToolbar)

        type = intent.getIntExtra(EXTRA_PARAMETER, 0)

        binding.tvCekIntent.text = type.toString()

        initToolbar()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionAddParameterToolbar.viewToolbar) {
            title = getString(R.string.add_index_biotic)
            setNavigationOnClickListener {
                onBackPressed()
                finish()
            }
        }
    }

    private fun actionButton() {
        addParameter()
        backButton()
    }

    private fun addParameter() {
        binding.btnAddParameterAdd.setOnClickListener {
            binding.apply {
                when {
                    etAddParameterName.text.isNullOrEmpty() -> {
                        Toast.makeText(this@AddParameterActivity, "Nama parameter tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }
                    etAddParameterDescription.text.isNullOrEmpty() -> {
                        Toast.makeText(this@AddParameterActivity, "Deskripsi parameter tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        val geoIndex = listOf(3,6)
                        val waterIndex = listOf(4,7)
                        if (geoIndex.contains(type)){
                            Toast.makeText(this@AddParameterActivity, "geographical zone", Toast.LENGTH_SHORT).show()
                            val geographicalZone = GeographicalZone(
                                zone = etAddParameterName.text.toString()
                            )
                            addParameterViewModel.addGeographicalZone(geographicalZone)
                        }else if (waterIndex.contains(type)){
                            Toast.makeText(this@AddParameterActivity, "water index", Toast.LENGTH_SHORT).show()
                            val typeOfWater = TypeOfWater(
                                water = etAddParameterName.text.toString()
                            )
                            addParameterViewModel.addTypeOfWater(typeOfWater)
                        }
                        else{
                            val parameter = Parameter(
                                name = etAddParameterName.text.toString(),
                                type = type,
                                description = etAddParameterDescription.text.toString(),
                            )
                            addParameterViewModel.addParameter(parameter)
                        }
                    }
                }
            }
        }

        with(addParameterViewModel) {
            isLoading.observe(this@AddParameterActivity){
                showLoading(it)
            }

            message.observe(this@AddParameterActivity) {
                if (it != null ) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@AddParameterActivity) {
                if (it == 200) {
//                    onBackPressed()
                    when(type){
                        1 -> {
                            AddIndexBioticActivity.isUpdateItem = true
                            finish()
                        }
                        2 -> {
                            AddMainAbioticActivity.isUpdateItem = true
                            finish()
                        }
                        3 -> { // geographical zone
                            AddMainAbioticActivity.isUpdateItem = true
                            finish()
                        }
                        4 -> { // type of water
                            AddMainAbioticActivity.isUpdateItem = true
                            finish()
                        }
                        5 -> {
                            AddAdditionalAbioticActivity.isUpdateItem = true
                            finish()
                        }
                        6 -> { // geographical zone
                            InputStationFragment.isUpdateItem = true
                            finish()
                        }
                        7 -> { // type of water
                            InputStationFragment.isUpdateItem = true
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun backButton() {
        binding.btnAddParameterCancel.setOnClickListener {
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

    companion object{
        const val EXTRA_PARAMETER = "extra_parameter"
    }
}