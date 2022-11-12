package rekayasaagromarin.ews3swj.ui.menu.operator.datafamily

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.adapter.FamilyAdapter
import rekayasaagromarin.ews3swj.databinding.FragmentDataFamilyBinding
import rekayasaagromarin.ews3swj.model.FamilyBiotic
import rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.add.AddFamilyBioticActivity
import rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.delete.DeleteFamilyBioticFragment
import rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.edit.EditFamilyBioticActivity

class DataFamilyFragment : Fragment() {

    private val dataFamilyViewModel: DataFamilyViewModel by viewModels()
    private var _binding: FragmentDataFamilyBinding? = null
    private val binding get() = _binding!!
    private val familyAdapter: FamilyAdapter by lazy { FamilyAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataFamilyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddFamily()
        setListFamilyBiotic()
        showListFamily()
    }

    private fun initAddFamily() {
        binding.actionAddFamily.setOnClickListener {
            val intentAddUser = Intent(context, AddFamilyBioticActivity::class.java)
            startActivity(intentAddUser)
        }
    }

    private fun setListFamilyBiotic() {
        with(dataFamilyViewModel) {
            setFamilyBiotic()

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

    private fun showListFamily() {
        dataFamilyViewModel.getListFamilyBiotic().observe(viewLifecycleOwner) { listFamily ->
            listFamily?.let {
                if (listFamily.isNotEmpty()) {
                    familyAdapter.setFamily(listFamily)
                }
            }
            binding.rvDataFamily.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = familyAdapter
            }
        }

        familyAdapter.setOnActionClickCallback(object : FamilyAdapter.OnActionClickCallback {
            override fun editFamilyOnClick(familyBiotic: FamilyBiotic) {
                val intentEditFamilyBiotic = Intent(context, EditFamilyBioticActivity::class.java)
                intentEditFamilyBiotic.putExtra(EXTRA_FAMILY, familyBiotic)
                startActivity(intentEditFamilyBiotic)
            }

            override fun deleteFamilyOnClick(familyBiotic: FamilyBiotic) {
                val bundle = Bundle()
                bundle.putInt(EXTRA_ID, familyBiotic.id)

                val mDeleteFamilyFragment = DeleteFamilyBioticFragment()
                mDeleteFamilyFragment.arguments = bundle

                val mFragmentManager = childFragmentManager
                mDeleteFamilyFragment.show(
                    mFragmentManager,
                    DeleteFamilyBioticFragment::class.java.simpleName
                )
            }

        })
    }

    fun deleteFamilyBiotic(id: Int) {
        with(dataFamilyViewModel) {
            deleteFamilyBiotic(id)

            isLoading.observe(this@DataFamilyFragment) {
                showLoading(it)
            }

            deleteMessage.observe(this@DataFamilyFragment) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isDelete.observe(this@DataFamilyFragment) {
                if (it) {
                    setListFamilyBiotic()
                    showListFamily()
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingDataFamily.visibility = View.VISIBLE
                rvDataFamily.visibility = View.GONE
            } else {
                loadingDataFamily.visibility = View.GONE
                rvDataFamily.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                rvDataFamily.visibility = View.GONE
                tvDataFamilyError.visibility = View.VISIBLE
                loadingDataFamily.visibility = View.GONE
            } else {
                tvDataFamilyError.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isUpdateItem) {
            setListFamilyBiotic()
            showListFamily()
            isUpdateItem = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_FAMILY = "extra_family"
        const val EXTRA_ID = "extra_id"
        var isUpdateItem = false
    }
}