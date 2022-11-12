package rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityEditFamilyBioticBinding
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.DataFamilyFragment

class EditFamilyBioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditFamilyBioticBinding
    private val editFamilyBioticViewModel: EditFamilyBioticViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFamilyBioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initEditField()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionEditFamilyToolbar.viewToolbar) {
            title = getString(R.string.edit_family_biotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initEditField() {
        val familyData = intent.getParcelableExtra<FamilyBiotic>(DataFamilyFragment.EXTRA_FAMILY)
        with(binding) {
            edtEditFamilyFamily.setText(familyData?.family.toString())
            edtEditFamilyWeight.setText(familyData?.weight.toString())
        }
    }

    private fun actionButton() {
        editFamilyBiotic()
        backButton()
    }

    private fun editFamilyBiotic() {
        val familyData = intent.getParcelableExtra<FamilyBiotic>(DataFamilyFragment.EXTRA_FAMILY)
        binding.btnEditFamilyEdit.setOnClickListener {
            binding.apply {
                when {

                    edtEditFamilyFamily.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Family is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditFamilyWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditFamilyFamily.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditFamilyWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val familyBiotic = FamilyBiotic(
                            id = familyData!!.id,
                            family = edtEditFamilyFamily.text.toString(),
                            weight = edtEditFamilyWeight.text.toString().toDouble()
                        )

                        editFamilyBioticViewModel.editFamilyBiotic(familyBiotic)
                    }
                }
            }
        }

        with(editFamilyBioticViewModel) {
            isLoading.observe(this@EditFamilyBioticActivity) {
                showLoading(it)
            }

            message.observe(this@EditFamilyBioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@EditFamilyBioticActivity) {
                if (it == 200) {
                    DataFamilyFragment.isUpdateItem = true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnEditFamilyCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingEditFamily.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}