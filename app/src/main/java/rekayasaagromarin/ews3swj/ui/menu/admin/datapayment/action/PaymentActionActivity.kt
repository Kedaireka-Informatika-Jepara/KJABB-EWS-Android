package rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityPaymentActionBinding
import rekayasaagromarin.ews3swj.model.Payment
import rekayasaagromarin.ews3swj.ui.menu.MainActivity
import rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.DataPaymentFragment
import rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action.confirm.ConfirmPaymentFragment
import rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action.delete.DeletePaymentFragment
import java.text.SimpleDateFormat
import java.util.*

class PaymentActionActivity : AppCompatActivity(), View.OnClickListener {

    private val paymentActionViewModel: PaymentActionViewModel by viewModels()
    private lateinit var binding: ActivityPaymentActionBinding
    private var userId = 0
    private var isConfirmed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initPayment()
        actionButton()
        refresh()
    }

    private fun initToolbar() {
        with(binding.actionPaymentToolbar.viewToolbar) {
            title = getString(R.string.menu_data_payment)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initPayment() {
        setPayment()
        getPayment()
    }

    private fun actionButton() {
        with(binding) {
            btnPaymentConfirm.setOnClickListener(this@PaymentActionActivity)
            btnPaymentDelete.setOnClickListener(this@PaymentActionActivity)
        }
    }

    private fun refresh() {
        paymentActionViewModel.unconfirmMessage.observe(this) {
            if (it != null) {
                DataPaymentFragment.isUpdateItem = true
                initPayment()
                showMessage(it)
            }
        }

        paymentActionViewModel.confirmMessage.observe(this) {
            if (it != null) {
                DataPaymentFragment.isUpdateItem = true
                initPayment()
                showMessage(it)
            }
        }
    }

    private fun setPayment() {
        with(paymentActionViewModel) {
            setPayment(intent.getIntExtra(DataPaymentFragment.EXTRA_ID, 0))

            isLoading.observe(this@PaymentActionActivity) {
                showLoading(it)
            }

            message.observe(this@PaymentActionActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isError.observe(this@PaymentActionActivity) {
                showError(it)
            }
        }
    }

    private fun getPayment() {
        paymentActionViewModel.getPayment().observe(this) { payment ->
            if (payment != Payment()) {
                with(binding) {
                    isConfirmed = payment.status != 0

                    this@PaymentActionActivity.userId =
                        intent.getIntExtra(DataPaymentFragment.EXTRA_ID, 0)

                    tvPaymentEmail.text = payment.userEmail

                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    val date = formatter.format(parser.parse(payment.date)!!)
                    tvPaymentDate.text = date

                    tvPaymentStatus.text =
                        if (isConfirmed) getString(R.string.confirmed) else getString(R.string.not_confirmed)

                    tvProof.text = payment.proof
                    btnViewProof.setOnClickListener {
                        val url = "${MainActivity.BASE_URL}api/v1/images/payment/${payment.proof}"

                        val intentViewProof = Intent(Intent.ACTION_VIEW)
                        intentViewProof.data = Uri.parse(url)
                        startActivity(intentViewProof)
                    }

                    setConfirmButton(isConfirmed)
                }
            } else {
                finish()
                DataPaymentFragment.isUpdateItem = true
                Toast.makeText(this, "Data payment tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deletePayment() {
        val imageName = binding.tvProof.text.toString()
        with(paymentActionViewModel) {
            deletePayment(this@PaymentActionActivity.userId, imageName)

            isLoading.observe(this@PaymentActionActivity) {
                showLoading(it)
            }

            deleteMessage.observe(this@PaymentActionActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isDelete.observe(this@PaymentActionActivity) {
                if (it) {
                    DataPaymentFragment.isUpdateItem = true
                    onBackPressed()
                }
            }

        }
    }

    fun confirmPayment() {
        paymentActionViewModel.confirmPayment(this.userId)
    }

    fun unconfirmPayment() {
        paymentActionViewModel.unconfirmPayment(this.userId)
    }

    fun isConfirmed(): Boolean {
        return isConfirmed
    }

    private fun setConfirmButton(isConfirm: Boolean) {
        with(binding) {
            if (isConfirm) {
                tvPaymentStatus.setText(R.string.confirmed)
                btnPaymentConfirm.setText(R.string.unconfrim)
                btnPaymentConfirm.setBackgroundColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.ripe_mango
                    )
                )
            } else {
                tvPaymentStatus.setText(R.string.not_confirmed)
                btnPaymentConfirm.setText(R.string.confirm)
                btnPaymentConfirm.setBackgroundColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.caribbean_green
                    )
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                cardUserInfo.visibility = View.GONE
                cardUserAction.visibility = View.GONE
                loadingPayment.visibility = View.VISIBLE
            } else {
                cardUserInfo.visibility = View.VISIBLE
                cardUserAction.visibility = View.VISIBLE
                loadingPayment.visibility = View.GONE
            }
        }
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                loadingPayment.visibility = View.GONE
                cardUserInfo.visibility = View.GONE
                cardUserAction.visibility = View.GONE
                tvPaymentError.visibility = View.VISIBLE
            } else {
                tvPaymentError.visibility = View.GONE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_payment_confirm -> {
                val mConfirmPaymentFragment = ConfirmPaymentFragment()
                val mFragmentManager = this.supportFragmentManager
                mConfirmPaymentFragment.show(
                    mFragmentManager,
                    ConfirmPaymentFragment::class.java.simpleName
                )
            }

            R.id.btn_payment_delete -> {
                val mDeletePaymentFragment = DeletePaymentFragment()
                val mFragmentManager = this.supportFragmentManager
                mDeletePaymentFragment.show(
                    mFragmentManager,
                    DeletePaymentFragment::class.java.simpleName
                )
            }
        }
    }

}