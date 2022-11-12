package rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action.delete

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.ui.menu.admin.datapayment.action.PaymentActionActivity

class DeletePaymentFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.confirm_delete_payment)
                .setPositiveButton(
                    R.string.delete
                ) { _, _ ->
                    (activity as PaymentActionActivity).deletePayment()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}