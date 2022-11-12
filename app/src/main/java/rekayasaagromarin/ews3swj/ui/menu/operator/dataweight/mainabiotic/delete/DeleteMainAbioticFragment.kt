package rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.delete

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.ui.menu.operator.dataweight.mainabiotic.MainAbioticActionActivity

class DeleteMainAbioticFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.confirm_delete_main_abiotic)
                .setPositiveButton(
                    R.string.delete
                ) { _, _ ->
                    (activity as MainAbioticActionActivity).deleteMainAbiotic()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}