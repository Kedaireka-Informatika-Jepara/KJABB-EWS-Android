package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityIndexBioticActionBinding
import rekayasaagromarin.ews3swj.model.IndexBiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.DataWeightFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.delete.DeleteIndexBioticFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.edit.EditIndexBioticActivity
import java.text.DecimalFormat

class IndexBioticActionActivity : AppCompatActivity(), View.OnClickListener {

    private val indexBioticActionViewModel: IndexBioticActionViewModel by viewModels()
    private lateinit var binding: ActivityIndexBioticActionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndexBioticActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initIndexBiotic()
        actionButton()
    }

    private fun initIndexBiotic() {
        setIndexBiotic()
        getIndexBiotic()
    }

    private fun initToolbar() {
        with(binding.indexBioticToolbar.viewToolbar) {
            title = getString(R.string.menu_data_weight)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }


    private fun actionButton() {
        with(binding) {
            btnIndexBioticEdit.setOnClickListener(this@IndexBioticActionActivity)
            btnIndexBioticDelete.setOnClickListener(this@IndexBioticActionActivity)
        }
    }

    private fun setIndexBiotic() {
        with(indexBioticActionViewModel) {
            setIndexBiotic(intent.getIntExtra(DataWeightFragment.EXTRA_ID, 0))

            isLoading.observe(this@IndexBioticActionActivity) {
                showLoading(it)
            }

            message.observe(this@IndexBioticActionActivity) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(this@IndexBioticActionActivity) {
                showError(it)
            }
        }
    }

    private fun getIndexBiotic() {
        indexBioticActionViewModel.getIndexBiotic().observe(this) { indexBiotic ->
            if (indexBiotic != IndexBiotic()) {
                with(binding) {
                    tvIndexBioticId.text = indexBiotic.id.toString()
                    tvIndexBioticName.text = indexBiotic.name
                    tvIndexBioticInitial.text = convertDecimal(indexBiotic.initialValue)
                    tvIndexBioticFinal.text = convertDecimal(indexBiotic.finalValue)
                    tvIndexBioticWeight.text = convertDecimal(indexBiotic.weight)
                }
            } else {
                finish()
                DataWeightFragment.isUpdateItem = true
                Toast.makeText(this, "Data index biotic tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteIndexBiotic() {
        with(indexBioticActionViewModel) {
            deleteIndexBiotic(intent.getIntExtra(DataWeightFragment.EXTRA_ID, 0))

            isLoading.observe(this@IndexBioticActionActivity) {
                showLoading(it)
            }

            deleteMessage.observe(this@IndexBioticActionActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isDelete.observe(this@IndexBioticActionActivity) {
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
                cardIndexInfo.visibility = View.GONE
                cardIndexAction.visibility = View.GONE
                loadingIndexBiotic.visibility = View.VISIBLE
            } else {
                loadingIndexBiotic.visibility = View.GONE
            }
        }
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                loadingIndexBiotic.visibility = View.GONE
                cardIndexInfo.visibility = View.GONE
                cardIndexAction.visibility = View.GONE
                tvIndexBioticError.visibility = View.VISIBLE
            } else {
                cardIndexInfo.visibility = View.VISIBLE
                cardIndexAction.visibility = View.VISIBLE
                tvIndexBioticError.visibility = View.GONE
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
            R.id.btn_index_biotic_edit -> {
                val intentEditIndexBiotic = Intent(this, EditIndexBioticActivity::class.java)
                val indexBiotic = IndexBiotic(
                    id = binding.tvIndexBioticId.text.toString().toInt(),
                    name = binding.tvIndexBioticName.text.toString(),
                    initialValue = binding.tvIndexBioticInitial.text.toString().toDouble(),
                    finalValue = binding.tvIndexBioticFinal.text.toString().toDouble(),
                    weight = binding.tvIndexBioticWeight.text.toString().toDouble()
                )
                intentEditIndexBiotic.putExtra(EXTRA_INDEX, indexBiotic)
                startActivity(intentEditIndexBiotic)
            }

            R.id.btn_index_biotic_delete -> {
                val mDeleteIndexFragment = DeleteIndexBioticFragment()
                val mFragmentManager = this.supportFragmentManager
                mDeleteIndexFragment.show(
                    mFragmentManager,
                    DeleteIndexBioticFragment::class.java.simpleName
                )
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (isUpdate) {
            DataWeightFragment.isUpdateItem = true
            initIndexBiotic()
            isUpdate = false
        }
    }

    companion object {
        const val EXTRA_INDEX = "extra_index"
        var isUpdate = false
    }
}