package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.delete

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.additionalabiotic.AdditionalAbioticActionActivity

class DeleteAdditionalAbioticFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.confirm_delete_additional_abiotic)
                .setPositiveButton(
                    R.string.delete
                ) { _, _ ->
                    (activity as AdditionalAbioticActionActivity).deleteAdditionalAbiotic()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}