package rekayasaagromarin.ews3swj.ui.menu.admin.datapayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.content.Intent
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.adapter.PaymentAdapter
import rekayasaagromarin.ews3swj.databinding.FragmentDataPaymentBinding
import rekayasaagromarin.ews3swj.model.Payment
import rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action.PaymentActionActivity

class DataPaymentFragment : Fragment() {

    private val dataPaymentViewModel: DataPaymentViewModel by viewModels()
    private var _binding: FragmentDataPaymentBinding? = null
    private val binding get() = _binding!!
    private val paymentAdapter: PaymentAdapter by lazy { PaymentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListPayment()
        showListPayment()
    }

    private fun setListPayment() {
        with(dataPaymentViewModel) {
            setListPayment()

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

    private fun showListPayment() {
        dataPaymentViewModel.getListUser().observe(viewLifecycleOwner) { listPayment ->
            listPayment?.let {
                if (listPayment.isNotEmpty()) {
                    paymentAdapter.setPayment(listPayment)
                    showNoResult(false)
                } else {
                    showNoResult(true)
                }
            }

            binding.rvPayment.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = paymentAdapter
            }
        }

        paymentAdapter.setOnItemClickCallback(object : PaymentAdapter.OnItemClickCallback {
            override fun onItemClicked(payment: Payment) {
                val intentPayment = Intent(context, PaymentActionActivity::class.java)
                intentPayment.putExtra(EXTRA_ID, payment.id)
                startActivity(intentPayment)
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingPayment.visibility = View.VISIBLE
                rvPayment.visibility = View.GONE
            } else {
                loadingPayment.visibility = View.GONE
                rvPayment.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                rvPayment.visibility = View.GONE
                tvPaymentError.visibility = View.VISIBLE
                loadingPayment.visibility = View.GONE
            } else {
                tvPaymentError.visibility = View.GONE
            }
        }
    }

    private fun showNoResult(isNoResult: Boolean) {
        with(binding) {
            if (isNoResult) {
                tvPaymentNoResult.visibility = View.VISIBLE
                rvPayment.visibility = View.GONE
            } else {
                tvPaymentNoResult.visibility = View.GONE
                rvPayment.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isUpdateItem) {
            setListPayment()
            showListPayment()
            isUpdateItem = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        var isUpdateItem = false
    }

}