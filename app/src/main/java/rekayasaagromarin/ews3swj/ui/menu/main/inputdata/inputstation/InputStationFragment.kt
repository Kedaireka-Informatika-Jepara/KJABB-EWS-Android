package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputstation

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
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.FragmentInputStationBinding
import rekayasaagromarin.ews3swj.model.Station
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam.InputMainParamBioticActivity
import rekayasaagromarin.ews3swj.ui.parameter.AddParameterActivity

class InputStationFragment : Fragment() {

    private var _binding: FragmentInputStationBinding? = null
    private val binding get() = _binding!!
    private val inputStationViewModel: InputStationViewModel by viewModels()
    private var geographicalZone = ""
    private var typeOfWater = ""
    private var geographicalZoneId = 0
    private var typeOfWaterId = 0
    private val preferencesManager: PreferencesManager by lazy { PreferencesManager(context) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputStationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnInputStationProceed.setOnClickListener { proceed() }
        nextInput()
        actionButton()
    }

    override fun onStart() {
        super.onStart()

        initGeographicalZone()
        initTypeOfWater()
    }

    private fun nextInput() {

        inputStationViewModel.isAvailableStationId.observe(viewLifecycleOwner) {
            if (it !== null && it.status == 1) {
                val station = Station(
                    stationId = binding.edtInputStationId.text.toString(),
                    typeOfWater = typeOfWater,
                    typeOfWaterId = typeOfWaterId,
                    geographicalZone = geographicalZone,
                    geographicalZoneId = geographicalZoneId,
                    userId = preferencesManager.getId()!!
                )

                val intentMainParamBiotic =
                    Intent(requireContext(), InputMainParamBioticActivity::class.java)
                intentMainParamBiotic.putExtra(EXTRA_STATION, station)

                Toast.makeText(
                    requireContext(),
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(intentMainParamBiotic)
            } else {
                Toast.makeText(
                    requireContext(),
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun actionButton(){
        addParameterGeographicalZone()
        addParameterTypeOfWater()
    }

    private fun addParameterGeographicalZone() {
        binding.btnAddParameterGeographicalZone.setOnClickListener {
            val intent = Intent(activity, AddParameterActivity::class.java).apply {
                putExtra(AddParameterActivity.EXTRA_PARAMETER, 6)
            }
            startActivity(intent)
        }
    }

    private fun addParameterTypeOfWater() {
        binding.btnAddParameterTypeOfWater.setOnClickListener {
            val intent = Intent(activity, AddParameterActivity::class.java).apply {
                putExtra(AddParameterActivity.EXTRA_PARAMETER, 7)
            }
            startActivity(intent)
        }
    }

    private fun initGeographicalZone() {
        val listZone = arrayListOf<String>()
        with(inputStationViewModel) {
            setGeographicalZone()
            getGeographicalZone().observe(viewLifecycleOwner) { list ->
                listZone.clear()
                listZone.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_text,
            listZone
        )
        (binding.tilInputStationZone.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownInputStationZone.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                geographicalZone = parent.getItemAtPosition(position).toString()
                geographicalZoneId = position + 1
            }

        with(inputStationViewModel) {
            message.observe(viewLifecycleOwner) {
                if (it != null && it != "") {
                    showMessage(it)
                }
            }

            isError.observe(viewLifecycleOwner) {
                showError(it)
            }

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

    }

    private fun initTypeOfWater() {
        val listWater = arrayListOf<String>()
        with(inputStationViewModel) {
            setTypeOfWater()
            getTypeOfWater().observe(viewLifecycleOwner) { list ->
                listWater.clear()
                listWater.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_text,
            listWater
        )
        (binding.tilInputStationWater.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownInputStationWater.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                typeOfWater = parent.getItemAtPosition(position).toString()
                typeOfWaterId = position + 1
            }
    }

    private fun proceed() {

        binding.apply {
            when {
                geographicalZoneId == 0 || dropdownInputStationZone.text.toString() == "" -> {
                    Toast.makeText(
                        requireContext(),
                        "Geographical zone is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                typeOfWaterId == 0 || dropdownInputStationWater.text.toString() == "" -> {
                    Toast.makeText(
                        requireContext(),
                        "Type of water is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                edtInputStationId.text?.isEmpty() == true -> {
                    Toast.makeText(
                        requireContext(),
                        "Station ID is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    inputStationViewModel.stationCheck(binding.edtInputStationId.text.toString())
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutInputStation.visibility = View.GONE
            binding.loadingInputStation.root.visibility = View.VISIBLE
        } else {
            binding.loadingInputStation.root.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.layoutInputStation.visibility = View.GONE
            binding.tvStationError.visibility = View.VISIBLE
        } else {
            binding.layoutInputStation.visibility = View.VISIBLE
            binding.tvStationError.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFinish) {
            with(binding) {
                dropdownInputStationZone.setText("")
                dropdownInputStationWater.setText("")
                edtInputStationId.setText("")
            }
            isFinish = false
        }
    }

    override fun onStop() {
        super.onStop()

        with(binding) {
            dropdownInputStationZone.setText("")
            dropdownInputStationWater.setText("")
            edtInputStationId.setText("")
        }
    }

    companion object {
        const val EXTRA_STATION = "extra_station"
        var isFinish = false
        var isUpdateItem = false
    }

}