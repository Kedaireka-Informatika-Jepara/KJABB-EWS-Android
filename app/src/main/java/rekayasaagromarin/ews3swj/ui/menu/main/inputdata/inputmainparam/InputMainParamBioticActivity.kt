package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.adapter.SpeciesAdapter
import rekayasaagromarin.ews3swj.databinding.ActivityInputMainParamBioticBinding
import rekayasaagromarin.ews3swj.model.Species
import rekayasaagromarin.ews3swj.model.Station
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation.InputStationFragment
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.edit.EditResultHistoryActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.delete.DeleteFamilyBioticFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.datastation.detail.DetailStationViewModel

class InputMainParamBioticActivity : AppCompatActivity() {

    private val detailStationViewModel: DetailStationViewModel by viewModels()
    private lateinit var binding: ActivityInputMainParamBioticBinding
    private val speciesAdapter: SpeciesAdapter by lazy { SpeciesAdapter(true) }
    private lateinit var station: Station

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputMainParamBioticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initStation()
        initListSpecies()
        showList()

        binding.btnMainBioticAdd.setOnClickListener { addBiotic() }
        binding.btnMainBioticNext.setOnClickListener { next() }
    }

    private fun initToolbar() {
        with(binding.inputMainBioticToolbar.viewToolbar) {
            title = getString(R.string.input_main_biotic)
            setNavigationOnClickListener {
                onBackPressed()
                listSpecies.clear()
            }
        }
    }

    private fun initStation() {
        if (EditResultHistoryActivity.isEdit) {
            station = intent.getParcelableExtra(EditResultHistoryActivity.EXTRA_EDIT_STATION)!!
            with(detailStationViewModel) {
                setDataSpecies(station.stationId)

                isLoading.observe(this@InputMainParamBioticActivity) {
                    showLoading(it)
                }

                message.observe(this@InputMainParamBioticActivity) {
                    if (it != "" && it != null) {
                        showMessage(it)
                    }
                }

                isError.observe(this@InputMainParamBioticActivity) {
                    showError(it)
                }
            }
        } else {
            station = intent.getParcelableExtra(InputStationFragment.EXTRA_STATION)!!
        }

    }

    private fun initListSpecies() {
        detailStationViewModel.getListSpecies().observe(this) {
            listSpecies.clear()
            listSpecies.addAll(it)
            showList()
        }
    }

    private fun showList() {
        if (listSpecies.isNotEmpty()) {
            with(binding) {
                rvDataSpecies.visibility = View.VISIBLE
                tvClickToAdd.visibility = View.GONE
            }
            speciesAdapter.setSpecies(listSpecies)
        } else {
            with(binding) {
                rvDataSpecies.visibility = View.GONE
                tvClickToAdd.visibility = View.VISIBLE
            }
        }

        with(binding.rvDataSpecies) {
            layoutManager = LinearLayoutManager(context)
            adapter = speciesAdapter
        }

        speciesAdapter.setOnActionClickCallback(object : SpeciesAdapter.OnActionClickCallback {
            override fun deleteDataOnClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt(EXTRA_POSITION, position)

                val mDeleteMainParamBiotic = DeleteMainParamBioticFragment()
                mDeleteMainParamBiotic.arguments = bundle

                val mFragmentManager = supportFragmentManager
                mDeleteMainParamBiotic.show(
                    mFragmentManager,
                    DeleteFamilyBioticFragment::class.java.simpleName
                )
            }

        })
    }

    private fun addBiotic() {
        val intentAddBiotic = Intent(this, AddMainParamBioticActivity::class.java)
        intentAddBiotic.putExtra(InputStationFragment.EXTRA_STATION, station)
        startActivity(intentAddBiotic)
    }

    fun deleteBiotic(position: Int) {
        listSpecies.removeAt(position)
        showList()
    }

    private fun next() {
        if (listSpecies.isEmpty()) {
            Toast.makeText(this, "Biotic data is required", Toast.LENGTH_SHORT).show()
        } else {
            val intentInputMainAbiotic = Intent(this, InputMainParamAbioticActivity::class.java)

            with(intentInputMainAbiotic) {
                putExtra(EXTRA_SPECIES, listSpecies)
                putExtra(InputStationFragment.EXTRA_STATION, station)
            }

            startActivity(intentInputMainAbiotic)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingInputMainBiotic.root.visibility = View.VISIBLE
                layoutInputMainBiotic.visibility = View.GONE
            } else {
                loadingInputMainBiotic.root.visibility = View.GONE
                layoutInputMainBiotic.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                layoutInputMainBiotic.visibility = View.GONE
                tvInputMainBioticError.visibility = View.VISIBLE
                loadingInputMainBiotic.root.visibility = View.GONE
            } else {
                tvInputMainBioticError.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isUpdateItem) {
            showList()
            isUpdateItem = false
        }

        if (isFinish) {

            if(EditResultHistoryActivity.isEdit){
                EditResultHistoryActivity.isFinish = true
            }else{
                InputStationFragment.isFinish = true
            }

            isFinish = false
            listSpecies.clear()
            finish()
        }

    }

    companion object {
        val listSpecies = ArrayList<Species>()
        var isUpdateItem = false
        var isFinish = false
        const val EXTRA_POSITION = "extra_position"
        const val EXTRA_SPECIES = "extra_species"
    }
}