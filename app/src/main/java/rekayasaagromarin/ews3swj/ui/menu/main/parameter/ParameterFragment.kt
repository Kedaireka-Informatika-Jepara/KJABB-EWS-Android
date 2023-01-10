package rekayasaagromarin.ews3swj.ui.menu.main.parameter

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
import rekayasaagromarin.ews3swj.adapter.ParameterAdapter
import rekayasaagromarin.ews3swj.databinding.FragmentParameterBinding
import rekayasaagromarin.ews3swj.model.Parameter
import rekayasaagromarin.ews3swj.ui.parameter.AddParameterActivity
import rekayasaagromarin.ews3swj.ui.parameter.EditParameterActivity

class ParameterFragment : Fragment() {
    private val parameterViewModel: ParameterViewModel by viewModels()
    private var _binding: FragmentParameterBinding? = null
    private val binding get() = _binding!!
    private var listParameter = ArrayList<Parameter>()
    private var typeParameter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParameterBinding.inflate(inflater, container, false)

        binding.fabAdd.setOnClickListener{
            val intent = Intent(context, AddParameterActivity::class.java)
            intent.putExtra(AddParameterActivity.EXTRA_TYPE, typeParameter)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initParameter()
        initListParameter()
    }

    private fun initParameter() {
        parameterViewModel.setListParameter()
        parameterViewModel.getListParameter().observe(viewLifecycleOwner) { list ->
            listParameter.clear()
            listParameter.addAll(list)
        }
    }

    private fun initListParameter() {
        val parameterName = listOf(
            "Indeks Biotik",
            "Indeks Abiotik Utama",
            "Tipe Air",
            "Zona Geografik",
            "Indeks Abiotik Tambahan"
        )

        val spinnerArrayAdapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.item_text,
                parameterName
            )
        }

        (binding.tilParameter.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownParameter.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                binding.fabAdd.visibility = View.VISIBLE
                showLoading(true)

                typeParameter = position + 1
                val parameter = ArrayList<Parameter>()
                listParameter.forEach {
                    if (it.type == typeParameter) {
                        parameter.add(it)
                    }
                }

                val parameterAdapter = ParameterAdapter()
                parameterAdapter.setListParameter(parameter)
                parameterAdapter.setOnItemClickCallback(object :
                    ParameterAdapter.OnItemClickCallback {
                    override fun onItemClicked(parameter: Parameter) {
                        Toast.makeText(context, "Edit ${parameter.name}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, EditParameterActivity::class.java)
                        intent.putExtra(EditParameterActivity.EXTRA_PARAMETER, parameter)
                        startActivity(intent)
                    }
                }, object : ParameterAdapter.OnItemClickCallback {
                    override fun onItemClicked(parameter: Parameter) {
                        showLoading(true)
                        parameterViewModel.deleteParameter(parameter.id)
                        parameterViewModel.isDeleted.observe(viewLifecycleOwner){
                            if (it) {
                                initParameter()
                                initListParameter()
                                showLoading(false)
                                Toast.makeText(context, "Berhasil menghapus parameter", Toast.LENGTH_SHORT).show()
                            } else {
                                showLoading(false)
                                Toast.makeText(context, "Gagal menghapus parameter", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
                showLoading(false)
                binding.rvParameter.layoutManager = LinearLayoutManager(context)
                binding.rvParameter.adapter = parameterAdapter
            }
    }

    override fun onPause() {
        super.onPause()

        binding.tilParameter.editText?.setText("")
        binding.dropdownParameter.setText("")
        binding.rvParameter.adapter = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingParameter.root.visibility = View.VISIBLE
        } else {
            binding.loadingParameter.root.visibility = View.GONE
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        if (isUpdate){
            initParameter()
            initListParameter()
            isUpdate = false
        }
    }

    companion object {
        const val EXTRA_PARAMETER = "extra_parameter"
        var isUpdate = false
    }
}