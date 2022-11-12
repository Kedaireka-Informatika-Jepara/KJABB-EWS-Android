package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.adapter.WeightAdapter
import rekayasaagromarin.ews3swj.databinding.FragmentDataWeightBinding
import rekayasaagromarin.ews3swj.model.ParameterResponse
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.AdditionalAbioticActionActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.add.AddAdditionalAbioticActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.IndexBioticActionActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.indexbiotic.add.AddIndexBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.MainAbioticActionActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.add.AddMainAbioticActivity

class DataWeightFragment : Fragment() {

    private val dataWeightViewModel: DataWeightViewModel by viewModels()
    private var _binding: FragmentDataWeightBinding? = null
    private val binding get() = _binding!!
    private val weightAdapter: WeightAdapter by lazy { WeightAdapter() }
    private var param: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataWeightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTypeOfParameter()
        initAddWeight()
    }

    private fun initTypeOfParameter() {
        val spinnerArrayAdapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.type_param,
                R.layout.item_text,
            )
        }

        (binding.tilTypeOfParam.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownTypeOfParam.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                if (param != position) {
                    when (position) {
                        0 -> {
                            param = position
                            setIndexBiotic()
                            showListWeight()
                        }

                        1 -> {
                            param = position
                            setMainBiotic()
                            showListWeight()
                        }

                        2 -> {
                            param = position
                            setAdditionalAbiotic()
                            showListWeight()
                        }
                    }
                }
            }
    }

    private fun setIndexBiotic() {
        with(dataWeightViewModel) {
            setIndexBiotic()

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            message.observe(viewLifecycleOwner) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(viewLifecycleOwner) {
                showError(it)
            }
        }
    }

    private fun setAdditionalAbiotic() {
        with(dataWeightViewModel) {
            setAdditionalAbiotic()

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            message.observe(viewLifecycleOwner) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(viewLifecycleOwner) {
                showError(it)
            }
        }
    }

    private fun setMainBiotic() {
        with(dataWeightViewModel) {
            setMainBiotic()

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            message.observe(viewLifecycleOwner) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(viewLifecycleOwner) {
                showError(it)
            }
        }
    }

    private fun showListWeight() {
        dataWeightViewModel.getListIndexBiotic().observe(viewLifecycleOwner) { listWeight ->
            listWeight?.let {
                if (listWeight.isNotEmpty()) {
                    weightAdapter.setWeight(listWeight)
                    showNoResult(false)
                } else {
                    showNoResult(true)
                }
            }

            binding.rvDataWeight.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = weightAdapter
            }
        }

        weightAdapter.setOnItemClickCallback(object : WeightAdapter.OnItemClickCallback {
            override fun onItemClicked(response: ParameterResponse) {
                when (this@DataWeightFragment.param) {
                    0 -> {
                        val intentIndexBiotic =
                            Intent(context, IndexBioticActionActivity::class.java)
                        intentIndexBiotic.putExtra(EXTRA_ID, response.id)
                        startActivity(intentIndexBiotic)
                    }

                    1 -> {
                        val intentMainAbiotic =
                            Intent(context, MainAbioticActionActivity::class.java)
                        intentMainAbiotic.putExtra(EXTRA_ID, response.id)
                        startActivity(intentMainAbiotic)
                    }

                    2 -> {
                        val intentAdditionalAbiotic =
                            Intent(context, AdditionalAbioticActionActivity::class.java)
                        intentAdditionalAbiotic.putExtra(EXTRA_ID, response.id)
                        startActivity(intentAdditionalAbiotic)
                    }
                }
            }

        })
    }

    private fun initAddWeight() {
        binding.actionAddWeight.setOnClickListener {
            when (param) {
                0 -> {
                    val intentAddIndexBiotic = Intent(context, AddIndexBioticActivity::class.java)
                    startActivity(intentAddIndexBiotic)
                }

                1 -> {
                    val intentAddMainAbiotic = Intent(context, AddMainAbioticActivity::class.java)
                    startActivity(intentAddMainAbiotic)
                }

                2 -> {
                    val intentAddAdditionalAbiotic =
                        Intent(context, AddAdditionalAbioticActivity::class.java)
                    startActivity(intentAddAdditionalAbiotic)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingDataWeight.visibility = View.VISIBLE
                rvDataWeight.visibility = View.GONE
            } else {
                loadingDataWeight.visibility = View.GONE
                rvDataWeight.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                rvDataWeight.visibility = View.GONE
                tvDataWeightError.visibility = View.VISIBLE
                loadingDataWeight.visibility = View.GONE
            } else {
                tvDataWeightError.visibility = View.GONE
            }
        }
    }

    private fun showNoResult(isNoResult: Boolean) {
        with(binding) {
            if (isNoResult) {
                tvDataWeightNoResult.visibility = View.VISIBLE
                rvDataWeight.visibility = View.GONE
            } else {
                tvDataWeightNoResult.visibility = View.GONE
                rvDataWeight.visibility = View.VISIBLE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (isUpdateItem) {
            when (param) {
                0 -> {
                    setIndexBiotic()
                    showListWeight()
                }

                1 -> {
                    setMainBiotic()
                    showListWeight()
                }

                2 -> {
                    setAdditionalAbiotic()
                    showListWeight()
                }
            }
            isUpdateItem = false
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        var isUpdateItem = false
    }
}