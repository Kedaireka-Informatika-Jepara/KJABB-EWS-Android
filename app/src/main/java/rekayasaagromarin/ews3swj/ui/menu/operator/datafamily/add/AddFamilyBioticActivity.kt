package rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityAddFamilyBioticBinding
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.DataFamilyFragment

class AddFamilyBioticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFamilyBioticBinding
    private val addFamilyBioticViewModel: AddFamilyBioticViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFamilyBioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        actionButton()
    }

    private fun initToolbar() {
        with(binding.actionAddFamilyToolbar.viewToolbar) {
            title = getString(R.string.add_family_biotic)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun actionButton() {
        addFamilyBiotic()
        backButton()
    }


    private fun addFamilyBiotic() {
        binding.btnAddFamilyAdd.setOnClickListener {
            binding.apply {
                when {

                    edtAddFamilyFamily.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Family is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddFamilyWeight.text?.isEmpty() == true -> {
                        Toast.makeText(
                            baseContext,
                            "Weight is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddFamilyFamily.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtAddFamilyWeight.text.toString() == "." -> {
                        Toast.makeText(
                            baseContext,
                            "Fill value with numbers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        val familyBiotic = FamilyBiotic(
                            family = edtAddFamilyFamily.text.toString(),
                            weight = edtAddFamilyWeight.text.toString().toDouble()
                        )

                        addFamilyBioticViewModel.addFamilyBiotic(familyBiotic)
                    }
                }
            }
        }

        with(addFamilyBioticViewModel) {
            isLoading.observe(this@AddFamilyBioticActivity) {
                showLoading(it)
            }

            message.observe(this@AddFamilyBioticActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isSuccess.observe(this@AddFamilyBioticActivity) {
                if (it == 200) {
                    DataFamilyFragment.isUpdateItem = true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnAddFamilyCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingAddFamily.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}