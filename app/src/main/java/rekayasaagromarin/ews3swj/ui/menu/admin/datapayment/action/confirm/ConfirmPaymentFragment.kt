package rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action.confirm

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action.PaymentActionActivity

class ConfirmPaymentFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val isConfirmed = (activity as PaymentActionActivity).isConfirmed()
            val stringConfirmed =
                if (isConfirmed) getString(R.string.unconfrim) else getString(R.string.confirm)
            val builder = AlertDialog.Builder(it)

            builder.setMessage(getString(R.string.confirm_confirm_payment, stringConfirmed))
                .setPositiveButton(
                    if (isConfirmed) R.string.unconfrim else R.string.confirm
                ) { _, _ ->
                    if (isConfirmed)
                        (activity as PaymentActionActivity).unconfirmPayment()
                    else
                        (activity as PaymentActionActivity).confirmPayment()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}